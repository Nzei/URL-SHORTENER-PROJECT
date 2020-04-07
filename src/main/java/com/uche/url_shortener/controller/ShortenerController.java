package com.uche.url_shortener.controller;

import com.uche.url_shortener.model.UrlShortenerRequest;
import com.uche.url_shortener.model.UrlShortenerResponse;
import com.uche.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api") //Entry point
public class ShortenerController {
    @Value("${get.request.path}")
    String getMappingRequestPath;

    @Autowired
    private UrlShortenerService urlShortenerService;


    @PostMapping("${get.request.path}")
    public UrlShortenerResponse generateUrl(@RequestBody UrlShortenerRequest urlShortenerRequest, HttpServletRequest httpServletRequest) {
        String longUrl = urlShortenerRequest.getLongUrl().trim();
        urlShortenerService.setServletRequest(httpServletRequest);
        return urlShortenerService.generateShortUrl(longUrl);
    }

    @GetMapping("{key}")
    public ModelAndView forwardToLongUrl(@PathVariable("key") String key, HttpServletRequest httpServletRequest, HttpServletResponse resp) throws IOException {
        String shortUrl = (httpServletRequest.getHeader("host") + httpServletRequest.getRequestURI()).trim();
        urlShortenerService.setServletRequest(httpServletRequest);
        String longUrl = urlShortenerService.getLongUrl(shortUrl).trim();
        if (!(longUrl == null)) {
            urlShortenerService.increaseNumberOfVisit(shortUrl);
            return new ModelAndView("redirect:" + longUrl);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }


}
