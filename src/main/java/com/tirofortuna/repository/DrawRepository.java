package com.tirofortuna.repository;

import com.tirofortuna.entities.Draw;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DrawRepository extends CrudRepository<Draw, Long> {

    List<Draw> findDrawByDateBetween(Date minDate, Date maxDate);
}
