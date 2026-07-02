package com.valorantassistant.admin.controller;

import com.valorantassistant.admin.dto.AdminUserOverviewResponse;
import com.valorantassistant.admin.service.AdminOverviewService;
import com.valorantassistant.common.api.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOverviewController {

    private final AdminOverviewService adminOverviewService;

    public AdminOverviewController(AdminOverviewService adminOverviewService) {
        this.adminOverviewService = adminOverviewService;
    }

    @GetMapping("/users/overview")
    public ApiResponse<AdminUserOverviewResponse> usersOverview() {
        return ApiResponse.success(adminOverviewService.getUserOverview());
    }
}
