package com.ldz.biz.model;

import java.util.List;

/**
 * 学员招生统计模型
 */
public class StudentAllModel {
    /**
     * 队名
     */
    private String jgmc;

    private List<StuAll> stu;
    /**
     * 合计
     */
    private long all;
    /**
     * 退学人数
     */
    private long dropOut;
    /**
     * 真实统计
     */
    private long realAll;
    /**
     * A1人数
     */
    private  Long a1;
    /**
     * A2人数
     */
    private  Long a2;
    /**
     * A3人数
     */
    private Long a3;
    /**
     * B2人数
     */
    private Long b2;
    /**
     * C1人数
     */
    private Long c1;
    /**
     * C2人数
     */
    private Long c2;



    public Long getA1() {
        return a1;
    }

    public void setA1(Long a1) {
        this.a1 = a1;
    }

    public Long getA2() {
        return a2;
    }

    public void setA2(Long a2) {
        this.a2 = a2;
    }

    public Long getA3() {
        return a3;
    }

    public void setA3(Long a3) {
        this.a3 = a3;
    }

    public Long getB2() {
        return b2;
    }

    public void setB2(Long b2) {
        this.b2 = b2;
    }

    public Long getC1() {
        return c1;
    }

    public void setC1(Long c1) {
        this.c1 = c1;
    }

    public Long getC2() {
        return c2;
    }

    public void setC2(Long c2) {
        this.c2 = c2;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public List<StuAll> getStu() {
        return stu;
    }

    public void setStu(List<StuAll> stu) {
        this.stu = stu;
    }

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

    public long getDropOut() {
        return dropOut;
    }

    public void setDropOut(long dropOut) {
        this.dropOut = dropOut;
    }

    public long getRealAll() {
        return realAll;
    }

    public void setRealAll(long realAll) {
        this.realAll = realAll;
    }

    public static class StuAll{

        private String time;

        private long count;

        /**
         * 报名人数
         */
        private Long signUp;


        public Long getSignUp() {
            return signUp;
        }

        public void setSignUp(Long signUp) {
            this.signUp = signUp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }

    @Override
    public String toString() {
        return "StudentAllModel{" +
                "jgmc='" + jgmc + '\'' +
                ", stu=" + stu +
                ", all=" + all +
                ", dropOut=" + dropOut +
                ", realAll=" + realAll +
                '}';
    }
}
