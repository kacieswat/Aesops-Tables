<template>
    <div v-on:click="like(cuisine)" class="oneCard" 
        v-bind:class="{liked: cuisine.liked}"
        >
        <h2>{{cuisine.cuisineName}}</h2>
    </div>
</template>

<script>
export default {
    props: ['cuisine'],
    methods: {
        like(cuisine) {
            if (!cuisine.liked && this.$store.state.likedCuisine.length < 5) {
                cuisine.liked = true;
                this.$store.commit('ADD_CUISINE', cuisine);
            }
            else if (cuisine.liked) {
                cuisine.liked = false;
                this.$store.commit('REMOVE_CUISINE', cuisine);
            }
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
    width: 250px;
    height: 100px;
    margin: 20px;
    text-align: center;
    background-color: #AFEADC;
    color: #041E42;
}
.oneCard.liked {
    background-color: whitesmoke;
    color: #041E42;
    border-color: #AFEADC;
    border-style: solid;
    border-width: 5px;
}

</style>