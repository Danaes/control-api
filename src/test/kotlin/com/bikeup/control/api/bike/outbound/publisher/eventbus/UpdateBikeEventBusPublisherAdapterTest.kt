package com.bikeup.control.api.bike.outbound.publisher.eventbus

import com.bikeup.control.api.bike.core.domain.BikeMother
import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent
import com.bikeup.control.api.common.outbound.publisher.eventbus.EventBusPublisher
import org.jboss.logging.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class UpdateBikeEventBusPublisherAdapterTest {

    private val eventBusPublisher = mock<EventBusPublisher>()
    private val log = mock<Logger>()

    private lateinit var sut: UpdateBikeEventBusPublisherAdapter

    @BeforeEach
    internal fun init() {
        reset(eventBusPublisher)
        reset(log)

        sut = UpdateBikeEventBusPublisherAdapter(eventBusPublisher, log)
    }

    @Test
    internal fun publish_whenBikeUpdatedEvent_shouldSend() {
        val domainEvent = BikeUpdatedEvent(BikeMother.of(), mock())

        sut.publish(domainEvent)

        verify(log).info("Received BikeUpdatedEvent for bike id: ${domainEvent.bike.id}")
        verify(eventBusPublisher).send("bike-updated", domainEvent)
    }
}