package com.APIwebsitebuilder.websitebuilder.service;


import com.APIwebsitebuilder.websitebuilder.model.Site;
import com.APIwebsitebuilder.websitebuilder.model.Widget;
import com.APIwebsitebuilder.websitebuilder.repository.SiteRepository;
import com.APIwebsitebuilder.websitebuilder.repository.WidgetRepository;
import com.APIwebsitebuilder.websitebuilder.requestHelper.SiteWidgetsReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidgetService {
    private static final Logger log = LoggerFactory.getLogger(WidgetService.class);
    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private SiteRepository siteRepository;

    public List<Widget> getWidgetsBySiteId(Long siteId) {
        List<Widget> widgets =  widgetRepository.findBySiteId(siteId);
        //widgets.forEach(Widget::deserializeProperties);
        return widgets;
    }

    public void saveWidgets(Long siteId, SiteWidgetsReq siteWidgetsReq) {
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid siteId: " + siteId));

        List<Widget> widgets = siteWidgetsReq.getCanvasWidgets();
        widgets.forEach(widget -> widget.setSite(site));

      //  widgets.forEach(Widget::serializeProperties);
        widgetRepository.saveAll(widgets);
    }
}
