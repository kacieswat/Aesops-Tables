<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <center><h1 class = "aesop-header">AESOP'S TABLES</h1>
      <img class = "plate" src = "../assets/images/dish.png" />

      <h2 id = "create-header" class="h3 mb-3 font-weight-normal">Create Account</h2> <br>
      <div id="alert-font" class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="username" class="sr-only">Username </label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <br><br>
      <label for="password" id="password-design" class="sr-only">Password </label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      /><br> <br>
      <label for="password" id="confirm-password-design" class="sr-only">Confirm Password </label>
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      /><br>
      <button id = "create-button" class="btn btn-lg btn-primary btn-block" type="submit">
        Register
      </button> <br>
      <router-link class = "make-account" :to="{ name: 'login' }">Already have an account?</router-link>
      <br><br>


      </center>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');



#alert-font {
  color: red;
  font-size: 22px;
}

#confirm-password-design {
   display: inline-block; 
   width: 75px; 
   text-align: left;
   margin: 1px 10px 10px 5px ; 
}
#register {
  background-color:rgba(4, 30, 66, 0.85);
  padding: 25px;
  border-radius: 45px;
  width: 700px;
  border-color: #AFEADC;
  border-style: solid;
  /* Starting FlexBox */
  display: flex;
  height: 100vh;
  flex-direction: column;
  height: 37rem;
  justify-content: center;
  margin: 10rem auto;
}


#create-button {
   display: inline-block;
  font-family: Quicksand;
  font-size: 20px;
  text-transform: uppercase;
  font-weight: bold;
  background-color: #afeadc;
  color: #041e42;
  border-color: #041e42;
  text-align: center;
  padding: 20px;
  width: 150px;
  margin: -20px 150px 10px;
  border-radius: 5px;
  
}

#create-header{
  font-family: Quicksand;
  text-transform: uppercase;
  font-weight: bold;
  text-shadow: -1px 0 #041E42, 0 1px #041E42, 1px 0 #041E42, 0 -1px #041E42;
  color: #AFEADC;
  letter-spacing: 10px;
}

.make-account {
  font-family: Quicksand;
  font-size: 23px;
  color: #AFEADC;
}

.aesop-header {
  font-family: Quicksand;
  text-transform: uppercase;
  color:#AFEADC;
  letter-spacing: 10px;
  font-size: 45px;
}



.plate {
  width: 18.5%;
}

.make-account:hover {
  color: whitesmoke;
}

</style>