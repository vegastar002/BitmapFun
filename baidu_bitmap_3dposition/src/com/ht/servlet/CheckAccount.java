package com.ht.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;



public class CheckAccount extends HttpServlet {

	Connection conn;
	final static int BUFFER_SIZE = 4096;
	
	ServletOutputStream sout;
	PrintWriter out;
	private List<String> weixin_trans = new ArrayList<String>();
	
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("gbk");
        response.setContentType("text/html;charset=gbk");
        
//		HttpSession session = request.getSession();
//		AcountBean account = new AcountBean();username
//		String username = request.getParameter("username");
//		System.out.println("username is : " + username);
		
        /*微信开发者验证代码
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String echostr = request.getParameter("echostr");
//            //在这里我只是接收了一下微信服务器返回的参数,没有按API里加密解析等操作,直接返回了echostr参数内容;
//        response.getWriter().write(echostr);
*/        
        
        //接受微信的信息
//        String ToUserName = request.getParameter("ToUserName");
//        String FromUserName = request.getParameter("FromUserName");
//        String CreateTime = request.getParameter("CreateTime");
//        String MsgType = request.getParameter("MsgType");
//        String Content = request.getParameter("Content");
//        String MsgId = request.getParameter("MsgId");
//        
//        String postStr = null;
//		try{
//			postStr = readStreamParameter(request.getInputStream());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		
		
        
        ///不是微信
        String address = request.getParameter("ad");
		String security = request.getParameter("se");
		String secondKey = request.getParameter("sec");
		String flashItem = request.getParameter("flash");
		String category = request.getParameter("category");
//		String str = new String(voteString.getBytes("ISO-8859-1"),"GBK");
//		System.out.println("voteString2 is : " + changeToChineseWord(voteString));
		
//		if ( "item1".equals(flashItem) ){
//			sout = response.getOutputStream();
//		}else {
//			out = response.getWriter();
//		}
		
		if ( flashItem == null ){
			out = response.getWriter();
		}else {
			sout = response.getOutputStream();
		}
		
//		if ( category!= null && "weixin".equals(category) ){
//			out.print(request.getServerName() +" - "+ request.getContextPath());
//			out.flush();
//		}
		
		/*if ( ToUserName != null) {
	        out.print(ToUserName + " - " + FromUserName+" - "+ CreateTime+" - "+
	        		MsgType+" - "+Content+" - "+MsgId);
			out.flush();
			
			
//        	weixin_trans.add(ToUserName);
        	
//        	try {
//    			java2xml j2x = new java2xml();
//    			j2x.BuildXMLDoc();
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		}
    		
        }*/
		
		
		
		String strT1 = "ok";//changeToUnicode("中文返回ぬすせ测试");


//		String agent = request.getHeader("user-agent");
//		if ( !"typefornone".equals(agent) ){
////			out.println("fuck U!");
//			String logon_fail = "fail.jsp";
//			response.sendRedirect(logon_fail);
//			return;
//		}
		

		
		String dbUrl = "jdbc:mysql://sqld.duapp.com:4050/GylItvSmEOzWRZSbFwpp";
		String username2 = request.getHeader("BAE_ENV_AK");
		String password = request.getHeader("BAE_ENV_SK");
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String resultString = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dbUrl, username2,password);
			stmt = connection.createStatement();
			
			
//			URL url = new URL("http://oss.aliyuncs.com/bitmapfun/test1234.gif");
//			URLConnection conn = url.openConnection();
//			InputStream inStream = conn.getInputStream();
//			PreparedStatement pspt = connection.prepareStatement(
//					"insert into tmp(filename,filedata) values(?,?)");
//			pspt.setString(1,"test1234.gif");
//			pspt.setBinaryStream(2,inStream,inStream.available());
//			pspt.executeUpdate();
			
			
			
			if ( "se".equals(security) ){
				rs = stmt.executeQuery("SELECT `signature`, `appSize` FROM `vote`");
				
				while (rs.next()) {
					resultString = rs.getString("signature") + "," + rs.getString("appSize");
				}
				out.print(resultString);
				out.flush();
				
			}else if ( "sk".equals(address) ) {
				rs = stmt.executeQuery("SELECT `xml_address` FROM `vote`");
				
				while (rs.next()) {
					resultString = rs.getString("xml_address");
				}
				out.print(resultString);
				out.flush();
				
			}else if ( "cgb".equals(secondKey) ) {
				rs = stmt.executeQuery("SELECT `secondKey` FROM `vote`");
				
				while (rs.next()) {
					resultString = rs.getString("secondKey");
				}
				out.print(resultString);
				out.flush();
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			out.print(e.getMessage());
		}
		
		
		
		
		if ( flashItem != null ){
			try {
				ResultSet rss = stmt
						.executeQuery("SELECT * FROM `position` WHERE 1");
				
				int total = 0; String makess1 = "";
				byte[] bzp;
				
				while (rss.next()) {
					makess1 = rss.getString("filename");
					if ( makess1.equals(flashItem) ){
						Blob blob = rss.getBlob("filedata");
						InputStream in = blob.getBinaryStream();
						int size = in.available();
						bzp = new byte[size];
						in.read(bzp);
						sout.write(bzp);
						break;
					}
					
					
//					int b = -1;
//					byte[] data = new byte[BUFFER_SIZE];
//					while((b = in.read(data,0,BUFFER_SIZE)) != -1){
//			        	total += b;
//			        }
						
				}
				
				
				sout.flush();
				
				stmt.close();
				connection.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.print(e.getMessage());
			}
			
		}

//		String logon_fail = "fail.jsp";
//		response.sendRedirect(logon_fail);
		return;

	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public String readStreamParameter(ServletInputStream in){
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new InputStreamReader(in));
			String line=null;
			while((line = reader.readLine())!=null){
				buffer.append(line);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=reader){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
	
	
}
