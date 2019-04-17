<template>
    <div>
        <navbar ref="navbar"></navbar>
        <NewItemModal></NewItemModal>
        <div class="pageheader">
            <div class="container">
                <h2 class="title">Welkom bij AlcoholGigant</h2>
                <p>Voor de grootste flessen van heel Europa!</p>
            </div>
        </div>
        <div class="container">
        <div class="flex flex-col mt-6">
            <div class="flex justify-end">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#itemModal">
                    Maak item aan.
                </button>
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
                                <h4 class="text-center"><span class="label label-warning">< 18 jaar verkopen wij geen alcohol</span></h4>
                                <img src="/webshop/images/alcohol-template.jpg" class="img-responsive">
                                <div class="caption">
                                    <div class="row">
                                        <div class="col-md-6 col-xs-6">
                                            <h3>{{item.productName}}</h3>
                                        </div>
                                        <div class="col-md-6 col-xs-6 price">
                                            <h3>
                                                <label>Prijs: <span>&#8364;</span>{{item.price}}</label></h3>
                                        </div>
                                    </div>
                                    <p>Productnummer: {{item.productNumber}}</p>
                                    <div class="row">
                                        <div v-if="item.stock > 0" class="col-md-6">
                                            <p class="text-center"><span class="label label-info">Voorraad: {{item.stock}}</span></p>
                                            <a @click="addToCart(item)" class="btn btn-success btn-product"><span class="glyphicon glyphicon-shopping-cart"></span> Koop</a>
                                        </div>
                                        <div v-else class="col-md-6">
                                            <p class="text-center"><span class="label label-danger">Voorraad: {{item.stock}}</span></p>
                                            <p class="label-warning"><span class="glyphicon glyphicon-shopping-cart"></span> Niet leverbaar</p>
                                            <p class="text-center">Volgende levering is 23 Juni 2019</p>
                                        </div>
                                    </div>
                                    <p> </p>
                                </div>
                            </div>
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
    import NewItemModal from "../modals/NewItemModal";
    export default  {
        name: 'home',
        components: {Navbar,NewItemModal},
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
            addToCart(item){
                item.stock--;
                    this.$refs.navbar.addToCart(item);
                    this.$swal.fire(
                        'Toegevoegd!',
                        item.productName + ' toegevoegd aan winkelwagen',
                        'success'
                    );
            },
            getItem()
            {
                axios.get('api/item/get/' + this.itemid).then(({data}) => {
                    this.item = data.item;
                });
            },
        }
    }
</script>