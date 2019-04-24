package com.java.xc.biz.impl;

import com.java.xc.biz.cardBiz;
import com.java.xc.dao.cardDao;
import com.java.xc.dao.impl.cardDaoimpl;
import com.java.xc.domain.Card;

public class cardBizimpl implements cardBiz {
	private cardDao cd;

	public cardBizimpl() {
		super();
		this.cd = new cardDaoimpl();
	}

	@Override
	public int opencard(int caccount) {

		return this.cd.opencard(caccount);
	}

	@Override
	public double cardmoney(int cid, double money) {

		return this.cd.cardmoney(cid, money);
	}

	@Override
	public boolean dolost(int cid) {
		if (this.cd.selectState(cid).equals("0")) {
			return this.cd.updateState(cid);
		}
		return false;
	}

	@Override
	public boolean relieveCard(int cid) {
		
		return cd.relieveC(cid);
	}
	
	@Override
	public int buildcard(Card card, int caccount) {

		return this.cd.buildcard(card, caccount);

	}

	@Override
	public String selectState(int cid) {
		// TODO Auto-generated method stub
		return cd.selectState(cid);
	}

	@Override
	public Card selectCBycid(int cid) {
		// TODO Auto-generated method stub
		return cd.selectCBycid(cid);
	}

	@Override
	public double selectDis(int caccount) {
		double dis = cd.selectDis(caccount);
		if(dis==0){
			return 1;
		}
		return dis;
	}

	@Override
	public double updateBa(int cid, double price) {
		
		return cd.updateBa(cid, price);
	}

	@Override
	public double updateInt(int cid, double caintegral) {
		// TODO Auto-generated method stub
		return cd.updateInt(cid, caintegral);
	}

	@Override
	public double selectInt(int cid) {
		
		return cd.selectInt(cid);
	}

	@Override
	public boolean updateType(int cid, String catype) {
		
		return cd.updateType(cid, catype);
	}



}
