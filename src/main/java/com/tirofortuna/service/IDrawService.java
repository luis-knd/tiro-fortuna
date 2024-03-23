package com.tirofortuna.service;

import com.tirofortuna.entities.Draw;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IDrawService {

    List<Draw> findAll();

    Optional<Draw> findById(Long id);

    void save(Draw draw);

    void deleteById(Long id);

    List<Draw> findDrawByDateInRange(Date minDate, Date maxDate);
}
