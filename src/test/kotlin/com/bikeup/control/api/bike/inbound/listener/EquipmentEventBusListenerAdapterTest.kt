package com.bikeup.control.api.bike.inbound.listener

import com.bikeup.control.api.bike.core.application.EquipmentServiceAdapter
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.BikeMother
import com.bikeup.control.api.bike.core.domain.EquipmentMother
import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent
import org.jboss.logging.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class EquipmentEventBusListenerAdapterTest {

    private val equipmentServiceAdapter = mock<EquipmentServiceAdapter>()
    private val log = mock<Logger>()

    private lateinit var sut: EquipmentEventBusListenerAdapter

    @BeforeEach
    internal fun init() {
        reset(equipmentServiceAdapter)
        reset(log)

        sut = EquipmentEventBusListenerAdapter(equipmentServiceAdapter, log)
    }

    @Test
    internal fun equipmentUpdated_whenBikeUpdatedEvent_shouldUpdateEquipment() {
        val equipment = EquipmentMother.of()
        val bike = BikeMother.of(equipments = listOf(equipment))
        val bikeUpdatedEvent = BikeUpdatedEvent(bike, mock())

        sut.equipmentUpdated(bikeUpdatedEvent)

        verify(log).info("Processing successful equipment ${equipment.id} for bike: ${bike.id}")
        verify(equipmentServiceAdapter).update(EquipmentUpdateCmd.map(equipment))
    }
}