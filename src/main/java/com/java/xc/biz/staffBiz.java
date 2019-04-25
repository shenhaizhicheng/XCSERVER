package com.java.xc.biz;

import com.java.xc.domain.Staff;

public interface staffBiz {
	// 添加员工（员工account为seq_saccount)返回员工号
	public int insertSta(Staff s);

	// 根据员工号查找员工
	public Staff selectBysac(int saccount);

	// 删除员工根据员工号
	public boolean deleteSta(int saccount);

	// 根据账号修改密码
	public String updatespw(int saccount, String newpw);
}
