package com.ms.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author  huahua
 *
 */
@Table(name = "T_LABEL")
@Getter
@Setter
public class Label implements Serializable {
    private static final long serialVersionUID = 4443956326295742150L;


    /**
     * 获取oracle sequence
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_T_LABEL.nextval from dual")
    private Integer id;

    @Column(name = "name")
    private String name;

    /**
     * 标签分类 0 标签 1 人群
     */
   /* @Column(name = "label_type" )
    private String labelCategory;*/


    /**
     * 覆盖人群
     */
    @Column(name = "cover_scope" )
    private Integer coverScope;




}

