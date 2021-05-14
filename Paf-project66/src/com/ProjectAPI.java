package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProjectsAPI") 
public class ProjectAPI  extends HttpServlet {
	
	Project projectObj = new Project();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 String output = projectObj.insertProject(request.getParameter("projCode"),
			request.getParameter("projName"),
			request.getParameter("projPrice"),
			request.getParameter("projDesc"));
			response.getWriter().write(output);
			}
	
	
	
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request)
	{
	 Map<String, String> map = new HashMap<String, String>();
	try
	 {
	 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
	 String queryString = scanner.hasNext() ?
	 scanner.useDelimiter("\\A").next() : "";
	 scanner.close();
	 String[] params = queryString.split("&");
	 for (String param : params)
	 { 
		 String[] p = param.split("=");
		 map.put(p[0], p[1]);
		 }
		 }
		catch (Exception e)
		 {
		 }
		return map;
		}
	
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 Map paras = getParasMap(request);
			 String output = projectObj.updateProject(paras.get("hidProjectIDSave").toString(),
			paras.get("projCode").toString(),
			paras.get("projName").toString(),
			paras.get("projPrice").toString(),
			paras.get("projDesc").toString());
			response.getWriter().write(output);
			} 
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 Map paras = getParasMap(request);
			 String output = projectObj.deleteProject(paras.get("projID").toString());
			response.getWriter().write(output);
			}
}
