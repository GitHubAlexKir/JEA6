import Vue from 'vue';
import router from './router'
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import moment from 'moment';
import App from "./App.vue";
import auth from './plugins/auth';
Vue.use(auth);

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
window.Vue = require('vue');
window.Moment = moment;

window.Bus = new Vue();

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');