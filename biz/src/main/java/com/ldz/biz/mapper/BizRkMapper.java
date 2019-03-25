package com.ldz.biz.mapper;

import com.ldz.biz.model.BizRk;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizRkMapper extends Mapper<BizRk> {

    @Select( " select pc from biz_rk group by pc order by pc desc ")
    List<String> getAllPCByPage();

}