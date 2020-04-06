package com.uche.url_shortener.repository;

import com.uche.url_shortener.dao.ShortenerMappings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortenerRepository extends JpaRepository<ShortenerMappings, Long> {

    boolean existsByShortUrl(String shortUrl);

    ShortenerMappings findShortenerMappingsByShortUrl(String shortUrl);


}
