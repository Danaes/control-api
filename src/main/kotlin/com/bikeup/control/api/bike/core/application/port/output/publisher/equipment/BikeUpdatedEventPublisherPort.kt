package com.bikeup.control.api.bike.core.application.port.output.publisher.equipment

import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent
import com.bikeup.control.api.common.core.domain.event.publisher.DomainEventPublisher

interface BikeUpdatedEventPublisherPort : DomainEventPublisher<BikeUpdatedEvent>