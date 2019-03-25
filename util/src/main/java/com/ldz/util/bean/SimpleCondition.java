package com.ldz.util.bean;

import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2018/2/8
 */
public class SimpleCondition extends Example {

    public SimpleCondition(Class<?> entityClass) {
        super(entityClass);
    }

    public void gt(String key, Object val){
        this.and().andGreaterThan(key,val);
    }

    public void gt(Enum key, Object val){
        this.and().andGreaterThan(key.name(),val);
    }

    public void gte(String key,Object val){
        this.and().andGreaterThanOrEqualTo(key,val);
    }
    public void gte(Enum key,Object val){
        this.and().andGreaterThanOrEqualTo(key.name(),val);
    }
    public void lte(String key,Object val){
        this.and().andLessThanOrEqualTo(key,val);
    }
    public void lte(Enum key,Object val){
        this.and().andLessThanOrEqualTo(key.name(),val);
    }
    public void lt(String key, Object val){
        this.and().andLessThan(key, val);
    }

    public void lt(Enum key, Object val){
        this.and().andLessThan(key.name(), val);
    }

    public void in(String key,Iterable val){
        this.and().andIn(key,val);
    }
    public void in(Enum key,String... vals){
        this.and().andIn(key.name(), Arrays.asList(vals));
    }
    public void in(Enum key,Iterable val){
        this.in(key.name(),val);
    }
    public void notIn(String key,Iterable val){
        this.and().andNotIn(key,val);
    }
    public void notIn(Enum key,Iterable val){
        this.notIn(key.name(),val);
    }
    public void eq(String key,Object val){
        this.and().andEqualTo(key,val);
    }
    public void eq(Enum key,Object val){
        this.eq(key.name(),val);
    }
    public void like(String key,String val){
        this.and().andLike(key,"%"+val+"%");
    }
    public void like(Enum key,String val){
        this.and().andLike(key.name(),"%"+val+"%");
    }
    public void startWith(String key,String val){
        this.and().andLike(key,val+"%");
    }
    public void startWith(Enum key,String val){
        this.and().andLike(key.name(),val+"%");
    }
}
