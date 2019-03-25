package com.ldz.biz.service;

import com.ldz.biz.model.PicRotation;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface PicRotationService extends BaseService<PicRotation, String> {
    ApiResponse<String> validAndSave(PicRotation entity);
}