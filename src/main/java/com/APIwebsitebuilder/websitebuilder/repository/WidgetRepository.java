package com.APIwebsitebuilder.websitebuilder.repository;

import com.APIwebsitebuilder.websitebuilder.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findBySiteId(Long siteId);
}
