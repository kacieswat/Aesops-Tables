<template>
<div>
    <br>
    <div class = "intro-paragraph"> 
        <center><br>
            <h1 class = "selector-title">Aesop's Cuisine Selector </h1>
 <div v-if="filteredList.length">
            <p class = "selector-description"> Below you'll find a variety of different cuisines in your area.
                <br>
                Select up to five different cuisines so we can build your restaurant list.
                <br>
                Click the "Submit" button to save your top five.<br>
                <br> There are plenty of cuisines to choose from, <br> so use the text box below
                to type in what you're craving.
                </p>

        
        <input class = "find-food" type="text" placeholder="Find some food!" v-model="userInput"/>
        <div class='choices'>
            <CuisineCard v-for="cuisine in $store.state.likedCuisine"
            v-bind:key='cuisine.cuisineName'
            v-bind:cuisine='cuisine' />
        </div>
        <br>
        <button class = "button" v-on:click="submitFavorites">Submit</button>
        <br>

</div>
<div v-if="!filteredList.length" class = "selector-description">
    <p>Sorry, we couldn't find any choices in your area!</p> 
    <p>Please enter a different location and try again.</p>
</div>
        <router-link id = "hover" class="sr-only" :to="{ name: 'survey' }">Try Again</router-link>
       <br><br>

        </center>
    </div>
    <div class="choices">
        <CuisineCard v-for="cuisine in filteredList" 
            v-bind:key="cuisine.cuisineName" 
            v-bind:cuisine="cuisine"
            v-show="!cuisine.liked" />
    </div>



    </div>
</template>

<script>
import CuisineCard from "./CuisineCard.vue"
import APIService from "../services/APIService.js"

export default {

    components: {
        CuisineCard
    },
    data() {
        return {
            userInput:""
            
        }
    },
    methods: {
        submitFavorites() {
            APIService.saveFavorites(this.$store.state.likedCuisine);
            this.$router.push({name: "findRestaurant"});
        },
        noCuisines() {
            if (this.$store.state.cuisine.length === 0 ) {
                window.alert("This doesn't exist!");
            }
        }
    },
    computed:{
        filteredList() {
            return this.$store.state.cuisine.filter( (type) => {
                if (type.cuisineName.toLowerCase().includes(this.userInput.toLowerCase())) {
                    return type;
                }
            })
        }

        
    },
    created() {
        this.$store.commit('CLEAR_CUISINE');
        APIService.getCuisines().then(
            (response) => {
                const cuisines = response.data;
                this.$store.commit('SET_CUISINES', cuisines);
        });
        this.$store.commit('CLEAR_LIKED');
        
       
        }
        //  isThereFood(){
        //     if(this.filteredList.size==0){
        //         this.noFood=true
        //     } else {
        //         this.noFood=false
        //     }
   }

</script>

<style scoped>

#hover{
      padding: 20px;
  width: 150px;
  margin: 5px 100px 30px; 
  border-radius: 5px;
}

.choices {
  flex-direction: row;
  display: flex;
  background-color: (4, 30, 66, 0.9);
  justify-content: space-evenly;
  flex-wrap: wrap;
}

.intro-paragraph {
    background-color: rgba(4, 30, 66, 0.9);
    display: flex;
    flex-direction: column;
    border-radius: 10px;
}

.selector-title {
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

.selector-description {
color: #AFEADC;
font-family: Quicksand;
font-size: 20px;

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
  margin: 5px 100px 30px; 
  border-radius: 5px;
}

.button:hover {
    background-color: whitesmoke;
    
}

.find-food {
    height: 30px;
    width: 200px;
}


</style>