package io.github.huafoog.fir.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.huafoog.fir.common.core.exception.BusinessException;
import io.github.huafoog.fir.common.core.util.SpringContextHolder;
import io.github.huafoog.fir.generator.datasource.DynamicRoutingDataSource;
import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import io.github.huafoog.fir.generator.mapper.GenDatasourceConfMapper;
import io.github.huafoog.fir.generator.service.GenDatasourceConfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据源表
 */
@Slf4j
@Service
@AllArgsConstructor
public class GenDatasourceConfServiceImpl extends ServiceImpl<GenDatasourceConfMapper, GenDatasourceConf>
		implements GenDatasourceConfService {

	private final StringEncryptor stringEncryptor;

	private final DynamicRoutingDataSource dynamicRoutingDataSource;

	/**
	 * 保存数据源并且加密
	 * @param conf
	 * @return
	 */
	@Override
	public Boolean saveDsByEnc(GenDatasourceConf conf) {

		if (StringUtils.isBlank(conf.getName())){
		    throw new BusinessException("请输入数据库别名");
		}

		Long aLong = baseMapper.selectCount(Wrappers.<GenDatasourceConf>lambdaQuery().eq(GenDatasourceConf::getName, conf.getName()));

		if (aLong > 0){
			throw new BusinessException("数据库别名重复");
		}

		// 校验配置合法性
		if (!checkDataSource(conf)) {
			return Boolean.FALSE;
		}

		// 添加动态数据源
		addDynamicDataSource(conf);

		// 更新数据库配置
		conf.setPassword(stringEncryptor.encrypt(conf.getPassword()));
		this.baseMapper.insert(conf);
		return Boolean.TRUE;
	}

	/**
	 * 更新数据源
	 * @param conf 数据源信息
	 * @return
	 */
	@Override
	public Boolean updateDsByEnc(GenDatasourceConf conf) {

		Long aLong = baseMapper.selectCount(Wrappers.<GenDatasourceConf>lambdaQuery()
				.eq(GenDatasourceConf::getName, conf.getName())
				.ne(GenDatasourceConf::getId, conf.getId()));

		if (aLong > 0){
			throw new BusinessException("数据库别名重复");
		}

		if (!checkDataSource(conf)) {
			return Boolean.FALSE;
		}
		// 先移除
		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		dynamicRoutingDataSource.delDatasources(conf.getName());

		// 再添加
		addDynamicDataSource(conf);

		// 更新数据库配置
		if (StrUtil.isNotBlank(conf.getPassword())) {
			conf.setPassword(stringEncryptor.encrypt(conf.getPassword()));
		}
		this.baseMapper.updateById(conf);
		return Boolean.TRUE;
	}

	/**
	 * 通过数据源名称删除
	 * @param dsName 数据源别名
	 * @return
	 */
	@Override
	public Boolean removeByDsName(String dsName) {
		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		dynamicRoutingDataSource.delDatasources(dsName);
		this.baseMapper.deleteById(dsName);
		return Boolean.TRUE;
	}

	/**
	 * 添加动态数据源
	 * @param conf 数据源信息
	 */
	@Override
	public void addDynamicDataSource(GenDatasourceConf conf) {
		dynamicRoutingDataSource.checkCreateDataSource(conf);
	}

	/**
	 * 校验数据源配置是否有效
	 * @param conf 数据源信息
	 * @return 有效/无效
	 */
	@Override
	public Boolean checkDataSource(GenDatasourceConf conf) {
		String url  = conf.getUrl();

		if (StringUtils.isBlank(url)){
			url =  String.format("jdbc:mysql://%s:%s/%s?characterEncoding=utf8"
					+ "&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true"
					+ "&useLegacyDatetimeCode=false&allowMultiQueries=true&allowPublicKeyRetrieval=true",conf.getHost(),conf.getPort(),conf.getDsName());
		}
		conf.setUrl(url);

		try (Connection connection = DriverManager.getConnection(url, conf.getUsername(), conf.getPassword())) {
		}
		catch (SQLException e) {
			log.error("数据源配置 {} , 获取链接失败", conf.getName(), e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
