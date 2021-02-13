package com.techelevator.model;

public class Restaurant {
	int id;
	String name;
	double rating;
	int cost;
	String location;
	String url;
	String photoUrl;
	String cuisineTypes;
	boolean liked;
	
	
	public Restaurant(int id, String name, double rating, int cost, String location, String url, String photoUrl, String cuisineTypes, boolean liked) {
		
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.cost = cost;
		this.location = location;
		this.url=url;
		this.photoUrl=photoUrl;
		this.cuisineTypes=cuisineTypes;
		this.liked = liked;
	}
	
	

	public String getCuisineTypes() {
		return cuisineTypes;
	}



	public void setCuisineType(String cuisineType) {
		this.cuisineTypes = cuisineTypes;
	}



	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPhotoUrl() {
		return photoUrl;
	}


	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	
	
	

}
