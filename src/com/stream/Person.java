package com.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dengKai
 * @Date: 2021-08-30-14-39
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private Integer salary;
    private Integer age;
    private String sex;
    private String area;

}