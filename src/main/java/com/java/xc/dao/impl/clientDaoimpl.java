package com.java.xc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.java.xc.dao.clientDao;
import com.java.xc.domain.Card;
import com.java.xc.domain.Client;
import com.java.xc.util.DBUtil;

public class clientDaoimpl implements clientDao {
	private DBUtil db;

	@Override
	public int insertCli(Client c) {
		this.db = new DBUtil();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(c.getCbirth());
		String sql = "insert into client values(seq_caccount.nextval,?,?,?,to_date('" + date + "','yyyy-mm-dd'),?)";
		try {
			int i = db.update(sql, c.getCpassword(), c.getCname(), c.getCsex(),c.getCemail());	
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.db.closed();
		}
		Client cl = this.selectByemail(c.getCemail());
		return cl.getCaccount();

	}

	@Override
	public Client selectByemail(String cemail) {
		this.db = new DBUtil();
		String sql = "select * from client where cemail='" + cemail+"'";
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				Client c = new Client();
				c.setCaccount(rs.getInt("caccount"));
				c.setCpassword(rs.getString("cpassword"));
				c.setCname(rs.getString("cname"));
				c.setCsex(rs.getString("csex"));
				c.setCemail(rs.getString("cemail"));
				String str = rs.getString("cbirth");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				c.setCbirth(sdf.parse(str));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		return null;

	}

	@Override
	public Client selectBycac(int caccount) {
		this.db = new DBUtil();
		String sql = "select * from client cl,card ca where cl.caccount=ca.caccount and cl.caccount=" + caccount;
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				Client c = new Client();
				c.setCaccount(rs.getInt("caccount"));
				c.setCpassword(rs.getString("cpassword"));
				c.setCname(rs.getString("cname"));
				c.setCsex(rs.getString("csex"));
				c.setCemail(rs.getString("cemail"));
				String str = rs.getString("cbirth");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				c.setCbirth(sdf.parse(str));
				Card card=new Card(rs.getDouble("cabalance"),rs.getString("catype"),rs.getString("castate"),rs.getDouble("caintegral")); 
				card.setCaid(rs.getInt("cid"));
				c.setCard(card);
				return c;
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		} catch (ParseException e) {

			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public List<Client> selectAllcli() {
		this.db = new DBUtil();
		String sql = "select * from client cl,card ca where cl.caccount=ca.caccount";
		try {
			ResultSet rs = this.db.query(sql);
			List<Client> list = new ArrayList<Client>();
			while (rs.next()) {
				Client c = new Client(rs.getString("cpassword"), rs.getString("cname"), rs.getString("csex"),
						rs.getDate("cbirth"), rs.getString("cemail"));
				c.setCaccount(rs.getInt("caccount"));
				Card card=new Card(rs.getDouble("cabalance"),rs.getString("catype"),rs.getString("castate"),rs.getDouble("caintegral")); 
				card.setCaid(rs.getInt("cid"));
				c.setCard(card);
				list.add(c);

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
	public boolean updatePass(int caccount, String cpassword) {
		this.db = new DBUtil();
		String sql = "update client set cpassword='" + cpassword + "' where caccount=" + caccount;
		try {
			int i = this.db.update(sql);
			return i > 0;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}finally {
			this.db.closed();
		}

	}

}
