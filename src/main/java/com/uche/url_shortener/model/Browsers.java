package com.uche.url_shortener.model;

public enum Browsers {
    CHROME {
        @Override
        public String toString() {
            return "Chrome";
        }
    },
    OPERA {
        @Override
        public String toString() {
            return "Opera";
        }
    },
    FIREFOX {
        @Override
        public String toString() {
            return "Firefox";
        }
    },
    SAFARI {
        @Override
        public String toString() {
            return "Safari";
        }
    },
    EDGE {
        @Override
        public String toString() {
            return "Edge";
        }
    },
    INTERNET_EXPLORER {
        @Override
        public String toString() {
            return "Internet Explorer";
        }
    },
    POSTMAN {
        @Override
        public String toString() {
            return "Postman";
        }
    },
    UNKNOWN {
        @Override
        public String toString() {
            return "Unknown Browser";
        }
    },
    POWERSHELL {
        @Override
        public String toString() {
            return "PowerShell";
        }
    }
}
