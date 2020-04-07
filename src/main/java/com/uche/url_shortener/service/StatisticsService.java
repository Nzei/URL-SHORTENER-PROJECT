package com.uche.url_shortener.service;

import com.uche.url_shortener.dao.ShortenerMapping;
import com.uche.url_shortener.model.*;
import com.uche.url_shortener.repository.ShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    private ShortenerRepository shortenerRepository;


    /**
     * A method that fetches the links with the most visits
     *
     * @return MostVisitedLinkResponse
     */
    public MostVisitedLinkResponse getMostVisitedLinks() {
        List<ShortenerMapping> shortenerMappings = shortenerRepository.findTopThreeMappings();
        List<LinkVisitsCount> linkVisitsCounts = new ArrayList<>();
        for (ShortenerMapping mappings : shortenerMappings) {
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


    /**
     * A method that fetches the top 3 browsers used in creating short urls
     *
     * @return MostBrowserCreationsRequestResponse
     */
    public MostBrowserCreationsRequestResponse getMostBrowserCreationRequest() {
        Map<String, Long> creationRequestMap = new HashMap<>();
        List<ShortenerMapping> allShortenerMappings = shortenerRepository.findAll();

        for (ShortenerMapping shortenerMapping : allShortenerMappings) {
            String key = shortenerMapping.getBrowserName();
            if (creationRequestMap.containsKey(key)) {
                creationRequestMap.put(key, creationRequestMap.get(key) + 1);
            } else {
                creationRequestMap.put(key, 1L);
            }
        }

        List<BrowserCreationRequest> browserCreationRequests = new ArrayList<>();
        for (Map.Entry<String, Long> entry : creationRequestMap.entrySet()) {
            BrowserCreationRequest browserCreationRequest = new BrowserCreationRequest();
            browserCreationRequest.setBrowserName(entry.getKey());
            browserCreationRequest.setNumberOfLinks(entry.getValue());
            browserCreationRequests.add(browserCreationRequest);
        }
        MostBrowserCreationsRequestResponse mostBrowserCreationsRequestResponse = new MostBrowserCreationsRequestResponse();
        mostBrowserCreationsRequestResponse.setBrowserCreationRequests(browserCreationRequests);
        return mostBrowserCreationsRequestResponse;
    }


    /**
     * A method that fetches the most popular browsers
     *
     * @return
     */
    public MostPopularBrowserResponse getMostPopularBrowsers() {
        Map<String, Long> listOfBrowsers = new HashMap<>();
        List<ShortenerMapping> shortenerMappings = shortenerRepository.findAll();

        List<String> browserTypes = Arrays.asList(new String[]{Browsers.POWERSHELL.toString(), Browsers.UNKNOWN.toString(),
                Browsers.POSTMAN.toString(), Browsers.INTERNET_EXPLORER.toString(), Browsers.CHROME.toString(),
                Browsers.FIREFOX.toString(), Browsers.OPERA.toString(), Browsers.SAFARI.toString(), Browsers.EDGE.toString()});

        for (ShortenerMapping shortenerMapping : shortenerMappings) {
            List<Long> individualBrowserValues = Arrays.asList(new Long[]{shortenerMapping.getPowershell(), shortenerMapping.getUnknown(), shortenerMapping.getPostman(),
                    shortenerMapping.getInternet_explorer(), shortenerMapping.getChrome(), shortenerMapping.getFirefox(), shortenerMapping.getOpera(),
                    shortenerMapping.getSafari(), shortenerMapping.getEdge()});

            for (int i = 0; i < browserTypes.size(); i++) {
                String key = browserTypes.get(i);
                if (listOfBrowsers.containsKey(key)) {
                    listOfBrowsers.put(key, listOfBrowsers.get(key) + individualBrowserValues.get(i));
                } else {
                    listOfBrowsers.put(key, individualBrowserValues.get(i));
                }
            }

        }

        List<MostPopularBrowsers> listOfMostPopularBrowsers = new ArrayList<>();

        for (Map.Entry<String, Long> entry : listOfBrowsers.entrySet()) {
            MostPopularBrowsers mostPopularBrowsers = new MostPopularBrowsers();
            mostPopularBrowsers.setBrowserName(entry.getKey());
            mostPopularBrowsers.setNumberOfVisits(entry.getValue());
            listOfMostPopularBrowsers.add(mostPopularBrowsers);
        }
        listOfMostPopularBrowsers.sort(new MostPopularBrowsersComparator());
        MostPopularBrowserResponse listOfMostPopularBrowser = new MostPopularBrowserResponse();
        listOfMostPopularBrowser.setMostPopularBrowsers(listOfMostPopularBrowsers);
        return listOfMostPopularBrowser;
    }

    private class MostPopularBrowsersComparator implements Comparator<MostPopularBrowsers> {

        @Override
        public int compare(MostPopularBrowsers o1, MostPopularBrowsers o2) {
            if (o1.getNumberOfVisits() > o2.getNumberOfVisits()) {
                return -1;
            } else if (o1.getNumberOfVisits() < o2.getNumberOfVisits()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
