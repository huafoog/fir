package io.github.huafoog.fir.auth.controller;

//import com.zyaxin.fir.common.core.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//只需要在需要动态读取配置的类上添加此注解就可以
public class NacosConfigController {
    @Value("${config.appName}")
    private String appName;
    //2 注解方式
    @GetMapping("/config")
    public String nacosConfingTest2() {
        return appName;
    }
}


