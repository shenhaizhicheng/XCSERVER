package com.java.xc.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.java.xc.biz.totalBiz;
import com.java.xc.biz.impl.totalBizimpl;
import com.java.xc.controlThread.controlThread;


public class control {
	private ServerSocket server;
	public static final int PROT = 99;
    private ExecutorService es;
    totalBiz ttb; 
	public control() {
		super();

		try {
			System.out.println("服务器正在开启...");
			server = new ServerSocket(PROT);
			System.out.println("服务器已开启");
			ttb=new totalBizimpl();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// client=server.accept();
            es=Executors.newCachedThreadPool();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void start() {
		while (true) {
			try {
				Socket client = server.accept();

				System.out.println(client.getInetAddress().getHostAddress() + "已连接");

				controlThread stu = new controlThread(client,ttb);
				es.submit(stu);
				int num = ((ThreadPoolExecutor)es).getActiveCount();
				System.out.println("活跃人数为："+num);
//				Thread t=new Thread(stu);
//				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
