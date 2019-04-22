package com.java.xc.dao;

import com.java.xc.domain.Card;

public interface cardDao {
	//注册成功默认开卡
	public int opencard(int caccount);
	
	//充值
	public double cardmoney(int cid,double money);
	
	//挂失（使卡片的状态由0变成1)
	public boolean updateState(int cid);
	
	//获取卡状态
	public String selectState(int cid);
	
	//根据卡号查找卡
	public Card selectCBycid(int cid);
	
	//补卡（用户自己设置金额,返回新卡号）
	public int buildcard(Card card,int caccount);
	
	//删除卡
	public void delectcard(int caccount);
	
	//通过客户账号查询卡号
	public int selectBycaccount(int caccount);
	
	//根据客户账号查询所属卡的折扣
	public double selectDis(int caccount);
	
	//修改卡上余额
	public double updateBa(int cid,double price);

	//解除卡挂失
	public boolean relieveC(int cid);


}
