package com.uche.url_shortener.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkVisitsCount {
    private String shortUrl;
    private String longUrl;
    private Long numberOfVisits;

    @Override
    public String toString() {
        return "LinkVisitsCounts{" +
                "shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", numberOfVisits=" + numberOfVisits +
                '}';
    }
}
