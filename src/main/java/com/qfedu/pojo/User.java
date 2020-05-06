package com.qfedu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther Zhangbo
 * @date 2020/5/6 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int uid;
    private String username;
    private  String password;
    private  int age;

}
