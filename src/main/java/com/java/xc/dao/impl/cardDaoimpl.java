package com.java.xc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.xc.dao.cardDao;
import com.java.xc.domain.Card;
import com.java.xc.util.DBUtil;

public class cardDaoimpl implements cardDao {
	private DBUtil db;

	@Override
	public int opencard(int caccount) {
		this.db = new DBUtil();
		String sql = "insert into card values(seq_caccount.nextval," + caccount + ",1,0,0)";
		try {
			int i = this.db.update(sql);

			return this.selectBycaccount(caccount);

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public double cardmoney(int cid, double money) {
		this.db = new DBUtil();
		Card c = this.selectCBycid(cid);
		double m=c.getCabalance()+money;
		String sql = "update card set cabalance=" + m + "where cid=" + cid;
		try {
			int i = this.db.update(sql);
			if( i > 0){
				return this.selectCBycid(cid).getCabalance();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			this.db.closed();
		}
		return 0000;
	}

	@Override
	public boolean updateState(int cid) {
//		System.out.println(cid);
		this.db = new DBUtil();
		String sql = "update card set castate='" + 1 + "' where cid=" + cid;
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
	public String selectState(int cid) {
		this.db = new DBUtil();
		String sql = "select * from card where cid=" + cid;
		try {
			ResultSet rs = this.db.query(sql);
			if (rs.next()) {
				String s = rs.getString("castate");
				return s;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public int buildcard(Card card, int caccount) {
		this.db = new DBUtil();
		this.delectcard(caccount);
		String sql = "insert into card values(seq_caccount.nextval," + caccount + ",?,?,?)";
		try {
			int i = this.db.update(sql, card.getCatype(), card.getCabalance(), card.getCastate());
			return this.selectBycaccount(caccount);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return 0;
	}

	@Override
	public int selectBycaccount(int caccount) {
		this.db = new DBUtil();
		String sql = "select * from card where caccount=" + caccount;
		try {
			ResultSet rs = this.db.query(sql);
			Card c = null;
			if (rs.next()) {
				c = new Card();
				c.setCaid(rs.getInt("cid"));
			}

			return c.getCaid();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return 0;
	}

	@Override
	public void delectcard(int caccount) {
		this.db = new DBUtil();
		String sql = "delete from card where caccount=" + caccount;
		try {
			this.db.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}

	}

	@Override
	public Card selectCBycid(int cid) {
		this.db = new DBUtil();
		String sql ="select * from card where cid="+cid;
		try {
			ResultSet rs = this.db.query(sql);
			if(rs.next()){
				Card c=new Card(rs.getDouble("cabalance"), rs.getString("catype"), rs.getString("castate"));
				c.setCaid(rs.getInt("cid"));
				return c;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public double selectDis(int caccount) {
		this.db = new DBUtil();
		String sql ="select ct.catypedis from card ca,cardtype ct where ca.catype=ct.catype and caccount="+caccount;
		try {
			ResultSet rs = this.db.query(sql);
			if(rs.next()){
				return rs.getDouble("catypedis");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return 0;
	}

	@Override
	public double updateBa(int cid,double price) {
		this.db = new DBUtil();
		Card card = this.selectCBycid(cid);
		double p=card.getCabalance()-price;
		String sql="update card set cabalance="+p+" where cid="+cid;
		try {
			int i = db.update(sql);
			if(i>0){
				return this.selectCBycid(cid).getCabalance();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return 0000;
	}

	@Override
	public boolean relieveC(int cid) {
		this.db = new DBUtil();
		String sql = "update card set castate='" + 0 + "' where cid=" + cid;
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

}
