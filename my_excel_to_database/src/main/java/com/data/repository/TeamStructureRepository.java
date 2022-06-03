package com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import com.data.model.TeamStructure;

public interface TeamStructureRepository extends JpaRepository<TeamStructure, Long>{

	void save(MultipartFile file);

}
