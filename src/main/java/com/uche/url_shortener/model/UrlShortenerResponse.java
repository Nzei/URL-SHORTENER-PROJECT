package com.uche.url_shortener.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlShortenerResponse {
    private String shortUrl;
    private String agent;
}
