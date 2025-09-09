package com.etfbl.ip.rentalcompany.util;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... values)throws SQLException {
			PreparedStatement preparedStatement = 
					connection.prepareStatement(
							sql,
							returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
			setValues(preparedStatement, values);
			return preparedStatement;
}


	public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			preparedStatement.setObject(i + 1, values[i]);
		}
	}
	
	public static void close(Connection con) {
		if (con != null) {
            try {
            	con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }

    public static void close(Connection connection, Statement statement) {
        close(statement);
        close(connection);
    }
	
	
}
