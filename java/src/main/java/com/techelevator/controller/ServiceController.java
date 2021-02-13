package com.techelevator.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.techelevator.dao.Service;
import com.techelevator.model.Cuisine;
import com.techelevator.model.Location;
import com.techelevator.model.Restaurant;

@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RestController
public class ServiceController {
	
	@Autowired
	Service service;
	
	@RequestMapping(path="/get-address", method=RequestMethod.GET)
	public Location getAddress(Principal principal) {
		return service.getUserLocation(principal.getName());
	}

	@RequestMapping(path="/cuisines", method=RequestMethod.GET)
	public List<Cuisine> getCuisines(Principal principal) throws JsonMappingException, JsonProcessingException {
		return service.getCuisines(principal.getName());
	}
	
	@RequestMapping(path="/save-address", method=RequestMethod.POST)
	public void saveAddress(@RequestBody Location userLocation, Principal principal) throws JsonMappingException, JsonProcessingException {
		System.out.println(principal.getName());
		service.saveAddress(userLocation, principal.getName());
	}
	
	@RequestMapping(path="/save-favorites", method=RequestMethod.POST)
	public void saveFavorites(@RequestBody List<Cuisine> userFavorites, Principal principal) {
		service.saveFavorites(userFavorites, principal.getName());
	}
	
	@RequestMapping(path="/get-restaurants", method=RequestMethod.GET)
	public List<Restaurant> getRestaurants(Principal principal) throws JsonMappingException, JsonProcessingException {
		return service.getRestaurants(principal.getName());
	}
	
	@RequestMapping(path="/see-favorite-restaurants", method=RequestMethod.GET)
	public List<Restaurant> getFavoriteRestaurants(Principal principal)
	{
		return service.getFavoriteRestaurants(principal.getName());
	}
	
	@RequestMapping(path="/save-favorite-restaurants", method=RequestMethod.POST)
	public void saveFavoriteRestaurants(@RequestBody List<Restaurant> userFavoriteRestaurants, Principal principal) {
		service.saveFavoriteRestaurants(userFavoriteRestaurants, principal.getName());
	}
	
}
