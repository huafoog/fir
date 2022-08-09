package io.github.huafoog.fir.generator.datasource;

import io.github.huafoog.fir.generator.datasource.holder.DataSourceContextHolder;
import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import io.github.huafoog.fir.generator.mapper.GenDatasourceConfMapper;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author YZY
 * @date 2021年05月28日 9:46
 */

@Service
public class ChangeDataSourceServiceImpl implements ChangeDataSourceService {

    @Autowired
    private GenDatasourceConfMapper dataSourceMapper;

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Autowired
    private StringEncryptor encryptor;

    @Override
    public boolean changeDS(String datasourceId) {
        //切到默认数据源
        DataSourceContextHolder.removeDataSource();
        //找到所有的配置
        List<GenDatasourceConf> dataSourceList = dataSourceMapper.selectList(null);

        if(!CollectionUtils.isEmpty(dataSourceList)){
            for (GenDatasourceConf dataSource : dataSourceList) {
                if(dataSource != null && dataSource.getName().equals(datasourceId)){
                    System.out.println("已找到数据源,datasource是：" + datasourceId);
                    dataSource.setPassword(encryptor.decrypt(dataSource.getPassword()));
                    //判断连接是否存在，不存在就创建
                    dynamicRoutingDataSource.checkCreateDataSource(dataSource);
                    //切换数据源
                    DataSourceContextHolder.setDataSource(dataSource.getName());
                    return true;
                }
            }
        }
        return false;
    }
}