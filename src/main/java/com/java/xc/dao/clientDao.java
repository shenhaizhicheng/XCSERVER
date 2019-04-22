package com.java.xc.dao;

import java.util.List;

import com.java.xc.domain.Client;

public interface clientDao {
    //添加客户信息(告知客户账号) 
	public int insertCli(Client c);
	
	//根据邮箱查找客户信息
	public Client selectByemail(String cemail);
	
	//根据cc号(账户名)查找客户信息
	public Client selectBycac(int caccount);
	
	//查找所有客户信息
	public List<Client> selectAllcli();
	
	//修改密码
	public boolean updatePass(int caccount,String cpassword);
}
