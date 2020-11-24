package com;

import java.sql.Connection;

public class ConnectionChecker {
	
	public static void main(String[] args) {
		Connection conn = ConnectionFactory.getConnection();
		if(conn!=null) {
			System.out.println("successfully connected");
			System.out.println(conn.getClass());
		}
	}

}
