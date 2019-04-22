package com.java.xc.dao;

import java.util.List;
import java.util.Map;

import com.java.xc.domain.Menu;

public interface menuDao {
	//添加菜品
	public boolean insertMenu(Menu m);
	
	//根据编号移除菜品
	public boolean deleteMenu(int meid);
	
	//根据编号修改折扣
	public boolean updateMenu(int meid,double mediscount);
	
	//查找所有菜品
	public List<Menu> selectAllMe();
	
	//根据编号查找菜信息
	public Menu selectBymeid(int meid);
	
	//查找菜品类型
	public Map<Integer,String> selectAlltype();
	

}
