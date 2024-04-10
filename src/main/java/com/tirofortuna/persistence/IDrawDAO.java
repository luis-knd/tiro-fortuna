package com.tirofortuna.persistence;

import com.tirofortuna.entities.Draw;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IDrawDAO {

    List<Draw> findAll();

    Optional<Draw> findById(Long id);

    void save(Draw draw);

    void deleteById(Long id);

    List<Draw> findDrawByDateInRange(Date minDate, Date maxDate);

    Map<Integer, Integer> findAbsoluteFrequencyByGame(Long gameId);
    Map<Integer, Double> findRelativeFrequencyByGame(Long gameId);
}
