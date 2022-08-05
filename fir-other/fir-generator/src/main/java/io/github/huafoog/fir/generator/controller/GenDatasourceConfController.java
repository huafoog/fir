package io.github.huafoog.fir.generator.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.common.core.R;
import io.github.huafoog.fir.generator.entity.GenDatasourceConf;
import io.github.huafoog.fir.generator.service.GenDatasourceConfService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理
 * @author shan
 */
@RestController
@AllArgsConstructor
@RequestMapping("/genDatasourceConf")
@Api(tags = { "genDatasourceConf" },description = "数据源管理")
public class GenDatasourceConfController {

	private final GenDatasourceConfService datasourceConfService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param datasourceConf 数据源表
	 * @return
	 */
	@GetMapping("/page")
	public R<Page<GenDatasourceConf>> getSysDatasourceConfPage(Page page, GenDatasourceConf datasourceConf) {
		return R.ok(datasourceConfService.page(page, Wrappers.query(datasourceConf)));
	}

	/**
	 * 查询全部数据源
	 * @return
	 */
	@GetMapping("/list")
	public R<List<GenDatasourceConf>> list() {
		return R.ok(datasourceConfService.list());
	}

	/**
	 * 通过id查询数据源表
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(datasourceConfService.getById(id));
	}

	/**
	 * 新增数据源表
	 * @param datasourceConf 数据源表
	 * @return R
	 */
	@PostMapping
	public R save(@RequestBody GenDatasourceConf datasourceConf) {
		return R.ok(datasourceConfService.saveDsByEnc(datasourceConf));
	}

	/**
	 * 修改数据源表
	 * @param conf 数据源表
	 * @return R
	 */
	@PutMapping
	public R updateById(@RequestBody GenDatasourceConf conf) {
		return R.ok(datasourceConfService.updateDsByEnc(conf));
	}

	/**
	 * 通过别名删除数据源表
	 * @param dsName id
	 * @return R
	 */
	@DeleteMapping("/{dsName}")
	public R removeById(@PathVariable String dsName) {
		return R.ok(datasourceConfService.removeByDsName(dsName));
	}

}
