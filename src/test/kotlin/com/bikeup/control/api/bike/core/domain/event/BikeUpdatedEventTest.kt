package com.bikeup.control.api.bike.core.domain.event

import com.bikeup.control.api.bike.core.domain.BikeMother
import com.bikeup.control.api.common.core.domain.event.publisher.DomainEventPublisher
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class BikeUpdatedEventTest {

    private val domainEventPublisher = mock<DomainEventPublisher<BikeUpdatedEvent>>()

    @Test
    internal fun fire_whenMethodIsInvoked_shouldPublishEvent() {
        val bike = BikeMother.of()
        val sut = BikeUpdatedEvent(bike, domainEventPublisher)

        sut.fire()

        verify(domainEventPublisher).publish(sut)
    }
}