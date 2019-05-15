package au.com.digitalspider.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.digitalspider.api.Constants;
import au.com.digitalspider.api.io.Response;
import au.com.digitalspider.api.model.Article;
import au.com.digitalspider.api.service.ArticleService;

@RestController
@CrossOrigin
@RequestMapping(Constants.BASE_API + "/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("")
	private ResponseEntity<Response> getAll() {
		try {
			List<Article> articles = articleService.getAll();
			HttpStatus status = HttpStatus.OK;
			Response body = new Response(status.value(), "Get all articles", articles);
			return ResponseEntity.status(status).body(body);
		} catch (Exception e) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			Response body = new Response(status.value(), e.getMessage());
			return ResponseEntity.status(status).body(body);
		}
	}

	@GetMapping("/title/{title}")
	private Iterable<Article> findByTitle(@PathVariable(value = "title") String title) {
		return articleService.findByTitle(title);
	}

	@GetMapping("/title/length/{titleLength}")
	private Iterable<Article> findByTitleLength(@PathVariable(value = "titleLength") int titleLength) {
		return articleService.getAll().stream().filter(article -> article.getTitleLength() >= titleLength)
				.collect(Collectors.toList());
	}

	@GetMapping("/search/{searchTerm}")
	private Iterable<Article> search(@PathVariable(value = "searchTerm") String searchTerm) {
		return articleService.search(searchTerm);
	}

	@PostMapping("")
	private ResponseEntity<Response> create(@Valid @RequestBody Article article) {
		try {
			Article updatedArticle = articleService.create(article);
			HttpStatus status = HttpStatus.OK;
			Response body = new Response(status.value(), "create new article", updatedArticle);
			return ResponseEntity.status(status).body(body);
		} catch (IllegalArgumentException e) {
			HttpStatus status = HttpStatus.PRECONDITION_FAILED;
			Response body = new Response(status.value(), e.getMessage());
			return ResponseEntity.status(status).body(body);
		}
	}

	@GetMapping("/{id}")
	private ResponseEntity<Article> get(@PathVariable(value = "id") Long articleId) {
		try {
			Article article = articleService.findById(articleId);
			return ResponseEntity.ok(article);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	private ResponseEntity<Article> update(@PathVariable(value = "id") Long articleId,
			@Valid @RequestBody Article newArticle) {
		try {
			Article updatedArticle = articleService.update(articleId, newArticle);
			return ResponseEntity.ok().body(updatedArticle);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<Void> delete(@PathVariable(value = "id") Long articleId) {
		try {
			articleService.delete(articleId);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
