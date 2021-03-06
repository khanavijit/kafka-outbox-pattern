package com.codependent.outboxpattern.messagerelay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@EnableBinding(Source::class)
@EnableScheduling
@SpringBootApplication
class MessageRelayServiceApplication

fun main(args: Array<String>) {
    runApplication<MessageRelayServiceApplication>(*args)
}
