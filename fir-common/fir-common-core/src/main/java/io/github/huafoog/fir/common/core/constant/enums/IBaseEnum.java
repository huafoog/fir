package io.github.huafoog.fir.common.core.constant.enums;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 枚举基类
 * @author DELL
 */
public interface IBaseEnum {

	/**
	 * code
	 * @return
	 */
    String code();

	/**
	 * 描述
	 * @return
	 */
    String desc();

	/**
	 * 类型
	 * @return
	 */
	Integer type();

	/**
	 * 转化为实体类
	 * @return EnumVO
	 */
	default EnumVO toEnumVO() {
		return new EnumVO(this.code(),this.desc(),this.type());
	}

	/**
	 * 将枚举转化成集合
	 * @param clazz 需要转化的枚举类
	 * @param <T> 枚举
	 * @return EnumVO集合
	 */
    static <T extends IBaseEnum> List<EnumVO> toList(Class<T> clazz){
        T[] ts = clazz.getEnumConstants();
        List<EnumVO> enumVos = new ArrayList<>(ts.length);
        for (IBaseEnum iEnum : ts) {
            enumVos.add(iEnum.toEnumVO());
        }
        return enumVos;
    }

	/**
	 * 根据枚举类型获取枚举值
	 * @param clazz 枚举
	 * @param type type
	 * @param <T> 枚举
	 * @return 枚举
	 * @return
	 */
	static <T extends IBaseEnum> T getEnumByType(Class<T> clazz,int type){
		T[] enumConstants = clazz.getEnumConstants();
		List<T> ts1 = Arrays.asList(enumConstants);
		for (T t : ts1) {
			if (t.type().equals(type)){
				return t;
			}
		}
		return null;
	}


	/**
	 * 根据code获取枚举值
	 * @param clazz 枚举
	 * @param code code
	 * @param <T> 枚举
	 * @return 枚举
	 */
	static <T extends IBaseEnum> T getEnumByCode(Class<T> clazz,String code){
		T[] enumConstants = clazz.getEnumConstants();
		List<T> ts1 = Arrays.asList(enumConstants);
		for (T t : ts1) {
			if (t.code().equals(code)){
				return t;
			}
		}
		return null;
	}



}
