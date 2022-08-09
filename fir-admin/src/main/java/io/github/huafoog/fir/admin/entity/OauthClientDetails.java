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

package io.github.huafoog.fir.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客户端
 *
 * @author qs
 * @date 2022-08-09 10:29:44
 */
@Data
@TableName("oauth_client_details")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户端")
public class OauthClientDetails extends Model<OauthClientDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * clientId
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="clientId")
    private String clientId;

    /**
     * resourceIds
     */
    @ApiModelProperty(value="resourceIds")
    private String resourceIds;

    /**
     * clientSecret
     */
    @ApiModelProperty(value="clientSecret")
    private String clientSecret;

    /**
     * scope
     */
    @ApiModelProperty(value="scope")
    private String scope;

    /**
     * authorizedGrantTypes
     */
    @ApiModelProperty(value="authorizedGrantTypes")
    private String authorizedGrantTypes;

    /**
     * webServerRedirectUri
     */
    @ApiModelProperty(value="webServerRedirectUri")
    private String webServerRedirectUri;

    /**
     * authorities
     */
    @ApiModelProperty(value="authorities")
    private String authorities;

    /**
     * accessTokenValidity
     */
    @ApiModelProperty(value="accessTokenValidity")
    private Integer accessTokenValidity;

    /**
     * refreshTokenValidity
     */
    @ApiModelProperty(value="refreshTokenValidity")
    private Integer refreshTokenValidity;

    /**
     * additionalInformation
     */
    @ApiModelProperty(value="additionalInformation")
    private String additionalInformation;

    /**
     * autoapprove
     */
    @ApiModelProperty(value="autoapprove")
    private String autoapprove;

}
