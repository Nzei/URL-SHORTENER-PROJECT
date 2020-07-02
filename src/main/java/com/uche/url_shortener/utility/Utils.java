package com.uche.url_shortener.utility;

import com.uche.url_shortener.model.Browsers;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class Utils {

    /**
     * A method to generate short key using the first 2 letters, last letter and a random 4 digit number where applicable
     *
     * @param longUrl
     * @return shortKey
     */
    public static String generateShortKey(String longUrl) {
        if(longUrl == null) return "";
        longUrl = getWebsiteName(longUrl);
        if (longUrl.length() <= 2) {
            return longUrl.toLowerCase();
        }

        String shortUrl = "";

        shortUrl += (longUrl.substring(0, 2) + longUrl.substring(longUrl.length() - 1)).toLowerCase();

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            shortUrl += String.valueOf(random.nextInt(10));
        }
        return shortUrl;
    }

    /**
     * A method to extract the website url excluding the default format 'http://' and 'www'  url inputted
     * http://www.google.com returns the string 'google.com'
     *
     * @param websiteName
     * @return Website url
     */
    private static String getWebsiteName(String websiteName) {
        websiteName = websiteName.toLowerCase();
        if (websiteName.contains("http") || websiteName.contains("www")) {
            websiteName = websiteName.substring(websiteName.indexOf(".") + 1);
        }
        return websiteName;
    }


    /**
     * A method to extract  browser types based on userAgent information as contained in the request.
     * This method is implemented as shown in the link below:
     *
     * @param httpServletRequest
     * @return browserName
     * @see <a href="http://uadetector.sourceforge.net/quickstart.html"> UA Detector website</a>
     * @see <a href="http://uadetector.sourceforge.net/usage.html"> Implementation </a>
     */
    public static Browsers getBrowserName(HttpServletRequest httpServletRequest) {
        String userAgent = httpServletRequest.getHeader("User-Agent");

        UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
        ReadableUserAgent agent = parser.parse(httpServletRequest.getHeader("User-Agent"));

        String browserName = capitalizeFirstLetter(agent.getName());

        if (userAgent.toLowerCase().contains("edge")) return Browsers.EDGE;
        if (userAgent.toLowerCase().contains("powershell")) return Browsers.POWERSHELL;

        if (browserName.equals(Browsers.CHROME.toString())) {
            return Browsers.CHROME;
        } else if (browserName.equals(Browsers.FIREFOX.toString())) {
            return Browsers.FIREFOX;
        } else if (browserName.equals(Browsers.SAFARI.toString())) {
            return Browsers.SAFARI;
        } else if (browserName.equals(Browsers.OPERA.toString())) {
            return Browsers.OPERA;
        }
        return getBrowserNameAdvanced(httpServletRequest);

    }

    /**
     * A method to extract  browser types based on userAgent information as contained in the request.
     * This method is implemented as shown in the link below:
     *
     * @param httpServletRequest
     * @return browserName
     * @see <a href="https://stackoverflow.com/questions/1326928/how-can-i-get-client-information-such-as-os-and-browser/18030465#answer-18030465"> Stack Overflow Link</a>
     */
    private static Browsers getBrowserNameAdvanced(HttpServletRequest httpServletRequest) {
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String user = userAgent.toLowerCase();
        String browser = "";

        //===============Browser===========================
        if (user.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera"))
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if (user.contains("opr"))
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            browser = "IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else {
            browser = "UnKnown, More-Info: " + userAgent;
        }

        if (browser.toLowerCase().contains("ie")) {
            return Browsers.INTERNET_EXPLORER;
        } else if (browser.toLowerCase().contains("postman")) {
            return Browsers.POSTMAN;
        }
        return Browsers.UNKNOWN;
    }

    /**
     * A method to capitalize the first letter of input string
     *
     * @param entry
     * @return String with only first letter capitalized
     */
    public static String capitalizeFirstLetter(String entry) {
        if (entry == null) return null;
        if (entry.isEmpty() || entry.length() == 1) return entry;
        return entry.substring(0, 1).toUpperCase() + entry.substring(1).toLowerCase();
    }
}
