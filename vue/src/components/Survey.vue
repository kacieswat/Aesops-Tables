<!-- Survey should be renamed to Set Location at a later date-->

<template>
  <div class = "survey-body">
      <center>
      <h2 class="survey-title">Location</h2><br>
      <img class = "plate" src = "../assets/images/dish.png" /> <br><br>

        <p>We need to know where to tickle your tastebuds! <br>
        Complete the survey below so we can provide a catered list of <br> cuisines based on your area.</p></center> <br>
      <center>
      <form v-on:submit.prevent="saveInfo">
        <label for="city" id="city-text" class="sr-only">City</label>
          <input class="input" type="text" placeholder="City" v-model="survey.cityName"/> <br><br>
          <label for="state" id="state-text" class="sr-only">State</label>
          <select class="input" id="drop-down" v-model="survey.stateCode">
            <option value="" disabled selected>Select State</option>
            <option value="AL">Alabama</option>
            <option value="AK">Alaska</option>
            <option value="AZ">Arizona</option>
            <option value="AR">Arkansas</option>
            <option value="CA">California</option>
            <option value="CO">Colorado</option>
            <option value="CT">Connecticut</option>
            <option value="DE">Delaware</option>
            <option value="FL">Florida</option>
            <option value="GA">Georgia</option>
            <option value="HI">Hawaii</option>
            <option value="ID">Idaho</option>
            <option value="IL">Illinois</option>
            <option value="IN">Indiana</option>
            <option value="IA">Iowa</option>
            <option value="KS">Kansas</option>
            <option value="KY">Kentucky</option>
            <option value="LA">Louisiana</option>
            <option value="ME">Maine</option>
            <option value="MD">Maryland</option>
            <option value="MA">Massachusetts</option>
            <option value="MI">Michigan</option>
            <option value="MN">Minnesota</option>
            <option value="MS">Mississippi</option>
            <option value="MO">Missouri</option>
            <option value="MT">Montana</option>
            <option value="NE">Nebraska</option>
            <option value="NV">Nevada</option>
            <option value="NH">New Hampshire</option>
            <option value="NJ">New Jersey</option>
            <option value="NM">New Mexico</option>
            <option value="NY">New York</option>
            <option value="NC">North Carolina</option>
            <option value="ND">North Dakota</option>
            <option value="OH">Ohio</option>
            <option value="OK">Oklahoma</option>
            <option value="OR">Oregon</option>
            <option value="PA">Pennsylvania</option>
            <option value="RI">Rhode Island</option>
            <option value="SC">South Carolina</option>
            <option value="SD">South Dakota</option>
            <option value="TN">Tennessee</option>
            <option value="TX">Texas</option>
            <option value="UT">Utah</option>
            <option value="VT">Vermont</option>
            <option value="VA">Virginia</option>
            <option value="WA">Washington</option>
            <option value="WV">West Virginia</option>
            <option value="WI">Wisconsin</option>
            <option value="WY">Wyoming</option>
          </select><br><br>
         
         <label for="ZIP" id="zip-text" class="sr-only">Zip Code</label>
         <input  class="input" type="number" placeholder="ZIP" v-model="survey.zipCode"/><br><br><br>
          <button id="enter-button" type=submit>Enter</button> <br>
          <router-link id = "hover" class="sr-only" :to="{ name: 'home' }">Home</router-link><br><br>
      </form>
          </center>
  </div>
</template>
<script>
import APIService from "../services/APIService.js"
export default {
  data() {
    return {
      survey: {
        cityName: '',
        stateCode: '',
        zipCode: ''
      }
    }
  },
  methods: {
    saveInfo() {
      this.$store.commit('SAVE_ADDRESS', this.survey);
      APIService.saveAddress(this.survey);
      this.$router.push({name: 'chooseCard'});
    }

  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

#city-text{
  margin: 1px 1px 10px 20px ; 
}

#zip-text {
  margin: 1px 1px 10px -25px ; 
}

#state-text {
  margin: 1px 1px 10px 10px ; 
}

#drop-down {
  width: 26%;
}
.input {
   width: 25%;
    float: center;
    text-align: left;
}
.survey-body{
  background-color:rgba(4, 30, 66, 0.9);
  padding: 25px;
  border-radius: 45px;
  width: 700px;
  border-color: #AFEADC;
  border-style: solid;
 
  /* Starting FlexBox */
  display: flex;
  flex-direction: column;
  height: 37rem;
  justify-content: center;
  margin: 10rem auto;
  
}

#enter-button{
  margin-left: 28px;
}

.survey-title{
  font-family: Quicksand; 
  text-transform: uppercase;
  font-weight: bold;
  letter-spacing: 10px;
  font-size: 50px;
  color: #AFEADC;
  margin-bottom: -5px;
  margin-top: -2px;
}

.info-title{
  font-family: Quicksand;
  color: #AFEADC;
}

#enter-button {
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
  margin: -10px 100px 20px ; 
  
}

#enter-button:hover {
  background-color: whitesmoke;
}

p {
  padding: 10px;
  font-size: 20px;
  margin: -5px;
  font-family: Quicksand; 
  color: #AFEADC;
}

.plate {
  width: 18.5%;

}

#hover:hover {
  color: whitesmoke;
}

</style>