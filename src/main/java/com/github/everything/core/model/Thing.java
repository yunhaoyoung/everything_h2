package com.github.everything.core.model;

import lombok.Data;

@Data
public class Thing {
    private String name;

    private String path;

    private Integer depth;

    private FileType fileType;
}
