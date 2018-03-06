package com.tericadonnelly.donationstation.controllers;


import com.tericadonnelly.donationstation.models.Charity;
import com.tericadonnelly.donationstation.models.Donor;
import com.tericadonnelly.donationstation.models.data.CharityDao;
import com.tericadonnelly.donationstation.models.data.DonorDao;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class CharityController {

    @Autowired
    private CharityDao charityDao;

    @Autowired
    private DonorDao donorDao;

    //ToDo: add phone number to charity, password hash, cookie session

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute Charity charity) {
        model.addAttribute(new Charity());
        model.addAttribute("title", "New Charity");

        return "charity/add";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Charity charity,
                      Errors errors, String verify){

        if(errors.hasErrors()){
            model.addAttribute(charity);
            model.addAttribute("title", "New Charity");
            return "charity/add";
        }
        if(charity.getPassword().equals(verify)){
            charityDao.save(charity);
            return "charity/index";
        }
        else{
            model.addAttribute(charity);
            model.addAttribute("title", "New Charity");
            model.addAttribute("problem", "Password and Verify Password did not match.");
            return "charity/add";
        }
    }

    @RequestMapping(value= "login", method = RequestMethod.GET)
    public String login(Model model, @ModelAttribute Charity charity){
        model.addAttribute("title", "Charity Login");

        return "charity/login";
    }

    @RequestMapping(value= "login", method = RequestMethod.POST)
    public String checkLogin(Model model, @ModelAttribute Charity charity){

        List<Charity> charityUser = charityDao.findByUsername(charity.getUsername());
        if(charityUser.get(0).getPassword().equals(charity.getPassword())){
            List<Donor> donors = charityUser.get(0).getDonors();
            new ArrayList<Donor> (donors);
            model.addAttribute("charityName", charityUser.get(0).getName());
            model.addAttribute("donors", donors);
            return "charity/index";
        }
        else{
            model.addAttribute(charity);
            model.addAttribute("title", "Charity Login");
            model.addAttribute("problem", "Username and Password did not match.");
            return "charity/login";
        }
    }
}
