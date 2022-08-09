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
package io.github.huafoog.fir.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.huafoog.fir.admin.entity.OauthClientDetails;
import io.github.huafoog.fir.admin.mapper.OauthClientDetailsMapper;
import io.github.huafoog.fir.admin.service.OauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * 客户端
 *
 * @author qs
 * @date 2022-08-09 10:29:44
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

}
