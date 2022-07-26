package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

/*
* CrudRepository<관리 대상 Entity, 관리 대상 Entity의 대표값 타입>
* */

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
