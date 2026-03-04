# Day 3: Jetpack Compose UI 入门

## 📋 今日学习目标

### 核心目标
1. ✅ 理解声明式 UI 概念
2. ✅ 掌握 Compose 基础组件
3. ✅ 理解 @Composable 函数
4. ✅ 创建第一个自定义组件

### 时间预估: 2小时

---

## 📚 学习任务清单

### 任务 1: 理解声明式 UI (20分钟)
- [ ] 阅读 `ANDROID_LEARNING_GUIDE.md` > "Jetpack Compose"
- [ ] 理解声明式 vs 命令式的区别:
  ```
  命令式 (传统 XML):
  1. 创建 TextView
  2. 设置文本
  3. 设置颜色
  4. 添加到布局
  
  声明式 (Compose):
  Text(text = "Hello", color = Color.Blue)
  ```

### 任务 2: 基础组件实验 (30分钟)
- [ ] 打开 `LoginScreen.kt`
- [ ] 在底部添加实验组件:

```kotlin
// 在 LoginScreen 函数内,Card 组件之后添加

Spacer(modifier = Modifier.height(16.dp))

// 实验区域
Card(
    modifier = Modifier.fillMaxWidth()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "🎨 Compose 组件实验",
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 文本组件
        Text(
            text = "这是普通文本",
            fontSize = 14.sp
        )
        
        // 按钮组件
        Button(onClick = { 
            Log.d("Compose", "按钮被点击了!")
        }) {
            Text("点我试试")
        }
        
        // 图标按钮
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "收藏"
            )
        }
    }
}
```

- [ ] 运行应用,查看效果
- [ ] 尝试修改文字、颜色、大小

### 任务 3: 布局组件练习 (25分钟)
- [ ] 创建新文件: `ExperimentScreen.kt`
- [ ] 复制以下代码进行练习:

```kotlin
package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExperimentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Column - 垂直布局", style = MaterialTheme.typography.headlineSmall)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Row 示例
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Blue)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Row - 水平布局", style = MaterialTheme.typography.bodyLarge)
    }
}
```

### 任务 4: Modifier 修饰符实验 (20分钟)
- [ ] 理解 Modifier 的作用
- [ ] 在 `ExperimentScreen.kt` 中尝试不同的修饰符:

```kotlin
// 在 ExperimentScreen 中添加

Text(
    text = "Modifier 实验",
    modifier = Modifier
        .fillMaxWidth()           // 填满宽度
        .background(Color.Yellow) // 黄色背景
        .padding(16.dp)           // 内边距
)

Spacer(modifier = Modifier.height(16.dp))

Text(
    text = "带边框的文本",
    modifier = Modifier
        .border(2.dp, Color.Red)  // 红色边框
        .padding(8.dp)
)
```

- [ ] 观察修饰符的顺序如何影响效果
- [ ] 尝试: `padding().background()` vs `background().padding()`

### 任务 5: 创建自定义组件 (25分钟)
- [ ] 在 `ExperimentScreen.kt` 中创建可复用组件:

```kotlin
@Composable
fun UserCard(
    name: String,
    age: Int,
    avatarColor: Color = Color.Blue
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 头像
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(avatarColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 用户信息
            Column {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$age 岁",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// 使用组件
@Composable
fun UserListExample() {
    Column {
        UserCard("张三", 25, Color.Red)
        UserCard("李四", 30, Color.Green)
        UserCard("王五", 28, Color.Blue)
    }
}
```

- [ ] 运行应用查看效果
- [ ] 理解组件参数化和复用

### 任务 6: 状态管理初体验 (20分钟)
- [ ] 创建一个计数器组件:

```kotlin
@Composable
fun CounterExample() {
    // remember: 在重组时保持状态
    var count by remember { mutableStateOf(0) }
    
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "计数: $count",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { count-- }) {
                    Text("-")
                }
                
                Button(onClick = { count = 0 }) {
                    Text("重置")
                }
                
                Button(onClick = { count++ }) {
                    Text("+")
                }
            }
        }
    }
}
```

- [ ] 理解 `remember` 的作用
- [ ] 理解 `mutableStateOf` 创建可观察状态
- [ ] 点击按钮,观察界面自动更新

---

## 💡 今日学习要点

### 1. 声明式 UI 的优势
```
命令式: 告诉程序"怎么做"
  - 创建对象
  - 设置属性
  - 添加监听器
  
声明式: 告诉程序"是什么"
  - 描述 UI 应该是什么样
  - 框架自动更新
  - 代码更简洁
```

### 2. 基础组件
```kotlin
Text()          → 文本
Button()        → 按钮
TextField()     → 输入框
Image()         → 图片
Card()          → 卡片
Icon()          → 图标
```

### 3. 布局组件
```kotlin
Column()        → 垂直布局
Row()           → 水平布局
Box()           → 层叠布局
LazyColumn()    → 滚动列表
```

### 4. Modifier 常用操作
```kotlin
.fillMaxWidth()      → 填满宽度
.padding(16.dp)      → 内边距
.background(Color)   → 背景色
.border()            → 边框
.clickable { }       → 点击事件
.size(50.dp)         → 固定大小
```

---

## 📝 学习成果记录

### 完成的代码实验

#### 1. 我创建的自定义组件
```kotlin
// 在这里粘贴你创建的组件代码




```

#### 2. 我的 Modifier 实验结果
- 尝试的修饰符组合: 
- 观察到的效果: 

#### 3. 状态管理实验
- 初始计数器值: 
- 点击 + 按钮后: 
- 点击重置后: 
- 理解了什么: 

---

## 🤔 思考题

### 1. 声明式 UI
**Q: 声明式 UI 的核心思想是什么?**
- 我的理解: 

**Q: 为什么声明式 UI 比传统方式更简洁?**
- 我的理解: 

### 2. @Composable 函数
**Q: 为什么 Compose 函数需要 @Composable 注解?**
- 我的理解: 

**Q: @Composable 函数和普通函数有什么区别?**
- 我的理解: 

### 3. 状态管理
**Q: 为什么需要 remember?**
- 我的理解: 

**Q: 什么是"重组"(Recomposition)?**
- 我的理解: 

---

## 🎨 UI 设计实践

### 我的创意组件设计

**组件名称**: 

**功能描述**: 

**代码实现**:
```kotlin
// 在这里写你自己设计的组件


```

**效果截图**:
- [ ] 已截图

---

## 🐛 遇到的问题

### 问题 1:
**描述**: 

**代码**:
```kotlin

```

**解决方案**: 

**学到的经验**: 

---

## 🎯 今日收获

### 新掌握的组件
1. **Text**: 
2. **Button**: 
3. **Column/Row**: 
4. **Card**: 

### 理解的概念
1. **声明式 UI**: 
2. **@Composable**: 
3. **Modifier**: 
4. **remember**: 

### 完成的实验
- [ ] 基础组件实验
- [ ] 布局组件练习
- [ ] Modifier 修饰符实验
- [ ] 创建自定义组件
- [ ] 状态管理初体验

---

## 📚 今日阅读的文档
- [ ] `ANDROID_LEARNING_GUIDE.md` - Jetpack Compose
- [ ] `LoginScreen.kt` - 实际 Compose 代码
- [ ] Compose 官方文档 (可选)

---

## 💪 明日预告

**Day 4: 深入理解 Models 和数据类**
- 理解数据建模
- 学习 data class
- 理解 sealed class
- 修改和扩展 Models.kt

**预计时间**: 1.5小时

---

## ⭐ 自我评价

### 学习效果 (1-5星)
- UI 理解: ⭐⭐⭐⭐⭐
- 代码能力: ⭐⭐⭐⭐⭐
- 创意程度: ⭐⭐⭐⭐⭐

### 心得体会
```
今天对 Compose 的理解如何?




```

### 最喜欢的发现
- 

---

## ✅ Day 3 完成检查

- [ ] 理解了声明式 UI 的概念
- [ ] 能使用基础 Compose 组件
- [ ] 理解了 Modifier 的作用
- [ ] 创建了自定义组件
- [ ] 理解了基本的状态管理

**全部完成?太棒了!继续 Day 4! 🚀**

---

**UI 是用户的第一印象,加油! 🎨**
