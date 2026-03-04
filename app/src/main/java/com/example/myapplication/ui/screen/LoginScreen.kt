package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 登录界面
 * 
 * 知识点:
 * 1. @Composable:标记这是一个可组合函数,用于构建 UI
 * 2. remember:在重组(Recomposition)时保持状态
 * 3. mutableStateOf:创建可观察的状态,当值改变时会触发重组
 * 4. by 关键字:属性委托,简化状态访问语法
 */
@Composable
fun LoginScreen(
    onLoginSuccess: (userId: String, channelName: String) -> Unit
) {
    // 状态:用户 ID 输入框的内容
    var userId by remember { mutableStateOf("") }
    
    // 状态:频道名称输入框的内容
    var channelName by remember { mutableStateOf("公共聊天室") }
    
    /**
     * Column:垂直布局
     * 
     * 知识点:
     * - modifier:修饰符,用于设置样式和行为
     * - fillMaxSize():填充整个可用空间
     * - padding():内边距
     * - horizontalAlignment:水平对齐方式
     * - verticalArrangement:垂直排列方式
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 标题
        Text(
            text = "💬 欢迎使用聊天应用",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // 用户 ID 输入框
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },  // 当文本改变时更新状态
            label = { Text("用户 ID") },
            placeholder = { Text("例如: user123") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // 频道名称输入框
        OutlinedTextField(
            value = channelName,
            onValueChange = { channelName = it },
            label = { Text("频道名称") },
            placeholder = { Text("例如: 公共聊天室") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )
        
        // 登录按钮
        Button(
            onClick = {
                if (userId.isNotBlank() && channelName.isNotBlank()) {
                    onLoginSuccess(userId.trim(), channelName.trim())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = userId.isNotBlank() && channelName.isNotBlank()  // 输入为空时禁用按钮
        ) {
            Text(
                text = "进入聊天室",
                fontSize = 16.sp
            )
        }
        
        // 提示信息
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "💡 使用提示",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "• 输入任意用户 ID,如果不存在会自动创建\n" +
                            "• 使用相同频道名称的用户可以互相聊天\n" +
                            "• 尝试在不同设备上使用不同 ID 进入同一频道",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

/**
 * 预览函数
 * 
 * 知识点:
 * @Preview:在 Android Studio 中显示预览
 * - showBackground = true:显示背景
 * - 帮助在不运行应用的情况下查看 UI
 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginSuccess = { _, _ -> }
        )
    }
}
