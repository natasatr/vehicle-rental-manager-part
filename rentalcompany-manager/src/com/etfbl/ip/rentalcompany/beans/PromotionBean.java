package com.etfbl.ip.rentalcompany.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PromotionBean {
	private Long id;
	private String title;
	private String description;
	private LocalDate duration;
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
	
	

}
