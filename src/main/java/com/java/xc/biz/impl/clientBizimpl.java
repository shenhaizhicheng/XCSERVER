package com.java.xc.biz.impl;

import java.util.List;

import com.java.xc.biz.clientBiz;
import com.java.xc.dao.clientDao;
import com.java.xc.dao.impl.clientDaoimpl;
import com.java.xc.domain.Client;

public class clientBizimpl implements clientBiz {
	private clientDao cd;

	public clientBizimpl() {
		super();
		this.cd = new clientDaoimpl();
	}

	@Override
	public int insertCli(Client c) {
		return this.cd.insertCli(c); 
		
		
	}

	@Override
	public Client selectByemail(String cemail) {

		return this.cd.selectByemail(cemail);
	}

	@Override
	public Client selectBycac(int caccount) {

		return this.cd.selectBycac(caccount);
	}

	@Override
	public List<Client> selectAllcli() {

		return this.cd.selectAllcli();
	}

	@Override
	public String updatePass(int caccount, String cpassword) {

		return this.cd.updatePass(caccount, cpassword) ? "修改成功" : "修改失败";
	}

}
