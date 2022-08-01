package io.github.huafoog.fir.generator;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.huafoog.fir.generator.datasource.ChangeDataSourceService;
import io.github.huafoog.fir.generator.datasource.DsJdbcUrlEnum;
import io.github.huafoog.fir.generator.datasource.holder.DataSourceContextHolder;
import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import io.github.huafoog.fir.generator.mapper.GeneratorMapper;
import io.github.huafoog.fir.generator.service.GenDatasourceConfService;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Tests {
    @Autowired
    private ChangeDataSourceService changeDataSourceService;
    @Autowired
    private GenDatasourceConfService genDatasourceConfService;

    @Autowired
    private GeneratorMapper generatorMapper;

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void jasypt(){
        String name = encryptor.encrypt("hello");
        System.out.println("en: " + name);
        System.out.println("de: " + encryptor.decrypt(name));
    }


    @Test
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
    @Test
    public void test(){
        String datasourceName = "mysql1";
        changeDataSourceService.changeDS(datasourceName);
        GenDatasourceConf one = genDatasourceConfService.getOne(Wrappers.<GenDatasourceConf>lambdaQuery().eq(GenDatasourceConf::getName, datasourceName));
        Map<String, String> stringStringMap = generatorMapper.queryAllFieldsByTableName(one.getDsName(), null);
        System.out.println(stringStringMap.toString());
        datasourceName = "mysql2";
        changeDataSourceService.changeDS(datasourceName);
        GenDatasourceConf two = genDatasourceConfService.getOne(Wrappers.<GenDatasourceConf>lambdaQuery().eq(GenDatasourceConf::getName, datasourceName));
        generatorMapper.queryAllFieldsByTableName(two.getDsName(),null);
        //切回主数据源
        DataSourceContextHolder.removeDataSource();
    }
}
