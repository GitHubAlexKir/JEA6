import Vue from 'vue';
import VueRouter from 'vue-router';
import Login from './components/login.vue';
import Home from './components/logout.vue';
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import moment from 'moment';


Vue.use(BootstrapVue);
window.Vue = require('vue');
window.Moment = moment;

Vue.use(VueRouter);
require('./bootstrap');

window.Bus = new Vue();

const routes = [
    { path: '/', component: Login, name: 'login', meta: { guest: true }},
    { path: '/home', component: Home, name: 'home', meta: { requiresAuth: true }},
    ];



const router = new VueRouter({
    routes // short for `routes: routes`
});

router.beforeEach((to, from, next) => {
    let token = localStorage.getItem('token');
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!token || token === null) {
            next({
                path: '/',
            });
        }
    }

    if (to.matched.some(record => record.meta.guest)) {
        if (token || token !== null) {
            next({
                path: '/home',
            });
        }
    }

    next();
});

const app = new Vue({
    router,
}).$mount('#app');