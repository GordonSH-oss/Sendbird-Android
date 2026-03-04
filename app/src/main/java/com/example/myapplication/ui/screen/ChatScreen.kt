package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.SimpleMessage
import com.example.myapplication.model.UiState
import com.example.myapplication.viewmodel.ChatViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * 聊天主界面
 * 
 * 知识点:
 * 1. collectAsState():将 StateFlow 转换为 Compose 的 State
 *    - 当 StateFlow 的值改变时,会自动触发重组
 * 2. LaunchedEffect:在 Composable 中执行副作用
 *    - key 改变时重新执行
 *    - 当 Composable 离开组合时自动取消
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    userId: String,
    channelName: String,
    onBackClick: () -> Unit
) {
    // 收集 ViewModel 的状态
    val messages by viewModel.messages.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    // 消息输入框的文本
    var messageText by remember { mutableStateOf("") }
    
    // Snackbar 状态
    val snackbarHostState = remember { SnackbarHostState() }
    
    // LazyColumn 的滚动状态
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    /**
     * LaunchedEffect:组件首次显示时连接频道
     * 
     * key1 = Unit:只执行一次
     * 如果 key 是变量,当变量改变时会重新执行
     */
    LaunchedEffect(Unit) {
        viewModel.connectAndJoinChannel(
            userId = userId,
            channelUrl = null,  // null 表示创建新频道
            channelName = channelName
        )
    }
    
    // 显示错误信息
    LaunchedEffect(uiState) {
        if (uiState is UiState.Error) {
            snackbarHostState.showSnackbar(
                message = (uiState as UiState.Error).error,
                duration = SnackbarDuration.Short
            )
        }
    }
    
    // 当新消息到来时自动滚动到底部
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    /**
     * Scaffold:Material Design 的基础布局结构
     * 提供 TopBar, BottomBar, FloatingActionButton 等槽位
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(text = channelName)
                        Text(
                            text = "用户: $userId",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 加载状态指示器
            if (uiState is UiState.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // 消息列表
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = messages,
                    key = { it.messageId }  // 使用唯一 ID 优化列表性能
                ) { message ->
                    MessageItem(message)
                }
            }
            
            // 消息输入区域
            MessageInputBar(
                text = messageText,
                onTextChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = ""  // 清空输入框
                    }
                }
            )
        }
    }
}

/**
 * 消息气泡组件
 * 
 * 知识点:
 * - 根据 isMyMessage 决定消息靠左还是靠右
 * - 使用不同颜色区分自己和他人的消息
 */
@Composable
fun MessageItem(message: SimpleMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isMyMessage) {
            Alignment.End  // 自己的消息靠右
        } else {
            Alignment.Start  // 他人的消息靠左
        }
    ) {
        // 发送者名称和时间
        if (!message.isMyMessage) {
            Text(
                text = message.senderName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
            )
        }
        
        // 消息气泡
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isMyMessage) 16.dp else 4.dp,
                bottomEnd = if (message.isMyMessage) 4.dp else 16.dp
            ),
            color = if (message.isMyMessage) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // 消息文本
                Text(
                    text = message.text,
                    color = if (message.isMyMessage) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontSize = 15.sp
                )
                
                // 时间戳
                Text(
                    text = formatTimestamp(message.timestamp),
                    fontSize = 10.sp,
                    color = if (message.isMyMessage) {
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    },
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/**
 * 消息输入栏
 * 
 * 知识点:
 * Row:水平布局
 */
@Composable
fun MessageInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 输入框
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("输入消息...") },
                maxLines = 4,
                shape = RoundedCornerShape(24.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // 发送按钮
            IconButton(
                onClick = onSendClick,
                enabled = text.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "发送",
                    tint = if (text.isNotBlank()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
            }
        }
    }
}

/**
 * 格式化时间戳
 * 
 * 知识点:
 * SimpleDateFormat:格式化日期时间
 */
private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
