<template>
<div>
  <br>
    <div class = "find-restaurants">
      <center><br>
      <h1 class = "find-restaurant-title">meet Your matches </h1>
        <p class = "description"> Ready to reconnect with your liked restaurants? Here you'll find all of the eateries you connected with while using Aesop's Tool!<br><br>
          If you want to match with more restaurants in your area, click the button below to return to Aesop's Tool.
          </p>
    <router-link v-bind:to="{name: 'findRestaurant'}" tag="button" class="return-to-find">Find More Restaurants</router-link><br><br>
    </center>
    </div>
  <div>
      <div class="choices">
        <restaurant-card v-for="res in $store.state.likedRestaurant"
          v-bind:key="res.id"
          v-bind:restaurant="res" />
      </div>
  </div>
  </div>
</template>

<script>
import RestaurantCard from './RestaurantCard.vue'
import APIService from '../services/APIService.js'

export default {
  components: { RestaurantCard },
  created() {
    APIService.getFavRestaurants().then( resp => {
      this.$store.commit('SET_FAV_RESTAURANT', resp.data);
     });
  }
}
</script>

<style scoped>

.choices {
  flex-direction: row;
  display: flex;
  background-color: (4, 30, 66, 0.9);
  justify-content: space-evenly;
  flex-wrap: wrap;
}

.find-restaurants {
    background-color: rgba(4, 30, 66, 0.9);
    display: flex;
    flex-direction: column;
    border-radius: 10px;
}

.find-restaurant-title {
  color: #AFEADC;
  font-family: Quicksand;
  text-transform: uppercase;
  letter-spacing: 10px;
  font-size: 45px;
  font-weight: bold;
  text-shadow: -1px 0 #041E42, 0 1px #041E42, 1px 0 #041E42, 0 -1px #041E42;
  text-align: center;
  margin: 0 auto;
  display: block; 
}

.description {
color: #AFEADC;
font-family: Quicksand;
font-size: 20px;

}

.return-to-find {
background-color: #AFEADC;
  border-color: #041E42;
  border-radius: 5px;
  height: 30px;
  width: 220px;
  font-family: Quicksand;
  font-weight: bold;
  text-transform: uppercase;
  font-size:15px
}


</style>