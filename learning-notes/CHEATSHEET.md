# 🎴 Android 开发速查卡

> 快速查找常用概念和代码

---

## 🔥 最常用

### 查看日志
```kotlin
import android.util.Log

Log.d("TAG", "调试信息")
Log.i("TAG", "一般信息")
Log.w("TAG", "警告")
Log.e("TAG", "错误")
```

### 修改 UI 文字
```kotlin
// LoginScreen.kt
Text(text = "你的新文字")
```

### 修改颜色
```kotlin
// 消息气泡颜色 - ChatScreen.kt
color = MaterialTheme.colorScheme.primary  // 改为 Color.Blue
```

---

## 📱 Android 核心

### Activity 生命周期
```
onCreate()      → Activity 创建,初始化
onStart()       → Activity 可见
onResume()      → Activity 可交互
onPause()       → 失去焦点
onStop()        → 不可见
onDestroy()     → 销毁
```

### 四大组件
```
Activity            → 界面
Service             → 后台服务
BroadcastReceiver   → 广播接收器
ContentProvider     → 内容提供者
```

---

## 🎨 Jetpack Compose

### 基础组件
```kotlin
Text(text = "文本")
Button(onClick = { }) { Text("按钮") }
Image(painter = painterResource(R.drawable.icon))
TextField(value = text, onValueChange = { text = it })
```

### 布局
```kotlin
// 垂直布局
Column {
    Text("第一行")
    Text("第二行")
}

// 水平布局
Row {
    Text("左边")
    Text("右边")
}

// 列表
LazyColumn {
    items(list) { item ->
        Text(item)
    }
}
```

### 状态
```kotlin
// 记住状态
var text by remember { mutableStateOf("") }

// 收集 Flow
val messages by viewModel.messages.collectAsState()
```

### 修饰符
```kotlin
Modifier
    .fillMaxWidth()           // 填满宽度
    .fillMaxHeight()          // 填满高度
    .fillMaxSize()            // 填满整个空间
    .padding(16.dp)           // 内边距
    .background(Color.Red)    // 背景色
    .clickable { }            // 点击事件
```

---

## 🧠 Kotlin 语法

### 变量
```kotlin
val constant = "不可变"    // 常量
var variable = "可变"      // 变量
```

### 函数
```kotlin
fun greet(name: String): String {
    return "Hello $name"
}

// 简化写法
fun greet(name: String) = "Hello $name"
```

### 空安全
```kotlin
var name: String = "Tom"      // 不能为 null
var name: String? = null      // 可以为 null

name?.length                  // 安全调用
name ?: "默认值"              // Elvis 操作符
name!!.length                 // 非空断言(危险!)
```

### Lambda
```kotlin
// 无参数
val action = { println("点击") }

// 有参数
val onClick = { text: String -> println(text) }

// 简化写法(单参数用 it)
list.filter { it > 5 }
list.map { it * 2 }
```

### 数据类
```kotlin
data class User(
    val id: String,
    val name: String
)

val user = User("1", "Tom")
val copy = user.copy(name = "Jerry")
```

---

## ⚡ 协程

### 启动协程
```kotlin
// 在 ViewModel 中
viewModelScope.launch {
    // 协程代码
}

// 在 Composable 中
LaunchedEffect(key) {
    // 协程代码
}
```

### suspend 函数
```kotlin
suspend fun fetchData(): Data {
    // 可以调用其他 suspend 函数
    return repository.getData()
}
```

### Flow
```kotlin
// 创建 Flow
fun getMessages(): Flow<Message> = flow {
    emit(message)
}

// 收集 Flow
flow.collect { message ->
    // 处理消息
}
```

---

## 🏗️ MVVM 架构

### Model (数据)
```kotlin
data class Message(
    val id: Long,
    val text: String
)
```

### Repository (数据操作)
```kotlin
class ChatRepository {
    suspend fun sendMessage(text: String): Message {
        // 调用 API
    }
}
```

### ViewModel (业务逻辑)
```kotlin
class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    
    fun sendMessage(text: String) {
        viewModelScope.launch {
            val message = repository.sendMessage(text)
            _messages.value = _messages.value + message
        }
    }
}
```

### View (UI)
```kotlin
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    
    LazyColumn {
        items(messages) { message ->
            Text(message.text)
        }
    }
}
```

---

## 🎯 常用操作

### 添加日志
```kotlin
// 在任何函数中
Log.d("MyTag", "变量值: $value")
```

### 显示 Toast
```kotlin
Toast.makeText(context, "提示信息", Toast.LENGTH_SHORT).show()
```

### 修改列表
```kotlin
// 添加
_messages.value = _messages.value + newMessage

// 删除
_messages.value = _messages.value.filter { it.id != deleteId }

// 更新
_messages.value = _messages.value.map { 
    if (it.id == updateId) updatedMessage else it
}
```

---

## 🐛 调试技巧

### 查看 Logcat
```
Android Studio 底部 > Logcat
搜索: ChatApplication, ChatViewModel, ChatRepository
```

### 打印调用栈
```kotlin
Log.d("TAG", "调用栈", Exception())
```

### 断点调试
```
1. 点击代码行号左侧设置断点
2. 点击 Debug 按钮(小虫子图标)
3. 触发代码执行
4. 使用 Step Over (F8), Step Into (F7)
```

---

## 📦 依赖管理

### 添加依赖
```kotlin
// app/build.gradle.kts
dependencies {
    implementation("包名:库名:版本")
}
```

### 同步项目
```
File > Sync Project with Gradle Files
或点击顶部的同步图标
```

---

## 🎨 常用颜色

### Material Colors
```kotlin
MaterialTheme.colorScheme.primary          // 主色
MaterialTheme.colorScheme.secondary        // 次要色
MaterialTheme.colorScheme.background       // 背景色
MaterialTheme.colorScheme.surface          // 表面色
MaterialTheme.colorScheme.error            // 错误色
```

### 自定义颜色
```kotlin
Color.Red
Color.Blue
Color.Green
Color(0xFF6200EE)  // 16进制
Color(red = 255, green = 0, blue = 0)
```

---

## 📐 常用尺寸

### DP (密度无关像素)
```kotlin
.padding(8.dp)      // 小间距
.padding(16.dp)     // 中等间距
.padding(24.dp)     // 大间距
.padding(32.dp)     // 超大间距
```

### SP (可缩放像素 - 用于字体)
```kotlin
fontSize = 12.sp    // 小字体
fontSize = 14.sp    // 正常字体
fontSize = 16.sp    // 大字体
fontSize = 20.sp    // 标题
```

---

## 🔧 常见错误

### 应用闪退
```
原因: 代码错误、空指针、权限问题
解决: 查看 Logcat 的红色错误信息
```

### 网络请求失败
```
原因: 没有 INTERNET 权限
解决: AndroidManifest.xml 添加
<uses-permission android:name="android.permission.INTERNET" />
```

### UI 不更新
```
原因: 状态没有使用 mutableStateOf 或 StateFlow
解决: 确保状态是可观察的
```

### 找不到符号
```
原因: 导入错误或拼写错误
解决: Alt+Enter 自动导入
```

---

## 📁 项目结构速查

```
ChatApplication.kt       → SDK 初始化 (修改 APP_ID)
MainActivity.kt          → 应用入口
Models.kt               → 数据模型
ChatRepository.kt       → 数据操作
ChatViewModel.kt        → 业务逻辑
LoginScreen.kt          → 登录界面
ChatScreen.kt           → 聊天界面
```

---

## 💡 快速跳转

### 想修改 UI?
→ `LoginScreen.kt` 或 `ChatScreen.kt`

### 想添加日志?
```kotlin
Log.d("TAG", "信息")
```

### 想修改业务逻辑?
→ `ChatViewModel.kt`

### 想修改数据操作?
→ `ChatRepository.kt`

### 想理解概念?
→ `ANDROID_LEARNING_GUIDE.md`

### 遇到问题?
→ `QUICKSTART.md` > "遇到问题?"

---

## 🎯 快速实现功能

### 添加新按钮
```kotlin
Button(
    onClick = { /* 点击事件 */ }
) {
    Text("按钮文字")
}
```

### 添加输入框
```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("标签") }
)
```

### 显示列表
```kotlin
LazyColumn {
    items(list) { item ->
        Text(item.toString())
    }
}
```

### 添加点击事件
```kotlin
Text(
    text = "可点击文本",
    modifier = Modifier.clickable {
        // 点击时执行
    }
)
```

---

## 📖 快速查找文档

### 项目文档
- 🚀 `QUICKSTART.md` - 5分钟开始
- 📚 `ANDROID_LEARNING_GUIDE.md` - 核心概念
- 📖 `README.md` - 项目说明
- 📋 `PROJECT_GUIDE.md` - 文件导航
- 🗺️ `LEARNING_ROADMAP.md` - 学习路线

### 在线资源
- [Android 官方文档](https://developer.android.com/)
- [Kotlin 文档](https://kotlinlang.org/docs/)
- [Compose 教程](https://developer.android.com/jetpack/compose)
- [Sendbird 文档](https://sendbird.com/docs/chat/sdk/v4/android/)

---

## ⌨️ 快捷键

### 常用
```
Ctrl+Space      自动完成
Alt+Enter       快速修复
Ctrl+/          注释/取消注释
Ctrl+D          复制行
Ctrl+Y          删除行
Ctrl+Z          撤销
Ctrl+Shift+Z    重做
```

### 导航
```
Ctrl+N          查找类
Ctrl+Shift+N    查找文件
Ctrl+Click      跳转到定义
Alt+Left        返回
Alt+Right       前进
```

### 运行
```
Shift+F10       运行
Shift+F9        调试
Ctrl+F2         停止
```

---

**📌 收藏这个文件,随时查阅!**
