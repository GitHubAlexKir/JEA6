<template>
    <div>
    <div v-if="user" class="col-md-6">
        <h4>
            <b> E-mail: </b>{{ user.email }}
        </h4>
        <h4>
            <b> Voornaam: </b>{{ user.firstName }}
        </h4>
        <h4>
            <b> Achternaam: </b>{{ user.lastName }}
        </h4>
        <h4><b> Groepen: </b></h4>
          <h6 v-for="group in user.groups"><b> Naam: </b>{{ group }} </h6>
    </div>
        <button type="button" class="btn btn-dark" @click="logout()">Uitloggen</button>
    </div>
</template>

<script>
    import axios from '../axios';
    export default  {
        name: 'home',
        data() {
            return {
                user:{},
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.user = data;
            });
        },
        methods: {
            logout() {
              localStorage.clear();
              window.location = '/1/';
            }
        }
    }
</script>