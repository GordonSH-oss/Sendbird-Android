package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.model.SimpleMessage
import com.sendbird.android.SendbirdChat
import com.sendbird.android.channel.OpenChannel
import com.sendbird.android.handler.OpenChannelHandler
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.params.OpenChannelCreateParams
import com.sendbird.android.params.UserMessageCreateParams
import com.sendbird.android.user.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * ChatRepository 负责与 Sendbird SDK 交互
 * 
 * 知识点:
 * 1. Repository 模式:封装数据操作,提供统一的数据访问接口
 * 2. suspend 函数:协程挂起函数,可以在不阻塞线程的情况下等待结果
 * 3. suspendCancellableCoroutine:将回调式 API 转换为协程风格
 * 4. Flow:Kotlin 的响应式数据流,类似 RxJava 的 Observable
 */
class ChatRepository {
    
    companion object {
        private const val TAG = "ChatRepository"
        private const val CHANNEL_HANDLER_ID = "CHAT_SCREEN_HANDLER"
    }
    
    /**
     * 连接到 Sendbird 服务器
     * 
     * @param userId 用户 ID
     * @return 连接的用户信息
     * 
     * 知识点:
     * suspendCancellableCoroutine 将回调转换为协程:
     * - resume(value):成功时恢复协程并返回值
     * - resumeWithException(exception):失败时抛出异常
     */
    suspend fun connect(userId: String): User = suspendCancellableCoroutine { continuation ->
        SendbirdChat.connect(userId) { user, e ->
            if (e != null) {
                Log.e(TAG, "连接失败: ${e.message}", e)
                continuation.resumeWithException(e)
            } else if (user != null) {
                Log.i(TAG, "连接成功: ${user.userId}")
                continuation.resume(user)
            } else {
                continuation.resumeWithException(Exception("User is null"))
            }
        }
    }
    
    /**
     * 断开连接
     * 
     * 知识点:
     * Sendbird 的 disconnect() 也有回调,但我们不需要等待结果
     */
    fun disconnect() {
        SendbirdChat.disconnect {
            Log.i(TAG, "已断开连接")
        }
    }
    
    /**
     * 创建开放频道
     * 
     * @param channelName 频道名称
     * @return 创建的频道
     * 
     * 知识点:
     * OpenChannel 是开放频道,所有用户都可以进入
     * GroupChannel 是群组频道,需要邀请才能进入
     */
    suspend fun createOpenChannel(channelName: String): OpenChannel = 
        suspendCancellableCoroutine { continuation ->
            val params = OpenChannelCreateParams().apply {
                name = channelName
            }
            
            OpenChannel.createChannel(params) { channel, e ->
                if (e != null) {
                    Log.e(TAG, "创建频道失败: ${e.message}", e)
                    continuation.resumeWithException(e)
                } else if (channel != null) {
                    Log.i(TAG, "频道创建成功: ${channel.name}")
                    continuation.resume(channel)
                } else {
                    continuation.resumeWithException(Exception("Channel is null"))
                }
            }
        }
    
    /**
     * 获取频道(如果不存在则创建)
     * 
     * @param channelUrl 频道 URL
     * @param channelName 频道名称(创建时使用)
     * @return 频道实例
     */
    suspend fun getOrCreateChannel(channelUrl: String?, channelName: String): OpenChannel {
        return if (channelUrl != null) {
            getChannel(channelUrl)
        } else {
            createOpenChannel(channelName)
        }
    }
    
    /**
     * 根据 URL 获取频道
     */
    suspend fun getChannel(channelUrl: String): OpenChannel = 
        suspendCancellableCoroutine { continuation ->
            OpenChannel.getChannel(channelUrl) { channel, e ->
                if (e != null) {
                    continuation.resumeWithException(e)
                } else if (channel != null) {
                    continuation.resume(channel)
                } else {
                    continuation.resumeWithException(Exception("Channel is null"))
                }
            }
        }
    
    /**
     * 进入频道
     * 
     * 知识点:
     * 必须先进入频道才能发送和接收消息
     */
    suspend fun enterChannel(channel: OpenChannel): Unit = 
        suspendCancellableCoroutine { continuation ->
            channel.enter { e ->
                if (e != null) {
                    Log.e(TAG, "进入频道失败: ${e.message}", e)
                    continuation.resumeWithException(e)
                } else {
                    Log.i(TAG, "进入频道成功: ${channel.name}")
                    continuation.resume(Unit)
                }
            }
        }
    
    /**
     * 发送文本消息
     * 
     * @param channel 目标频道
     * @param text 消息内容
     * @return 发送的消息
     */
    suspend fun sendMessage(channel: OpenChannel, text: String): SimpleMessage = 
        suspendCancellableCoroutine { continuation ->
            val params = UserMessageCreateParams(text)
            
            channel.sendUserMessage(params) { message, e ->
                if (e != null) {
                    Log.e(TAG, "发送消息失败: ${e.message}", e)
                    continuation.resumeWithException(e)
                } else if (message != null) {
                    Log.i(TAG, "消息发送成功: ${message.message}")
                    val simpleMessage = message.toSimpleMessage()
                    continuation.resume(simpleMessage)
                } else {
                    continuation.resumeWithException(Exception("Message is null"))
                }
            }
        }
    
    /**
     * 加载历史消息
     * 
     * @param channel 目标频道
     * @param limit 加载数量
     * @return 消息列表
     */
    suspend fun loadMessages(channel: OpenChannel, limit: Int = 50): List<SimpleMessage> = 
        suspendCancellableCoroutine { continuation ->
            val params = com.sendbird.android.params.MessageListParams().apply {
                previousResultSize = limit
                isInclusive = true
            }
            
            channel.getMessagesByTimestamp(Long.MAX_VALUE, params) { messages, e ->
                if (e != null) {
                    Log.e(TAG, "加载消息失败: ${e.message}", e)
                    continuation.resumeWithException(e)
                } else {
                    val simpleMessages = messages?.map { it.toSimpleMessage() } ?: emptyList()
                    Log.i(TAG, "加载了 ${simpleMessages.size} 条消息")
                    continuation.resume(simpleMessages)
                }
            }
        }
    
    /**
     * 监听新消息
     * 
     * @param channelUrl 频道 URL
     * @return Flow 数据流,持续发送新消息
     * 
     * 知识点:
     * 1. callbackFlow:创建一个基于回调的 Flow
     * 2. awaitClose:等待 Flow 被关闭
     * 3. trySend:尝试发送数据到 Flow
     */
    fun observeMessages(channelUrl: String): Flow<SimpleMessage> = callbackFlow {
        val handler = object : OpenChannelHandler() {
            override fun onMessageReceived(channel: com.sendbird.android.channel.BaseChannel, message: BaseMessage) {
                if (channel.url == channelUrl) {
                    Log.i(TAG, "收到新消息: ${message}")
                    val simpleMessage = message.toSimpleMessage()
                    trySend(simpleMessage)
                }
            }
        }
        
        // 注册事件处理器
        SendbirdChat.addChannelHandler(CHANNEL_HANDLER_ID, handler)
        
        // 当 Flow 被关闭时,移除事件处理器
        awaitClose {
            SendbirdChat.removeChannelHandler(CHANNEL_HANDLER_ID)
            Log.i(TAG, "移除消息监听器")
        }
    }
    
    /**
     * 将 Sendbird 的 BaseMessage 转换为简化的 SimpleMessage
     * 
     * 知识点:
     * 扩展函数:为现有类添加新方法,而不需要继承
     */
    private fun BaseMessage.toSimpleMessage(): SimpleMessage {
        val currentUserId = SendbirdChat.currentUser?.userId ?: ""
        return SimpleMessage(
            messageId = messageId,
            text = message,
            senderName = sender?.nickname ?: "Unknown",
            senderId = sender?.userId ?: "",
            timestamp = createdAt,
            isMyMessage = sender?.userId == currentUserId
        )
    }
}
