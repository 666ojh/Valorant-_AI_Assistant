package com.valorantassistant.common.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardPageController {

    @GetMapping({"/valorant-dashboard", "/valorant-dashboard/"})
    public String dashboardEntry() {
        return "forward:/valorant-dashboard/index.html";
    }
}
