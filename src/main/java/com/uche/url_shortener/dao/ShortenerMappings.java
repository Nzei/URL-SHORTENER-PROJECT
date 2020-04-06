package com.uche.url_shortener.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ShortenerMappings {

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
    private String browsers;

    public ShortenerMappings() {
    }
}
