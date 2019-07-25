
package com.ms.utils;

import com.alibaba.druid.sql.SQLUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyBatisSqlUtils {
/**
	 * 运行期获取MyBatis执行的SQL及参数
	 * 
	 * @param id                Mapper xml 文件里的select Id
	 * @param parameterMap      参数
	 * @param sqlSessionFactory
	 * @return
	 */

	public static MyBatisSql getMyBatisSql(String id, Map<String, Object> parameterMap,
			SqlSessionFactory sqlSessionFactory) {
		MyBatisSql ibatisSql = new MyBatisSql();
		MappedStatement mappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(id);
		BoundSql boundSql = mappedStatement.getBoundSql(parameterMap);
		Configuration configuration = mappedStatement.getConfiguration();
		String sql = showSql(configuration, boundSql);

		ibatisSql.setSql(sql);
		return ibatisSql;
	}

	public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = SQLUtils.formatMySql(boundSql.getSql());
        sql = sql.replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
	
	private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }
}
