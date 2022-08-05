package io.github.huafoog.fir.auth.controller;


import io.github.huafoog.fir.common.feign.client.RemoteAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "auth:123";
    }

    private final RemoteAdminService remoteAdminService;
    @GetMapping("/test2")
    public String test2(){
        return remoteAdminService.test();
    }
}
