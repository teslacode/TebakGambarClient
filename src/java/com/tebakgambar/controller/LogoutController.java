package com.tebakgambar.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Page Logout
 *
 * @author Ade Fruandta
 */
@Controller
@RequestMapping(value = "/logout.htm")
public class LogoutController extends DefaultController {

    public LogoutController() {
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
    public ModelAndView logout(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            model.setViewName("redirect:/index.htm");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return model;
    }

}
