package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.SimpleMessage
import com.example.myapplication.model.UiState
import com.example.myapplication.repository.ChatRepository
import com.sendbird.android.channel.OpenChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ChatViewModel 负责管理聊天界面的状态和业务逻辑
 * 
 * 知识点:
 * 1. ViewModel:专门用于存储和管理 UI 相关的数据
 *    - 在配置更改(如屏幕旋转)时保持数据
 *    - 与 Activity/Fragment 的生命周期独立
 * 
 * 2. StateFlow:用于 UI 状态管理的响应式数据流
 *    - MutableStateFlow:可修改的状态流
 *    - StateFlow:只读的状态流
 *    - 类似 LiveData,但更适合 Kotlin 协程
 * 
 * 3. viewModelScope:ViewModel 的协程作用域
 *    - 当 ViewModel 被清除时自动取消所有协程
 *    - 避免内存泄漏
 */
class ChatViewModel : ViewModel() {
    
    private val repository = ChatRepository()
    
    // 当前频道
    private var currentChannel: OpenChannel? = null
    
    // UI 状态
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    // 消息列表
    private val _messages = MutableStateFlow<List<SimpleMessage>>(emptyList())
    val messages: StateFlow<List<SimpleMessage>> = _messages.asStateFlow()
    
    // 当前用户 ID
    private val _currentUserId = MutableStateFlow("")
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()
    
    /**
     * 连接并进入频道
     * 
     * @param userId 用户 ID
     * @param channelUrl 频道 URL(可选,如果为空则创建新频道)
     * @param channelName 频道名称
     * 
     * 知识点:
     * viewModelScope.launch:在 ViewModel 的协程作用域中启动协程
     * - 运行在主线程
     * - 可以调用 suspend 函数
     * - 自动处理生命周期
     */
    fun connectAndJoinChannel(userId: String, channelUrl: String?, channelName: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                
                // 步骤 1: 连接到 Sendbird
                Log.i(TAG, "开始连接用户: $userId")
                val user = repository.connect(userId)
                _currentUserId.value = user.userId
                
                // 步骤 2: 获取或创建频道
                Log.i(TAG, "获取频道: $channelUrl")
                val channel = repository.getOrCreateChannel(channelUrl, channelName)
                currentChannel = channel
                
                // 步骤 3: 进入频道
                Log.i(TAG, "进入频道: ${channel.name}")
                repository.enterChannel(channel)
                
                // 步骤 4: 加载历史消息
                Log.i(TAG, "加载历史消息")
                val historyMessages = repository.loadMessages(channel)
                _messages.value = historyMessages
                
                // 步骤 5: 开始监听新消息
                Log.i(TAG, "开始监听新消息")
                startObservingMessages(channel.url)
                
                _uiState.value = UiState.Success("连接成功!")
                
            } catch (e: Exception) {
                Log.e(TAG, "连接失败", e)
                _uiState.value = UiState.Error("连接失败: ${e.message}")
            }
        }
    }
    
    /**
     * 发送消息
     * 
     * @param text 消息内容
     */
    fun sendMessage(text: String) {
        // 验证输入
        if (text.isBlank()) {
            Log.w(TAG, "消息内容为空")
            return
        }
        
        val channel = currentChannel
        if (channel == null) {
            Log.e(TAG, "频道未初始化")
            _uiState.value = UiState.Error("请先连接到频道")
            return
        }
        
        viewModelScope.launch {
            try {
                Log.i(TAG, "发送消息: $text")
                val message = repository.sendMessage(channel, text)
                
                // 将新消息添加到列表
                _messages.value = _messages.value + message
                
            } catch (e: Exception) {
                Log.e(TAG, "发送消息失败", e)
                _uiState.value = UiState.Error("发送失败: ${e.message}")
            }
        }
    }
    
    /**
     * 开始监听新消息
     * 
     * 知识点:
     * Flow.collect:收集 Flow 中的数据
     * - 是一个挂起函数,会持续运行直到 Flow 完成或协程被取消
     * - 每当 Flow 发出新数据时,lambda 代码块就会执行
     */
    private fun startObservingMessages(channelUrl: String) {
        viewModelScope.launch {
            repository.observeMessages(channelUrl).collect { newMessage ->
                Log.i(TAG, "收到新消息: ${newMessage.text}")
                
                // 避免重复添加(如果是自己发送的消息,已经在 sendMessage 中添加了)
                val exists = _messages.value.any { it.messageId == newMessage.messageId }
                if (!exists) {
                    _messages.value = _messages.value + newMessage
                }
            }
        }
    }
    
    /**
     * 离开频道并断开连接
     * 
     * 知识点:
     * ViewModel 被清除时自动调用,用于清理资源
     */
    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel 被清除,断开连接")
        repository.disconnect()
    }
    
    companion object {
        private const val TAG = "ChatViewModel"
    }
}
