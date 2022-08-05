package io.github.huafoog.fir.gateway.Provider;

import io.github.huafoog.fir.common.core.constant.enums.IBaseEnum;
import io.github.huafoog.fir.common.core.constant.enums.ProjectEnum;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 青杉
 */
@Component
@AllArgsConstructor
public class FirSwaggerResourceProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources
                                .add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String url) {
        SwaggerResource swaggerResource = new SwaggerResource();
        ProjectEnum enumByCode = IBaseEnum.getEnumByCode(ProjectEnum.class, name);
        if (enumByCode != null){
            swaggerResource.setName(enumByCode.desc());
        }else{
            swaggerResource.setName(name);
        }
        swaggerResource.setUrl(url);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
