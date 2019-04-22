package com.java.xc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.xc.dao.managerDao;
import com.java.xc.domain.Manager;
import com.java.xc.util.DBUtil;

public class managerDaoimpl implements managerDao {
	private DBUtil db;

	@Override
	public Manager selectBymac(int maccount) {
		this.db = new DBUtil();
		String sql = "select * from manager where maccount=" + maccount;
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				return new Manager(rs.getInt("maccount"), rs.getString("mpassword"), rs.getString("mname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.db.closed();
		}
		return null;

	}

}
