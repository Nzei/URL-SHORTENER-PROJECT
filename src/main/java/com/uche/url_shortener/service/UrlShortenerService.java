package com.uche.url_shortener.service;

import com.uche.url_shortener.dao.ShortenerMappings;
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

    public void setServiceUrl(HttpServletRequest httpServletRequest) {
        this.serviceUrl = httpServletRequest.getHeader("host") + httpServletRequest.getRequestURI().split(getMappingRequestPath)[0];
        this.httpServletRequest = httpServletRequest;
    }

    public UrlShortenerResponse generateShortUrl (String longUrl) {
        UrlShortenerResponse urlShortenerResponse = new UrlShortenerResponse();
        String shortUrl = serviceUrl + "/" + generateValidatedShortUrl(longUrl);
        urlShortenerResponse.setShortUrl(shortUrl);
        urlShortenerResponse.setAgent(getBrowserAgent().toString());
        ShortenerMappings shortenerMappings = new ShortenerMappings();
        shortenerMappings.setLongUrl(longUrl);
        shortenerMappings.setShortUrl(shortUrl);
        shortenerMappings.setBrowsers(getBrowserAgent().toString());
        persist(shortenerMappings);
        return urlShortenerResponse;
    }

    private Browsers getBrowserAgent () {
        return Utils.getBrowserName(httpServletRequest);
    }

    private void persist (ShortenerMappings shortenerMappings) {
        this.shortenerRepository.save(shortenerMappings);
    }

    private String generateValidatedShortUrl (String longUrl) {
        String shortUrl = Utils.generateShortUrl(longUrl);
        while (shortenerRepository.existsByShortUrl(shortUrl)) {
            shortUrl = generateValidatedShortUrl(longUrl);
        }
        return shortUrl;
    }

    public String getLongUrl (String shortUrl) {
        boolean shortUrlExist = shortenerRepository.existsByShortUrl(shortUrl);
        if (!shortUrlExist) {
            return null;
        }
        String longUrl = shortenerRepository.findShortenerMappingsByShortUrl(shortUrl).getLongUrl();
        return longUrl;
    }

    public void increaseNumberOfVisit (String shortUrl) {
        ShortenerMappings shortenerMappings = shortenerRepository.findShortenerMappingsByShortUrl(shortUrl);
        shortenerMappings.setVisits(shortenerMappings.getVisits()+1);
        shortenerRepository.save(shortenerMappings);
    }


}
