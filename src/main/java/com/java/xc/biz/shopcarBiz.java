package com.java.xc.biz;

import java.util.List;
import java.util.Map;

import com.java.xc.domain.ShopCar;

public interface shopcarBiz {
	// 添加购物车
	public String insertShopcar(ShopCar s);

	// 移除指定数量商品
	public String deleteMenuS(int meid, int menum, int caccount);// ...

	// 按菜品编号查找相关菜信息
	public ShopCar selectBymeid(int meid, int caccount);

	// 按登录账户查找菜列表
	public List<ShopCar> selectMBycac(int caccount);

	// 把指定用户的商品信息添加到月销售单
	public boolean insertSales(ShopCar s, double meprofit);// 判断日期，菜名，同时符合时只数量增加

	// 查看本月热销菜编号
	public int selectIDSalT();

	// 查询本月销量最高的菜品
	public Map<String, Integer> salesTall();

	// 清空月销售账单
	public boolean deleteSale();

	// 下单
	public void insertOF(String s, ShopCar sc);

	// 查询所有订单号根据时间排序
	public List<String> selectOFid();

	// 根据订单号查询菜品名称及数量
	public Map<String, Integer> selectByOfid(String ofid);

	// 删除订单按订单号
	public boolean deleteByofid(String ofid);

	// 导出月销售单
	public String exportSales();
}
