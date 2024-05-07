package com.me.user.service.implementaion;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.me.user.service.entities.Hotel;
import com.me.user.service.entities.Rating;
import com.me.user.service.entities.User;
import com.me.user.service.exception.ResourceNotFoundException;
import com.me.user.service.external.services.HotelService;
import com.me.user.service.repositories.UserRepository;
import com.me.user.service.services.UserService;


@Service
public class UserServiceImplementaion implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImplementaion.class);
	
	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new 
				ResourceNotFoundException("User not found with the given userId: " + userId));
		//fetch rating of the user form RATING-SERVICE
		//localhost:8083/ratings/users/5b2f5ef0-f0e9-420f-8d8c-fddce07b40f0
		Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		logger.info("{}", ratingsOfUsers);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUsers).toList();
		
		//get hotels
		List<Rating> ratingList = ratings.stream().map(rating-> {
			//api call to hotel service
			//http:localhost:8082/hotels/20615463-9f76-4e93-891d-18a00dfbd439
			//rest template
//			ResponseEntity<Hotel> hotelResponse = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//			Hotel hotel = hotelResponse.getBody();
//			logger.info("Response status code : {}", hotelResponse.getStatusCode());
			
			//feignClient
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			
			//set the hotel to rating
			rating.setHotel(hotel);
			//return the rating
			return rating;
		}).collect(Collectors.toList());
		
		user.setRatings(ratings);
		
		return user;
	}

}
