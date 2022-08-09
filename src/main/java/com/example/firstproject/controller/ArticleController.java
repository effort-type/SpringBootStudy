package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해둔 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
        // System.out.println(form.toString()); --> 로깅으로 기능 대체

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        // System.out.println(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하도록 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        // System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") // {}를 사용하면 변하는 수다
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id로 데이터 가져오기
        // Optional<Article> articleEntity = articleRepository.findById(id); 모두가 java 8 버전이 아닐수도있기 때문에 지양
        Article articleEntity = articleRepository.findById(id).orElse(null); // .orElse(null)은 값이 없다면 null을 반환해라

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 데이터(Article) 가지고 오기
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 데이터(Article)묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        // 3. 보여줄 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "/articles/edit";
    }

    @PostMapping("/articles/update") // 업데이트는 patch 또는 put으로 보내고 받아야하는데 form 태그에서 제공하지 않음
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1: DTO -> Entity로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: Entity를 DB로 저장한다다
        // 2-1: DB에서 기존 데이터를 가져온다
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2: 기존 데이터에 값을 갱신한다.
        if(target != null) {
            articleRepository.save(articleEntity); // Entity가 DB로 갱신
        }

        // 3: 수정 결과 페이지로 리다이렉트한다.
        return "redirect:/articles/" + articleEntity.getId();
    }
}
