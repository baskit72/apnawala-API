package com.APIwebsitebuilder.websitebuilder.requestHelper;


import com.APIwebsitebuilder.websitebuilder.model.Widget;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteWidgetsReq {

    List<Widget> canvasWidgets;
    List<String> deletedWidgetIds;

}
