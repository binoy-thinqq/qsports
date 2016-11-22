package com.thinqq.qsports.server.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.server.process.UserProfileProcess;


@Controller
@RequestMapping("img")
public class ImageUploadController extends BaseController {

	
	private static final String CLOUD_NAME="dcha6bliq";
	private static final String API_SECRET = "a4IKOlxl9sPicdJfau06jlYTc-A";
	private static final String API_KEY="246221326969431";
	private static Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			  "cloud_name", CLOUD_NAME,
			  "api_key", API_KEY,
			  "api_secret", API_SECRET));
	
	
	@Autowired
	UserProfileProcess userProcess;
	
	
	@POST
	@RequestMapping("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	public String fileUpload(@RequestParam("img") MultipartFile inputfile, HttpServletRequest request) {
		UserVo signedInUser = getSignedInUser(request);
		try {
			Map uploadResult = cloudinary.uploader().upload(inputfile, ObjectUtils.emptyMap());
			String uploadedUrl = uploadResult.get("secure_url").toString();
			signedInUser.setPictureUrl(uploadedUrl);
			userProcess.saveUser(signedInUser);
			return uploadedUrl;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
		}
		return "ERROR";
	}

	
}
