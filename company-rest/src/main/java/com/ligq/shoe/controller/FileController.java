package com.ligq.shoe.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ligq.shoe.entity.Image;
import com.ligq.shoe.model.ImageResponse;
import com.ligq.shoe.service.ImageService;


@Controller
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
    @Autowired
	private ImageService imageService;
    
	@RequestMapping(value="/images", method=RequestMethod.POST)
	public HttpEntity<?> imageUpLoad(
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
				
		if(null == file || file.isEmpty()){
			logger.error("image is empty");
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = imageService.save(file,request,response);
	    } catch (Exception e) {
			logger.error(e.getMessage(),e);
            return new ResponseEntity<HttpStatus>(
            		HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/images/multipartfile", method=RequestMethod.POST)
	public HttpEntity<?> imagesUpLoad(
			@RequestParam(value = "file") MultipartFile[] file,
			HttpServletRequest request,
			HttpServletResponse response){
				
		if(null == file || file.length < 1){
			logger.error("image is empty");
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = imageService.save(file,request,response);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
            return new ResponseEntity<HttpStatus>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/images/{uuid}", method = RequestMethod.GET,
			produces = "application/hal+json")
	public HttpEntity<?> findOneImage(
			@PathVariable String uuid,
			HttpServletRequest request,
			HttpServletResponse response) {
		
        Image image = imageService.findOne(uuid);
        if (null == image)
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        
	    Link link =  linkTo(methodOn(FileController.class)
	    		.findOneImage(
	    				image.getUuid(), request,response)).withSelfRel();

        ImageResponse imageResponse = new ImageResponse();
        BeanUtils.copyProperties(image, imageResponse);
        imageResponse.add(link);
        return new ResponseEntity<Resource>(
        		new Resource<ImageResponse>(imageResponse), HttpStatus.OK);

	}

	@RequestMapping(value = "/images/{filename}", method = RequestMethod.POST,
			produces = "application/hal+json")
	public HttpEntity<?> addImage(
			@PathVariable String filename,
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if(null == file || file.isEmpty()){
			logger.error("image is empty");
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = imageService.save(filename,file,request,response);
	    } catch (Exception e) {
			logger.error(e.getMessage(),e);
            return new ResponseEntity<HttpStatus>(
            		HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

	
	@RequestMapping(value = "/images/show/{uuid}")
	public void getImage(
			@PathVariable String uuid,
			HttpServletRequest request,
			HttpServletResponse response) {
        Image image = imageService.findOne(uuid);
        if (null == image)
            return;

		FileInputStream fis = null;
		OutputStream out = null;
	    response.setContentType(image.getMimeType());
	    try {
	        out = response.getOutputStream();
	        File file = new File(image.getPath());
	        fis = new FileInputStream(file);
	        byte[] b = new byte[fis.available()];
	        fis.read(b);
	        out.write(b);
	        out.flush();
	    } catch (Exception e) {
			logger.error(e.getMessage(),e);
	    } finally {
	        if (fis != null) {
	            try {
	               fis.close();
	            } catch (IOException e) {
	    	       logger.error(e.getMessage(),e);
	            }   
	         }
	        
	        if (out != null) {
	            try {
	            	out.close();
	            } catch (IOException e) {
	    	       logger.error(e.getMessage(),e);
	            }   
	         }
	    }
	}
}
