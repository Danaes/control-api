package com.bikeup.control.api.bike.inbound.listener

import com.bikeup.control.api.bike.core.application.EquipmentServiceAdapter
import com.bikeup.control.api.bike.core.application.port.input.listener.EquipmentListenerPort
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.event.BikeUpdatedEvent
import io.quarkus.vertx.ConsumeEvent
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class EquipmentEventBusListenerAdapter(
    private val equipmentServiceAdapter: EquipmentServiceAdapter,
    private val log: Logger
) : EquipmentListenerPort {

    @ConsumeEvent(value = "bike-updated")
    override fun equipmentUpdated(bikeUpdatedEvent: BikeUpdatedEvent) {
        val bike = bikeUpdatedEvent.bike
        bike.equipments.forEach {
            log.info("Processing successful equipment ${it.id} for bike: ${bike.id}")
            equipmentServiceAdapter.update(EquipmentUpdateCmd.map(it))
        }
    }
}