package com.example.firstproject.dto;

/*
 * 데이터 던지는 갯수 만큼 변수를 선언해야함
 * */

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private String title;
    private String content;

    public Article toEntity() {
        return new Article(null, title, content);
    }
}