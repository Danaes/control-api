package com.bikeup.control.api.common.outbound.publisher.eventbus

import com.bikeup.control.api.bike.core.domain.BikeMother
import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent
import io.vertx.mutiny.core.eventbus.EventBus
import org.jboss.logging.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class EventBusPublisherTest {

    private val eventBus = mock<EventBus>()
    private val log = mock<Logger>()

    private lateinit var sut: EventBusPublisher

    @BeforeEach
    internal fun init() {
        reset(eventBus)
        reset(log)

        sut = EventBusPublisher(eventBus, log)
    }

    @Test
    internal fun send_whenAddressAndDomainEvent_shouldPublish() {
        val address = "address"
        val domainEvent = BikeUpdatedEvent(BikeMother.of(), mock())

        sut.send(address, domainEvent)

        verify(log).info("Sending ${domainEvent.javaClass.simpleName} to address: $address")
        verify(eventBus).publish(address, domainEvent)
    }
}