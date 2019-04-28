package au.com.digitalspider.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import au.com.digitalspider.api.model.Article;
import au.com.digitalspider.api.model.User;
import au.com.digitalspider.api.repo.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserService userService;

	public Article init(Article article) {
		calculateTitleLength(article);
		return article;
	}

	public Article calculateTitleLength(Article article) {
		article.setTitleLength(article.getTitle().length());
		return article;
	}

	public List<Article> search(String searchTerm) {
		List<Article> articles = articleRepository.findAll();
		final String searchTermLc = searchTerm.toLowerCase();
		return articles.stream().filter(
				article -> article.getTitle().toLowerCase().indexOf(searchTermLc) > 0 ||
						article.getContent().toLowerCase().indexOf(searchTermLc) > 0)
				.map(article -> init(article)).collect(Collectors.toList());
	}

	public List<Article> getAll() {
		return articleRepository.findAll().stream().map(article -> init(article)).collect(Collectors.toList());
	}

	public List<Article> findByTitle(String title) {
		return articleRepository.findByTitleContainingIgnoreCase(title).stream().map(article -> init(article)).collect(Collectors.toList());
	}

	public Article create(Article article) {
		if (article.getCreatedById() == null) {
			throw new IllegalArgumentException("Cannot create article without a createdById value");
		}
		User creator = userService.findById(article.getCreatedById());
		article.setCreatedBy(creator);
		article.setUpdatedBy(creator);
		try {
			articleRepository.findOneByTitleIgnoreCase(article.getTitle());
			throw new IllegalArgumentException("Article with title ${article.title} already exists");
		}
		catch (EmptyResultDataAccessException e) {
			// Ignore: article with title does not exist
		}
		return init(articleRepository.save(article));
	}

	public Article findById(Long articleId) {
		return articleRepository.findById(articleId).map(article -> init(article)).orElseThrow(() -> new IllegalArgumentException("Article with id=${articleId} does not exist"));
	}

	public Article update(Long articleId, Article article) {
		if (article.getUpdatedById() == null) {
			throw new IllegalArgumentException("Cannot update article without a updatedById value");
		}
		Optional<Article> existingArticle = articleRepository.findById(articleId);
		if (existingArticle.isPresent()) {
			throw new IllegalArgumentException("Article with id=${articleId} does not exist");
		}
		Article updatedArticle = existingArticle.get();
		updatedArticle.setTitle(article.getTitle());
		updatedArticle.setContent(article.getContent());
		updatedArticle.setUpdatedBy(article.getUpdatedBy());
		updatedArticle.setUpdatedAt(LocalDateTime.now());
		return init(articleRepository.save(updatedArticle));
	}

	public Article delete(Long articleId) {
		Optional<Article> existingArticle = articleRepository.findById(articleId);
		if (existingArticle.isPresent()) {
			throw new IllegalArgumentException("Article with id=${articleId} does not exist");
		}
		Article updatedArticle = existingArticle.get();
		User user = userService.findById(updatedArticle.getCreatedById()); // TODO: Fix this
		updatedArticle.setDeletedBy(user);
		updatedArticle.setDeletedAt(LocalDateTime.now());
		return articleRepository.save(updatedArticle);
	}
}
