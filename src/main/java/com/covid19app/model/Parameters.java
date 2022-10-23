package com.covid19app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Parameters {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String simulationName;
private double populationSize;
private double initialNumberOfInfectedPeople;
private double infectionRate;
private double deathRate;
private int timeToRecovery;
private int timeToDeath;
private int timeOfSimulation;
@JsonIgnore
@OneToMany(mappedBy = "parameters", cascade = CascadeType.MERGE)
private List<Results> resultList;
}
