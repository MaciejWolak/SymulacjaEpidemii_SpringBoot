package com.covid19app.controller;

import com.covid19app.model.Parameters;
import com.covid19app.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController()
@RequestMapping("/")
public class ParametersController {
    @Autowired
    private ParametersService parametersService;


@GetMapping("/simulations")
    public ResponseEntity<List<Parameters>> getAllSimulations(){
        List<Parameters> parameters = parametersService.getAll();
        if (CollectionUtils.isEmpty(parameters)) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(parameters, HttpStatus.OK);
    }


@GetMapping("simulations/{id}")
public ResponseEntity<Parameters> getSimulation(@PathVariable long id){
    Parameters parameters = parametersService.getOneSimulationById(id);
    return new ResponseEntity<>(parameters, HttpStatus.OK);
}


    @PostMapping("simulations")
    public ResponseEntity<Parameters> createNewSimulation(@RequestBody Parameters parameters){
    Parameters newParameters = parametersService.createNewSimulation(parameters);
    return  new ResponseEntity<>(newParameters, HttpStatus.CREATED);
    }

    @PutMapping("simulations/{id}")
    public ResponseEntity<Parameters> updateSimulation(@PathVariable long id, @RequestBody Parameters simulationParameters){
    Parameters newSimulationParameters = parametersService.getOneSimulationById(id);



    newSimulationParameters.setSimulationName(simulationParameters.getSimulationName());
    newSimulationParameters.setPopulationSize(simulationParameters.getPopulationSize());
    newSimulationParameters.setInitialNumberOfInfectedPeople(simulationParameters.getInitialNumberOfInfectedPeople());
    newSimulationParameters.setDeathRate(simulationParameters.getDeathRate());
    newSimulationParameters.setInfectionRate(simulationParameters.getInfectionRate());
    newSimulationParameters.setTimeToRecovery(simulationParameters.getTimeToRecovery());
    newSimulationParameters.setTimeToDeath(simulationParameters.getTimeToDeath());
    newSimulationParameters.setTimeOfSimulation(simulationParameters.getTimeOfSimulation());
    parametersService.deleteResults(simulationParameters.getId());
    parametersService.updateParameters(newSimulationParameters);
    return new ResponseEntity<>(simulationParameters, HttpStatus.OK);
    }

    @DeleteMapping("simulations/{id}")
    public ResponseEntity<Parameters> deleteSimulation(@PathVariable long id){
    parametersService.deleteSimulation(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
