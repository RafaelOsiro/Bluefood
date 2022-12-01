package io.ucb.rafael.bluefood.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.ucb.rafael.bluefood.util.IOUtils;

@Service
public class ImageService {
	
	@Value("${bluefood.files.logotipo}")
	private String logotipoDir;
	
	public void funcUploadLogtipo(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, logotipoDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
}
