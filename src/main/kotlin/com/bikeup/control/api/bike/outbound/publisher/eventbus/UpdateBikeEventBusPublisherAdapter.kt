package com.bikeup.control.api.bike.outbound.publisher.eventbus

import com.bikeup.control.api.bike.core.application.port.output.publisher.equipment.BikeUpdatedEventPublisherPort
import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent
import com.bikeup.control.api.common.outbound.publisher.eventbus.EventBusPublisher
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class UpdateBikeEventBusPublisherAdapter(
    private val eventBusPublisher: EventBusPublisher,
    private val log: Logger
) : BikeUpdatedEventPublisherPort {

    override fun publish(domainEvent: BikeUpdatedEvent) {
        log.info("Received BikeUpdatedEvent for bike id: ${domainEvent.bike.id}")
        eventBusPublisher.send("bike-updated", domainEvent)
    }
}