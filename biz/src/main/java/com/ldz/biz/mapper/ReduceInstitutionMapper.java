package com.ldz.biz.mapper;

import com.ldz.biz.model.ReduceInstitution;
import com.ldz.util.mapperprovider.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ReduceInstitutionMapper extends Mapper<ReduceInstitution> , InsertListMapper<ReduceInstitution> {
}