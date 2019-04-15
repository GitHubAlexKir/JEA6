import Vue from 'vue';
import VueRouter from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';
import order from './components/order.vue';
import chat from './components/chat.vue';
import moment from 'moment';
import VueSweetalert2 from 'vue-sweetalert2';

window.Vue = require('vue');
window.Moment = moment;

Vue.use(VueRouter);
Vue.use(VueSweetalert2);

window.Bus = new Vue();

const routes = [
    { path: '/', component: login, name: 'login', meta: { guest: true }},
    { path: '/home', component: home, name: 'home', meta: { requiresAuth: true }},
    { path: '/order', component: order, name: 'order', meta: { requiresAuth: true }},
    { path: '/chat', component: chat, name: 'chat', meta: { requiresAuth: true }}
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