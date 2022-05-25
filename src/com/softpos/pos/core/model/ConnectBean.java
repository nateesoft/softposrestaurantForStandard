package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConnectBean {

    private String username;
    private String password;
    private int port;
    private String charset;
    private String url;
    private String className;
    private String databaseName;
    private String hostName;
}
