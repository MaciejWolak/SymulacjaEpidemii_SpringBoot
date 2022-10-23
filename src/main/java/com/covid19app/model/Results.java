package com.covid19app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double numberOfInfectedPeople;
    private Double numberOfHealthyPeople;
    private Double numberOfDiedPeople;
    private Double numberOfRecoveryPeople;
    private int elapsedDay;
    @JsonProperty("parameters")
    @ManyToOne
    private Parameters parameters;

}
