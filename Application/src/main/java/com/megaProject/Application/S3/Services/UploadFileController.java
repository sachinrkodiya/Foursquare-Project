package com.megaProject.Application.S3.Services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.megaProject.Application.FilesSystems.FileInfo;
import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.ReviewPhotos;
import com.megaProject.Application.repository.PhotosRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.response.ImageResponse;
import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.JwtUserDetailsService;
import com.megaProject.Application.service.Pagging;

@CrossOrigin // (origins = "http://localhost:4200")
@RestController
public class UploadFileController {

	@Autowired
	S3Services s3Services;

	@Autowired
	private UserDao userDao;

	@Autowired
	PlaceRepository placeRepo;
	
	@Autowired
	Pagging pagging;

	@Autowired
	PhotosRepository photoRepo;

	@Autowired
	private JwtUserDetailsService userDetailsService;


	@Autowired
	private AmazonS3 s3client;

	// @Value("${endpointUrl}")
	private String endpointUrl = "https://aws-foursquare.s3.us-east-2.amazonaws.com";

	// @Value("${bucketName}")
	private String bucketName = "aws-foursquare";

	@PostMapping("/uploadUserImage")
	public ResponseEntity<ReturnResponse> uploadUserImage(@RequestPart(value = "file") MultipartFile multipartFile,
			@RequestParam long userId) {

		String fileUrl = "";
		String status = null;

		DAOUser userCheck = userDao.findByUserID(userId);
		if (userCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NO CONTENT", "User ID not valid"));

		}
		try {

			// converting multipart file to file
			File file = convertMultiPartToFile(multipartFile);

			// filename
			String fileName = "UserImage/" + multipartFile.getOriginalFilename();

			fileUrl = endpointUrl + "/" + fileName;

			status = uploadFileTos3bucket(fileName, file);

			file.delete();
			DAOUser user = userDao.findByUserID(userId);
			user.setImage(fileUrl);
			userDetailsService.update(user);

		} catch (Exception e) {
			return ResponseEntity.status(200).body(
					new ReturnResponse(200, "UploadController().uploadFile().Exception : " + e.getMessage(), " "));

		}

		return ResponseEntity.status(200).body(new ReturnResponse(200, "", "user image uploaded " + fileUrl));
	}

	@PostMapping("/uploadPlaceImage")
	public ResponseEntity<ReturnResponse> uploadPlaceImage(@RequestPart(value = "file") MultipartFile multipartFile,
			@RequestParam int placeId) {

		String fileUrl = "";
		String status = null;

		Place place = placeRepo.findByPlaceId(placeId);
		if (place == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND"));

		}
		try {

			// converting multipart file to file
			File file = convertMultiPartToFile(multipartFile);

			// filename
			String fileName = "PlaceImage/" + multipartFile.getOriginalFilename();

			fileUrl = endpointUrl + "/" + fileName;

			status = uploadFileTos3bucket(fileName, file);

			file.delete();
			place.setImage(fileUrl);
			placeRepo.save(place);

		} catch (Exception e) {
			return ResponseEntity.status(200).body(
					new ReturnResponse(200, "UploadController().uploadFile().Exception : " + e.getMessage(), " "));

		}

		return ResponseEntity.status(200).body(new ReturnResponse(200, "", "Place image uploaded " + fileUrl, place));
	}

	@PostMapping("/uploadReviewImage")
	public ResponseEntity<?> uploadReviewImage(@RequestPart(value = "files") MultipartFile[] multipartFiles,
			@RequestParam int placeId, @RequestParam long userId) {

		String message = "";
		List<String> fileNames = new ArrayList<>();
		
		DAOUser userCheck = userDao.findByUserID(userId);
		if (userCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NO CONTENT", "User ID not valid"));

		}

		Place place = placeRepo.findByPlaceId(placeId);
		if (place == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND"));

		}

		try {

			Arrays.asList(multipartFiles).stream().forEach(file -> {
				File file1 = null;
				try {
					file1 = convertMultiPartToFile(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String fileName = "ReviewImage/" + file.getOriginalFilename();
				String fileUrl = endpointUrl + "/" + fileName;
				String status = uploadFileTos3bucket(fileName, file1);
				file1.delete();

				String pattern = "dd-MM-yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());

				ReviewPhotos images = new ReviewPhotos(userId, placeId, fileUrl, date);

				photoRepo.save(images);
				fileNames.add(fileUrl);
			});

			message = "Uploaded the files successfully: " + fileNames;
		} catch (Exception e) {
			return ResponseEntity.status(200).body(
					new ReturnResponse(200, "UploadController().uploadFile().Exception : " + e.getMessage(), " "));
		}
		return ResponseEntity.status(200).body(new ReturnResponse(200, "", "Place image uploaded ", fileNames));

	}

	@GetMapping("/getPictures")
	public ResponseEntity<?> getReviewPhotos(@RequestParam int placeId,@RequestParam int pageNo,@RequestParam int pageSize) {

		List<ReviewPhotos> photoList = photoRepo.getPhotosByPlaceId(placeId);
		if (photoList.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT CONTENT", "NO PHOTOS ARE AVAILABLE"));

		}

		return pagging.PagedValues(photoList, pageNo, pageSize);
	}

	@GetMapping("/getPhoto")
	public ResponseEntity<ReturnResponse> getReviewPhoto(@RequestParam long photoId) {

		ReviewPhotos photos = photoRepo.getPhotoByPhotoId(photoId);
		Place place = placeRepo.findByPlaceId(photos.getPlace_id());
		DAOUser user = userDao.findByUserID(photos.getUser_id());

		ImageResponse image = new ImageResponse();
		image.setPhotoId(photos.getPhoto_id());
		image.setDate(photos.getDate());
		image.setPhotoUrl(photos.getImage());
		image.setPlaceName(place.getName());
		image.setUserImage(user.getImage());
		image.setUserName(user.getUsername());

		return ResponseEntity.status(200).body(new ReturnResponse(200, "", "Success ", image));
	}

	// =======================================================================================================

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String uploadFileTos3bucket(String fileName, File file) {
		try {
			s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
		} catch (AmazonServiceException e) {
			return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
		}
		return "Uploading Successfull -> ";
	}
}