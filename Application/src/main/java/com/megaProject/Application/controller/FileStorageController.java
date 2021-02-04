package com.megaProject.Application.controller;

import java.io.IOException;

import java.util.Optional;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.megaProject.Application.fileSystem.FileStorageService;
import com.megaProject.Application.fileSystem.UploadFileResponse;
import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.service.JwtUserDetailsService;




@RestController
public class FileStorageController {
	
	@Autowired
    private FileStorageService fileStorageService;
	
	@Autowired
	private UserDao userDao;


	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private PlaceRepository placeRepository;
	

	
	 // user image upload code
	 @PostMapping("/uploadPhoto/{email}")
	 @ResponseStatus(HttpStatus.CREATED)
	    public UploadFileResponse uploadSingleFile(@RequestParam("file") MultipartFile file,
	    		@PathVariable String email) {


		 
	        String fileName = fileStorageService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(fileName)
	                .toUriString();
	        
	        DAOUser user = userDao.findByEmail(email);
	        user.setImage(fileDownloadUri);
	        userDetailsService.update(user);

	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }
	 
	 
	 
	 
	 //place image upload code...
	 
		@PostMapping("/uploadFile/{placeID}")
		@ResponseStatus(HttpStatus.CREATED)
		public UploadFileResponse uploadSingleFile(@RequestParam("file") MultipartFile file, @PathVariable int placeID) {
			Optional<Place> place = placeRepository.findById(placeID);

			Place content = place.get();

			String fileName = fileStorageService.storeFile(file);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadFile/" + fileName + "/").path(fileName).toUriString();

			content.setImage(fileDownloadUri);
			placeRepository.save(content);

			return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		}
	 

	    @GetMapping("/downloadFile/{fileName:.+}")
		@ResponseStatus(HttpStatus.CREATED)
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	        Resource resource = fileStorageService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = "png";
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            System.out.print("Could not determine file type.");
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }
	    
	    
	    //multiplr file

}


