package com.data.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.data.model.UserPhotos;

public interface PhotoRepository extends CrudRepository<UserPhotos, Integer>{

	void deleteById(Integer id);

}
