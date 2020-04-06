package com.uche.url_shortener.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MostVisitedLinkResponse {
    private List<LinkVisitsCount> linkVisitsCounts;


}
