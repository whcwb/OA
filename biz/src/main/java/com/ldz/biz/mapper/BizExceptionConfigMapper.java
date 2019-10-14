package com.ldz.biz.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;

import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.util.cache.MybatisRedisCache;

import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface BizExceptionConfigMapper extends Mapper<BizExceptionConfig> {
}