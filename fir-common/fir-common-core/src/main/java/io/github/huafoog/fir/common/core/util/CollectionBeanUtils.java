package io.github.huafoog.fir.common.core.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 集合拷贝
 * @author qs
 */
public class CollectionBeanUtils extends BeanUtils {
	/**
	 * 集合数据的拷贝
	 * @param sources: 数据源类
	 * @param target: 目标类::new(eg: UserVO::new)
	 * @return
	 */
	public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
		if (sources == null){
			return null;
		}
		return copyListProperties(sources, target, null);
	}


	/**
	 * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
	 * @param sources: 数据源类
	 * @param target: 目标类::new(eg: UserVO::new)
	 * @param callBack: 回调函数
	 * @return
	 */
	public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack) {
		List<T> list = new ArrayList<>(sources.size());
		for (S source : sources) {
			T t = target.get();
			copyProperties(source, t);
			list.add(t);
			if (callBack != null) {
				// 回调
				callBack.callBack(source, t);
			}
		}
		return list;
	}
}
