package com.covid19app.controller;

import com.covid19app.model.Results;
import com.covid19app.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController()
@RequestMapping("/")
public class ResultsController {

    @Autowired
    ParametersService parametersService;

    @GetMapping("/results")
    public ResponseEntity<List<Results>> getAllSimulations(){
        List<Results> results = parametersService.getAllResults();
        if (CollectionUtils.isEmpty(results)) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


}
