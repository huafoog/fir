/*
 *    Copyright (c) 2018-2025, fir All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: fir
 */

package io.github.huafoog.fir.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.huafoog.fir.common.core.R;
import io.github.huafoog.fir.admin.entity.OauthClientDetails;
import io.github.huafoog.fir.admin.service.OauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端
 *
 * @author qs
 * @date 2022-08-09 10:29:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauthclientdetails" )
@Api(value = "oauthclientdetails", tags = "客户端管理")
public class OauthClientDetailsController {

    private final  OauthClientDetailsService oauthClientDetailsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oauthClientDetails 客户端
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOauthClientDetailsPage(Page page, OauthClientDetails oauthClientDetails) {
        return R.ok(oauthClientDetailsService.page(page, Wrappers.query(oauthClientDetails)));
    }


    /**
     * 通过id查询客户端
     * @param clientId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{clientId}" )
    public R getById(@PathVariable("clientId" ) String clientId) {
        return R.ok(oauthClientDetailsService.getById(clientId));
    }

    /**
     * 新增客户端
     * @param oauthClientDetails 客户端
     * @return R
     */
    @ApiOperation(value = "新增客户端", notes = "新增客户端")
    @PostMapping
    public R save(@RequestBody OauthClientDetails oauthClientDetails) {
        return R.ok(oauthClientDetailsService.save(oauthClientDetails));
    }

    /**
     * 修改客户端
     * @param oauthClientDetails 客户端
     * @return R
     */
    @ApiOperation(value = "修改客户端", notes = "修改客户端")
    @PutMapping
    public R updateById(@RequestBody OauthClientDetails oauthClientDetails) {
        return R.ok(oauthClientDetailsService.updateById(oauthClientDetails));
    }

    /**
     * 通过id删除客户端
     * @param clientId id
     * @return R
     */
    @ApiOperation(value = "通过id删除客户端", notes = "通过id删除客户端")
    @DeleteMapping("/{clientId}" )
    public R removeById(@PathVariable String clientId) {
        return R.ok(oauthClientDetailsService.removeById(clientId));
    }
}
