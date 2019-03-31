<template>
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
                itemid: 2,
                item:{}
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
            },
            getItem()
            {
                axios.get('api/item/get/' + this.itemid).then(({data}) => {
                    this.item = data.item;
                });
            }
        }
    }
</script>