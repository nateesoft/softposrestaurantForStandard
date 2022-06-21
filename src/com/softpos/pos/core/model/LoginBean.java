package com.softpos.pos.core.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author nathee
 */

@Getter
@Setter
@ToString
public class LoginBean {
    private String username;
    private String password;
    private String onact;
    private String macno;
    private boolean validLogin;
    private String name;
}
