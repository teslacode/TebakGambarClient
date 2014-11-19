package com.tebakgambar.controller;

import com.tebakgambar.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Page Login
 * 
 * @author Ade Fruandta
 */
@Controller
@RequestMapping(value = "/login.htm")
public class LoginController extends DefaultController {

    public LoginController() {
        super();
    }
    
    /**
     * Load awal
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView login(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        try {
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return model;
    }
    
    /**
     * Proses Login
     *
     * @param model
     * @param userName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(
            ModelAndView model,
            @RequestParam(value = "userName") String userName,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        try {
            User userTicket = new User(userName);
            this.setSession(request, "userTicket", userTicket);
            model.setViewName("redirect:/index.htm");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return model;
    }
    
}
