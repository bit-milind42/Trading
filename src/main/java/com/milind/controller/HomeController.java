package com.milind.controller;
// import getmapping
// import restController

// @RestController
public class HomeController {
    @GetMapping
    // @getmapping
    public String home(){
        return "Welcome";
    }
}
