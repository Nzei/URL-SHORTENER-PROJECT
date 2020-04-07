package com.uche.url_shortener.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MostPopularBrowsers {
    private String browserName;
    private long numberOfVisits;

}
