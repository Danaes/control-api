package com.bikeup.control.api.bike.core.domain.event

import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.common.core.domain.event.publisher.DomainEventPublisher

class BikeUpdatedEvent(
    override val bike: Bike,
    private val domainEventPublisher: DomainEventPublisher<BikeUpdatedEvent>
) : BikeEvent(bike) {
    override fun fire() {
        domainEventPublisher.publish(this)
    }
}
