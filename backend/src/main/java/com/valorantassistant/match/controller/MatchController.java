package com.valorantassistant.match.controller;

import com.valorantassistant.common.api.ApiResponse;
import com.valorantassistant.match.dto.MatchDetailResponse;
import com.valorantassistant.match.dto.MatchHistoryResponse;
import com.valorantassistant.match.service.MatchQueryService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/players/{playerId}/matches")
public class MatchController {

    private final MatchQueryService matchQueryService;

    public MatchController(MatchQueryService matchQueryService) {
        this.matchQueryService = matchQueryService;
    }

    @GetMapping
    public ApiResponse<MatchHistoryResponse> getMatchHistory(
        @PathVariable Long playerId,
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "page must be >= 0") int page,
        @RequestParam(defaultValue = "20") @Min(value = 1, message = "size must be >= 1") @Max(value = 100, message = "size must be <= 100") int size,
        Authentication authentication
    ) {
        return ApiResponse.success(matchQueryService.getMatchHistory(authentication.getName(), playerId, page, size));
    }

    @GetMapping("/{matchId}")
    public ApiResponse<MatchDetailResponse> getMatchDetail(
        @PathVariable Long playerId,
        @PathVariable Long matchId,
        Authentication authentication
    ) {
        return ApiResponse.success(matchQueryService.getMatchDetail(authentication.getName(), playerId, matchId));
    }
}
