package com.java.xc.dao;

import java.util.List;
import java.util.Map;

import com.java.xc.domain.ShopCar;

public interface shopcarDao {
	//添加购物车（不存在）
	public boolean insertShopcar(ShopCar s);
	
	//添加购物车（存在）
	public boolean insertShopcarANY(ShopCar s);
	
	//移除指定数量商品（数量等于购物车数量）
	public boolean deleteMenuS(int meid,int caccount);//...
	
	//移除指定数量商品（数量小于购物车数量）
	public boolean deleteMenuANY(int meid, int menum,int caccount);//...
	
	//按菜品编号查找相关菜信息
	public ShopCar selectBymeid(int meid,int caccount);
	
	//按登录账户查找菜列表
	public List<ShopCar> selectBycac(int cacouunt);
	
	//把指定用户的商品信息添加到月销售单(表中没有今日当前菜品的信息)
	public boolean insertSales(ShopCar s,double meprofit);
	
	//把指定用户的商品信息添加到月销售单(表中有今日当前菜品的信息)
	public boolean insertSalesANY(ShopCar s,double meprofit);
	
	//根据菜品号和日期查询菜品数量
	public int sBymeidAndCac(int meid,int caccount);
	
	//查询本月销量排行
	public Map<String,Integer> salesTall();
	
	//查看本月热销菜编号
	public int selectIDSalT();
	
	//清空月销售账单
	public boolean deleteSale();

	//下单
	public void insertOF(String s, ShopCar sc);
	
	//查询所有订单号根据时间排序
	public List<String> selectOFid();
	
	//根据订单号查询菜品名称及数量
	public Map<String,Integer> selectByOfid(String ofid);
	
	//删除订单按订单号
	public boolean deleteByofid(String ofid);
	
	//根据订单号查询下单时间
	public String findtime(String ofid);

	//导出月销售单
	public String exportSales();
	

}
