package com.bikeup.control.api.common.outbound.publisher.eventbus

import com.bikeup.control.api.common.core.domain.event.DomainEvent
import io.vertx.mutiny.core.eventbus.EventBus
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class EventBusPublisher(
    private val eventBus: EventBus,
    private val log: Logger
) {
    fun send(address: String, domainEvent: DomainEvent) {
        log.info("Sending ${domainEvent.javaClass.simpleName} to address: $address")
        eventBus.publish(address, domainEvent)
    }
}