package io.github.huafoog.fir.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.generator.entity.ColumnEntity;
import io.github.huafoog.fir.generator.entity.GenConfig;

/**
 * 表字段管理
 */
public interface GenTableColumnService {

	/**
	 * 查询表的字段信息
	 * @param page
	 * @param genConfig 查询条件
	 * @return
	 */
	IPage<ColumnEntity> listTable(Page page, GenConfig genConfig);

}
