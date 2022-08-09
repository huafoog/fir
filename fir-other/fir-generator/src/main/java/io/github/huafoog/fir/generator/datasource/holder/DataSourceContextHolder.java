package io.github.huafoog.fir.generator.datasource.holder;

import lombok.extern.slf4j.Slf4j;

/**
 * 多数据源
 * @author shan
 */
@Slf4j
public class DataSourceContextHolder {

    /**
     * 线程级别的私有变量
     */
    private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<>();

    /**
     * 切换数据源
     */
    public static void setDataSource(String datasourceName) {
        CONTEXTHOLDER.set(datasourceName);
        log.info("已切换到数据源:{}",datasourceName);
    }

    public static String getDataSource() {
        return CONTEXTHOLDER.get();
    }


    /**
     * 删除数据源
     */
    public static void removeDataSource() {
        CONTEXTHOLDER.remove();
        log.info("移除当前数据源，使用默认数据源");
    }
}