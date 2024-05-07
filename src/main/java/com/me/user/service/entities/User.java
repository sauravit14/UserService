package com.me.user.service.entities;

import java.util.ArrayList;
import java.util.List;

import com.me.user.service.util.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Constant.USERS)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(name = Constant.Id)
	private String userId;
	
	@Column(name =Constant.NAME)
	private String name;
	
	@Column(name = Constant.EMAIL)
	private String email;
	
	@Column(name = Constant.ABOUT)
	private String about;
	
	@Transient
	private List<Rating> ratings =  new ArrayList<>();
}
