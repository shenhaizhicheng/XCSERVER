package com.java.xc.dao.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.java.xc.dao.clientDao;
import com.java.xc.dao.shopcarDao;
import com.java.xc.domain.Client;
import com.java.xc.domain.Menu;
import com.java.xc.domain.ShopCar;
import com.java.xc.util.DBUtil;

public class shopcarDaoimpl implements shopcarDao {
	private DBUtil db;

	@Override
	public boolean insertShopcar(ShopCar s) {
		this.db = new DBUtil();
		String sql = "insert into shopcar values(?,?,?)";
		try {
			int i = this.db.update(sql, s.getM().getMeid(), s.getC().getCaccount(),s.getMenum());
			return i > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.db.closed();
		}
	}

	@Override
	public boolean deleteMenuS(int meid, int caccount) {
		this.db = new DBUtil();

		String sql = "delete from shopcar where meid=" + meid
				+ "and caccount=" + caccount;
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
         this.db = new DBUtil();
         ShopCar sc = this.selectBymeid(meid, caccount);
         int num=sc.getMenum()-menum;
	     String sql ="update shopcar set menum="+num+" where meid="+meid+" and caccount="+caccount;
	     try {
			int i = db.update(sql);
			return i>0;
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
				+ meid+" and s.caccount="+caccount;
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
		}finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public List<ShopCar> selectBycac(int cacouunt) {
		this.db = new DBUtil();
		List<ShopCar> list=new ArrayList<ShopCar>();
		clientDao cD = new clientDaoimpl();
		Client c = cD.selectBycac(cacouunt);
		String sql= "select m.meid,m.mename,m.meprice,m.typeid,m.mediscount,m.mepuprice,s.menum,s.caccount from shopcar s,menu m where s.meid=m.meid and caccount="+cacouunt;
		try {
			ResultSet rs = db.query(sql);
			while (rs.next()) {
				Menu m = new Menu(rs.getInt("meid"), rs.getString("mename"),
						rs.getDouble("meprice"), rs.getInt("typeid"),
						rs.getDouble("mediscount"), rs.getDouble("mepuprice"));
				list.add(new ShopCar(m, rs.getInt("menum"), c)) ;
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return null;
	}

	@Override
	public boolean insertSales(ShopCar s, double meprofit) {
		this.db = new DBUtil();
		Date date=new Date();
		String d=date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		String sql="insert into salesrecord values(?,?,to_date('"+d+"','yyyy-mm-dd'),?,?)";
		try {
			int i = db.update(sql, s.getM().getMeid(),s.getC().getCaccount(),s.getMenum(),meprofit);
			return i>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			this.db.closed();
		}

		
	}
	

	@Override
	public boolean insertSalesANY(ShopCar s, double meprofit) {
		this.db = new DBUtil();
		int sanumber = this.sBymeidAndCac(s.getM().getMeid(),s.getC().getCaccount());
		int num=sanumber+s.getMenum();
		String sql="update salesrecord set sanumber="+num+" where meid="+s.getM().getMeid()+" and caccount="+s.getC().getCaccount();
		try {
			int i = db.update(sql);
			return i>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return false;
	}

	@Override
	public boolean exportSales() {
		this.db = new DBUtil();
		String path = "a.xls";
		FileOutputStream out = null;
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("表");
		try {

			String sql = "select sa.meid 菜品编号 ,me.mename 菜名,sa.sadate 销售日期 ,sa.caccount 客户编号,sa.sanumber 卖出数量  from salesrecord sa,menu me where sa.meid=me.meid";
			ResultSet rs = db.query(sql);
			ResultSetMetaData rsmd = rs.getMetaData();// 得到结果集的字段名
			int c = rsmd.getColumnCount();// 得到数据表的结果集的字段的数量
			// 生成表单的第一行，即表头
			HSSFRow row0 = sheet.createRow(0);// 创建第一行
			for (int i = 0; i < c; i++) {
				HSSFCell cel = row0.createCell(i);// 创建第一行的第i列
				cel.setCellValue(rsmd.getColumnName(i + 1));
				// cel.setCellStyle(style);
			}
			// 将数据表中的数据按行导入进Excel表中
			int r = 1;
			while (rs.next()) {
				HSSFRow row = sheet.createRow(r++);// 创建非第一行的其他行
				for (int i = 0; i < c; i++) {// 仍然是c列，导入第r行的第i列
					HSSFCell cel = row.createCell(i);
					// 以下两种写法均可
					// cel.setCellValue(rs.getString(rsmd.getColumnName(i+1)));
					cel.setCellValue(rs.getString(i + 1));
				}
			}
			// 用文件输出流类创建名为table的Excel表格
			out = new FileOutputStream(path);
			book.write(out);// 将HSSFWorkBook中的表写入输出流中

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				out.close();
				this.db.closed();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public int sBymeidAndCac(int meid,int caccount) {
		this.db = new DBUtil();
		Date date=new Date();
		String d=date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		String sql="select sanumber from salesrecord where sadate=to_date('"+d+"','yyyy-mm-dd') and meid="+meid+" and caccount="+caccount;
		try {
			ResultSet rs = db.query(sql);
			if(rs.next()){
			return rs.getInt("sanumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.db.closed();
		}
		return 0;
	}


}
