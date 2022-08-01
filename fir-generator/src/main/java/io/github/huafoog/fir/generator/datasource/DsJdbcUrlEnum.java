package io.github.huafoog.fir.generator.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * jdbc-url
 */
@Getter
@AllArgsConstructor
public enum DsJdbcUrlEnum {

	/**
	 * mysql 数据库
	 */
	MYSQL("mysql",
			"jdbc:mysql://%s:%s/%s?characterEncoding=utf8"
					+ "&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true"
					+ "&useLegacyDatetimeCode=false&allowMultiQueries=true&allowPublicKeyRetrieval=true",
			"select 1", "mysql8 链接");
	private final String dbName;

	private final String url;

	private final String validationQuery;

	private final String description;

	public static DsJdbcUrlEnum get(String dsType) {
		return Arrays.stream(DsJdbcUrlEnum.values()).filter(dsJdbcUrlEnum -> dsType.equals(dsJdbcUrlEnum.getDbName()))
				.findFirst().get();
	}

}
