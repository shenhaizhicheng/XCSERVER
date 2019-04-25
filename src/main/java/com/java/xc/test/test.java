package com.java.xc.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.java.xc.dao.shopcarDao;
import com.java.xc.dao.impl.shopcarDaoimpl;

public class test {
public static void main(String[] args) {
	shopcarDao sc=new shopcarDaoimpl();
	List<String> list = sc.selectOFid();
	for (String s : list) {
		System.out.println(s);
	}
	
//	Date d=new Date();
//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	String date = sdf.format(d);
//	System.out.println(date);
}
}
