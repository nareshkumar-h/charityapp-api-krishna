package com.revature.charityapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.FundRequest;
import com.revature.charityapp.util.CloseConnection;
import com.revature.charityapp.util.MessageConstant;

@Repository
public class FundDAO {
	
/** Start **/
	
	@Autowired
	private DataSource dataSource;
	
	/** List fund request **/
	public List<FundRequest> findByAll() throws ServiceException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FundRequest> list = null;

		
		try {
			list = new ArrayList<>();
			conn = dataSource.getConnection();
			String sqlStmt = "SELECT id,request_type,description,"
					+ "(amount -(SELECT IFNULL(SUM(amount),0) FROM transaction WHERE fund_request_id = fr.id)) as needed_amount,"
					+ "expire_date FROM fund_request fr"
					+ " WHERE"
					+ " expire_date >= NOW()"
					+ " AND amount > (SELECT IFNULL(SUM(amount),0) as funded_amount FROM transaction WHERE fund_request_id = fr.id)";
			pstmt = conn.prepareStatement(sqlStmt);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				Integer fundRequestId = rs.getInt("id");
				String requestType = rs.getString("request_type");
				String description = rs.getString("description");
				Double neededAmount = rs.getDouble("needed_amount");
				Date expireDate = rs.getDate("expire_date");
				
				FundRequest request = null;
				
				request = new FundRequest();
				request.setId(fundRequestId);
				request.setRequestType(requestType);
				request.setDescription(description);
				request.setAmount(neededAmount);
				request.setExpireDate(expireDate.toLocalDate());
				list.add(request);
			}
			
		} catch(SQLException e){
			throw new ServiceException(MessageConstant.UNABLE_TO_LIST_FUND_REQUEST);
		} finally {
			
				CloseConnection.close(conn, pstmt, rs);;
			
		}
		return list;
	}
	
	/** End **/

}
