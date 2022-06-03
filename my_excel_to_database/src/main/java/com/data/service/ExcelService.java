package com.data.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.data.helper.ExcelHelper;
import com.data.model.TeamStructure;
import com.data.repository.TeamStructureRepository;

@Service
public class ExcelService {
	@Autowired
	private TeamStructureRepository teamRepo;
	
	public void save(MultipartFile file) {
	    try {
	      List<TeamStructure> teamList = ExcelHelper.excelToTutorials(file.getInputStream());
	      teamRepo.saveAll(teamList);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	  }
	  public List<TeamStructure> getAllTeam() {
	    return teamRepo.findAll();
	  }
	
}
