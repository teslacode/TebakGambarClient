package com.tebakgambar.controller;

import com.tebakgambar.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

/**
 * Parent Semua Controller
 *
 * @author Ade Fruandta
 */
@Controller
public class DefaultController {

    public DefaultController() {
    }

    /**
     * Setup awal load all controller
     *
     * @param model
     * @param request
     * @param response
     * @return True / False
     */
    public Boolean setup(
            ModelAndView model, 
            HttpServletRequest request, 
            HttpServletResponse response) {
        Boolean result = true;
        User userTicket = (User) this.getSession(request, "userTicket");
        if (userTicket == null || userTicket.getUserName().equalsIgnoreCase("")) {
            model.setViewName("redirect:/login.htm");
            result = false;
        }
        return result;
    }

    /**
     * Set Session
     *
     * @param request
     * @param key
     * @param value
     */
    public void setSession(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * Get Session
     *
     * @param request
     * @param key
     * @return Object
     */
    public Object getSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    /**
     * Get GetObjectMap
     *
     * @param model
     * @param key
     * @return
     */
    public Object getObjectMap(ModelAndView model, String key) {
        ModelMap map = model.getModelMap();
        return map.get(key);
    }

}
