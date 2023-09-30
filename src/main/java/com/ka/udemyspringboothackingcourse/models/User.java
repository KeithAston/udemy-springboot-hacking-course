package com.ka.udemyspringboothackingcourse.models;

import lombok.Data;

@Data
public class User {
    private String name;
    private String userType;
    private int userId;
    private String contactNumber;
    private String address;

}
