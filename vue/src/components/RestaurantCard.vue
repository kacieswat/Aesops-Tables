<template>
  <div class="oneCard" v-bind:class="{liked: restaurant.liked}">
      <h2>{{restaurant.name}}</h2>
      <h3>Rating: {{restaurant.rating}}</h3>
      <h3>Price: {{restaurant.cost}}</h3>
      <h3>{{restaurant.location}}</h3>
      <h3>{{restaurant.cuisineTypes}}</h3>
      <a class = "rest-link" v-bind:href="restaurant.url" target="_blank">View Website</a> <br><br>
      <button class = "like-button" v-on:click="like(restaurant)">
          <span v-show="restaurant.liked">Unlike Restaurant</span>
            <span v-show="!restaurant.liked">Like Restaurant</span>
            </button>&nbsp; &nbsp;
      
  </div>
</template>

<script>
import APIService from '../services/APIService.js'

export default {
    props: ['restaurant'],
    methods: {
        like(restaurant) {
            if (!restaurant.liked) {
                let found = this.$store.state.likedRestaurant.includes(restaurant);

                if (!found) {
                    restaurant.liked = true;
                    this.$store.commit('ADD_RESTAURANT', restaurant);
                }
            }
            else {
                restaurant.liked = false;
                this.$store.commit('REMOVE_RESTAURANT', restaurant);
            }
            APIService.saveRestaurants(this.$store.state.likedRestaurant);
        }
    }
}
</script>

<style scoped>
.oneCard {
    border: 2px solid #AFEADC;
    font-family: Quicksand;
    text-transform: uppercase;
    border-color: #041E42;
    border-style: solid;
    border-radius: 10px;
    width: 500px;
    height: auto;
    margin: 20px;
    text-align: center;
    background-color: rgba(175, 234, 220, 0.95);
    color: #041E42;
    border-width: 2px;
    padding: 10px;
}
.oneCard.liked {
    background-color: rgba(245, 245, 245, 0.95);
    color: #041E42;
    border-color: #041E42;
    border-style: solid;
    border-width: 2px;
}

.clicked {
    background-color: #041E42;
    border-color: #AFEADC;
    color: #afeadc;
    height: 30px;
    width: 150px;
    font-family: Quicksand;
    text-transform: uppercase;
    font-weight: bold;
}

.rest-link {
    color: #041E42;
}
.rest-link:hover {
    font-weight: bold;
    color: #041E42;
}

.like-button {
    background-color: #041E42;
    font-family: Quicksand;
    color: #afeadc;
    text-transform: uppercase;
    border-color:#041E42;
    font-size: 15px;
}

.choose-button {
width: 100px;
height: auto;
}

</style>