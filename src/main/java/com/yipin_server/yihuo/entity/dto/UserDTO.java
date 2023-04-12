package com.yipin_server.yihuo.entity.dto;

import com.yipin_server.yihuo.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String avatarUrl;

    private String token;

    private String role;

    private List<Menu> menus;
}
