<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <center>
        <h1 id="signin" class="h3 mb-3 font-weight-normal">Please Sign In</h1>
        <br /><br />
        <img class="plate" src="../assets/images/dish.png" /> <br /><br /><br />
        <div
          id="sign-in-alert"
          class="alert alert-danger"
          role="alert"
          v-if="invalidCredentials"
        >
          Invalid username and password
        </div>
        <div
          class="alert alert-success"
          id="registration-success"
          role="alert"
          v-if="this.$route.query.registration"
        >
          Thank you for registering, please sign in.
        </div>
        <br />
        <label for="username" class="sr-only">Username</label>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <br />
        <br />
        <label for="password" class="sr-only">Password</label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <br />
        <br />
        <button class="button" type="submit">Sign in</button><br /><br />
        <router-link
          id="accountregister"
          class="sr-only"
          :to="{ name: 'register' }"
          >Need an account?</router-link
        ><br /><br />
      </center>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/home");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Martel");
.sr-only:hover {
  color: whitesmoke;
}
git .router-link-active {
  font-weight: 700;
}

#sign-in-alert {
  font-family: Quicksand;
  color: red;
}

#login {
  background-color: rgba(4, 30, 66, 0.9);
  padding: 25px;
  border-radius: 45px;
  width: 700px;
  border-color: #afeadc;
  border-style: solid;

  /* Starting FlexBox */
  display: flex;
  flex-direction: column;
  height: 33rem;
  justify-content: center;
  margin: 10rem auto;
}

.button:hover {
  background-color: whitesmoke;
}

#signin {
  color: #afeadc;
  font-family: Quicksand;
  text-transform: uppercase;
  letter-spacing: 10px;
  font-size: 45px;
  font-weight: bold;
  align-items: center;
  text-shadow: -1px 0 #041e42, 0 1px #041e42, 1px 0 #041e42, 0 -1px #041e42;
  text-align: center;
  margin: 0 auto;
  display: block;
}

.button {
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
  margin: 10px 150px 10px;
  border-radius: 5px;
}

#registration-success{
  font-family: Quicksand;
    font-size: 20px;
    color: whitesmoke;
}

.plate {
  width: 18.5%;
}
#accountregister {
    
    font-size: 23px;

}
</style>
