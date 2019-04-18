package com.github.everything.core.model;

import lombok.Data;

@Data
public class Condition {
    private String name;

    private String fileType;

    private Integer limit;

    private Boolean orderByDepthAsc;
}
