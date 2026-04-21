package com.shoonglogitics.hubservice.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class HubDeactivatedEvent extends HubDomainEvent{

    private final UUID hubId;

    private final Long deletedBy;

    public HubDeactivatedEvent(UUID hubId, Long deletedBy) {
        super(LocalDateTime.now());
        this.hubId = hubId;
		this.deletedBy = deletedBy;
    }

}
