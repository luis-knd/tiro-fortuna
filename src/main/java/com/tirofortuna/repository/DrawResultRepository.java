package com.tirofortuna.repository;

import com.tirofortuna.entities.DrawResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawResultRepository extends CrudRepository<DrawResult, Long> {
}
