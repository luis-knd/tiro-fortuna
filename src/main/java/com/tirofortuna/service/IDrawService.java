package com.tirofortuna.service;

import com.tirofortuna.entities.Draw;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IDrawService {

    List<Draw> findAll();

    void save(Draw draw);

    void deleteById(Long id);

    Optional<Draw> findDrawByDateInRange(Date minDate, Date maxDate);
}