package io.github.huafoog.fir.common.feign.client;

import io.github.huafoog.fir.common.core.constant.ProjectNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author DELL
 */
@FeignClient(contextId = "remoteAuthService",value = ProjectNameConstants.AUTH)
public interface RemoteAuthService {
    @GetMapping("/test")
    String test();
}
