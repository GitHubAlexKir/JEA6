<template>
    <div>
        <navbar ref="navbar"></navbar>
        <div class="pageheader">
            <div class="container">
                <h2 class="title">Welkom bij AlcoholGigant</h2>
                <p>Voor de grootste flessen van heel Europa!</p>
            </div>
        </div>
        <div v-if="user.email" class="col-md-6">
            <h4>
                <b> E-mail: </b>{{ user.email }}
            </h4>
            <h4>
                <b> Voornaam: </b>{{ user.firstName }}
            </h4>
            <h4>
                <b> Achternaam: </b>{{ user.lastName }}
            </h4>
            <h4><b> privileges: </b></h4>
            <h6 v-for="privilege in user.privileges"><b> soort: </b>{{ privilege }} </h6>
        </div>
        <div v-if="user.addressInformation" class="col-md-6">
            <h4>
                <b> Verzending informatie </b>
            </h4>
            <h6>
                <b> Ontvanger: </b>{{ user.addressInformation.addressee }}
            </h6>
            <h6>
                <b> Adres: </b>{{ user.addressInformation.address }}
            </h6>
            <h6>
                <b> Postcode: </b>{{ user.addressInformation.zip }}
            </h6>
            <h6>
                <b> Plaats: </b>{{ user.addressInformation.city }}
            </h6>

        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div v-for="item in items" class="col-sm-6 col-md-4">
                        <div class="thumbnail">
                            <h4 class="text-center"><span class="label label-info">{{item.productName}}</span></h4>
                            <img src="http://placehold.it/650x450&text=item.productName" class="img-responsive">
                            <div class="caption">
                                <div class="row">
                                    <div class="col-md-6 col-xs-6">
                                        <h3>{{item.productName}}</h3>
                                    </div>
                                    <div class="col-md-6 col-xs-6 price">
                                        <h3>
                                            <label>{{item.price}}</label></h3>
                                    </div>
                                </div>
                                <p>{{item.productNumber}}</p>
                                <div class="row">
                                    <div class="col-md-6">
                                        <a @click="addToCart(item)" class="btn btn-success btn-product"><span class="glyphicon glyphicon-shopping-cart"></span> Koop</a></div>
                                </div>
                                <p> </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from '../axios';
    import Navbar from "./navbar";
    export default  {
        name: 'home',
        components: {Navbar},
        data() {
            return {
                user:{},
                itemid: 2,
                items:[]
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.user = data;
            });
            axios.get('api/item').then(({data}) => {
                this.items = data.items;
            });
        },
        methods: {
            logout() {
                localStorage.clear();
                window.location = '/1/';
            },
            addToCart(item){
                this.$refs.navbar.addToCart(item);
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