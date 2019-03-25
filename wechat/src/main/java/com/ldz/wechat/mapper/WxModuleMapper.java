package com.ldz.wechat.mapper;

import com.ldz.wechat.model.WxModule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WxModuleMapper extends Mapper<WxModule> {
    /**
     * 查询用户权限列表
     * @param yhId
     * @return
     */
    @Select({ "<script> " +
        " SELECT a.*,b.* FROM  wx_module b  " +
        " LEFT JOIN  wx_module_sys a ON b.id  = a.module_id    " +
        " WHERE a.yh_id =#{yhId} GROUP BY b.id " +
        " </script>"})
    List<Map> getPression(@Param("yhId") String yhId);
}