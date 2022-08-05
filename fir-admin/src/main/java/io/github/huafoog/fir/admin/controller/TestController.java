package io.github.huafoog.fir.admin.controller;


import io.github.huafoog.fir.common.feign.client.RemoteAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(value = "测试",tags = "测试接口")
public class TestController {

    private final RemoteAuthService remoteAuthService;

    @GetMapping("/test")
    @ApiOperation("测试1")
    public String test(){
        return "admin:123";
    }

    @GetMapping("/test2")
    @ApiOperation("测试2")
    public String test2(){
        return remoteAuthService.test();
    }
}
