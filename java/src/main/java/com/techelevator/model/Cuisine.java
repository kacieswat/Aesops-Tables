package com.techelevator.model;

public class Cuisine {
	int cuisineId;
	String cuisineName;
	boolean liked = false;
	
	public Cuisine(int cuisineId, String cuisineName, boolean liked) {
		this.cuisineId=cuisineId;
		this.cuisineName = cuisineName;
		this.liked=liked;
	}
	
	

	public int getCuisineId() {
		return cuisineId;
	}



	public void setCuisineId(int cuisineId) {
		this.cuisineId = cuisineId;
	}



	public String getCuisineName() {
		return cuisineName;
	}

	public void setCuisineName(String cuisineName) {
		this.cuisineName = cuisineName;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	
	
	
	
}
