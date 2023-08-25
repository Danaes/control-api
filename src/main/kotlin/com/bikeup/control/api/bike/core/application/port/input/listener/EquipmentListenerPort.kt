package com.bikeup.control.api.bike.core.application.port.input.listener

import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent

interface EquipmentListenerPort {

    fun equipmentUpdated(bikeUpdatedEvent: BikeUpdatedEvent)
}