package com.practice.websocket.controller

import com.practice.websocket.dto.MessageDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val messageTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/chat.send")
    fun send(@Payload msg: MessageDto) {
        val response = MessageDto(
            roomId = msg.roomId,
            sender = msg.sender,
            message = msg.message
        )

        // room별 topic으로 연결
        messageTemplate.convertAndSend("/topic/rooms/${msg.roomId}", response)
    }
}