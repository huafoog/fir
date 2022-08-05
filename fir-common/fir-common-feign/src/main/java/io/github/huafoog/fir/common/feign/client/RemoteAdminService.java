package io.github.huafoog.fir.common.feign.client;

import io.github.huafoog.fir.common.core.constant.ProjectNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author DELL
 */
@FeignClient(contextId = "remoteAdminService",value = ProjectNameConstants.ADMIN)
public interface RemoteAdminService {
    @GetMapping("/test")
    String test();
}
