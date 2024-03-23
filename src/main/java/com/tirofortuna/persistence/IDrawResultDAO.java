package com.tirofortuna.persistence;

import com.tirofortuna.entities.DrawResult;

import java.util.List;
import java.util.Optional;

public interface IDrawResultDAO {

    List<DrawResult> findAll();

    Optional<DrawResult> findById(Long id);

    void save(DrawResult drawResult);

    void deleteById(Long id);
}
