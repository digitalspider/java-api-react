package au.com.digitalspider.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.api.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	List<Article> findByTitleContainingIgnoreCase(String title);

	Article findOneByTitleIgnoreCase(String title);
}
