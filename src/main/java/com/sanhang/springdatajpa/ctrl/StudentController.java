package com.sanhang.springdatajpa.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanhang.springdatajpa.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public String list() {
    	studentService.search();
        return "home";
    }
}