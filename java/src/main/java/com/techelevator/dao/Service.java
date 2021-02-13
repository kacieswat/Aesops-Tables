package com.techelevator.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Cuisine;
import com.techelevator.model.Location;
import com.techelevator.model.Restaurant;

@Component
public class Service implements ServiceDAO {

	@Value("${zomato.api.url}")
	private String baseURL;

	@Value("${zomato.api.key}")
	private String key;

	@Autowired
	private UserSqlDAO userDao;

	RestTemplate rest = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	JsonNode node;
	JdbcTemplate jdbcTemplate;

	public Service(JdbcTemplate jdbcTemplate, DataSource DS) {
		this.jdbcTemplate = new JdbcTemplate(DS);

	}
	

	@Override
	public List<Cuisine> getCuisines(String userName) throws JsonMappingException, JsonProcessingException {
		
		Location userLocation=getUserLocation(userName);

		HttpHeaders header = new HttpHeaders();
		header.set("user-key", key);
		HttpEntity<String> entity = new HttpEntity<>(header);
		String url = this.baseURL + "/cuisines?city_id=" + getCityID(userLocation);
		ResponseEntity<String> results = rest.exchange(url, HttpMethod.GET, entity, String.class);

		node = mapper.readTree(results.getBody());
		List<Cuisine> cuisines = new ArrayList<>();

		for (int i = 0; i < node.path("cuisines").size(); i++) {
			int id = node.path("cuisines").path(i).path("cuisine").path("cuisine_id").asInt();
			String name = node.path("cuisines").path(i).path("cuisine").path("cuisine_name").asText();
			Cuisine newCuisine = new Cuisine(id, name, false);
			cuisines.add(newCuisine);
		}

		return cuisines;
	}
	
	@Override
	public Location getUserLocation(String userName) {
		int userID = userDao.findIdByUsername(userName);
		String locationQuery = "SELECT * FROM user_location WHERE user_id=?";

		SqlRowSet locationResult = jdbcTemplate.queryForRowSet(locationQuery, userID);
		Location userLocation = null;
		if (locationResult.next()) {
			String cityName = locationResult.getString("user_city");
			String stateCode = locationResult.getString("user_state");
			Integer zipCode = locationResult.getInt("user_zip");
			Integer cityID = locationResult.getInt("city_id");
			userLocation = new Location(cityName, stateCode, zipCode, cityID);
		}
		return userLocation;
	}

	@Override
	public Integer getCityID(Location userLocation) throws JsonMappingException, JsonProcessingException {

		HttpHeaders header = new HttpHeaders();
		header.set("user-key", key);
		HttpEntity<String> entity = new HttpEntity<>(header);

		String cityName = userLocation.getCityName();
		String stateCode = userLocation.getStateCode();
		Integer cityId = null;

		String url = this.baseURL + "/cities?q=" + cityName;
		ResponseEntity<String> results = rest.exchange(url, HttpMethod.GET, entity, String.class);
		node = mapper.readTree(results.getBody());

		for (int i = 0; i < node.path("location_suggestions").size(); i++) {
			String state = node.path("location_suggestions").path(i).path("state_code").asText().toLowerCase();
			if (stateCode.toLowerCase().equals(state)) {
				cityId = node.path("location_suggestions").path(i).path("id").asInt();
				break;
			}
		}
		return cityId;
	}

	@Override
	public void saveAddress(Location userLocation, String userName) throws JsonMappingException, JsonProcessingException {
	
		int userID = userDao.findIdByUsername(userName);
		int cityID = getCityID(userLocation);
		String cityName = userLocation.getCityName();
		String stateCode = userLocation.getStateCode();
		Integer zipCode = userLocation.getZipCode();

		String queryRequest = "SELECT user_id FROM user_location WHERE user_id=?";

		SqlRowSet result = jdbcTemplate.queryForRowSet(queryRequest, userID);

		if (result.next()) {
			String updateLocation = "UPDATE user_location SET user_city=?, user_state=?, user_zip=?, city_id=? WHERE user_id=?";
			jdbcTemplate.update(updateLocation, cityName, stateCode, zipCode, cityID, userID);
		} else {

			String sql = "INSERT INTO user_location (user_id, user_city, user_state, user_zip, city_id) VALUES(?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql, userID, cityName, stateCode, zipCode, cityID);
		}
	}

	@Override
	public void saveFavorites(List<Cuisine> userFavorites, String userName) {
		int userID = userDao.findIdByUsername(userName);

		String queryRequest = "DELETE FROM favorites WHERE user_id=?";

		jdbcTemplate.update(queryRequest, userID);

		for (Cuisine cuisine : userFavorites) {
			String cuisineName = cuisine.getCuisineName();
			int cuisineId = cuisine.getCuisineId();
			String updateFavorite = "INSERT INTO favorites (user_id, cuisine_name, cuisine_id) VALUES(?, ?, ?)";
			jdbcTemplate.update(updateFavorite, userID, cuisineName, cuisineId);
		}

	}

	@Override
	public List<Restaurant> getRestaurants(String userName) throws JsonMappingException, JsonProcessingException {

		Location userLocation = getUserLocation(userName);
		String favoritesQuery = "SELECT * FROM favorites WHERE user_id=?";
		int userID = userDao.findIdByUsername(userName);
		SqlRowSet userFavoritesResults = jdbcTemplate.queryForRowSet(favoritesQuery, userID);

		StringBuilder firstHalf = new StringBuilder("/search?entity_id=");
		firstHalf.append(userLocation.getCityID()).append("&entity_type=city&start=");
		StringBuilder secondHalf = new StringBuilder("&cuisines=");
		boolean first = true;
		
		while (userFavoritesResults.next()) {
			if (first) {
				secondHalf.append(userFavoritesResults.getInt("cuisine_id"));
				first = false;
			} else {
				secondHalf.append("%2C").append(userFavoritesResults.getInt("cuisine_id"));
			}
		}
		
		List<Restaurant> selectedRestaurants=new ArrayList<Restaurant>();
		Random rand = new Random();
		int start = rand.nextInt(81);
		
		String fullURL=this.baseURL+firstHalf.append(start).append(secondHalf).toString();
		ResponseEntity<String> results = makeEntity(fullURL);
		node = mapper.readTree(results.getBody());
			
		for (int i = 0; i < node.path("restaurants").size(); i++) {
				
			int id=node.path("restaurants").path(i).path("restaurant").path("id").asInt();
			String name=node.path("restaurants").path(i).path("restaurant").path("name").asText();
			double rating=node.path("restaurants").path(i).path("restaurant").path("user_rating").path("aggregate_rating").asDouble();
			int cost=node.path("restaurants").path(i).path("restaurant").path("price_range").asInt();;
			String address=node.path("restaurants").path(i).path("restaurant").path("location").path("address").asText();
			String url=node.path("restaurants").path(i).path("restaurant").path("url").asText();
			String photoUrl=node.path("restaurants").path(i).path("restaurant").path("featured_image").asText();
			String cuisineTypes=node.path("restaurants").path(i).path("restaurant").path("cuisines").asText();
				
			Restaurant restaurant=new Restaurant(id, name, rating, cost, address, url, photoUrl, cuisineTypes, false);
			selectedRestaurants.add(restaurant);
		}
		Collections.shuffle(selectedRestaurants);
		return selectedRestaurants;
	}

	@Override
	public List<Restaurant> getFavoriteRestaurants(String userName) {
		int userID = userDao.findIdByUsername(userName);		
		String restaurantsQuery="SELECT * FROM liked_restaurants WHERE user_id=?";
		SqlRowSet restaurantResult=jdbcTemplate.queryForRowSet(restaurantsQuery, userID);
		
		List<Restaurant> results=new ArrayList<Restaurant>();
		
		while (restaurantResult.next()) {
			int restaurantId = restaurantResult.getInt("restaurant_id");
			String restaurantName = restaurantResult.getString("name");
			double restaurantRating=restaurantResult.getDouble("rating");
			int restaurantCost=restaurantResult.getInt("cost");
			String restaurantLocation=restaurantResult.getString("location");
			String restaurantUrl=restaurantResult.getString("url");
			String restaurantPhotoUrl=restaurantResult.getString("photo_url");
			String cuisineTypes=restaurantResult.getString("cuisine_type");
			boolean restaurantLiked=restaurantResult.getBoolean("liked");
			
			Restaurant restaurant=new Restaurant(restaurantId, restaurantName, restaurantRating, restaurantCost, restaurantLocation, restaurantUrl, restaurantPhotoUrl, cuisineTypes, restaurantLiked);
			results.add(restaurant);
			
		}
		
		return results;
	}
	
	@Override
	public void saveFavoriteRestaurants(List<Restaurant> userFavoriteRestaurants, String userName) {
		int userID = userDao.findIdByUsername(userName);
		String queryRequest = "DELETE FROM liked_restaurants WHERE user_id=?";
		
		jdbcTemplate.update(queryRequest, userID);
		
		for (Restaurant restaurant : userFavoriteRestaurants) {
			int restaurantId = restaurant.getId();
			String restaurantName = restaurant.getName();
			double restaurantRating=restaurant.getRating();
			int restaurantCost=restaurant.getCost();
			String restaurantLocation=restaurant.getLocation();
			String restaurantUrl=restaurant.getUrl();
			String restaurantPhotoUrl=restaurant.getPhotoUrl();
			String cuisineTypes=restaurant.getCuisineTypes();
		
		
			
			boolean restaurantLiked=restaurant.isLiked();
			
			String updateFavorite = "INSERT INTO liked_restaurants (restaurant_id, name, rating, cost, location, url, photo_url, liked, user_id, cuisine_type) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(updateFavorite, restaurantId, restaurantName, restaurantRating, restaurantCost, restaurantLocation, restaurantUrl,restaurantPhotoUrl, restaurantLiked, userID, cuisineTypes);
		}
		
	}
	
	public ResponseEntity<String> makeEntity(String fullURL) {
		
		HttpHeaders header = new HttpHeaders();
		header.set("user-key", this.key);
		HttpEntity<String> entity = new HttpEntity<>(header);
		return rest.exchange(fullURL, HttpMethod.GET, entity, String.class);
	}
 


}
