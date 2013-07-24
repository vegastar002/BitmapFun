package com.ht.servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class java2xml {

	private List<String> fruit = new ArrayList<String>();
	
	public void initList(){  
        fruit.add("ToUserName");  
        fruit.add("FromUserName");  
        fruit.add("CreateTime");  
        fruit.add("MsgType");  
        fruit.add("Content");
        fruit.add("FuncFlag");
    }
	
	
	public void BuildXMLDoc() throws IOException, JDOMException {
		initList();
		
		// 创建根节点 list;
		Element root = new Element("xml");
		Document Doc = new Document(root);
		// 此处 for 循环可替换成 遍历 数据库表的结果集操作;
		for (int i = 0; i < fruit.size(); i++) {
			// 创建节点 user;
			Element elements = new Element(fruit.get(i));
			// 给 user 节点添加属性 id;
//			elements.setAttribute("id", "" + i);
			// 给 user 节点添加子节点并赋值；
			// new Element("name")中的 "name" 替换成表中相应字段，setText("xuehui")中 "xuehui
			// 替换成表中记录值；
			elements.addContent("content");
			// 给父节点list添加user子节点;
			root.addContent(elements);
		}
		XMLOutputter XMLOut = new XMLOutputter();
		
		
		Format format = Format.getPrettyFormat();     
        format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题     
        XMLOutputter xmlout = new XMLOutputter(format);     
        ByteArrayOutputStream bo = new ByteArrayOutputStream();     
        xmlout.output(Doc, bo);     
        int pos = bo.toString().indexOf("?>");
        
        String data = bo.toString().substring(pos+2);
        data = data.trim();
//		data = data.replace("\n","");
		data = data.replace("\r","");
		
		
//        System.out.print(data);
        
        
        
		// 输出 user.xml 文件；
//		 XMLOut.output(Doc, new FileOutputStream("user.xml"));
	}

}
