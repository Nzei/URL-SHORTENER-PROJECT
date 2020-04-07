package com.uche.url_shortener.repository;

import com.uche.url_shortener.dao.ShortenerMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShortenerRepository extends JpaRepository<ShortenerMapping, Long> {

    boolean existsByShortUrl(String shortUrl);

    ShortenerMapping findShortenerMappingsByShortUrl(String shortUrl);

    ShortenerMapping findShortenerMappingsByLongUrl(String longUrl);

    boolean existsByLongUrl(String longUrl);

    @Query(value = "SELECT * FROM SHORTENER_MAPPING ORDER BY NO_OF_VISITS DESC LIMIT 3", nativeQuery = true)
    List<ShortenerMapping> findTopThreeMappings();

}
