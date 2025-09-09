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

public class PostDAO {
	private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String INSERT_SQL = "INSERT INTO post (title, content, created_at) VALUES (?,?,?)";
	private static final String GET_ALL_SQL = "SELECT * FROM post";
	
	public boolean createPost(PostDTO post) {
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		
		try {
			connection = connectionPool.checkOut();
			preparedStatment = DBUtil.prepareStatement(connection, INSERT_SQL, false, 
					post.getTitle(),
					post.getContent(),
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
	
	public List<PostDTO> getAll() {
		List<PostDTO> posts = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = connectionPool.checkOut();
			ps = conn.prepareStatement(GET_ALL_SQL);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				PostDTO post = new PostDTO();
				post.setId(rs.getLong("id"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				
				posts.add(post);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			connectionPool.checkIn(conn);
		}
		return posts;
	}
	
	public List<PostDTO> getPaginated(int page, int size) {
	    List<PostDTO> posts = new ArrayList<>();
	    String sql = "SELECT * FROM post ORDER BY created_at DESC LIMIT ? OFFSET ?";
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
					PostDTO post = new PostDTO();
					post.setId(rs.getLong("id"));
					post.setTitle(rs.getString("title"));
					post.setContent(rs.getString("content"));
					post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
					
					posts.add(post);
				}
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			connectionPool.checkIn(conn);
		}
	    return posts;
	}

	public int getTotalCount() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM post";
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

	public List<PostDTO> searchByContent(String keyword) {
        List<PostDTO> resultsPost = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.checkOut();
            ps = conn.prepareStatement("select * from post where lower(content) like lower(?)");
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                PostDTO p = new PostDTO();
                p.setId(rs.getLong("id"));
				p.setTitle(rs.getString("title"));
				p.setContent(rs.getString("content"));
				p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                resultsPost.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	 DBUtil.close(rs);
		     DBUtil.close(ps);
		     connectionPool.checkIn(conn);
        }
        return resultsPost;
    }
}
