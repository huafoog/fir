package io.github.huafoog.fir.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.generator.entity.ColumnEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 支持 mysql 代码生成器
 * @author shan
 */
@Mapper
public interface GeneratorMapper {
    /**
     * 分页查询表格
     * @param page 分页信息
     * @param tableName 表名称
     * @return
     */
    IPage<Map<String, Object>> queryTable(Page page, @Param("tableName") String tableName);

    Map<String,String> queryAllFieldsByTableName(@Param("database") String database,@Param("tableName") String tableName);

    /**
     * 查询表信息
     * @param tableName 表名称
     * @param dsName 数据源名称
     * @return
     */
    Map<String, String> queryTable(@Param("tableName") String tableName, String dsName);

    /**
     * 分页查询表分页信息
     * @param page
     * @param tableName
     * @param dsName
     * @return
     */
    IPage<ColumnEntity> selectTableColumn(Page page, @Param("tableName") String tableName,
                                          @Param("dsName") String dsName);

    /**
     * 查询表全部列信息
     * @param tableName
     * @param dsName
     * @return
     */
    List<ColumnEntity> selectTableColumn(@Param("tableName") String tableName, @Param("dsName") String dsName);

    /**
     * 查询表全部列信息
     * @param tableName 表名称
     * @param dsName 数据源名称
     * @return
     */
    List<Map<String, String>> selectMapTableColumn(@Param("tableName") String tableName, String dsName);
}
