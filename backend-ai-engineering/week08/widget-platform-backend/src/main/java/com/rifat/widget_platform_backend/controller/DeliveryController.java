package com.rifat.widget_platform_backend.controller;

import com.rifat.widget_platform_backend.entity.Widget;
import com.rifat.widget_platform_backend.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/widgets")
@RequiredArgsConstructor
public class DeliveryController {

    private final WidgetService widgetService;

    @GetMapping("/{id}/config")
    public ResponseEntity<Widget> getWidgetConfig(@PathVariable UUID id) {
        Widget widget = widgetService.getWidgetById(id);

        // Return configuration with HTTP cache headers for faster delivery
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(widget);
    }
}
