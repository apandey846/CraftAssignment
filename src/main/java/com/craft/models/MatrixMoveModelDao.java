package com.craft.models;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MatrixMoveModelDao extends CrudRepository<MatrixMoveModel, Long>{
	public Iterable<MatrixMoveModel> findByCraftUserGameModel(CraftUserGameModel craftUserGame);
}
