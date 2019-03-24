import Vue from 'vue'
import logout from './components/logout.vue'
import authentication from './components/authentication.vue'


require('./bootstrap');



const app = new Vue({ el: '#app', components: {
        login,
        logout
    }})