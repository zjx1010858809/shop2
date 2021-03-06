package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.Admin;
import service.admin_service;

@Controller
public class login_controller {
    @Autowired
    admin_service service;
    @RequestMapping("admin-login")
    public String login(Admin a,HttpSession session,String code,ModelMap m){
    	    if(!((String) session.getAttribute("randomCode")).equalsIgnoreCase(code)) {
    	    	session.setAttribute("msg", "验证码错误！");
    	    	return "login";
    	    }else if(service.selectbynike(a)==null) {
    	    	session.setAttribute("msg", "用户名不存在！");
    	    	return "login";
    	    }else if(service.selectbynike(a).get(0).getStatus()==1) {
    	    	System.out.println(1111);
    	    	session.setAttribute("msg", "账户已冻结！");
    	    	return "login";
    	    }else if(service.selectbynike(a).get(0).getPassword().equals(a.getMd5())) {
    	    
    	    	session.setAttribute("user",service.selectbynike(a).get(0));
    	    	session.setMaxInactiveInterval(6000);
    	    	
    			
    			session.removeAttribute("msg");
    			m.put("list",service.selectbynike(a).get(0));
    			return "index";
    		}else {
    			System.out.println(222);
    			session.setAttribute("msg", "用户名或密码错误请重新输入！");
   			return "login";
    		}
	}
    
    @RequestMapping("out")
    public String out(HttpSession session) {
    	session.removeAttribute("nike");
		return "redirect:login.jsp";
    	
    }
    
    
}
