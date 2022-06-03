package com.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.model.UserPhotos;
import com.data.repository.PhotoRepository;

@Service
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepo;
	
	public UserPhotos savePhoto(UserPhotos userPhoto) {
		return photoRepo.save(userPhoto);
	}
	
	public List<UserPhotos> getAllPhoto(){
		
		return (List<UserPhotos>) photoRepo.findAll();
	}
	
	public void deletePhoto(Integer id) {
		photoRepo.deleteById(id);
	}
}
