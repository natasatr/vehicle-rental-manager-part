package com.etfbl.ip.rentalcompany.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.etfbl.ip.rentalcompany.beans.UserBean;
import com.etfbl.ip.rentalcompany.util.ConnectionPool;
import com.etfbl.ip.rentalcompany.util.DBUtil;

public class UserService {
	public UserBean login(String username, String password) {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPool.getConnectionPool().checkOut();
            String sql = "SELECT u.id, u.first_name, u.last_name, u.username, u.password, u.email, u.role\r\n"
            		+ "FROM user u\r\n"
            		+ "JOIN employee e ON e.id = u.id\r\n"
            		+ "WHERE u.username = ? AND u.password = ?";
            
            preparedStatement = DBUtil.prepareStatement(connection, sql, false, username, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	UserBean user = new UserBean();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                user.setLoggedIn(true);
                return user;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            ConnectionPool.getConnectionPool().checkIn(connection);
        }
    }

}
