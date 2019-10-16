package com.ldz.biz.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name  = "NOT_SCHOOL_TEST_INFO")
@Data
public class NotSchoolTestInfo {

    @Id
    private String id;

    @Column(name = "TRAINEE_ID")
    private String traineeId;

    @Column(name = "TRAINEE_NAME")
    private String traineeName;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    private String subject;

    @Column(name = "TEST_PLACE")
    private String testPlace;

    @Column(name = "TEST_RESULT")
    private String testResult;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "PAY_STATUS")
    private String payStatus;

    @Column(name = "OPERATE_TIME")
    private String operateTime;

    @Column(name = "OPERATOR")
    private String operator;

    @Column(name = "CJR")
    private String cjr;

    @Column(name = "CJSJ")
    private String cjsj;


    public enum InnerColumn{

        id("ID"),
        traineeId("TRAINEE_ID"),
        traineeName("TRAINEE_NAME"),
        idCardNo("ID_CARD_NO"),
        subject("SUBJECT"),
        testPlace("TEST_PLACE"),
        testResult("TEST_RESULT"),
        remark("REMARK"),
        testTime("TEST_TIME"),
        payStatus("PAY_STATUS"),
        operateTime("OPERATE_TIME"),
        operator("OPERATOR"),
        cjr("CJR"),
        cjsj("CJSJ");

        private final String column;

        InnerColumn(String column) {
            this.column = column;
        }


        public String value(){
            return this.column;
        }

        public String getValue(){
            return this.column;
        }

        public String asc(){
            return this.column + " asc";
        }

        public String desc(){
            return this.column + " desc";
        }

    }
}
