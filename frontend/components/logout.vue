<template>
    <div>
    <button @click="getUser()">get User</button>
    <button @click="logout()">Logout</button>
    </div>
</template>

<script>
    import axios from '../axios';
    export default  {
        data() {
            return {
                User:{},
            }
        },
        methods: {
            logout() {
              localStorage.clear();
              window.location = '/1/';
            },
            getUser(){
                axios.post('api/jwt/user').then(({data}) => {
                   this.User = data;
                   console.log(data);
                }).catch(() => {
                    this.wrong = true;
                })
            }
        }
    }
</script>