package com.java.xc.biz.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.xc.biz.cardBiz;
import com.java.xc.biz.clientBiz;
import com.java.xc.biz.managerBiz;
import com.java.xc.biz.menuBiz;
import com.java.xc.biz.shopcarBiz;
import com.java.xc.biz.totalBiz;
import com.java.xc.domain.Card;
import com.java.xc.domain.Client;
import com.java.xc.domain.Manager;
import com.java.xc.domain.Menu;
import com.java.xc.domain.ShopCar;

public class totalBizimpl implements totalBiz {
	managerBiz mB;
	clientBiz cB;
	cardBiz caB;
	menuBiz meB;
	shopcarBiz scB;

	public totalBizimpl() {
		super();
		this.mB =new managerBizimpl();
		this.cB =new clientBizimpl();
		this.caB =new cardBizimpl();
		this.meB =new menuBizimpl();
		this.scB =new shopcarBizimpl();
	}

	@Override
	public Manager selectBymac(int maccount) {
		
		return mB.selectBymac(maccount);
	}

	@Override
	public String cliAndcard(Client c) {
		int caccount = cB.insertCli(c);
		int cid = caB.opencard(caccount);
		if(caccount!=0&&cid!=0){
		String str="注册成功，您的cc号为"+caccount+"，为您开的新卡，卡号为:"+cid;
		return str;
		}
		return "注册失败";
	}

	@Override
	public Client selectByemail(String cemail) {
		
		return cB.selectByemail(cemail);
	}

	@Override
	public Client selectBycac(int caccount) {
		
		return cB.selectBycac(caccount);
	}

	@Override
	public List<Client> selectAllcli() {
		
		return cB.selectAllcli();
	}

	@Override
	public String updatePass(int caccount, String cpassword) {
	
		return cB.updatePass(caccount, cpassword);
	}

	@Override
	public double cardmoney(int cid, double money) {
		
		return caB.cardmoney(cid, money);
	}

	@Override
	public String dolost(int cid) {
		
		return caB.dolost(cid)?"挂失成功":"挂失失败";
	}
	
	@Override
	public String relieveC(int cid) {
		
		return caB.relieveCard(cid)?"解除成功":"解除失败";
	}

	@Override
	public String selectState(int cid) {
		
		return caB.selectState(cid);
	} 

	@Override
	public Card buildcard(Card card, int caccount) {
		int cid = caB.buildcard(card, caccount);
		Card c = caB.selectCBycid(cid);
		return c;
	}

	@Override
	public String insertShopcar(ShopCar s) {
		
		return scB.insertShopcar(s);
	}

	@Override
	public String deleteMenuS(int meid, int menum, int caccount) {
		
		return scB.deleteMenuS(meid, menum,caccount);
	}

	@Override
	public ShopCar selectBymeid(int meid, int caccount) {
		
		return scB.selectBymeid(meid, caccount);
	}

	@Override
	public double addMoney(int caccount) {
		List<ShopCar> list = scB.selectMBycac(caccount);
		double price=0;
		for (ShopCar sc : list) {
			price+=sc.getM().getMeprice()*sc.getMenum()*sc.getM().getMediscount();
		}
		double discount = caB.selectDis(caccount);
		double endprice=price*discount;
		return endprice;
	}

	@Override
	public List<ShopCar> selectMBycac(int caccount) {
		
		return scB.selectMBycac(caccount);
	}

	@Override
	public boolean insertSales(Map<ShopCar, Double> map) {
		Set<ShopCar> keySet = map.keySet();
		for (ShopCar sc : keySet) {
			boolean flag = scB.insertSales(sc, map.get(sc));
		}
		
		return true;
	}

	@Override
	public String insertMenu(Menu m) {
		
		return meB.insertMenu(m);
	}

	@Override
	public String deleteMenu(int meid) {
		
		return meB.deleteMenu(meid);
	}

	@Override
	public String updateMenu(int meid, double mediscount) {
		
		return meB.updateMenu(meid, mediscount);
	}

	@Override
	public List<Menu> selectAllMe() {
		
		return meB.selectAllMe();
	}

	@Override
	public Menu selectBymeid(int meid) {
		
		return meB.selectBymeid(meid);
	}

	@Override
	public Map<Integer, String> selectAlltype() {
		
		return meB.selectAlltype();
	}

	@Override
	public boolean exportSales() {
		
		return scB.exportSales();
	}

	@Override
	public Card selectBycid(int cid) {
		
		return caB.selectCBycid(cid);
	}

	@Override
	public double updateBa(int cid, double price) {
		
		return caB.updateBa(cid, price);
	}

	@Override
	public double selectDis(int caccount) {
		// TODO Auto-generated method stub
		return caB.selectDis(caccount);
	}

	

}
