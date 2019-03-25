package com.ldz.biz.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Table(name = "biz_kc_lb")
@Getter
@Setter
@ToString
public class BizKcLb implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    private String pid;

    private String mc;

    private String cjsj;

    private String cjr;

    private String bz;

    private List<BizKcLb> children;

    private static final long serialVersionUID = 1L;


    public enum InnerColumn {
        id("ID"),
        zdlm("zdlm"),
        mc("mc"),
        cjsj("cjsj"),
        cjr("cjr"),
        bz("bz");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}