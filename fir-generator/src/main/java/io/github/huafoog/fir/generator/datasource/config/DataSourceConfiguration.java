package io.github.huafoog.fir.generator.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.github.huafoog.fir.generator.datasource.DynamicRoutingDataSource;
import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import io.github.huafoog.fir.generator.handlers.MybatisPlusMetaObjectHandler;
import io.github.huafoog.fir.generator.util.SqlUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfiguration {

    @Autowired
    private DruidDBProperties druidDBProperties;

    @Bean
    @Primary
    @Qualifier("mainDataSource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        // 基础连接信息
        datasource.setUrl(druidDBProperties.getDbUrl());
        datasource.setUsername(druidDBProperties.getUsername());
        datasource.setPassword(druidDBProperties.getPassword());
        datasource.setDriverClassName(druidDBProperties.getDriverClassName());
        // 连接池连接信息
        datasource.setInitialSize(druidDBProperties.getInitialSize());
        datasource.setMinIdle(druidDBProperties.getMinIdle());
        datasource.setMaxActive(druidDBProperties.getMaxActive());
        datasource.setMaxWait(druidDBProperties.getMaxWait());
        //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        datasource.setPoolPreparedStatements(false);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
        datasource.setTestOnBorrow(true);
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        datasource.setTestWhileIdle(true);
        //用来检测连接是否有效的sql
        datasource.setValidationQuery("select 1 from dual");
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
        datasource.setMinEvictableIdleTimeMillis(180000);
        datasource.setKeepAlive(true);
        return datasource;
    }

    @SneakyThrows
    @Bean(name = "dynamicDataSource")
    @Qualifier("dynamicDataSource")
    public DynamicRoutingDataSource dynamicDataSource() throws SQLException {
        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        dynamicDataSource.setDebug(false);
        DataSource dataSource = dataSource();
        //配置缺省的数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //额外数据源配置 TargetDataSources
        targetDataSources.put("mainDataSource", dataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);


        Connection connection = dataSource.getConnection();
        List<Map<String, Object>> data = SqlUtils.executeGet(connection, null, "select * from gen_datasource_conf");
        List<GenDatasourceConf> genDatasourceConfs = SqlUtils.parserDbo(data, GenDatasourceConf.class, true);
        System.out.println(genDatasourceConfs.toString());
        return dynamicDataSource;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //用mybatis的这里会有点区别，mybatis用的是SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        // 多数据库MybatisPlusMetaObjectHandler失效 重新配置
        sqlSessionFactoryBean.setGlobalConfig(new GlobalConfig().setBanner(false).setMetaObjectHandler(new MybatisPlusMetaObjectHandler()));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * @Description: 将动态数据加载类添加到事务管理器
     * @param dataSource
     * @return org.springframework.jdbc.datasource.DataSourceTransactionManager
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicRoutingDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
