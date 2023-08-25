package com.bikeup.control.api.bike.core.domain.event

import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.common.core.domain.event.DomainEvent

abstract class BikeEvent(
    open val bike: Bike
) : DomainEvent
