package com.APIwebsitebuilder.websitebuilder.controller;

import com.APIwebsitebuilder.websitebuilder.model.Site;
import com.APIwebsitebuilder.websitebuilder.model.User;
import com.APIwebsitebuilder.websitebuilder.repository.UserRepository;
import com.APIwebsitebuilder.websitebuilder.requestHelper.siteRequest;
import com.APIwebsitebuilder.websitebuilder.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/sites")
public class SiteController {
    private static final Logger log = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Site> getSites(@AuthenticationPrincipal UserDetails currentUser, @RequestParam(required = false) String userName) {
        log.info("Fetching sites for user: {}===========================================================================================================", currentUser.getUsername());
        if (currentUser == null) {
            throw new RuntimeException("User is not authenticated.");
        }
        // If userId is provided, fetch sites for that user; otherwise, fetch sites for the authenticated user
        String targetUserName = (userName != null) ? userName : currentUser.getUsername(); // Adjust based on your UserDetails implementation
        return siteService.getSitesByUser(targetUserName);
    }

    @PostMapping
    public Site createSite(@AuthenticationPrincipal UserDetails currentUser, @RequestBody siteRequest siteR) {
        log.info("Creating site for user: {}", currentUser.getUsername());
        // Associate the site with the authenticated user
         // Assuming your Site model has a method to set user
        return siteService.createSite(siteR);
    }
}
