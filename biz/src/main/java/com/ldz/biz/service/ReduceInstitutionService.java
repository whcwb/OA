package com.ldz.biz.service;

import com.ldz.biz.model.ReduceInstitution;
import com.ldz.sys.base.BaseService;

import java.util.List;

public interface ReduceInstitutionService extends BaseService<ReduceInstitution, String> {

    void saveBatch(List<ReduceInstitution> reduceInstitutions);

    void deleteByReduceId(String reduceId);
}