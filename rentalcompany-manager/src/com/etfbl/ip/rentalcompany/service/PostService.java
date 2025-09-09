package com.etfbl.ip.rentalcompany.service;

import java.time.LocalDateTime;
import java.util.List;

import com.etfbl.ip.rentalcompany.beans.PostBean;
import com.etfbl.ip.rentalcompany.dao.PostDAO;
import com.etfbl.ip.rentalcompany.dto.PostDTO;


public class PostService {
	
	private PostDAO postDAO = new PostDAO();
	
	public boolean createPost(PostBean post) {
		PostDTO dto = new PostDTO();
		dto.setTitle(post.getTitle());
		dto.setContent(post.getContent());
		dto.setCreatedAt(LocalDateTime.now());
		
		return postDAO.createPost(dto);
	}
	
	public List<PostDTO> getAll() {
		return postDAO.getAll();
	}
	
	public List<PostDTO> getPaged(int n, int m) {
		return postDAO.getPaginated(n, m);
	}
	
	public int getTotalCount() {
		return postDAO.getTotalCount();
	}
	
	public List<PostDTO> searchByContent(String content) {
		return postDAO.searchByContent(content);
	}
}

