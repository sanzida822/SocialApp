package com.social.validation;

import java.util.LinkedHashMap;
import java.util.Map;

public class PostValidator {
	private PostValidator postValidator;
	private PostValidator() {}
	
	public PostValidator getInstance() {
		if(postValidator==null) {
			postValidator=new PostValidator();
		}
		return postValidator;
	}
	
	public Map<String,String> validatePost(){
		LinkedHashMap<String, String> errorMessges=new LinkedHashMap<String, String>();
		
		
		return null;
		
	}
	

}
