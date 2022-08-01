package io.github.huafoog.fir.generator.controller;

import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import io.github.huafoog.fir.generator.service.GenDatasourceConfService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class TestController {
    private final GenDatasourceConfService genDatasourceConfService;
    @GetMapping("/test")
    public void createDatasource(){
        String url =  String.format("jdbc:mysql://%s:%s/%s?characterEncoding=utf8"
                + "&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true"
                + "&useLegacyDatetimeCode=false&allowMultiQueries=true&allowPublicKeyRetrieval=true","101.35.101.237",3306,"fir_codegen");
        GenDatasourceConf genDatasourceConf = new GenDatasourceConf();
        genDatasourceConf.setName("mysql2");
        genDatasourceConf.setHost("101.35.101.237");
        genDatasourceConf.setPort(3306);
        genDatasourceConf.setUrl(url);
//        genDatasourceConf.setInstance();
        genDatasourceConf.setDsName("mall_pms");
        genDatasourceConf.setUsername("qs");
        genDatasourceConf.setPassword("qs@9eHD10uCzbngPd@001");
        genDatasourceConfService.saveDsByEnc(genDatasourceConf);
    }
}
