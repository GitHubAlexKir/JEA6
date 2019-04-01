<template>
    <div>
    <nav class="navbar navbar-dark bg-dark">
        <a class="navbar-brand" href="#">
            <img src="../img/logo.png" width="40" height="40">
        </a>
        <div>
            <img :src="$auth.user.picture" width="30" height="30">
            <span class="text-muted font-weight-light px-2">{{$auth.user.name}}</span>
            <button type="button" class="btn btn-outline-secondary btn-sm" @click="$auth.logout()">Logout</button>
        </div>
    </nav>
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4">Hallo, {{$auth.user.name}}!</h1>
            <p class="lead">Welkom bij AlcoholGigant</p>
        </div>
    </div>
    <div>
    <div v-if="user.email !== null" class="col-md-6">
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
        <div v-if="item.id !== null" class="col-md-6">
            <h4>
                <b> id: </b>{{ item.id }}
            </h4>
            <h4>
                <b> price: </b>{{ item.price }}
            </h4>
            <h4>
                <b> name: </b>{{ item.name }}
            </h4></div>
        <input type="number" v-model="itemid" id="itemid" class="form-control" name="itemid">
        <button type="button" class="btn btn-dark" @click="getItem()">Get item</button>
    </div>
    </div>
</template>

<script>
    import axios from '../axios';
    export default  {
        name: 'home',
        data() {
            return {
                user:{},
                itemid: 2,
                item:{},
                profle:{},
                isAuthenticated: false
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.user = data;
            });
        },
        methods: {
            getItem()
            {
                axios.get('api/item/get/' + this.itemid).then(({data}) => {
                    this.item = data.item;
                });
            }
        }
    }
</script>