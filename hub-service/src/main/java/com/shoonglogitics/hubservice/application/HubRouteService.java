package com.shoonglogitics.hubservice.application;

import com.shoonglogitics.hubservice.application.dto.RouteResult;
import com.shoonglogitics.hubservice.domain.entity.HubRoute;
import com.shoonglogitics.hubservice.domain.repository.HubRouteRepository;
import com.shoonglogitics.hubservice.domain.service.RouteCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class HubRouteService {

    private final RouteCalculationService routeCalculationService;
    private final HubRouteRepository hubRouteRepository;

    @Cacheable(value = "routes", key = "#departureId + ':' + #arrivalId")
    public RouteResult calculateRoute(UUID departureId, UUID arrivalId){
        log.info("경로 계산 요청 : departureId={}, arrivalId={}", departureId, arrivalId);
        return routeCalculationService.calculateOptimalRoute(departureId, arrivalId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deactivateByHubId(UUID hubId, Long deletedBy) {
        List<HubRoute> routes = hubRouteRepository.findByHubId(hubId);
        routes.forEach(route -> route.deactivate(deletedBy));
        routes.forEach(hubRouteRepository::save);
    }
}
