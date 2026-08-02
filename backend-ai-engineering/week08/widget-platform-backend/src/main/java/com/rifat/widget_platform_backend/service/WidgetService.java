package com.rifat.widget_platform_backend.service;

import com.rifat.widget_platform_backend.entity.Widget;
import com.rifat.widget_platform_backend.repository.WidgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;

    public Widget createWidget(Widget widget) {
        return widgetRepository.save(widget);
    }

    public List<Widget> getWidgetsByOwner(String ownerId) {
        return widgetRepository.findByOwnerId(ownerId);
    }

    public Widget getWidgetById(UUID id) {
        return widgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Widget not found with ID: " + id));
    }
}