package com.codependent.outboxpattern.outbox.service

import com.codependent.outboxpattern.outbox.entity.Outbox
import com.codependent.outboxpattern.outbox.entity.State
import com.codependent.outboxpattern.outbox.repository.OutboxRepository
import org.springframework.cloud.stream.schema.avro.AvroSchemaRegistryClientMessageConverter
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class OutboxServiceImpl(private val outboxRepository: OutboxRepository,
                        private val avroMessageConverter: AvroSchemaRegistryClientMessageConverter) : OutboxService {

    override fun markAsProcessed(outboxId: Int) {
        val outbox = outboxRepository.findById(outboxId)
        outbox.ifPresent {
            it.state = State.PROCESSED
            outboxRepository.save(it)
        }

    }

    override fun getPending(): List<Outbox> {
        return outboxRepository.findByState(State.PENDING)
    }

    override fun save(messageId: String, topic: String, entity: Any) {
        val message = avroMessageConverter.toMessage(entity, MessageHeaders(mutableMapOf(MessageHeaders.CONTENT_TYPE to "application/*+avro" as Any))) as Message<*>
        outboxRepository.save(Outbox(0, messageId, topic, State.PENDING, message.payload as ByteArray))
    }

    override fun getAll(): List<Outbox> {
        return outboxRepository.findAll()
    }
}
