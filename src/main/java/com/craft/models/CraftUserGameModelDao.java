package com.craft.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CraftUserGameModelDao extends CrudRepository<CraftUserGameModel, Long>{
	public CraftUserGameModel findById(long id);
}
