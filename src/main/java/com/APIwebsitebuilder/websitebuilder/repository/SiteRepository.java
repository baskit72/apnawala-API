package com.APIwebsitebuilder.websitebuilder.repository;


import com.APIwebsitebuilder.websitebuilder.model.Site;
import com.APIwebsitebuilder.websitebuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findByUser(User user);
}
