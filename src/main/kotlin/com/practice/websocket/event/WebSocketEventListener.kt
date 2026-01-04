package com.practice.websocket.event

import com.practice.websocket.dto.SystemMessageDto
import com.practice.websocket.entity.Type
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener(
    private val messagingTemplate: SimpMessagingTemplate
) {
    @EventListener
    fun onConnect(event: SessionConnectEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)

        val nickname = accessor.getFirstNativeHeader("Nickname") ?: "Unknown"
        val roomId = accessor.getFirstNativeHeader("RoomId") ?: "Unknown"

        // 세션 저장
        accessor.sessionAttributes?.apply {
            put("Nickname", nickname)
            put("RoomId", roomId)
        }

        val message = SystemMessageDto(
            type = Type.ENTER,
            content = "$nickname 님이 입장하셨습니다."
        )

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId",
            message
        )
    }

    @EventListener
    fun onDisconnect(event: SessionDisconnectEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)

        val nickname = accessor.getFirstNativeHeader("Nickname") ?: "Unknown"
        val roomId = accessor.getFirstNativeHeader("RoomId") ?: "Unknown"

        val message = SystemMessageDto(
            type = Type.LEAVE,
            content = "$nickname 님이 퇴장하셨습니다."
        )

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId",
            message
        )
    }
}