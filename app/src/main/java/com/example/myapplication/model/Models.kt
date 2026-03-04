package com.example.myapplication.model

/**
 * UI 状态枚举
 * 
 * 知识点:
 * 1. sealed class 是密封类,所有子类必须在同一文件中定义
 * 2. 适合表示有限的状态集合
 * 3. when 表达式可以保证处理所有情况
 */
sealed class UiState {
    // 空闲状态
    data object Idle : UiState()
    
    // 加载中
    data object Loading : UiState()
    
    // 成功
    data class Success(val message: String) : UiState()
    
    // 失败
    data class Error(val error: String) : UiState()
}

/**
 * 简化的消息数据类
 * 
 * 知识点:
 * 1. data class 自动生成 equals(), hashCode(), toString(), copy() 等方法
 * 2. 适合用作数据载体
 */
data class SimpleMessage(
    val messageId: Long,
    val text: String,
    val senderName: String,
    val senderId: String,
    val timestamp: Long,
    val isMyMessage: Boolean = false
)
