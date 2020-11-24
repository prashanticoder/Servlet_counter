package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ConnectionFactory;



public class JdbcDemoCounter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	AtomicInteger counter = new AtomicInteger(0);
	Connection conn = ConnectionFactory.getConnection();
	PrintWriter out;
	
	@Override
	public void init() throws ServletException {
		super.init();
		Integer value = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from counter_table");
			while(rs.next()) {
				value = rs.getInt("counter");
				System.out.println(value);
			}
			
			counter = new AtomicInteger(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		counter.addAndGet(1);
		out.print(counter);
	}



	@Override
	public void destroy() {
		try {
			PreparedStatement pstmt = conn.prepareStatement("update counter_table set counter = ?");
			System.out.println(counter.get());
			pstmt.setInt(1, counter.get());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		super.destroy();
	}
}