package com.revature.charityapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.charityapp.util.Logger;

public class CloseConnection {
	
	public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs)
	{
		try {
			if(conn != null)
				conn.close();
			if(pstmt != null)
				pstmt.close();
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			Logger.error(e.getMessage());
		}
	}

}
