package com.uche.url_shortener.service;

import com.uche.url_shortener.dao.ShortenerMapping;
import com.uche.url_shortener.model.BrowserCreationRequest;
import com.uche.url_shortener.model.LinkVisitsCount;
import com.uche.url_shortener.model.MostBrowserCreationsRequestResponse;
import com.uche.url_shortener.model.MostVisitedLinkResponse;
import com.uche.url_shortener.repository.ShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private ShortenerRepository shortenerRepository;

    public MostVisitedLinkResponse getMostVisitedLinks () {
        List<ShortenerMapping> shortenerMappings = shortenerRepository.findTopThreeMappings();
        List<LinkVisitsCount> linkVisitsCounts = new ArrayList<>();
        for (ShortenerMapping mappings : shortenerMappings ) {
            LinkVisitsCount linkVisitsCount = new LinkVisitsCount();
            linkVisitsCount.setLongUrl(mappings.getLongUrl());
            linkVisitsCount.setShortUrl(mappings.getShortUrl());
            linkVisitsCount.setNumberOfVisits(mappings.getVisits());
            linkVisitsCounts.add(linkVisitsCount);
        }
        MostVisitedLinkResponse mostVisitedLinksResponse = new MostVisitedLinkResponse();
        mostVisitedLinksResponse.setLinkVisitsCounts(linkVisitsCounts);
        return mostVisitedLinksResponse;
    }

    public MostBrowserCreationsRequestResponse getMostBrowserCreationRequest() {
        Map<String, Long> creationRequestMap = new HashMap<>();
        List<ShortenerMapping> allShortenerMappings = shortenerRepository.findAll();

        for(ShortenerMapping shortenerMapping : allShortenerMappings) {
            String key = shortenerMapping.getBrowserName();
            if (creationRequestMap.containsKey(key)) {
                creationRequestMap.put(key, creationRequestMap.get(key) + 1);
            }
            else{
                creationRequestMap.put(key, 1L);
            }
        }
        List <BrowserCreationRequest> browserCreationRequests = new ArrayList<>();
        for(Map.Entry <String, Long> entry : creationRequestMap.entrySet()) {
            BrowserCreationRequest browserCreationRequest = new BrowserCreationRequest();
            browserCreationRequest.setBrowserName(entry.getKey());
            browserCreationRequest.setNumberOfLinks(entry.getValue());
            browserCreationRequests.add(browserCreationRequest);
        }
        MostBrowserCreationsRequestResponse mostBrowserCreationsRequestResponse = new MostBrowserCreationsRequestResponse();
        mostBrowserCreationsRequestResponse.setBrowserCreationRequests(browserCreationRequests);
        return mostBrowserCreationsRequestResponse;
    }
}
