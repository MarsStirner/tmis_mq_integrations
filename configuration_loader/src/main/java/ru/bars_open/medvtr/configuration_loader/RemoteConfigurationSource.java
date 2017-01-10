package ru.bars_open.medvtr.configuration_loader;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

/**
 * Author: Upatov Egor <br>
 * Date: 09.01.2017, 16:56 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class RemoteConfigurationSource implements ConfigurationSource {

    private String url;
    private String baseUrl;
    private String appName;
    private String method;

    public RemoteConfigurationSource(final JsonNode node, final String appName) {
        final JsonNode url = node.get("url");
        if (url != null && StringUtils.isNotEmpty(url.textValue())) {
            this.url = url.textValue();
        }
        final JsonNode baseUrl = node.get("baseUrl");
        if (baseUrl != null && StringUtils.isNotEmpty(baseUrl.textValue())) {
            this.baseUrl = baseUrl.textValue();
        }
        final JsonNode appNameNode = node.get("appName");
        if (appNameNode != null && StringUtils.isNotEmpty(appNameNode.textValue())) {
            this.appName = appNameNode.textValue();
        } else {
            this.appName = appName;
        }
        final JsonNode method = node.get("method");
        if (method != null && StringUtils.isNotEmpty(method.textValue())) {
            this.method = method.textValue().toUpperCase();
        }
        if (this.method == null) {
            this.method = "GET";
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("remote[");
        sb.append(method).append(' ').append(getFullUrl());
        sb.append(']');
        return sb.toString();
    }

    private String getFullUrl() {
        if(StringUtils.isNotEmpty(url)) {
            return url;
        } else {
            return baseUrl + "/" + appName;
        }
    }

    @Override
    public boolean isValid() {
        return StringUtils.isNotEmpty(url) || (StringUtils.isNotEmpty(baseUrl) && StringUtils.isNotEmpty(appName));
    }

    @Override
    public String getResourceType() {
        return getType();
    }

    public static final String getType() {
        return "remote";
    }


}
