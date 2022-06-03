package com.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.data.helper.ExcelHelper;
import com.data.message.ResponseMessage;
import com.data.model.TeamStructure;
import com.data.model.UserPhotos;
import com.data.service.ExcelService;
import com.data.service.PhotoService;

@Controller
public class ExcelController {
	@Autowired
	ExcelService excelService;
	@Autowired
	PhotoService photoService;
	
	@GetMapping("/")
	public String displayTeam(ModelMap model) {
		List<TeamStructure> teamList = excelService.getAllTeam();
		model.addAttribute("teamList",teamList);
		List<UserPhotos> userPhotoList = photoService.getAllPhoto();
		model.addAttribute("userPhotoList",userPhotoList);
		return "showPage";
	}
	
	@GetMapping("/add")
	public ModelAndView addTeam() {
		return new ModelAndView("teamStructure","team",new TeamStructure());
	}
	  @PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    if (ExcelHelper.hasExcelFormat(file)) {
	      try {
	        excelService.save(file);
	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }
	    message = "Please upload an excel file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }
	  @GetMapping("/teamList")
	  public ResponseEntity<List<TeamStructure>> getAllTutorials() {
	    try {
	      List<TeamStructure> teamStructureList = excelService.getAllTeam();
	      if (teamStructureList.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(teamStructureList, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

}
