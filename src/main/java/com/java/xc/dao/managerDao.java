 package com.java.xc.dao;

import com.java.xc.domain.Manager;

public interface managerDao {
	//根据cc号(账户名)查找经理信息
	public  Manager selectBymac(int maccount);
	

}
