package com.dwd.dwdb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Site {

    fastcampus("패스트캠퍼스"),
    inflearn("인프런");

    private String name;

}
