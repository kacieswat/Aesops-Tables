<template>
  <div>
    <div class = "find-rest-paragraph">
      <center>
        <h1 class = "finder-title">Meet Your Match with Aesop's Tool</h1>
        <p class = "finder-description"> Use Aesop's Tool to meet restaurants that serve some of your favorite cusines.<br>
        Want to save a restaurant for later? Hit the "Like" button, and you can peep your curated<br>
        like by clicking here or on the button at the top of your screen. <br><br> Want to skip a restaurant? No big deal. Click the arrow button to check out the next restaurant on deck.</p>
        </center>
      </div>
      <center>
      
      <div class="choices">
        <restaurant-card v-bind:restaurant="this.$store.state.restaurant[i]" />
          
      </div>
      <button class = "button"><img class = "choose-button" @click="next" src = "../assets/images/arrows-blue.png" /></button>
    </center>
  </div>
</template>

<script>
import RestaurantCard from './RestaurantCard.vue'
import APIService from '../services/APIService.js'

export default {
  components: { RestaurantCard },
  data() {
    return {
      i: 0,
      liked: []
    }
  },
  methods: {
    next() {
      if (this.i == this.$store.state.restaurant.length-1) {
        this.getRestaurants();
      }
      else {
        this.i = this.i + 1;
      }
    },
    getRestaurants() {
        APIService.getRestaurants().then( (resp) => {
          let restaurants = []
          if (this.$store.state.likedRestaurant.length === 0) {
            restaurants = resp.data;
          }
          else {

            for (let i = 0; i < resp.data.length; i++) {
              let found = false;
              for (let j = 0; j < this.$store.state.likedRestaurant.length; j++) {
                if (resp.data[i].id == this.$store.state.likedRestaurant[j].id) {
                  found = true;
                }
              }
              if (!found) {
                restaurants.push(resp.data[i]);
              }
            }
          }
          this.$store.commit('SET_RESTAURANTS', restaurants);
          this.i = 0;
        });
      }
  },
  created() {
    this.$store.commit('CLEAR_RESTAURANT')
    APIService.getFavRestaurants().then( resp => {
      this.$store.commit('SET_FAV_RESTAURANT', resp.data);
     });
    this.getRestaurants();
  }
}
</script>

<style scoped>
.arrow {
  width: 35%;
}
.choices {
  flex-direction: row;
  
  display: flex;
  background-color: (4, 30, 66, 0.9);
  justify-content: space-evenly;
  flex-wrap: wrap;
  margin-top: 20px;
}

.button {
  display: inline-block;
  font-family: Quicksand;
  font-size: 20px;
  text-transform: uppercase;
  font-weight: bold;
  background-color:#AFEADC;
  color: #041E42;
  border-color: #041E42;
  text-align: center;
  padding: 20px;
  width: 150px;
  margin: 10px 100px 100px; 
  border-radius: 5px;
}

.find-rest-paragraph {
    background-color: rgba(4, 30, 66, 0.9);
    display: flex;
    flex-direction: column;
    border-radius: 10px;
}

.button:hover{
  background-color:whitesmoke;
}

.intro-paragraph {
    background-color: rgba(4, 30, 66, 0.9);
    display: flex;
    flex-direction: column;
    border-radius: 10px;
}

.finder-title {
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

.finder-description {
color: #AFEADC;
font-family: Quicksand;
font-size: 20px;

}

.choose-button {
width: 50px;
height: auto;
}

.plate-holder {
  width: 200px;
}

</style>