package com.java.xc.biz.impl;

import java.util.List;
import java.util.Map;

import com.java.xc.biz.menuBiz;
import com.java.xc.dao.menuDao;
import com.java.xc.dao.impl.menuDaoimpl;
import com.java.xc.domain.Menu;

public class menuBizimpl implements menuBiz {
	private menuDao me;

	public menuBizimpl() {
		super();
		this.me = new menuDaoimpl();
	}

	@Override
	public String insertMenu(Menu m) {
		boolean b = this.me.insertMenu(m);
		return b ? "添加成功" : "添加失败";
	}

	@Override
	public String deleteMenu(int meid) {
		boolean b = this.me.deleteMenu(meid);
		return b ? "删除成功" : "删除失败";
	}

	@Override
	public String updateMenu(int meid, double mediscount) {
		boolean b = this.me.updateMenu(meid, mediscount);
		return b ? "修改成功" : "修改失败";
	}

	@Override
	public List<Menu> selectAllMe() {
		List<Menu> list = this.me.selectAllMe();
		return list;
	}

	@Override
	public Menu selectBymeid(int meid) {
		Menu m = this.me.selectBymeid(meid);
		return m;
	}

	@Override
	public Map<Integer, String> selectAlltype() {
		
		return this.me.selectAlltype();
	}

}
