package com.rifat.widget_platform_backend.repository;

import com.rifat.widget_platform_backend.entity.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, UUID> {
    List<Widget> findByOwnerId(String ownerId);
}