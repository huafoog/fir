package io.github.huafoog.fir.generator.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import io.github.huafoog.fir.common.core.util.CollectionBeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlUtils {

    // 执行select
    public static List<Map<String, Object>> executeGet(Connection connection,String schema, String sql) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Map<String, Object>> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    dataMap.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }                resultList.add(dataMap);
            }
            return resultList;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    //执行update，insert，delete
    public static int executeUpdate(Connection connection,String schema, String sql) throws SQLException {
        Statement statement = null;
        try {
            connection.setCatalog(schema);
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    //将结果集转换为对象
    public static <T> List<T> parserDbo(List<Map<String, Object>> dboMapList, Class<T> dboClass, boolean canEmpty) throws Exception {
        if (CollectionUtil.isEmpty(dboMapList)) {
            if (!canEmpty) {
                throw new Exception("");
            }
            dboMapList = new ArrayList<>();
        }
        List<T> resultList = new ArrayList<>();
        for (Map<String, Object> dboMap : dboMapList) {
            resultList.add(BeanUtil.mapToBean(dboMap, dboClass, true, CopyOptions.create()));
        }
        return resultList;
    }


}

