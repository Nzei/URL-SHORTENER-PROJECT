package com.uche.url_shortener.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ShortenerMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Short_url")
    private String shortUrl;

    @Column(name = "Long_url", columnDefinition = "MEDIUMTEXT")
    private String longUrl;

    @Column(name = "no_of_visits")
    private Long visits=0L;

    @Column(name = "user_agent")
    private String browserName;

    @Column(name = "Chrome")
    private Long chrome=0L;

    @Column(name = "Firefox")
    private Long firefox=0L;

    @Column(name = "Safari")
    private Long safari=0L;

    @Column(name = "Opera")
    private Long opera=0L;

    @Column(name = "Edge")
    private Long edge=0L;

    @Column(name = "Internet_Explorer")
    private Long internet_explorer=0L;

    @Column(name = "Postman")
    private Long postman=0L;

    @Column(name = "Powershell")
    private Long powershell=0L;

    @Column(name = "Unknown_agents")
    private Long unknown=0L;

    public ShortenerMapping() {
    }
}
