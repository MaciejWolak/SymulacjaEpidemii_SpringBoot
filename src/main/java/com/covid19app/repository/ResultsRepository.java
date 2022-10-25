package com.covid19app.repository;

import com.covid19app.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Long> {
    Results getResultsByParametersId(long id);
    void deleteResultsByParametersId(long id);
}
