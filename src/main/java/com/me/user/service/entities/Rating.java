package com.me.user.service.entities;

import lombok.Data;

@Data
public class Rating {

	private String ratindId;
	private String userId;
	private String hotelId;
	private int rating;
	private String feedback;
	
	private Hotel hotel;
}
