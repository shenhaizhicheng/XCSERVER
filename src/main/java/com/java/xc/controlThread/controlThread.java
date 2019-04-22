package com.java.xc.controlThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class controlThread implements Runnable{
	private Socket client;
	Object o;

	public controlThread(Socket client,Object o) {
		this.client = client;
		this.o=o;

	}

	@Override
	public void run() {
		ObjectInputStream ois=null;
		try {
			ois=new ObjectInputStream(client.getInputStream());
			String name = ois.readUTF();
			
			Class<?>[] parameterTypes =(Class<?>[]) ois.readObject();
			
			Object[] args =(Object[]) ois.readObject();
			
			Method method = o.getClass().getMethod(name, parameterTypes);
			Object restult = method.invoke(o, args);
			
			ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(restult);
			oos.flush();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
