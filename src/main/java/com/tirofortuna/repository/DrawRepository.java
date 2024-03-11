package com.tirofortuna.repository;

import com.tirofortuna.entities.Draw;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DrawRepository extends CrudRepository<Draw, Long> {

    Optional<Draw> findDrawByDateBetween(Date minDate, Date maxDate);
}
