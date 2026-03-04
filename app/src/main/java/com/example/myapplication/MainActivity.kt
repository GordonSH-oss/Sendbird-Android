package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.screen.ChatScreen
import com.example.myapplication.ui.screen.LoginScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ChatViewModel

/**
 * MainActivity - 应用的主入口
 * 
 * 知识点:
 * 1. ComponentActivity:支持 Jetpack Compose 的 Activity 基类
 * 2. onCreate():Activity 的生命周期方法,在 Activity 创建时调用
 * 3. setContent:设置 Compose UI 内容
 * 4. enableEdgeToEdge():启用全屏沉浸式显示
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            MyApplicationTheme {
                ChatApp()
            }
        }
    }
}

/**
 * 应用主组件 - 管理导航和状态
 * 
 * 知识点:
 * 1. 状态提升:将状态放在父组件中,子组件通过回调修改状态
 * 2. 简单的导航:通过 Boolean 状态控制显示哪个界面
 *    (复杂项目可以使用 Navigation Compose 库)
 */
@Composable
fun ChatApp() {
    // ViewModel 实例 - 会在配置更改时保留
    val chatViewModel: ChatViewModel = viewModel()
    
    // 导航状态
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentUserId by remember { mutableStateOf("") }
    var currentChannelName by remember { mutableStateOf("") }
    
    /**
     * 根据登录状态显示不同界面
     * 
     * 知识点:
     * if-else 表达式在 Compose 中用于条件渲染
     */
    if (isLoggedIn) {
        // 聊天界面
        ChatScreen(
            viewModel = chatViewModel,
            userId = currentUserId,
            channelName = currentChannelName,
            onBackClick = {
                // 返回登录界面
                isLoggedIn = false
            }
        )
    } else {
        // 登录界面
        LoginScreen(
            onLoginSuccess = { userId, channelName ->
                // 登录成功,切换到聊天界面
                currentUserId = userId
                currentChannelName = channelName
                isLoggedIn = true
            }
        )
    }
}

/**
 * Android 开发重要概念总结:
 * 
 * 1. 生命周期:
 *    - onCreate(): Activity 创建
 *    - onStart(): Activity 可见
 *    - onResume(): Activity 可交互
 *    - onPause(): Activity 失去焦点
 *    - onStop(): Activity 不可见
 *    - onDestroy(): Activity 销毁
 * 
 * 2. Jetpack Compose:
 *    - 声明式 UI:描述 UI 应该是什么样,而不是如何构建
 *    - 重组:当状态改变时,UI 自动更新
 *    - @Composable:可组合函数,用于构建 UI
 * 
 * 3. 状态管理:
 *    - remember:在重组时保持状态
 *    - mutableStateOf:创建可观察的状态
 *    - collectAsState:收集 Flow/StateFlow 的值
 * 
 * 4. ViewModel:
 *    - 管理 UI 状态
 *    - 在配置更改时保留数据
 *    - 与 Activity 生命周期独立
 * 
 * 5. 协程:
 *    - viewModelScope:ViewModel 的协程作用域
 *    - launch:启动协程
 *    - suspend:挂起函数,不阻塞线程
 * 
 * 6. Repository 模式:
 *    - 封装数据操作
 *    - 统一数据访问接口
 *    - 方便切换数据源和测试
 */