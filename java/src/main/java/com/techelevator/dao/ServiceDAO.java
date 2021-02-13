package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.techelevator.model.Cuisine;
import com.techelevator.model.Location;
import com.techelevator.model.Restaurant;

public interface ServiceDAO {

	public List<Cuisine> getCuisines(String userName) throws JsonMappingException, JsonProcessingException;
	
	public Integer getCityID(Location userLocation) throws JsonMappingException, JsonProcessingException;
	
	
	public void saveAddress(Location userLocation, String userName) throws JsonMappingException, JsonProcessingException;
	
	public void saveFavorites(List<Cuisine> userFavorites, String userName);
	
	public List<Restaurant> getRestaurants(String userName) throws JsonMappingException, JsonProcessingException;
	
	public Location getUserLocation(String userName);
	
	public List<Restaurant> getFavoriteRestaurants(String userName);

	void saveFavoriteRestaurants(List<Restaurant> userFavoriteRestaurants, String name);

	
	
}
