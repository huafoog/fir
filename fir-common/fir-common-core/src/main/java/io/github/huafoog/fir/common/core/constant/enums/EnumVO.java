package io.github.huafoog.fir.common.core.constant.enums;

import lombok.Data;

import java.io.Serializable;

/**
 * 枚举转对象
 * @author 青杉
 */
@Data
public class EnumVO implements Serializable{
    
    private static final long serialVersionUID = 4973830882027128719L;
    
    private String code;
    
    
    private String desc;

	private Integer type;
    
    public EnumVO(String code, String desc, Integer type) {
        this.code = code;
		this.desc = desc;
		this.type = type;
	}
	public EnumVO() {
    }
    
}
