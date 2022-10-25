package com.covid19app.service;

import com.covid19app.model.Parameters;
import com.covid19app.model.Results;

import java.util.List;

public interface ParametersService {
    List<Parameters> getAll();
    Parameters getOneSimulationById(long id);
    Parameters createNewSimulation(Parameters parameters);
    void updateParameters(Parameters parameters);
    void deleteSimulation(long id);
    List<Results> getAllResults();
    void deleteResults(long id);
    Results getOneResultById(long id);


}
