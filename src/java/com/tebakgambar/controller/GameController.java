package com.tebakgambar.controller;

import com.tebakgambar.model.Player;
import com.tebakgambar.model.User;
import com.tebakgambar.service.RfTema;
import com.tebakgambar.model.Room;
import com.tebakgambar.service.Soal;
import com.tebakgambar.service.SoalService;
import com.tebakgambar.service.SoalService_Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Page Game
 *
 * @author Ade Fruandta
 */
@Controller
@RequestMapping(value = "/Game")
public class GameController extends DefaultController {

    private final SoalService_Service portSoal = new SoalService_Service();
    private final SoalService soalService = this.portSoal.getSoalServicePort();

    public GameController() {
        super();
    }

    /**
     * Load awal, menampilkan list Tema
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView index(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (this.setup(model, request, response)) {
                User userTicket = (User) this.getSession(request, "userTicket");
                List<RfTema> listRfTema = this.soalService.getTemaActive();

                model.addObject("listRfTema", listRfTema);
                model.addObject("userTicket", userTicket);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return model;
    }

    /**
     * Waiting room. Menunggu Player lain masuk room
     *
     * @param model
     * @param temaId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/waiting.htm", method = RequestMethod.GET)
    public ModelAndView waiting(
            ModelAndView model,
            @RequestParam(value = "temaId", defaultValue = "0") int temaId,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (this.setup(model, request, response)) {
                User userTicket = (User) this.getSession(request, "userTicket");
                RfTema rfTema = this.soalService.getTema(temaId);
                this.setSession(request, "rfTemaSession", rfTema);
                this.setSession(request, "maxPlayerSession", 2);

                model.addObject("userTicket", userTicket);
                model.addObject("rfTema", rfTema);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return model;
    }

    /**
     * Get Soal berdasarkan Tema
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/start.htm", method = RequestMethod.GET)
    public ModelAndView start(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (this.setup(model, request, response)) {
                User userTicket = (User) this.getSession(request, "userTicket");
                Room room = (Room) this.getSession(request, "roomSession");
                Player player = (Player) this.getSession(request, "playerSession");
                if (player.equals(room.getPlayers().get(0))) {
                    player.setTurn(true);
                }

                model.addObject("listSoal", room.getSoals());
                model.addObject("listPlayer", room.getPlayers());
                model.addObject("userTicket", userTicket);
                model.addObject("player", player);
                model.addObject("rfTema", room.getRfTema());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return model;
    }

    /**
     * Dapatkan gambar sesuai potongannya
     *
     * @param model
     * @param index
     * @param x
     * @param y
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getGambar.htm", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGambar(
            ModelAndView model,
            @RequestParam(value = "index") int index,
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y,
            HttpServletRequest request,
            HttpServletResponse response) {
        byte[] result = null;
        try {
            if (this.setup(model, request, response)) {
                Room room = (Room) this.getSession(request, "roomSession");
                List<Soal> listSoal = room.getSoals();
                Soal soal = listSoal.get(index);
                result = soalService.getPotonganGambar(soal.getId(), x, y);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result;
    }

    /**
     * Check Jawaban
     *
     * @param model
     * @param index
     * @param jawaban
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/checkJawaban.htm", method = RequestMethod.POST)
    @ResponseBody
    public String checkJawaban(
            ModelAndView model,
            @RequestParam(value = "index") int index,
            @RequestParam(value = "jawaban") String jawaban,
            HttpServletRequest request,
            HttpServletResponse response) {
        String result = "SALAH";
        try {
            if (this.setup(model, request, response)) {
                Room room = (Room) this.getSession(request, "roomSession");
                List<Soal> listSoal = room.getSoals();
                Soal soal = listSoal.get(index);
                Boolean resultCheckJawaban = soalService.checkJawaban(soal.getId(), jawaban);
                if (resultCheckJawaban) {
                    result = "BENAR";
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result;
    }
    
    /**
     * Profile
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/profile.htm", method = RequestMethod.GET)
    public ModelAndView profile(
            ModelAndView model,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (this.setup(model, request, response)) {
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return model;
    }


}
