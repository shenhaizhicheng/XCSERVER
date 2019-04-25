package com.java.xc.biz.impl;

import com.java.xc.biz.staffBiz;
import com.java.xc.dao.staffDao;
import com.java.xc.dao.impl.staffDaoimpl;
import com.java.xc.domain.Staff;

public class staffBizimpl implements staffBiz {
	private staffDao sd;

	public staffBizimpl() {
		super();
		this.sd = new staffDaoimpl();
	}

	@Override
	public int insertSta(Staff s) {
		int i = this.sd.insertSta(s);
		return i;
	}

	@Override
	public Staff selectBysac(int saccount) {

		return this.sd.selectBysac(saccount);

	}

	@Override
	public boolean deleteSta(int saccount) {
		
		return this.sd.deleteSta(saccount);
	}

	@Override
	public String updatespw(int saccount, String newpw) {
		
		return sd.updatespw(saccount, newpw)?"修改成功":"修改失败";
	}

}
