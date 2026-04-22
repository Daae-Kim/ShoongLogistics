package com.shoonglogitics.hubservice.application.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.hubservice.application.HubRouteService;
import com.shoonglogitics.hubservice.domain.event.HubDeactivatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubRouteEventHandler {

	private final HubRouteService hubRouteService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleHubDeactivated(HubDeactivatedEvent event){
		hubRouteService.deactivateByHubId(event.getHubId(), event.getDeletedBy());
	}
}
