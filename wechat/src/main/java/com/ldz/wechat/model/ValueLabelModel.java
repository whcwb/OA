package com.ldz.wechat.model;

import java.util.List;

public class ValueLabelModel {

    private String value;

    private String label;

    private List<ValueLabel> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ValueLabel> getChildren() {
        return children;
    }

    public void setChildren(List<ValueLabel> children) {
        this.children = children;
    }
   public static class ValueLabel{
        private String value;

        private String label;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
