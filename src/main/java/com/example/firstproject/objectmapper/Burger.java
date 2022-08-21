package com.example.firstproject.objectmapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor // ObjectMapper.readValue()에서 디폴트 생성자 필요
@ToString
@Getter // JSON으로 변환하기 위해서 필요
public class Burger {
    private String name;
    private int price;
    private List<String> ingredients;
}
