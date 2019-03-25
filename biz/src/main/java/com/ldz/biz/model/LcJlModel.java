package com.ldz.biz.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class LcJlModel {

    /**
     * 安全员姓名
     */
    private String zgXm;
    /**
     * 车号
     */
    private String clBh;
    /**
     * 记录
     */
    private List<BizLcJl> jls;

    /**
     * 学员信息
     */
    private List<BizLcJlXy> xyList;
    /**
     * 教练名称
     */
    private String jlXm;
    /**
     * 总时长 (分钟)
     */
    private Integer sc;
    /**
     * 总价
     */
    private Integer zj;
    /**
     * 教练驾校
     */
    private String jlJx;
}
