# 📋 项目文件导航

这是一份完整的文件说明,帮助你快速找到需要的内容。

---

## 📖 教学文档 (必读)

### 🚀 [QUICKSTART.md](./QUICKSTART.md)
**5分钟快速开始指南**
- 获取 Sendbird Application ID
- 配置项目
- 运行应用
- 测试聊天功能
- 问题排查

👉 **适合**: 完全的初学者,想快速看到效果

---

### 📚 [ANDROID_LEARNING_GUIDE.md](./ANDROID_LEARNING_GUIDE.md)
**Android 开发核心概念教学**
- Android 四大组件详解
- 项目结构说明
- Jetpack Compose 教程
- Kotlin 语法基础
- MVVM 架构讲解
- 协程和异步编程

👉 **适合**: 想系统学习 Android 开发理论

---

### 📖 [README.md](./README.md)
**项目完整说明**
- 项目介绍
- 架构设计图
- 核心概念详解
- 关键代码分析
- UI 组件说明
- 扩展练习
- 常见问题解答

👉 **适合**: 了解项目整体结构和深入学习

---

### 📝 [LEARNING_NOTES.md](./LEARNING_NOTES.md)
**学习笔记模板**
- 学习目标记录
- 代码阅读笔记
- 问题和解决方案
- 收获总结
- 学习计划

👉 **适合**: 记录自己的学习过程

---

## 💻 源代码文件

### 应用入口

#### [ChatApplication.kt](./app/src/main/java/com/example/myapplication/ChatApplication.kt)
```
应用的 Application 类
├─ 初始化 Sendbird SDK
├─ 配置 APP_ID (需要修改这里!)
└─ 全局单例
```

**关键知识点**:
- Application 类的作用
- SDK 初始化流程
- InitResultHandler 回调处理

**需要修改**: ✅ 替换 `APP_ID`

---

#### [MainActivity.kt](./app/src/main/java/com/example/myapplication/MainActivity.kt)
```
主 Activity
├─ 应用入口点
├─ 管理页面导航
└─ 连接 ViewModel 和 UI
```

**关键知识点**:
- Activity 生命周期
- Jetpack Compose 集成
- 状态提升和导航

**修改难度**: 🟡 中等

---

### 数据层

#### [model/Models.kt](./app/src/main/java/com/example/myapplication/model/Models.kt)
```
数据模型定义
├─ UiState: UI 状态枚举
└─ SimpleMessage: 消息数据类
```

**关键知识点**:
- sealed class 用法
- data class 特性
- 数据建模最佳实践

**修改难度**: 🟢 简单

---

#### [repository/ChatRepository.kt](./app/src/main/java/com/example/myapplication/repository/ChatRepository.kt)
```
数据仓库 - 封装 Sendbird SDK
├─ connect(): 连接用户
├─ createOpenChannel(): 创建频道
├─ enterChannel(): 进入频道
├─ sendMessage(): 发送消息
├─ loadMessages(): 加载历史消息
└─ observeMessages(): 监听新消息
```

**关键知识点**:
- Repository 模式
- suspend 函数
- suspendCancellableCoroutine
- callbackFlow 创建响应式数据流
- 回调转协程

**修改难度**: 🔴 困难

---

### 逻辑层

#### [viewmodel/ChatViewModel.kt](./app/src/main/java/com/example/myapplication/viewmodel/ChatViewModel.kt)
```
视图模型 - 管理 UI 状态和业务逻辑
├─ connectAndJoinChannel(): 连接并进入频道
├─ sendMessage(): 发送消息
├─ startObservingMessages(): 监听消息
└─ 状态: messages, uiState, currentUserId
```

**关键知识点**:
- ViewModel 架构组件
- StateFlow 状态管理
- viewModelScope 协程作用域
- 状态更新和 UI 重组

**修改难度**: 🟡 中等

---

### UI 层

#### [ui/screen/LoginScreen.kt](./app/src/main/java/com/example/myapplication/ui/screen/LoginScreen.kt)
```
登录界面
├─ 用户 ID 输入框
├─ 频道名称输入框
├─ 登录按钮
└─ 使用提示
```

**关键知识点**:
- @Composable 函数
- remember 和 mutableStateOf
- Column 垂直布局
- OutlinedTextField 输入框
- Button 按钮

**修改难度**: 🟢 简单 (适合 UI 练习)

---

#### [ui/screen/ChatScreen.kt](./app/src/main/java/com/example/myapplication/ui/screen/ChatScreen.kt)
```
聊天界面
├─ TopBar: 顶部栏
├─ LazyColumn: 消息列表
├─ MessageItem: 消息气泡
└─ MessageInputBar: 输入栏
```

**关键知识点**:
- Scaffold 布局
- LazyColumn 滚动列表
- LaunchedEffect 副作用
- collectAsState 状态收集
- 自动滚动实现

**修改难度**: 🟡 中等

---

## 📁 配置文件

### [settings.gradle.kts](./settings.gradle.kts)
- Gradle 仓库配置
- 添加了 Sendbird Maven 仓库

### [app/build.gradle.kts](./app/build.gradle.kts)
- 应用级构建配置
- 依赖管理
- 已添加 Sendbird SDK 依赖

### [app/src/main/AndroidManifest.xml](./app/src/main/AndroidManifest.xml)
- 应用清单文件
- 注册 Application 类
- 声明权限 (INTERNET)
- 定义 Activity

---

## 🎯 推荐学习路径

### 第一天: 快速上手
1. ✅ 阅读 `QUICKSTART.md`
2. ✅ 配置 `ChatApplication.kt` 的 APP_ID
3. ✅ 运行应用,发送第一条消息
4. ✅ 观察 Logcat 日志

### 第二天: 理解基础
1. ✅ 阅读 `ANDROID_LEARNING_GUIDE.md`
2. ✅ 理解 Android 四大组件
3. ✅ 学习 Kotlin 基础语法
4. ✅ 了解 Jetpack Compose

### 第三天: 阅读代码
1. ✅ 阅读 `Models.kt` - 理解数据模型
2. ✅ 阅读 `LoginScreen.kt` - 学习 Compose UI
3. ✅ 阅读 `ChatApplication.kt` - 了解初始化流程
4. ✅ 尝试修改 UI 颜色或文字

### 第四天: 深入理解
1. ✅ 阅读 `ChatRepository.kt` - 理解数据层
2. ✅ 阅读 `ChatViewModel.kt` - 理解业务逻辑层
3. ✅ 理解 MVVM 架构
4. ✅ 理解协程和异步编程

### 第五天: 实战练习
1. ✅ 阅读 `ChatScreen.kt` - 理解复杂 UI
2. ✅ 阅读 `MainActivity.kt` - 理解整体流程
3. ✅ 尝试添加新功能
4. ✅ 查看 README 的扩展练习

---

## 🔍 按主题查找

### 想学习 Kotlin 语法?
→ `ANDROID_LEARNING_GUIDE.md` > "Kotlin 语言基础"

### 想学习 Compose UI?
→ `ANDROID_LEARNING_GUIDE.md` > "Jetpack Compose"
→ `LoginScreen.kt` (简单示例)
→ `ChatScreen.kt` (复杂示例)

### 想学习协程?
→ `ANDROID_LEARNING_GUIDE.md` > "关键知识点" > "协程"
→ `ChatRepository.kt` (suspend 函数示例)
→ `ChatViewModel.kt` (viewModelScope 示例)

### 想学习状态管理?
→ `ChatViewModel.kt` (StateFlow 示例)
→ `ChatScreen.kt` (collectAsState 示例)
→ `LoginScreen.kt` (mutableStateOf 示例)

### 想学习 MVVM 架构?
→ `README.md` > "MVVM 架构"
→ 按顺序阅读: Models → Repository → ViewModel → Screen

### 想解决问题?
→ `QUICKSTART.md` > "遇到问题?"
→ `README.md` > "常见问题"

---

## 📊 文件难度评级

### 🟢 初级 (建议先看)
- `QUICKSTART.md`
- `ANDROID_LEARNING_GUIDE.md`
- `Models.kt`
- `LoginScreen.kt`
- `ChatApplication.kt`

### 🟡 中级
- `README.md`
- `ChatViewModel.kt`
- `ChatScreen.kt`
- `MainActivity.kt`

### 🔴 高级 (需要一定基础)
- `ChatRepository.kt` (涉及复杂的异步编程)

---

## 💡 学习建议

1. **不要跳过基础**: 先读 `QUICKSTART.md` 和 `ANDROID_LEARNING_GUIDE.md`
2. **动手实践**: 不要只看,要运行代码、修改代码
3. **记录笔记**: 使用 `LEARNING_NOTES.md` 记录学习过程
4. **循序渐进**: 按照推荐学习路径,不要急于求成
5. **多看注释**: 代码中有详细的中文注释,仔细阅读
6. **查看日志**: 运行时打开 Logcat,观察程序执行流程
7. **遇到问题**: 先查"常见问题",再搜索,最后提问

---

## 🎓 完成标志

当你能做到以下几点,说明你已经掌握了这个项目:

- [ ] 能独立配置和运行项目
- [ ] 理解 Android 四大组件的作用
- [ ] 能解释 MVVM 架构的优势
- [ ] 能修改 UI 界面(颜色、布局、文字)
- [ ] 理解 StateFlow 和状态管理
- [ ] 理解协程的基本用法
- [ ] 能添加简单的新功能
- [ ] 能解决常见的运行问题
- [ ] 能独立完成至少 3 个扩展练习

---

开始你的 Android 开发之旅吧! 🚀

有问题随时查阅这份导航文档!
