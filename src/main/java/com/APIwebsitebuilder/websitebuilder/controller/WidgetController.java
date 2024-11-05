package com.APIwebsitebuilder.websitebuilder.controller;


import com.APIwebsitebuilder.websitebuilder.model.Widget;
import com.APIwebsitebuilder.websitebuilder.repository.SiteRepository;
import com.APIwebsitebuilder.websitebuilder.repository.WidgetRepository;
import com.APIwebsitebuilder.websitebuilder.requestHelper.SiteWidgetsReq;
import com.APIwebsitebuilder.websitebuilder.service.WidgetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/widgets")
public class WidgetController {
    private static final Logger log = LoggerFactory.getLogger(WidgetController.class);
    @Autowired
    private WidgetService widgetService;

    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private SiteRepository siteRepository;


    @GetMapping("/{siteId}")
    public List<Map<String, Object>> getWidgets(@PathVariable Long siteId) throws JsonProcessingException {
        List<Widget> widgets = widgetRepository.findBySiteId(siteId);
        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Widget widget : widgets) {
            Map<String, Object> widgetData = new HashMap<>();
            widgetData.put("id", widget.getId());
            widgetData.put("height", widget.getHeight());
            widgetData.put("type", widget.getType());
            widgetData.put("x", widget.getX());
            widgetData.put("y", widget.getY());
            widgetData.put("width", widget.getWidth());
            widgetData.put("mode", widget.getMode());


            // Convert properties JSON string back to Map
            widgetData.put("properties", objectMapper.readValue(widget.getProperties(), Map.class));
            result.add(widgetData);
        }

        return result;
    }


    @PostMapping("/save/{siteId}")
    public List<Widget> saveWidgets(@PathVariable Long siteId, @RequestBody Map<String, Object> payload) throws JsonProcessingException {

        log.info("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        List<Map<String, Object>> canvasWidgets = (List<Map<String, Object>>) payload.get("canvasWidgets");

        List<Long> deletedWidgets = (List<Long>) payload.get("deletedWidgetIds");

        // Delete widgets that exist in the database
        for (Long widgetId : deletedWidgets) {
            Optional<Widget> widgetOptional = widgetRepository.findById(widgetId);
            if (widgetOptional.isPresent()) {
                widgetRepository.delete(widgetOptional.get());
                log.info("Deleted widget with ID: {}", widgetId);
            } else {
                log.warn("Widget with ID: {} not found, skipping deletion.", widgetId);
            }
        }


        List<Widget> widgets = new ArrayList<>();
        for (Map<String, Object> widgetData : canvasWidgets) {
            Widget widget = new Widget();
            widget.setSite(siteRepository.findById(siteId).orElseThrow());
            widget.setId((Long) widgetData.get("id"));
            widget.setType((String) widgetData.get("type"));
            widget.setX((int) widgetData.get("x"));
            widget.setY((int) widgetData.get("y"));
            widget.setWidth((int) widgetData.get("width"));
            widget.setHeight((int) widgetData.get("height"));
            widget.setMode((String) widgetData.get("mode"));

            // Convert properties to JSON string
            widget.setProperties(new ObjectMapper().writeValueAsString(widgetData.get("properties")));

            widgets.add(widgetRepository.save(widget));
        }

        return widgets;
    }
}
