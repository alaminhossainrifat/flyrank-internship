package com.rifat.widget_platform_backend.controller;

import com.rifat.widget_platform_backend.entity.Widget;
import com.rifat.widget_platform_backend.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/widgets")
@RequiredArgsConstructor
public class WidgetController {

    private final WidgetService widgetService;

    @PostMapping
    public ResponseEntity<Widget> createWidget(@RequestBody Widget widget) {
        // Hardcoding ownerId for now. Later we will extract it from Spring Security context.
        widget.setOwnerId("user-1");
        return ResponseEntity.ok(widgetService.createWidget(widget));
    }

    @GetMapping
    public ResponseEntity<List<Widget>> getWidgetsByOwner() {
        // Hardcoding ownerId for now to test the endpoint.
        return ResponseEntity.ok(widgetService.getWidgetsByOwner("user-1"));
    }
}
