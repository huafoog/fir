package io.github.huafoog.fir.generator.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.huafoog.fir.common.core.util.SpringContextHolder;
import io.github.huafoog.fir.generator.datasource.ChangeDataSourceService;
import io.github.huafoog.fir.generator.datasource.holder.DataSourceContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 多数据源拦截器
 * @Author: 青杉
 * @Date: 8/8/2022 下午 5:51
 */
public class DataSourceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ds = request.getHeader("ds");
        if (!StringUtils.isBlank(ds)){
            if (!ds.equals(DataSourceContextHolder.getDataSource())){
                SpringContextHolder.getBean(ChangeDataSourceService.class).changeDS(ds);
            }
        }else{
            if (DataSourceContextHolder.getDataSource()!=null){
                // 使用默认数据源
                DataSourceContextHolder.removeDataSource();
            }
        }
        return true;
    }
}
