package com.java.xc.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.java.xc.biz.shopcarBiz;
import com.java.xc.dao.shopcarDao;
import com.java.xc.dao.impl.shopcarDaoimpl;
import com.java.xc.domain.ShopCar;

public class shopcarBizimpl implements shopcarBiz {
	private shopcarDao scD;

	public shopcarBizimpl() {
		super();
		this.scD =new shopcarDaoimpl();
	}

	@Override
	public String insertShopcar(ShopCar s) {
		ShopCar sc = scD.selectBymeid(s.getM().getMeid(), s.getC().getCaccount());
		if(sc!=null){
			return scD.insertShopcarANY(s)?"添加成功":"添加失败";
		}
		return scD.insertShopcar(s)?"添加成功":"添加失败";
	}

	@Override
	public String deleteMenuS(int meid, int menum,int caccount) {
		ShopCar sc = scD.selectBymeid(meid, caccount);
		if(sc.getMenum()==menum){
			return scD.deleteMenuS(meid, caccount)?"移除成功":"移除失败";
		}else{
			return scD.deleteMenuANY(meid, menum, caccount)?"移除成功":"移除失败";
		}
		
	}

	@Override
	public ShopCar selectBymeid(int meid,int caccount) {
		
		return scD.selectBymeid(meid, caccount);
	}

	@Override
	public List<ShopCar> selectMBycac(int caccount) {
		// TODO Auto-generated method stub
		return scD.selectBycac(caccount);
	}

	@Override
	public boolean insertSales(ShopCar s, double meprofit) {
		int num = scD.sBymeidAndCac(s.getM().getMeid(),s.getC().getCaccount());
		if(num!=0){
			return scD.insertSalesANY(s, meprofit);
		}
		return scD.insertSales(s, meprofit);
	}

	@Override
	public Map<String,Integer> salesTall() {
		
		return scD.salesTall();
	}

	@Override
	public boolean deleteSale() {
		// TODO Auto-generated method stub
		return scD.deleteSale();
	}

	@Override
	public void insertOF(String s, ShopCar sc) {
		scD.insertOF(s,sc);
		
	}

	@Override
	public List<String> selectOFid() {
		List<String> l=new ArrayList<String>();
		List<String> list = scD.selectOFid();
		for (String ofid : list) {
			String ftime = scD.findtime(ofid);
			String str=ofid+"#"+ftime;
			l.add(str);
		}
		return l;
	}

	@Override
	public Map<String, Integer> selectByOfid(String ofid) {
		// TODO Auto-generated method stub
		return scD.selectByOfid(ofid);
	}

	@Override
	public boolean deleteByofid(String ofid) {
		// TODO Auto-generated method stub
		return scD.deleteByofid(ofid);
	}

	@Override
	public int selectIDSalT() {
		
		return scD.selectIDSalT();
	}

	@Override
	public String exportSales() {
		// TODO Auto-generated method stub
		return scD.exportSales();
	}

}
