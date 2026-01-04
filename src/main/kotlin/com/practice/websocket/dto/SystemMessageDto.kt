package com.practice.websocket.dto

import com.practice.websocket.entity.Type
import java.time.Instant

data class SystemMessageDto(
    val type: Type,
    val content: String,
    val at: Instant? = Instant.now()
) {
}