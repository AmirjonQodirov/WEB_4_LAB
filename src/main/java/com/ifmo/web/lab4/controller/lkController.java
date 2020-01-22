package com.ifmo.web.lab4.controller;

import com.ifmo.web.lab4.model.data.Lk;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@URL
public class lkController {

    @CrossOrigin
    @GetMapping("/lkAmir")
    String getLKAmir(){

        try {
            return new Lk().getLKAmir() ;
        } catch (Exception e) {
            return "";
        }
    }

    @CrossOrigin
    @GetMapping("/lkBeha")
    String getLKBeha(){

        try {
            return new Lk().getLKBeha() ;
        } catch (Exception e) {
            return "";
        }
    }


}
