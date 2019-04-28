package au.com.digitalspider.api.model;

/*
import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.Loader
import org.hibernate.annotations.NamedNativeQuery
import org.hibernate.annotations.ParamDef
*/
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
// @Loader(namedQuery = "activeSelect")
// @NamedNativeQuery(name="activeSelect", query="select * from article where (deleted_at is null or deleted_at>now()) and id= ?", resultClass = Article.class)
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@Transient
	int titleLength;

	@Transient
	Long createdById;
	@Transient
	Long updatedById;
	@Transient
	Long deletedById;

	@ManyToOne
	@JoinColumn(insertable = true, updatable = false)
	private User createdBy = null;

	@Column(insertable = true, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne
	private User updatedBy;

	private LocalDateTime updatedAt = LocalDateTime.now();

	@ManyToOne
	private User deletedBy;

	private LocalDateTime deletedAt = LocalDateTime.now();

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content + ", titleLength=" + titleLength + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTitleLength() {
		return titleLength;
	}

	public void setTitleLength(int titleLength) {
		this.titleLength = titleLength;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}

	public Long getDeletedById() {
		return deletedById;
	}

	public void setDeletedById(Long deletedById) {
		this.deletedById = deletedById;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

}