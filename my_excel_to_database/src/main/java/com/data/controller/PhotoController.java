package com.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.data.model.UserPhotos;
import com.data.service.PhotoService;


@Controller
public class PhotoController {
	@Autowired
	private PhotoService service;
	
	@GetMapping("/addPhoto")
	public ModelAndView addPhoto() {
		return new ModelAndView("uploadPhoto","photo",new UserPhotos());
	}
	
	@PostMapping("/photo")
	public String savePhoto(@ModelAttribute(name="photo") UserPhotos userPhotos,RedirectAttributes redirectAtt,@RequestParam("file") MultipartFile multipartFile) throws IOException{
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		userPhotos.setLogo(fileName);
		UserPhotos savePhotos = service.savePhoto(userPhotos);
		
		String uploadDir = "./user-logo/"+savePhotos.getId();
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		 try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {        
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }   
		 
		 redirectAtt.addFlashAttribute("message","The user photos has been already successfully.");
		 return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String deletePhoto(@RequestParam ("id") Integer id,RedirectAttributes redirectAtt) {
		service.deletePhoto(id);
		redirectAtt.addFlashAttribute("message","Deleted successfully");
		return "redirect:/";
	}
}
