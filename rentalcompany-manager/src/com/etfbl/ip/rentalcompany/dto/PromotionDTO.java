package com.etfbl.ip.rentalcompany.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class PromotionDTO implements Serializable {
	private Long id;
	private String title;
	private String description;
	private LocalDate duration;
	private LocalDateTime createdAt;
	
	public PromotionDTO() {
		super();
	}

	public PromotionDTO(Long id, String title, String description, LocalDate duration, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.createdAt = createdAt;
	}

	public PromotionDTO(String title, String description, LocalDate duration, LocalDateTime createdAt) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDuration() {
		return duration;
	}

	public void setDuration(LocalDate duration) {
		this.duration = duration;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, description, duration, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PromotionDTO other = (PromotionDTO) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(description, other.description)
				&& Objects.equals(duration, other.duration) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title);
	}
	
}
