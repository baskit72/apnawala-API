package com.APIwebsitebuilder.websitebuilder.service;

import com.APIwebsitebuilder.websitebuilder.model.Site;
import com.APIwebsitebuilder.websitebuilder.repository.SiteRepository;
import com.APIwebsitebuilder.websitebuilder.repository.UserRepository;
import com.APIwebsitebuilder.websitebuilder.requestHelper.siteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Site> getSitesByUser(String userName) {
        return siteRepository.findByUser(userRepository.findByUsername(userName).orElseThrow());
    }

    public Site createSite(siteRequest siteR) {
        Site site = new Site();
        site.setSiteName(siteR.getSiteName());
        site.setUser(userRepository.findByUsername(siteR.getUsername()).orElseThrow());
        site.setDomain(siteR.getDomain());
        return siteRepository.save(site);
    }
}
