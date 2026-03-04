package com.example.myapplication

import android.app.Application
import android.util.Log
import com.sendbird.android.SendbirdChat
import com.sendbird.android.exception.SendbirdException
import com.sendbird.android.handler.InitResultHandler
import com.sendbird.android.params.InitParams

/**
 * 聊天应用的 Application 类
 * 
 * 知识点:
 * 1. Application 类是应用的入口点,在所有组件之前创建
 * 2. onCreate() 方法只会被调用一次,适合做全局初始化
 * 3. Application 的生命周期贯穿整个应用运行期间
 */
class ChatApplication : Application() {
    
    companion object {
        private const val TAG = "ChatApplication"
        
        // TODO: 替换为你自己的 Sendbird Application ID
        // 在 https://dashboard.sendbird.com/ 创建应用后获取
        private const val APP_ID = "76AC6A13-4C1D-4820-A9EE-5DFC77719C26"
        
        // 单例实例,方便全局访问
        lateinit var instance: ChatApplication
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // 初始化 Sendbird Chat SDK
        initializeSendbird()
    }
    
    /**
     * 初始化 Sendbird SDK
     * 
     * 知识点:
     * 1. InitParams 用于配置 SDK 的初始化参数
     * 2. useCaching = true 启用本地缓存,可以离线查看历史消息
     * 3. InitResultHandler 用于处理初始化结果的回调
     */
    private fun initializeSendbird() {
        // 创建初始化参数
        val initParams = InitParams(
            appId = APP_ID,
            context = applicationContext,
            useCaching = true  // 启用本地缓存
        )
        
        // 初始化 SDK
        SendbirdChat.init(initParams, object : InitResultHandler {
            
            // 当本地缓存需要迁移时调用
            override fun onMigrationStarted() {
                Log.i(TAG, "Sendbird 数据迁移开始")
            }
            
            // 初始化失败时调用
            override fun onInitFailed(e: SendbirdException) {
                Log.e(TAG, "Sendbird 初始化失败: ${e.message}", e)
                // 即使初始化失败,SDK 仍会以无缓存模式运行
            }
            
            // 初始化成功时调用
            override fun onInitSucceed() {
                Log.i(TAG, "Sendbird 初始化成功")
            }
        })
    }
}
