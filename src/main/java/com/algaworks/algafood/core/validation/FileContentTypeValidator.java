package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile>{

	private List<String> allowed;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		// TODO Auto-generated method stub
		this.allowed = Arrays.asList(constraintAnnotation.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return value == null || this.allowed.contains(value.getContentType());
	}

}
