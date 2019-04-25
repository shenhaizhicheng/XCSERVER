package com.java.xc.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.java.xc.dao.clientDao;
import com.java.xc.dao.shopcarDao;
import com.java.xc.domain.Client;
import com.java.xc.domain.Menu;
import com.java.xc.domain.Sales;
import com.java.xc.domain.ShopCar;
import com.java.xc.util.DBUtil;

public class shopcarDaoimpl implements shopcarDao {
	private DBUtil db;

	@Override
	public boolean insertShopcar(ShopCar s) {
		this.db = new DBUtil();
		String sql = "insert into shopcar values(?,?,?)";
		try {
			int i = this.db.update(sql, s.getM().getMeid(), s.getC()
					.getCaccount(), s.getMenum());
			return i > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}
	}

	@Override
	public boolean insertShopcarANY(ShopCar s) {
		ShopCar sc = this.selectBymeid(s.getM().getMeid(), s.getC().getCaccount());
		int menum = sc.getMenum();
		int num=menum+s.getMenum();
		this.db = new DBUtil();
		String sql="update shopcar set menum="+num+" where meid="+s.getM().getMeid()+" and caccount="+s.getC().getCaccount();
		try {
			int i = this.db.update(sql);
			return i>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean deleteMenuS(int meid, int caccount) {
		this.db = new DBUtil();

		String sql = "delete from shopcar where meid=" + meid + "and caccount="
				+ caccount;
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
	public boolean deleteMenuANY(int meid, int menum, int caccount) {
		ShopCar sc = this.selectBymeid(meid, caccount);
		this.db = new DBUtil();

		int num = sc.getMenum() - menum;
		String sql = "update shopcar set menum=" + num + " where meid=" + meid
				+ " and caccount=" + caccount;
		try {
			int i = db.update(sql);
			return i > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public ShopCar selectBymeid(int meid, int caccount) {
		this.db = new DBUtil();
		clientDao cD = new clientDaoimpl();
		String sql = "select m.meid,m.mename,m.meprice,m.typeid,m.mediscount,m.mepuprice,s.menum,s.caccount from shopcar s,menu m where s.meid=m.meid and s.meid="
				+ meid + " and s.caccount=" + caccount;
		try {
			ResultSet rs = this.db.query(sql);
			ShopCar s = null;
			if (rs.next()) {
				Client c = cD.selectBycac(rs.getInt("caccount"));
				Menu m = new Menu(rs.getInt("meid"), rs.getString("mename"),
						rs.getDouble("meprice"), rs.getInt("typeid"),
						rs.getDouble("mediscount"), rs.getDouble("mepuprice"));
				s = new ShopCar(m, rs.getInt("menum"), c);
			}
			return s;
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public List<ShopCar> selectBycac(int cacouunt) {
		clientDao cD = new clientDaoimpl();
		Client c = cD.selectBycac(cacouunt);
		this.db = new DBUtil();
		List<ShopCar> list = new ArrayList<ShopCar>();
		String sql = "select m.meid,m.mename,m.meprice,m.typeid,m.mediscount,m.mepuprice,s.menum,s.caccount from shopcar s,menu m where s.meid=m.meid and caccount="
				+ cacouunt;
		try {
			ResultSet rs = db.query(sql);
			while (rs.next()) {
				Menu m = new Menu(rs.getInt("meid"), rs.getString("mename"),
						rs.getDouble("meprice"), rs.getInt("typeid"),
						rs.getDouble("mediscount"), rs.getDouble("mepuprice"));
				list.add(new ShopCar(m, rs.getInt("menum"), c));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public boolean insertSales(ShopCar s, double meprofit) {
		this.db = new DBUtil();
//		Date date = new Date();
		Calendar cal=Calendar.getInstance();     
		String d = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+ "-"
				+ cal.get(Calendar.DATE);
		String sql = "insert into salesrecord values(?,?,to_date('" + d
				+ "','yyyy-mm-dd'),?,?)";
		try {
			int i = db.update(sql, s.getM().getMeid(), s.getC().getCaccount(),
					s.getMenum(), meprofit);
			return i > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}

	}

	@Override
	public boolean insertSalesANY(ShopCar s, double meprofit) {

		int sanumber = this.sBymeidAndCac(s.getM().getMeid(), s.getC()
				.getCaccount());
		this.db = new DBUtil();
		int num = sanumber + s.getMenum();
		Calendar cal=Calendar.getInstance();     
		String d = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+ "-"
				+ cal.get(Calendar.DATE);
		String sql = "update salesrecord set sanumber=" + num + " where meid="
				+ s.getM().getMeid() + " and caccount="
				+ s.getC().getCaccount()+" and sadate=to_date('"
				+ d + "','yyyy-mm-dd')";
		try {
			int i = db.update(sql);
			return i > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		return false;
	}

	@Override
	public List<Sales> exportSales() {
		List<Sales> list=new ArrayList<Sales>();
		this.db = new DBUtil();
		try {

			String sql = "select sa.meid 菜品编号 ,me.mename 菜名,to_char(sa.sadate,'yyyy-mm-dd') 销售日期 ,sa.caccount 客户编号,sa.sanumber 卖出数量  from salesrecord sa,menu me where sa.meid=me.meid order by sa.sadate";
			ResultSet rs = db.query(sql);
			while(rs.next()){
				list.add(new Sales(rs.getInt("菜品编号"), rs.getString("菜名"), rs.getString("销售日期"), rs.getInt("客户编号"), rs.getInt("卖出数量")));
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  finally {
				this.db.closed();
		}

	}

	@Override
	public int sBymeidAndCac(int meid, int caccount) {
		this.db = new DBUtil();
		Calendar cal=Calendar.getInstance();     
		String d = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+ "-"
				+ cal.get(Calendar.DATE);
		String sql = "select sanumber from salesrecord where sadate=to_date('"
				+ d + "','yyyy-mm-dd') and meid=" + meid + " and caccount="
				+ caccount;
		try {
			ResultSet rs = db.query(sql);
			if (rs.next()) {
				return rs.getInt("sanumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.db.closed();
		}
		return 0;
	}
	
	@Override
	public int selectIDSalT() {
		this.db = new DBUtil();
		String sql="select s.meid,s.su from(select meid,sum(sanumber) su from salesrecord group by meid )s order by s.su desc";
		try {
			ResultSet rs = this.db.query(sql);
			if(rs.next()){
				return rs.getInt("meid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Map<String,Integer> salesTall() {
		this.db = new DBUtil();
		Map<String,Integer> map=new TreeMap<String,Integer>();
		String sql="select s.meid,me.mename,s.su from(select meid,sum(sanumber) su from salesrecord group by meid )s,menu me where s.meid=me.meid order by s.su desc";
		try {
			ResultSet rs = this.db.query(sql);
			while(rs.next()){
				map.put(rs.getString("mename"),rs.getInt("su"));
			}
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteSale() {
		this.db = new DBUtil();
		String sql="delete from salesrecord";
		try {
			int i = this.db.update(sql);
			return i>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void insertOF(String s, ShopCar sc) {
		this.db = new DBUtil();
//		Date d=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date = sdf.format(d);
		String sql="insert into orderform values(?,?,?,sysdate,?)";
		try {
			int i = this.db.update(sql,s,sc.getC().getCaccount(),sc.getM().getMeid(),sc.getMenum());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<String> selectOFid() {
		List<String> list=new ArrayList<String>();
		this.db = new DBUtil();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql="select ofid from orderform group by ofid";
		try {
			ResultSet rs = this.db.query(sql);
			while(rs.next()){
//				String da = sdf.format(rs.getDate("oftime"));
//				String str=rs.getString("ofid")+"#"+da;
				list.add(rs.getString("ofid"));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Integer> selectByOfid(String ofid) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		this.db = new DBUtil();
		String sql="select me.mename,o.ofnum from menu me,orderform o where me.meid=o.meid and ofid='"+ofid+"'";
		try {
			ResultSet rs = this.db.query(sql);
			while(rs.next()){
				map.put(rs.getString("mename"), rs.getInt("ofnum"));
			}
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteByofid(String ofid) {
		this.db = new DBUtil();
		String sql="delete from orderform where ofid='"+ofid+"'";
		try {
			int i = this.db.update(sql);
			return i>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String findtime(String ofid) {
		this.db = new DBUtil();
		String sql="select to_char(oftime,'yyyy-mm-dd hh24:mi:ss') otime from orderform where ofid='"+ofid+"'";
		try {
			ResultSet rs = this.db.query(sql);
			if(rs.next()){
				return rs.getString("otime");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
