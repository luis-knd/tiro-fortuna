package com.tirofortuna.repository;

import com.tirofortuna.entities.Draw;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DrawRepository extends CrudRepository<Draw, Long> {

    List<Draw> findDrawByDateBetween(Date minDate, Date maxDate);

    @Query("SELECT d.drawResult.result FROM Draw d WHERE d.game.id = :gameId")
    String[] findAllDrawByGameId(Long gameId);
}
