package com.bikeup.control.api.common.core.domain.event.publisher

import com.bikeup.control.api.common.core.domain.event.DomainEvent

interface DomainEventPublisher<T : DomainEvent> {

    fun publish(domainEvent: T)
}