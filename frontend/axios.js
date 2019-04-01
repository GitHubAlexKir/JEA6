import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080/1/',
    timeout: 10000,
    params: {} // do not remove this, its added to add params later in the config
});


instance.interceptors.request.use(function (config) {
    /* global window Store */
    let token = localStorage.getItem('access_token');

    // console.log(location.id, location)
    if (token) {
        config.headers.common['Authorization'] = 'Bearer '+localStorage.getItem('access_token');
    }

    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

export default instance;
