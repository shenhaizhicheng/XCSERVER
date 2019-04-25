package com.java.xc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.xc.dao.staffDao;
import com.java.xc.domain.Staff;
import com.java.xc.util.DBUtil;

public class staffDaoimpl implements staffDao {
	private DBUtil db;

	@Override
	public int insertSta(Staff s) {
		this.db = new DBUtil();
		String sql = "insert into staff values(seq_saccount.nextval,?,?,?,?)";
		try {
			int i = this.db.update(sql, s.getSname(),"123456", s.getSposition(), s.getSemail());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		Staff s1 = this.selectByemail(s.getSemail());

		return s1.getSaccount();
	}

	@Override
	public Staff selectBysac(int saccount) {
		this.db = new DBUtil();
		String sql = "select * from staff where saccount=" + saccount;
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				Staff s = new Staff(rs.getString("sname"),rs.getString("sposition"), rs.getString("semail"));
				s.setSaccount(rs.getInt("saccount"));
				s.setSpassword(rs.getString("spassword"));
				return s;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteSta(int saccount) {
		this.db = new DBUtil();
		String sql = "delete from staff where saccount=" + saccount;
		try {
			int i = this.db.update(sql);
			return i > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Staff selectByemail(String email) {
		this.db = new DBUtil();
		String sql = "select * from staff where semail='" + email + "'";
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				Staff s = new Staff( rs.getString("sname"),
						rs.getString("sposition"), rs.getString("semail"));
				s.setSaccount(rs.getInt("saccount"));
				s.setSpassword( rs.getString("spassword"));
				return s;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updatespw(int saccount,String newpw) {
		this.db = new DBUtil();
		String sql="update staff set spassword="+newpw+" where saccount="+saccount;
		try {
			int i = this.db.update(sql);
			return i>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
