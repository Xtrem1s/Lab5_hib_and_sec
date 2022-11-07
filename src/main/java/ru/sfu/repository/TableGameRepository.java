package ru.sfu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.model.TableGame;

@Repository
public interface TableGameRepository extends CrudRepository<TableGame, Integer> {
    Iterable<TableGame> findAllByPriceIsLessThanEqual(int value);

}

