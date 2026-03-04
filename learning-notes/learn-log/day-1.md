# Day 1: Android 开发初体验 (2026-03-04)

## 📋 今日学习目标

### 核心目标
1. ✅ 搭建 Android 开发环境
2. ✅ 获取 Sendbird Application ID
3. ✅ 运行第一个聊天应用
4. ✅ 发送第一条消息

### 时间预估: 1-2小时

---

## 📚 学习任务清单

### 任务 1: 环境准备 (30分钟)
- [ ] 确认 Android Studio 已安装
- [ ] 创建/启动 Android 模拟器
  - 推荐: Pixel 5, API 31 (Android 12)
- [ ] 或准备真实 Android 设备
  - 开启开发者选项
  - 启用 USB 调试

### 任务 2: 获取 Sendbird APP_ID (10分钟)
- [ ] 访问 https://dashboard.sendbird.com/
- [ ] 注册/登录账号
- [ ] 创建新应用
  - 应用名称: `我的聊天应用`
  - Product Type: `Chat`
  - Region: `Singapore` (亚洲用户)
- [ ] 复制 Application ID

### 任务 3: 配置项目 (5分钟)
- [ ] 打开 `ChatApplication.kt`
- [ ] 找到第 21 行的 `APP_ID`
- [ ] 替换为你的 Application ID
- [ ] 保存文件 (Ctrl/Cmd + S)

### 任务 4: 运行应用 (10分钟)
- [ ] 点击 Android Studio 顶部的绿色运行按钮 ▶️
- [ ] 等待应用安装到设备/模拟器
- [ ] 确认应用成功启动,看到登录界面

### 任务 5: 发送第一条消息 (10分钟)
- [ ] 在登录界面输入:
  - 用户 ID: `tom`
  - 频道名称: `测试聊天室`
- [ ] 点击"进入聊天室"
- [ ] 等待连接成功(观察顶部加载条)
- [ ] 在输入框输入: `Hello Sendbird!`
- [ ] 点击发送按钮 ✉️
- [ ] 确认消息显示在右侧(蓝色气泡)

### 任务 6: 查看日志 (5分钟)
- [ ] 打开 Android Studio 底部的 Logcat 面板
- [ ] 在搜索框输入: `ChatApplication`
- [ ] 找到以下日志:
  - "Sendbird 初始化成功"
  - "连接成功"
  - "进入频道成功"
  - "消息发送成功"

### 任务 7: 了解项目结构 (20分钟)
- [ ] 阅读 `START_HERE.md` 前半部分
- [ ] 浏览项目文件树,了解各文件作用
- [ ] 打开 `MainActivity.kt`,快速浏览代码
- [ ] 打开 `LoginScreen.kt`,查看 UI 代码

---

## 💡 学习要点

### 1. Android Studio 界面认识
```
顶部工具栏 → 运行/调试按钮
左侧面板   → 项目文件树
中间编辑器 → 代码编辑
底部面板   → Logcat (日志查看)
右侧面板   → Gradle (构建工具)
```

### 2. 项目文件结构
```
app/src/main/java/  → Kotlin 源代码
app/src/main/res/   → 资源文件(图片、字符串)
AndroidManifest.xml → 应用配置
build.gradle.kts    → 构建配置和依赖
```

### 3. Logcat 使用技巧
- 按标签筛选: 输入 `ChatApplication` 或 `ChatViewModel`
- 按级别筛选: Debug, Info, Warn, Error
- 清除日志: 点击 🚫 图标

---

## 📝 学习成果记录

### 成功完成的任务
- [ ] 应用成功运行
- [ ] 登录界面显示正常
- [ ] 成功发送第一条消息
- [ ] 消息显示在聊天界面
- [ ] Logcat 显示正确的日志

### 我发送的第一条消息
```
内容: 
时间: 
截图: (可选)
```

### 遇到的问题和解决方案

#### 问题 1:
**描述**: 

**解决方案**: 

**学到的经验**: 

---

### 问题 2:
**描述**: 

**解决方案**: 

**学到的经验**: 

---

## 🤔 思考题

1. **Activity 是什么?**
   - 我的理解: 

2. **为什么需要在 Application 类中初始化 Sendbird?**
   - 我的理解: 

3. **Logcat 日志的作用是什么?**
   - 我的理解: 

---

## 📸 今日截图

### 登录界面
- [ ] 已截图

### 聊天界面
- [ ] 已截图

### Logcat 日志
- [ ] 已截图

---

## 🎯 今日收获

### 掌握的技能
1. 
2. 
3. 

### 理解的概念
1. 
2. 
3. 

### 新学到的术语
- **Activity**: 
- **Application**: 
- **Logcat**: 
- **Sendbird**: 

---

## 📚 今日阅读的文档
- [ ] `START_HERE.md` (前半部分)
- [ ] `QUICKSTART.md` (浏览)
- [ ] 项目 README.md (浏览)

---

## 💪 明日预告

**Day 2: Android 核心概念**
- 理解 Android 四大组件
- 学习 Activity 生命周期
- 理解 Kotlin 基础语法
- 修改第一个 UI 元素

**预计时间**: 1.5-2小时

---

## ⭐ 自我评价

### 今日学习效果 (1-5星)
- 理解程度: ⭐⭐⭐⭐⭐
- 完成度: ⭐⭐⭐⭐⭐
- 遇到困难: ⭐⭐⭐⭐⭐ (5星=很简单)

### 心得体会
```
写下你对今天学习的感受...





```

### 需要加强的方面
- 
- 

---

## ✅ 完成标志

当你完成以下事项,就可以进入 Day 2:
- [x] 应用成功运行并发送消息
- [x] 理解了基本的项目结构
- [x] 知道如何查看 Logcat 日志
- [x] 记录了学习笔记

---

**Keep Learning! 明天见! 💪**

---

## 📚 学习资源快速导航

### 📋 必读文档
- [00-OVERVIEW.md](./00-OVERVIEW.md) - 30天学习计划总览
- [PROGRESS.md](./PROGRESS.md) - 学习进度跟踪表
- [day-2.md](./day-2.md) - 明天的学习内容

### 📖 项目文档
- [QUICKSTART.md](../../QUICKSTART.md) - 快速开始指南
- [ANDROID_LEARNING_GUIDE.md](../../ANDROID_LEARNING_GUIDE.md) - Android 核心概念
- [CHEATSHEET.md](../CHEATSHEET.md) - 速查手册

### 💡 学习建议
1. **不要跳过**: 按照顺序完成每一天的任务
2. **记录详细**: 认真填写学习成果记录
3. **及时复习**: 遇到困难时回看之前的笔记
4. **保持节奏**: 每天固定时间学习,保持连续性

### 🎯 下一步
完成 Day 1 后:
1. ✅ 在 [PROGRESS.md](./PROGRESS.md) 中标记 Day 1 为完成
2. ✅ 复习今天的学习笔记
3. ✅ 预习 [day-2.md](./day-2.md) 的内容
4. ✅ 明天继续!