package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class ParameterDto implements Serializable {
    private static final long serialVersionUID = 6843145128058673779L;
    private String name;
    private String content;

    public ParameterDto() {
    }

    public ParameterDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
