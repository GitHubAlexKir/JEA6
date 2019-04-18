<template>
    <div>
        <navbar ref="navbar"></navbar>
        <NewItemModal></NewItemModal>
        <NewReviewModal ref="reviewmodal"></NewReviewModal>
        <div class="pageheader">
            <div class="container">
                <h2 class="title">Welkom bij AlcoholGigant</h2>
                <p>Voor de grootste flessen van heel Europa!</p>
            </div>
        </div>
        <div class="container">
        <div class="flex flex-col mt-6">
            <div v-if="admin" class="flex justify-end">
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
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h4 class="text-center"><span class="label label-info">Reviews</span></h4>
                                            <div v-if="item.reviews.length > 0">
                                                <div v-for="review in item.reviews" class="col-md-auto">
                                                    <h4><b>Auteur:</b> {{review.author}}</h4>
                                                    <h4><b>Waardering:</b> {{review.appreciation}}/5 sterren</h4>
                                                    <h4><b>Beschrijving:</b></h4>
                                                    <h4> {{review.content}}</h4>
                                                    <div v-if="admin" class="flex justify-end">
                                                    <a @click="deleteReview(item,review)" class="btn btn-danger">
                                                        Verwijder review.
                                                    </a>
                                                </div>
                                                </div>

                                            </div>
                                            <div v-else>
                                                <h4>Heeft nog geen waarderingen.</h4>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <button type="button" class="btn btn-primary" @click="setItem(item)" data-toggle="modal" data-target="#reviewModal">
                                            Maak review aan.
                                        </button>
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
    import NewReviewModal from "../modals/NewReviewModal";
    export default  {
        name: 'home',
        components: {Navbar,NewItemModal,NewReviewModal},
        data() {
            return {
                user:{},
                items:[],
                admin: false
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.user = data;
                console.log(this.user);
                this.admin = this.isAdmin();
            });
            axios.get('api/item').then(({data}) => {
                this.items = data.items;
            });
        },
        methods: {
            isAdmin(){
                let isAdmin = false;
                this.user.privileges.forEach((privilege) => {
                    if (privilege === 'Owner') {
                        isAdmin = true;
                    }
                });

                return isAdmin;

            },
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
            setItem(item){
                this.$refs.reviewmodal.setItem(item);
            },
            deleteReview(item,review){
                axios.post('api/item/review/remove/' + item.id,review).then(({data}) => {
                    this.$swal.fire(
                        'Verwijderd!',
                        review.id + ' verwijderd.',
                        'success'
                    );
                    location.reload();
                });
            }
        },
    }
</script>