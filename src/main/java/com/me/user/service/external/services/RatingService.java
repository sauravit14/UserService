package com.me.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.me.user.service.entities.Rating;

@FeignClient(name ="RATING-SERVICE")
public interface RatingService {

	//get
	
	//post
	@PostMapping("/ratings")
	Rating createRating(Rating values);
	
	
	
}
