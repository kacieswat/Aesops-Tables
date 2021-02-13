<template>
  <div class="home">
    <h1 class = "home-subtitle">Welcome back!</h1>
    <center>
      <img class = "plate" src = "../assets/images/dish.png" /><br><br><br><br>
       <button class = "button" @click ="$router.push('survey')">Location</button>
        <button class = "button" @click="$router.push({ name: 'chooseCard' })">Cuisines</button>
      </center> 
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.home {
  background-color:rgba(4, 30, 66, 0.9);
  padding: 25px;
  border-radius: 45px;
  width: 700px;
  border-color: #AFEADC;
  border-style: solid;
 
  /* Starting FlexBox */
  display: flex;
  flex-direction: column;
  height: 32rem;
  justify-content:center;
  margin: 10rem auto;
}

.home-subtitle{
   color: #AFEADC;
  font-family: Quicksand;
  text-transform: uppercase;
  letter-spacing: 10px;
  font-size: 65px;
  font-weight: bold;
  align-items: center;
  text-shadow: -1px 0 #041E42, 0 1px #041E42, 1px 0 #041E42, 0 -1px #041E42;
  text-align: center;
  margin-top: 100px;
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
  margin: 0 100px 100px; 
  border-radius: 5px;
}

.button:hover{
  background-color:whitesmoke;
}


.plate {
  width: 35%;
}

</style>
