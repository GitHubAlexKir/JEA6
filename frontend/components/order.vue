<template>
    <div>
        <navbar ref="navbar"></navbar>
        <div class="pageheader">
            <div class="container">
                <h2 class="title">Mijn Bestellingen</h2>
                <p>Controlleer hier uw huidige status van de bestelling.</p>
            </div>
        </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div v-for="order in orders" class="col-sm-6 col-md-4">
                            <div class="thumbnail">
                                <h4 class="text-center"><span class="label label-info">{{order.id}}</span></h4>
                                <div class="caption">
                                    <div v-for="item in order.items" class="row">
                                        <div class="col-md-6 col-xs-6">
                                            <h3>{{item.productName}}</h3>
                                        </div>
                                        <div class="col-md-6 col-xs-6 price">
                                            <h3>
                                                <label><span>&#8364;</span>{{item.price}}</label></h3>
                                        </div>
                                        <p>{{item.productNumber}}</p>
                                    </div>
                                    <p> </p>
                                </div>
                                <h4>
                                    <b> Verzending informatie </b>
                                </h4>
                                <h6>
                                    <b> Ontvanger: </b>{{ order.addressInformation.addressee }}
                                </h6>
                                <h6>
                                    <b> Adres: </b>{{ order.addressInformation.address }}
                                </h6>
                                <h6>
                                    <b> Postcode: </b>{{ order.addressInformation.zip }}
                                </h6>
                                <h6>
                                    <b> Plaats: </b>{{ order.addressInformation.city }}
                                </h6>
                                <div v-if="order.dispatched">
                                    <h4 class="text-center"><span class="label label-success">Verzonden</span></h4>
                                </div>
                                <div v-else>
                                    <h4 class="text-center"><span class="label label-danger">Nog niet verzonden</span></h4>
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
        name: 'order',
        components: {Navbar},
        data() {
            return {
                user:{},
                orders:[]
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.user = data;
            });
            axios.get('api/order').then(({data}) => {
                this.orders = data.orders;
            });
        },
        methods: {

        }
    }
</script>