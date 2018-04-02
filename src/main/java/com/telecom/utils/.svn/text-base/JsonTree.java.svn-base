package com.telecom.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTree {

	/**
	 * 遍历  ---> 得到JSONArray
	 * @param menuList
	 * @param parentId
	 * @return
	 */
	public static JSONArray treeMenuList(JSONArray menuList, String parentId) {  
	      JSONArray childMenu = new JSONArray();  
	      for (Object object : menuList) {  
	          JSONObject jsonMenu = JSONObject.fromObject(object);  
	          String menuId = jsonMenu.getString("id");  
	          String pid = jsonMenu.getString("parentId");  
	          if (parentId.equals(pid)) {  
	              JSONArray c_node = treeMenuList(menuList, menuId);  
	              jsonMenu.put("children", c_node);  
	              childMenu.add(jsonMenu);  
	          }  
	      }
	      return childMenu;  
	  }  
}
