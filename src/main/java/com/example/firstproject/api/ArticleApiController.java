package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // RestAPI용 컨트롤러, 데이터(JSON)를 반환
public class ArticleApiController {
    @Autowired // DI 외부에서 주입시켜줘야함
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) { // RestController에서는 데이터를 받아올 때 @RequestBody 어노테이션을 붙여야함
        // 1: DTO -> Entity 변환
        Article articleEntity = dto.toEntity();

        return articleRepository.save(articleEntity);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { // ResponseEntity에 감싸서 보내면 상태 코드도 보낼수 있다. (ResponseEntity에 Article 데이터가 담겨서 들어간다)

        // 1: 수정용 엔티티를 생성
        Article articleEntity = dto.toEntity();
        log.info("id: {}, article: {}", id, articleEntity.toString());

        // 2: 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3: 잘못된 요청 처리 (대상이 없거나, id가 다른 경우)
        if(target == null || id != articleEntity.getId()) {
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청 ! id: {}, article: {}", id, articleEntity.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 4: 정상이면 업데이터 및 정상 응답 (200)
        target.patch(articleEntity);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) { // RestController에서는 데이터를 받아올 때 @RequestBody 어노테이션을 붙여야함
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 대상 삭제
        articleRepository.delete(target);
        
        // 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build(); // build 대신 body(null) 줘도됨
    }
    
}
