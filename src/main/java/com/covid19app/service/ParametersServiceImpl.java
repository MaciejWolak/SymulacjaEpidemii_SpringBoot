package com.covid19app.service;

import com.covid19app.model.Parameters;
import com.covid19app.model.Results;
import com.covid19app.repository.ParametersRepository;
import com.covid19app.repository.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParametersServiceImpl implements ParametersService{

    @Autowired
    ParametersRepository parametersRepository;

    @Autowired
    ResultsRepository resultRepository;

    @Override
    public List<Parameters> getAll() {
        return parametersRepository.findAll();
    }

    @Override
    public Parameters getOneSimulationById(long id) {
        return parametersRepository.findById(id).orElseThrow(()-> new NotFoundException("Simulation not found"));
    }


    Map<Integer, Double> waitToDeath = new HashMap<>();
    Map<Integer, Double> waitToRecovery = new HashMap<>();

    @Override
    public Parameters createNewSimulation(Parameters parameters) {
       parameters.setId(null);
        parametersRepository.save(parameters);
        double oldVarOfInfectedPeople = parameters.getInitialNumberOfInfectedPeople();
        waitToDeath.put(parameters.getTimeToDeath(), oldVarOfInfectedPeople *parameters.getDeathRate());
        double varNumberOfHealthyPeople = parameters.getPopulationSize() - oldVarOfInfectedPeople;
        double varNumberOfDiedPeople = 0;
        double varNumberOfRecoveryPeople = 0;
        double varNumberOfInfectedPeople = parameters.getInitialNumberOfInfectedPeople();
        waitToDeath.put(parameters.getTimeToDeath(), oldVarOfInfectedPeople *parameters.getDeathRate());
        waitToRecovery.put(parameters.getTimeToRecovery(), oldVarOfInfectedPeople - oldVarOfInfectedPeople *parameters.getDeathRate());
        for (int i = 0; i < parameters.getTimeOfSimulation()+1; i++) {
            Results newResult = new Results();
            newResult.setElapsedDay(i);
            if(i==0){
                newResult.setNumberOfHealthyPeople(parameters.getPopulationSize()-parameters.getInitialNumberOfInfectedPeople());
                newResult.setNumberOfInfectedPeople(parameters.getInitialNumberOfInfectedPeople());
                newResult.setNumberOfDiedPeople(newResult.getNumberOfDiedPeople());
                newResult.setNumberOfRecoveryPeople(newResult.getNumberOfRecoveryPeople());
            }else {
                double nevVarOfInfectedPeople = oldVarOfInfectedPeople * parameters.getInfectionRate();
                oldVarOfInfectedPeople = nevVarOfInfectedPeople;
                varNumberOfInfectedPeople = varNumberOfInfectedPeople + nevVarOfInfectedPeople;
                varNumberOfHealthyPeople = varNumberOfHealthyPeople - nevVarOfInfectedPeople;
                newResult.setNumberOfInfectedPeople(varNumberOfInfectedPeople);
                newResult.setNumberOfHealthyPeople(varNumberOfHealthyPeople);
                waitToDeath.put(i + parameters.getTimeToDeath(), nevVarOfInfectedPeople * parameters.getDeathRate());
                waitToRecovery.put(i + parameters.getTimeToRecovery(), nevVarOfInfectedPeople - (nevVarOfInfectedPeople * parameters.getDeathRate()));
                if (waitToDeath.get(i) != null) {
                    varNumberOfDiedPeople = varNumberOfDiedPeople + waitToDeath.get(i);
                    newResult.setNumberOfDiedPeople(varNumberOfDiedPeople);
                    varNumberOfInfectedPeople = varNumberOfInfectedPeople - waitToDeath.get(i);
                    newResult.setNumberOfInfectedPeople(varNumberOfInfectedPeople);
                }
                if (waitToRecovery.get(i) != null) {
                    varNumberOfRecoveryPeople = varNumberOfRecoveryPeople + waitToRecovery.get(i);
                    newResult.setNumberOfRecoveryPeople(varNumberOfRecoveryPeople);
                    varNumberOfInfectedPeople = varNumberOfInfectedPeople - waitToRecovery.get(i);
                    newResult.setNumberOfInfectedPeople(varNumberOfInfectedPeople);
                }
            }


            newResult.setParameters(parameters);
           resultRepository.save(newResult);
        }

        return parametersRepository.save(parameters);
    }


    @Override
    public void updateParameters(Parameters parameters) {
        parameters.setId(parameters.getId());
        parametersRepository.save(parameters);
    }

    @Override
    public void deleteSimulation(long id) {
        parametersRepository.deleteById(id);
    }

    @Override
    public List<Results> getAllResults() {
        return resultRepository.findAll();
    }

}
