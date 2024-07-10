package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Holiday;

import com.eazybytes.eazyschool.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidaysController {
    @Autowired
    private  HolidayRepository holidayRepository;

    @GetMapping("/holidays/{display}")
//    public String displayHolidays(@RequestParam(required = false) boolean festival,
//                                  @RequestParam(required = false) boolean federal, Model model) {
    public String displayHolidays(@PathVariable String display, Model model) {
//        model.addAttribute("festival",festival);
//        model.addAttribute("federal",federal);
        if(display!=null&&display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }
        else if(display!=null&&display.equals("festival")){
            model.addAttribute("festival",true);
        }
        else if(display!=null&&display.equals("federal")){
            model.addAttribute("federal",true);
        }
        Iterable<Holiday> holidays = holidayRepository.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        Holiday.Type[] types = Holiday.Type.values(); //creates an array where types are stored : FESTIVAL , FEDERAL
        for(Holiday.Type type : types){ //For each loop on each type of holiday in the types array
            model.addAttribute(type.toString(),holidayList.stream() //attribute is added into model object for each holiday ,
                    //holidays object is converted to stream and for each value in type array holidays from this stream is
                            //filtered and list of holidays is equal to type of holiday is added to that attribute
                    .filter(holiday -> holiday.getType().equals(type)) // filter on
                    .collect(Collectors.toList()));
        }
        return "holidays.html"; //tells thymeleaf to display this html after parsing the dynamic content to be displayed
    }
}