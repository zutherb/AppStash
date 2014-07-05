package io.github.appstash.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author zutherb
 */
public class SeoUtils {

    public static String urlFriendly(String urlUnfriendly) {
        String urlFriendly = urlUnfriendly.toLowerCase();
        urlFriendly = urlFriendly.replaceAll("ä", "ae");
        urlFriendly = urlFriendly.replaceAll("ö", "oe");
        urlFriendly = urlFriendly.replaceAll("ü", "ue");
        urlFriendly = urlFriendly.replaceAll("ß", "ss");
        urlFriendly = urlFriendly.replaceAll("ß", "ss");
        urlFriendly = urlFriendly.replaceAll("\\.", "-");
        urlFriendly = urlFriendly.replaceAll(",", "-");
        urlFriendly = urlFriendly.replaceAll("\\s", "-");
        urlFriendly = urlFriendly.replaceAll("[^a-z0-9\\-]+", "");
        urlFriendly = urlFriendly.replaceAll("\\-+", "-");
        if (StringUtils.endsWith(urlFriendly, "-")) {
            return urlFriendly.substring(0, urlFriendly.length() - 1);
        }
        return urlFriendly;
    }

}

