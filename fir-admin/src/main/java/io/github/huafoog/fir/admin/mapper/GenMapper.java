package io.github.huafoog.fir.admin.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 支持 mysql 代码生成器
 * @author shan
 */
@Mapper
public interface GenMapper {
    /**
     * @return
     */
    List<Map<String,String>> selectTableColumn();
}
