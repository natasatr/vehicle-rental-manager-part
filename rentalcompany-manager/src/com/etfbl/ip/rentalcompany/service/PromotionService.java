package com.etfbl.ip.rentalcompany.service;

import java.util.List;

import com.etfbl.ip.rentalcompany.beans.PromotionBean;
import com.etfbl.ip.rentalcompany.dao.PromotionDAO;
import com.etfbl.ip.rentalcompany.dto.PostDTO;
import com.etfbl.ip.rentalcompany.dto.PromotionDTO;

public class PromotionService {
	private PromotionDAO promotionDAO = new PromotionDAO();
	
	public boolean createPromotion(PromotionBean promotionBean) {
		PromotionDTO dto = new PromotionDTO();
		dto.setTitle(promotionBean.getTitle());
		dto.setDescription(promotionBean.getDescription());
		dto.setDuration(promotionBean.getDuration());
		
		return promotionDAO.createPromotion(dto);
	}
	
	public List<PromotionDTO> getAll() {
		return promotionDAO.getAll();
	}
	
	
	public List<PromotionDTO> getPaged(int n, int m) {
		return promotionDAO.getPaginated(n, m);
	}
	
	public int getTotalCount() {
		return promotionDAO.getTotalCount();
	}
	
	public List<PromotionDTO> searchByTitle(String title) {
		return promotionDAO.searchByTitle(title);
	}

}
