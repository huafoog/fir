package io.github.huafoog.fir.generator.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import io.github.huafoog.fir.generator.datasource.holder.DataSourceContextHolder;
import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.*;
import java.util.Map;
import java.util.Set;

@Slf4j
@Data
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private boolean debug = true;
    /**
     * 存储我们注册的数据源
     */
    private volatile Map<Object, Object> customDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        String datasourceName = DataSourceContextHolder.getDataSource();
        if(datasourceName != null){
            Map<Object, Object> map = this.customDataSources;
            if(map.containsKey(datasourceName)){
                log.info("当前数据源是：{}",datasourceName);
            }else{
                log.info("不存在数据源：{}",datasourceName);
                return null;
}
        }else{
        log.info("当前是默认数据源");
        }
        return datasourceName;
        }


    @Override
    public void setTargetDataSources(Map<Object, Object> param) {

        super.setTargetDataSources(param);
        this.customDataSources = param;
    }


    /**
     * @Description: 检查数据源是否已经创建 未创建将创建一条
     * @param dataSource
     */
    public void checkCreateDataSource(GenDatasourceConf dataSource){
        String dataSourceName = dataSource.getName();
        Map<Object, Object> map = this.customDataSources;
        if(map.containsKey(dataSourceName)){
            //这里检查一下之前创建的数据源，现在是否连接正常
            DruidDataSource druidDataSource = (DruidDataSource) map.get(dataSourceName);
            boolean flag = true;
            DruidPooledConnection connection = null;
            try {
                connection = druidDataSource.getConnection();
            } catch (SQLException throwables) {
                //抛异常了说明连接失效吗，则删除现有连接
                log.error(throwables.getMessage());
                flag = false;
                delDatasources(dataSourceName);
                //
            }finally {
                //如果连接正常记得关闭
                if(null != connection){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                    }
                }
            }
            if(!flag){
                createDataSource(dataSource);
            }
        }else {
            createDataSource(dataSource);
        }
    }

    /**
     * @Description: 创建数据源
     * @param dataSource
     */
    private void createDataSource(GenDatasourceConf dataSource) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
            if(connection==null){
                log.error("数据源配置有错误，DataSource：{}",dataSource);
            }else{
                connection.close();
            }

            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(dataSource.getName());
            druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            druidDataSource.setUrl(dataSource.getUrl());
            druidDataSource.setUsername(dataSource.getUsername());
            druidDataSource.setPassword(dataSource.getPassword());
            druidDataSource.setMaxActive(20);
            druidDataSource.setMinIdle(5);
            //获取连接最大等待时间，单位毫秒
            druidDataSource.setMaxWait(6000);
            String validationQuery = "select 1 from dual";
            //申请连接时执行validationQuery检测连接是否有效，防止取到的连接不可用
            druidDataSource.setTestOnBorrow(true);
            druidDataSource.setValidationQuery(validationQuery);
            druidDataSource.init();
            this.customDataSources.put(dataSource.getId(),druidDataSource);
            // 将map赋值给父类的TargetDataSources
            setTargetDataSources(this.customDataSources);
            // 将TargetDataSources中的连接信息放入resolvedDataSources管理
            super.afterPropertiesSet();
        } catch (Exception e) {
            log.error("数据源创建失败",e);
        }
    }

    /**
     * @Description: 删除数据源
     * @param datasourceName 连接源名称
     */
    public void delDatasources(String datasourceName) {
        Map<Object, Object> map = this.customDataSources;
        Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
        for (DruidDataSource dataSource : druidDataSourceInstances) {
            if (datasourceName.equals(dataSource.getName())) {
                map.remove(datasourceName);
                //从实例中移除当前dataSource
                DruidDataSourceStatManager.removeDataSource(dataSource);
                // 将map赋值给父类的TargetDataSources
                setTargetDataSources(map);
                // 将TargetDataSources中的连接信息放入resolvedDataSources管理
                super.afterPropertiesSet();
            }
        }
    }


}