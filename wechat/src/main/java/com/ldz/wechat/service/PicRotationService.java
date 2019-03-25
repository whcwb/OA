package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.PicRotation;

import java.util.List;

public interface PicRotationService extends BaseService<PicRotation, String> {
    ApiResponse<List<PicRotation>> queryPic(PicRotation picRotation);
}