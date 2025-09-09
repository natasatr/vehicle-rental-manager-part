package com.etfbl.ip.rentalcompany.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class PostDTO implements Serializable {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	
	public PostDTO() {
		super();
	}

	public PostDTO(String title, String content, LocalDateTime createdAt) {
		super();
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}

	public PostDTO(Long id, String title, String content, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
