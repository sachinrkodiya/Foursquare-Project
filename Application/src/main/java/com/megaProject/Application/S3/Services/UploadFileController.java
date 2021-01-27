package com.megaProject.Application.S3.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.JwtUserDetailsService;

@CrossOrigin // (origins = "http://localhost:4200")
@RestController
public class UploadFileController {

	@Autowired
	S3Services s3Services;

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/api/file/upload")
	public ResponseEntity<ReturnResponse> uploadMultipartFile(@RequestParam long userId,
			@RequestParam("file") MultipartFile file) {
		DAOUser userCheck = userDao.findByUserID(userId);
		if (userCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NO CONTENT", "User ID not valid"));

		}
		String keyName = userId + file.getOriginalFilename(); // file.getOriginalFilename();

		System.out.println(keyName);

		s3Services.uploadFile(keyName, file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(keyName)
                .toUriString();
		DAOUser user = userDao.findByUserID(userId);
		user.setImage(fileDownloadUri);
		userDetailsService.update(user);

		return ResponseEntity.status(200)
				.body(new ReturnResponse(200, fileDownloadUri, "Upload Successfully -> KeyName =" + keyName,user));
	}
}