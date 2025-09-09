package com.etfbl.ip.rentalcompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.etfbl.ip.rentalcompany.dto.PostDTO;
import com.etfbl.ip.rentalcompany.dto.PromotionDTO;
import com.etfbl.ip.rentalcompany.util.ConnectionPool;
import com.etfbl.ip.rentalcompany.util.DBUtil;

public class PromotionDAO {
	private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String INSERT_SQL = "INSERT INTO promotion (title, description, duration, created_at) VALUES (?,?,?,?)";
	private static final String GET_ALL_SQL = "SELECT * FROM promotion";
	
	public boolean createPromotion(PromotionDTO promotion) {
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		
		try {
			connection = connectionPool.checkOut();
			preparedStatment = DBUtil.prepareStatement(connection, INSERT_SQL, false, 
					promotion.getTitle(),
					promotion.getDescription(),
					promotion.getDuration(),
					LocalDateTime.now()
			);
			return preparedStatment.executeUpdate() > 0;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtil.close(preparedStatment);
			connectionPool.checkIn(connection);
		}
	}
	
	public List<PromotionDTO> getAll() {
		List<PromotionDTO> promotions = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = connectionPool.checkOut();
			ps = conn.prepareStatement(GET_ALL_SQL);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				PromotionDTO prom = new PromotionDTO();
				prom.setId(rs.getLong("id"));
				prom.setTitle(rs.getString("title"));
				prom.setDescription(rs.getString("description"));
				prom.setDuration(rs.getTimestamp("duration").toLocalDateTime().toLocalDate());
				prom.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				
				promotions.add(prom);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			connectionPool.checkIn(conn);
		}
		return promotions;
	}
	
	public List<PromotionDTO> getPaginated(int page, int size) {
	    List<PromotionDTO> promotions = new ArrayList<>();
	    String sql = "SELECT * FROM promotion ORDER BY created_at DESC LIMIT ? OFFSET ?";
	    Connection conn= null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	    	conn = connectionPool.checkOut();
	        ps = conn.prepareStatement(sql);
	        
	        int offset = (page - 1) * size;
	        ps.setInt(1, size);
	        ps.setInt(2, offset);
	        
	        rs = ps.executeQuery();
	        while(rs.next()) {
				PromotionDTO prom = new PromotionDTO();
				prom.setId(rs.getLong("id"));
				prom.setTitle(rs.getString("title"));
				prom.setDescription(rs.getString("description"));
				prom.setDuration(rs.getTimestamp("duration").toLocalDateTime().toLocalDate());
				prom.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				
				promotions.add(prom);
			}
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			connectionPool.checkIn(conn);
		}
	    return promotions;
	}

	public int getTotalCount() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM promotion";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = connectionPool.checkOut();
			stmt = conn.prepareStatement(sql);
		    rs = stmt.executeQuery();
		    if (rs.next()) {
		    	count = rs.getInt(1);
		      }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        DBUtil.close(rs);
		        DBUtil.close(stmt);
		        connectionPool.checkIn(conn);
		    }
		   return count;
	}
	
	 public List<PromotionDTO> searchByTitle(String keyword) {
	        List<PromotionDTO> promotions = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            conn = connectionPool.checkOut();
	            ps = conn.prepareStatement("SELECT * FROM promotion WHERE LOWER(title) LIKE LOWER(?)");
	            ps.setString(1, "%" + keyword + "%");
	            rs = ps.executeQuery();

	            while (rs.next()) {
	                PromotionDTO p = new PromotionDTO();
	                p.setId(rs.getLong("id"));
					p.setTitle(rs.getString("title"));
					p.setDescription(rs.getString("description"));
					p.setDuration(rs.getTimestamp("duration").toLocalDateTime().toLocalDate());
					p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
	                promotions.add(p);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	        	 DBUtil.close(rs);
			     DBUtil.close(ps);
			     connectionPool.checkIn(conn);
	        }
	        return promotions;
	    }
}
