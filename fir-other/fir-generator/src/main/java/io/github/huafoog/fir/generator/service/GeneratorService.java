package io.github.huafoog.fir.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.generator.entity.GenConfig;

import java.util.Map;
public interface GeneratorService {

	/**
	 * 生成代码
	 * @param genConfig 生成信息
	 * @return
	 */
	byte[] generatorCode(GenConfig genConfig);

	/**
	 * 分页查询表
	 * @param page 分页信息
	 * @param tableName 表名
	 * @return
	 */
	IPage<Map<String, Object>> getPage(Page page, String tableName);

	/**
	 * 预览代码
	 * @param genConfig 生成信息
	 * @return
	 */
	Map<String, String> previewCode(GenConfig genConfig);

}
