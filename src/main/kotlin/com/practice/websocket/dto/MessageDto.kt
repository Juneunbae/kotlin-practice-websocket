package com.practice.websocket.dto

import java.time.Instant

data class MessageDto(
    val roomId: Int,
    val sender: String,
    val message: String,
    val sendAt: Instant? = Instant.now()
)