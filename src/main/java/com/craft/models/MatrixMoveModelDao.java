package com.craft.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MatrixMoveModelDao extends CrudRepository<MatrixMoveModel, Long> {
	public Iterable<MatrixMoveModel> findByCraftUserGameModel(CraftUserGameModel craftUserGame);

//	@Query("SELECT count(*) FROM MatrixMoveModel e group by e.m_row having count(*)=3")
//	public long findRowCount();
}
