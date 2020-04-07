package com.uche.url_shortener.service;

import com.uche.url_shortener.dao.ShortenerMapping;
import com.uche.url_shortener.model.Browsers;
import com.uche.url_shortener.model.UrlShortenerResponse;
import com.uche.url_shortener.repository.ShortenerRepository;
import com.uche.url_shortener.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UrlShortenerService {

    private String serviceUrl;
    private HttpServletRequest httpServletRequest;

    @Value("${get.request.path}")
    String getMappingRequestPath;

    @Autowired
    private ShortenerRepository shortenerRepository;

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.serviceUrl = httpServletRequest.getHeader("host") + httpServletRequest.getRequestURI().split(getMappingRequestPath)[0];

    }


    /**
     * A method to generate the short url form the given long url and returns it in the 'UrlShortenerResponse'
     *
     * @param longUrl
     * @return UrlShortenerResponse
     */
    public UrlShortenerResponse generateShortUrl(String longUrl) {
        UrlShortenerResponse urlShortenerResponse = new UrlShortenerResponse();
        String shortUrl = generateValidatedShortUrl(longUrl);
        urlShortenerResponse.setShortUrl(shortUrl);
        urlShortenerResponse.setAgent(getBrowserAgent().toString());
        //Saving to database before returning
        ShortenerMapping shortenerMappings = new ShortenerMapping();
        shortenerMappings.setLongUrl(longUrl);
        shortenerMappings.setShortUrl(shortUrl);
        shortenerMappings.setBrowserName(getBrowserAgent().toString());
        if (!shortenerRepository.existsByLongUrl(longUrl)) {
            persist(shortenerMappings);
        }
        return urlShortenerResponse;
    }

    private Browsers getBrowserAgent() {
        return Utils.getBrowserName(httpServletRequest);
    }

    private void persist(ShortenerMapping shortenerMappings) {
        this.shortenerRepository.save(shortenerMappings);
    }

    /**
     * A method to generated a validated short key and returns it in a desired url format
     * It checks for existing long urls before creating new short keys and ensures that no two different url formats have the same short url
     *
     * @param longUrl
     * @return short url
     */
    private String generateValidatedShortUrl(String longUrl) {
        if (shortenerRepository.existsByLongUrl(longUrl))
            return shortenerRepository.findShortenerMappingsByLongUrl(longUrl).getShortUrl();
        String shortUrlKey = Utils.generateShortKey(longUrl);
        while (shortenerRepository.existsByShortUrl(serviceUrl + "/" + shortUrlKey)) {
            shortUrlKey = generateValidatedShortUrl(longUrl);
        }
        return serviceUrl + "/" + shortUrlKey;
    }

    public String getLongUrl(String shortUrl) {
        boolean shortUrlExist = shortenerRepository.existsByShortUrl(shortUrl);
        if (!shortUrlExist) {
            return null;
        }
        String longUrl = shortenerRepository.findShortenerMappingsByShortUrl(shortUrl).getLongUrl();
        return longUrl;
    }


    /**
     * A method that increases the number of visits by browser for each link
     * * @param shortUrl
     */
    public void increaseNumberOfVisit(String shortUrl) {
        ShortenerMapping shortenerMappings = shortenerRepository.findShortenerMappingsByShortUrl(shortUrl);
        shortenerMappings.setVisits(shortenerMappings.getVisits() + 1);
        Browsers currentVisitingBrowsers = getBrowserAgent();
        if (currentVisitingBrowsers == Browsers.CHROME) {
            shortenerMappings.setChrome(shortenerMappings.getChrome() + 1);
        } else if (currentVisitingBrowsers == Browsers.FIREFOX) {
            shortenerMappings.setFirefox(shortenerMappings.getFirefox() + 1);
        } else if (currentVisitingBrowsers == Browsers.SAFARI) {
            shortenerMappings.setSafari(shortenerMappings.getSafari() + 1);
        } else if (currentVisitingBrowsers == Browsers.OPERA) {
            shortenerMappings.setOpera(shortenerMappings.getOpera() + 1);
        } else if (currentVisitingBrowsers == Browsers.EDGE) {
            shortenerMappings.setEdge(shortenerMappings.getEdge() + 1);
        } else if (currentVisitingBrowsers == Browsers.INTERNET_EXPLORER) {
            shortenerMappings.setInternet_explorer(shortenerMappings.getInternet_explorer() + 1);
        } else if (currentVisitingBrowsers == Browsers.POSTMAN) {
            shortenerMappings.setPostman(shortenerMappings.getPostman() + 1);
        } else if (currentVisitingBrowsers == Browsers.POWERSHELL) {
            shortenerMappings.setPowershell(shortenerMappings.getPowershell() + 1);
        } else {
            shortenerMappings.setUnknown(shortenerMappings.getUnknown() + 1);
        }
        shortenerRepository.save(shortenerMappings);
    }


}
