package io.github.huafoog.fir.generator.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.smallbun.screw.boot.config.Screw;
import cn.smallbun.screw.boot.properties.ScrewProperties;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.common.core.R;
import io.github.huafoog.fir.common.core.util.SpringContextHolder;
import io.github.huafoog.fir.generator.entity.GenConfig;
import io.github.huafoog.fir.generator.service.GenTableColumnService;
import io.github.huafoog.fir.generator.service.GeneratorService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 代码生成器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

	private final Screw screw;

	private final GeneratorService generatorService;

	private final GenTableColumnService columnService;

	/**
	 * 列表
	 * @param tableName 参数集
	 * @param dsName 数据源编号
	 * @return 数据库表
	 */
	@GetMapping("/page")
	public R getPage(Page page, String tableName, String dsName) {
		return R.ok(generatorService.getPage(page, tableName, dsName));
	}

	/**
	 * 预览代码
	 * @param genConfig 数据表配置
	 * @return
	 */
	@GetMapping("/preview")
	public R previewCode(GenConfig genConfig) {
		return R.ok(generatorService.previewCode(genConfig));
	}

	/**
	 * 查询表的列信息
	 * @param page 分页
	 * @param genConfig 数据表配置
	 * @return
	 */
	@GetMapping("/table")
	public R getTable(Page page, GenConfig genConfig) {
		return R.ok(columnService.listTable(page, genConfig));
	}

	/**
	 * 生成代码
	 * @param genConfig 数据表配置
	 */
	@SneakyThrows
	@PostMapping("/code")
	public void generatorCode(@RequestBody GenConfig genConfig, HttpServletResponse response) {
		byte[] data = generatorService.generatorCode(genConfig);
		response.reset();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				String.format("attachment; filename=%s.zip", genConfig.getTableName()));
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");

		IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
	}

	@SneakyThrows
	@GetMapping("/doc")
	public void generatorDoc(String[] tableNames, String dsName, HttpServletResponse response) {
		// 设置指定的数据源
		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		DynamicDataSourceContextHolder.push(dsName);
		DataSource dataSource = dynamicRoutingDataSource.determineDataSource();

		// 设置指定的目标表
		ScrewProperties screwProperties = SpringContextHolder.getBean(ScrewProperties.class);
		screwProperties.setDesignatedTableName(CollUtil.toList(tableNames));

		// 生成
		byte[] data = screw.documentGeneration(dataSource, screwProperties).toByteArray();
		response.reset();
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
	}

}
