package com.social.model;
import java.sql.Timestamp;
import java.util.List;
import com.social.enums.Privacy;

public class Post {
	private int id;
	private String content;
	private User postedBy;
	private List<PostImages> postImages;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Privacy privacy;
	
	
}