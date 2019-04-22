package com.java.xc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.xc.dao.menuDao;
import com.java.xc.domain.Menu;
import com.java.xc.util.DBUtil;

public class menuDaoimpl implements menuDao {
	private DBUtil db;

	@Override
	public boolean insertMenu(Menu m) {
		this.db = new DBUtil();
		String sql = "insert into menu values(?,?,?,?,?,?)";
		try {
			int i = this.db.update(sql, m.getMeid(), m.getMename(), m.getMeprice(), m.getMepuprice(), m.getTypeid(),m.getMediscount());
			return i > 0;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public boolean deleteMenu(int meid) {
		this.db = new DBUtil();
		String sql = "delete from menu where meid=" + meid;
		try {
			int i = this.db.update(sql);
			return i > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public boolean updateMenu(int meid, double mediscount) {
		this.db = new DBUtil();
		String sql = "update menu set mediscount=" + mediscount + "where meid=" + meid;
		try {
			int i = this.db.update(sql);
			return i > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public List<Menu> selectAllMe() {
		this.db = new DBUtil();
		String sql = "select * from menu";
		try {
			ResultSet rs = this.db.query(sql);
			List<Menu> list = new ArrayList<Menu>();
			while (rs.next()) {
				list.add(new Menu(rs.getInt("meid"), rs.getString("mename"), rs.getDouble("meprice"),
						rs.getInt("typeid"), rs.getDouble("mediscount"), rs.getDouble("mepuprice")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public Menu selectBymeid(int meid) {
		this.db = new DBUtil();
		String sql = "select * from menu where meid=" + meid;
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				return new Menu(rs.getInt("meid"), rs.getString("mename"), rs.getDouble("meprice"), rs.getInt("typeid"),
						rs.getDouble("mediscount"), rs.getDouble("mepuprice"));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return null;

	}

	@Override
	public Map<Integer, String> selectAlltype() {
		Map<Integer, String> map=new HashMap<Integer, String>();
		this.db = new DBUtil();
		String sql ="select * from menutype";
		try {
			ResultSet rs = this.db.query(sql);
			while(rs.next()){
				map.put(rs.getInt("typeid"), rs.getString("typename"));
			}
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return null;
	}

}
