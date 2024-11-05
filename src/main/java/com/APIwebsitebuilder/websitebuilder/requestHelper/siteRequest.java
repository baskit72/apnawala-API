package com.APIwebsitebuilder.websitebuilder.requestHelper;

import com.APIwebsitebuilder.websitebuilder.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class siteRequest {

    private String siteName;
    private String domain;
    private String username;






}
