package io.github.huafoog.fir.common.core.constant.enums;

import io.github.huafoog.fir.common.core.constant.ProjectNameConstants;

/**
 * 项目枚举类
 * @author DELL
 */

public enum ProjectEnum implements IBaseEnum {
    AUTH(ProjectNameConstants.AUTH,"授权API",0),
    ADMIN(ProjectNameConstants.ADMIN,"后台API",1)
    ;


    private String code;
    private String desc;
    private Integer type;
    ;
    ProjectEnum(String code,String desc,Integer type){
        this.code = code;
        this.desc = desc;
        this.type = type;
    }
    @Override
    public String code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public Integer type() {
        return type;
    }
}
