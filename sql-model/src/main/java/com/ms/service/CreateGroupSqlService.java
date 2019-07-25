package com.ms.service;


import com.ms.utils.MyBatisSql;
import com.ms.utils.MyBatisSqlUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreateGroupSqlService {

    Logger logger = LoggerFactory.getLogger(CreateGroupSqlService.class);

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;



    //人群sql
    public String getCrowdSql(List<Map<String, Object>> ruleGroups, int labelId, Date date, List<Integer> caseIds) {

        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", caseIds);
        map.put("opList", 1);
        map.put("nameList", 2);
        String ruleId = "rule_with_group";
        MyBatisSql labelSql = MyBatisSqlUtils.getMyBatisSql(ruleId, map, sqlSessionFactory);
        logger.info("新增人群sql:{}", labelSql.getSql());
        return labelSql.getSql();

    }

}
