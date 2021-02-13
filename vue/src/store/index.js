import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    cuisine: [],
    address: {},
    likedCuisine: [],
    restaurant: [],
    likedRestaurant: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SAVE_ADDRESS(state, userInfo) {
      state.address = userInfo;
    },
    SET_CUISINES(state, cuisines){
      state.cuisine = cuisines; 
    },
    ADD_CUISINE(state, cuisine) {
      state.likedCuisine.push(cuisine);
    },
    REMOVE_CUISINE(state, cuisine) {
      state.likedCuisine = state.likedCuisine.filter( (type) => {
        return type != cuisine;
      });
    },
    CLEAR_CUISINE(state) {
      state.cuisine = [];
    },
    CLEAR_LIKED(state) {
      if (state.likedCuisine.length > 0) {
        state.likedCuisine.forEach( (cuisine) => cuisine.liked = false);
        state.likedCuisine = [];
      }
    },
    ADD_RESTAURANT(state, res) {
      state.likedRestaurant.push(res);
    },
    REMOVE_RESTAURANT(state, res) {
      state.likedRestaurant = state.likedRestaurant.filter( (each) => {
        return each != res;
      });
    },
    SET_RESTAURANTS(state, res) {
      state.restaurant = res;
    },
    CLEAR_RESTAURANT(state) {
      state.restaurant = [];
    },
    SET_FAV_RESTAURANT(state, fav) {
      state.likedRestaurant = fav;
    }
  }
})
