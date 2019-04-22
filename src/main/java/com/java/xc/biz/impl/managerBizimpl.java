package com.java.xc.biz.impl;

import com.java.xc.biz.managerBiz;
import com.java.xc.dao.managerDao;
import com.java.xc.dao.impl.managerDaoimpl;
import com.java.xc.domain.Manager;

public class managerBizimpl implements managerBiz {
	private managerDao mg;

	public managerBizimpl() {
		super();
		this.mg = new managerDaoimpl();
	}

	@Override
	public Manager selectBymac(int maccount) {

		return this.mg.selectBymac(maccount);
	}

}
