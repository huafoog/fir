package io.github.huafoog.fir.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "system")
public class UrlConfig {
    // 配置文件使用list接收
    private List<String> whiteList;

    public List<String> getWhiteList() {
        return whiteList;
    }
    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}