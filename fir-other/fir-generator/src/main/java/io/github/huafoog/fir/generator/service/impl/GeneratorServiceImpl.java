package io.github.huafoog.fir.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.generator.datasource.ChangeDataSourceService;
import io.github.huafoog.fir.generator.entity.GenConfig;
import io.github.huafoog.fir.generator.entity.GenFormConf;
import io.github.huafoog.fir.generator.mapper.GenFormConfMapper;
import io.github.huafoog.fir.generator.mapper.GeneratorMapper;
import io.github.huafoog.fir.generator.service.GeneratorService;
import io.github.huafoog.fir.generator.util.GenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 * @author 青杉
 */
@Service
@AllArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

	private final GeneratorMapper mapper;


	/**
	 * 分页查询表
	 * @param tableName 查询条件
	 * @return
	 */
	@Override
	public IPage<Map<String, Object>> getPage(Page page, String tableName) {
		// 关闭SQL 优化
		page.setOptimizeCountSql(false);
		return mapper.queryTable(page, tableName);
	}

	@Override
	public Map<String, String> previewCode(GenConfig genConfig) {
		String tableNames = genConfig.getTableName();

		for (String tableName : StrUtil.split(tableNames, StrUtil.DASHED)) {
			// 查询表信息
			Map<String, String> table = mapper.queryTable(tableName);
			// 查询列信息
			List<Map<String, String>> columns = mapper.selectMapTableColumn(tableName);
			return GenUtils.generatorCode(genConfig, table, columns, null, null);
		}
		return MapUtil.empty();
	}

	/**
	 * 生成代码
	 * @param genConfig 生成配置
	 * @return
	 */
	@Override
	public byte[] generatorCode(GenConfig genConfig) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		String tableNames = genConfig.getTableName();

		for (String tableName : StrUtil.split(tableNames, StrUtil.DASHED)) {
			// 查询表信息
			Map<String, String> table = mapper.queryTable(tableName);
			// 查询列信息
			List<Map<String, String>> columns = mapper.selectMapTableColumn(tableName);
			// 生成代码
			GenUtils.generatorCode(genConfig, table, columns, zip, null);
		}
		IoUtil.close(zip);
		return outputStream.toByteArray();
	}
}
