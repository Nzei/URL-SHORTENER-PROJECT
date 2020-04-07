package com.uche.url_shortener.controller;

import com.uche.url_shortener.model.MostBrowserCreationsRequestResponse;
import com.uche.url_shortener.model.MostPopularBrowserResponse;
import com.uche.url_shortener.model.MostVisitedLinkResponse;
import com.uche.url_shortener.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/most_visits")
    public MostVisitedLinkResponse getMostVisitedSites() {
        return statisticsService.getMostVisitedLinks();
    }

    @GetMapping("/browser_most_created_links")
    public MostBrowserCreationsRequestResponse getMostCreationResponse() {
        return statisticsService.getMostBrowserCreationRequest();

    }

    @GetMapping("/most_popular_browsers")
    public MostPopularBrowserResponse getMostPopularBrowser() {
        return statisticsService.getMostPopularBrowsers();
    }

}
