package com.covid19app.repository;

import com.covid19app.model.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends JpaRepository<Parameters, Long> {

}
