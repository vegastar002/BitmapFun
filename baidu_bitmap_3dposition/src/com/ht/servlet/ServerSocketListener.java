package com.ht.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.ws.Response;

public class ServerSocketListener implements ServletContextListener {

	private SocketThread socketThread;
	public void contextDestroyed(ServletContextEvent sce) {
		if ( socketThread != null  &&  !socketThread.isInterrupted()){
			socketThread.closeServerSocket();
			socketThread.interrupt();
		}
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		if ( socketThread == null ){
			socketThread = new SocketThread(null, servletContext);
			socketThread.start();
		}
		
	}

	class SocketThread extends Thread{
		private ServletContext servletContext;
		private ServerSocket serverSocket;
		
		public SocketThread(ServerSocket serverSocket, ServletContext servletContext){
			this.servletContext = servletContext;
			String port = this.servletContext.getInitParameter("socketPort");
			if ( serverSocket == null ){
				try {
					this.serverSocket = new ServerSocket(Integer.parseInt(port));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		public void run() {
			while ( !this.isInterrupted() ) {
				try {
					Socket socket = serverSocket.accept();
					if (socket != null){
						new ProcessSocketData(socket, this.servletContext).start();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
		
		public void closeServerSocket(){
			try {
				if ( serverSocket != null && !serverSocket.isClosed() ){
					serverSocket.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	class ProcessSocketData extends Thread{
		private Socket socket;
		private ServletContext servletContext;
		public ProcessSocketData(){
			
		}
		
		public ProcessSocketData(Socket socket, ServletContext servletContext){
			this.socket = socket;
			this.servletContext = servletContext;
		}
		
		public void run(){
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				String request = br.readLine();
				pw.print(request);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}
}
