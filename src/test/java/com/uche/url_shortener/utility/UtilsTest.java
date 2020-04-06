package com.uche.url_shortener.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    void generateShortUrl() {

        // case insensitive
        assertEquals("goe", Utils.generateShortUrl("https://www.google.com/search?hl=en&suge").substring(0,3));

        //case sensitive
        assertEquals("gob", Utils.generateShortUrl("https://mail.google.com/mail/u/0/#inbox/FMfcgxwHMZQrPZzzhGCTsfNbFvdjKnvb").substring(0,3));

        //empty string
        assertEquals("", Utils.generateShortUrl(""));

        //two strings
        assertEquals("uc",Utils.generateShortUrl("Uc"));
    }

    @Test
    void capitalizeFirstLetter() {
        assertEquals("Neueda", Utils.capitalizeFirstLetter("NEUEDA"));
        assertEquals("", Utils.capitalizeFirstLetter(""));
        assertEquals("u", Utils.capitalizeFirstLetter("u"));
    }
}
