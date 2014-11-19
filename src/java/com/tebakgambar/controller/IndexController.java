package com.tebakgambar.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Page Index
 *
 * @author Ade Fruandta
 */
@Controller
@RequestMapping(value = "/index.htm")
public class IndexController extends DefaultController {

    public IndexController() {
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
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView post(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if(this.setup(model, request, response)){
                model.setViewName("redirect:/Game/index.htm");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return model;
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
    public ModelAndView get(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        this.setup(model, request, response);
        return model;
    }

}
