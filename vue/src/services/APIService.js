import axios from "axios";


const http = axios.create({

    baseURL:"http://localhost:8080"

});

export default {
    getCuisines() {
        return http.get("/cuisines");
    },
    saveAddress(address) {
        return http.post("/save-address", address);
    },
    saveFavorites(likedCuisine) {
        return http.post("/save-favorites", likedCuisine);
    },
    getRestaurants() {
        return http.get("/get-restaurants");
    },
    saveRestaurants(favorites) {
        return http.post("/save-favorite-restaurants", favorites);
    },
    getFavRestaurants() {
        return http.get("/see-favorite-restaurants");
    }
}