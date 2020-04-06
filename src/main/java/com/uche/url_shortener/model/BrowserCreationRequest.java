package com.uche.url_shortener.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrowserCreationRequest {
    private String browserName;
    private Long numberOfLinks;

}
