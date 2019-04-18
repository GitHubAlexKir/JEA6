<template>
    <div>
        <navbar ref="navbar"></navbar>
        <div class="pageheader">
            <div class="container">
                <h2 class="title">Geplaatste Bestellingen - Medewerker pagina</h2>
            </div>
        </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <p v-if="wrong">Je bent geen medewerker van AlcoholGigant</p>
                        <div v-if="orders.length > 0"  v-for="order in orders" class="col-sm-6 col-md-4">
                            <div class="thumbnail">
                                <h4 class="text-center"><span class="label label-info">{{order.id}}</span></h4>
                                <div class="caption">
                                    <div v-for="item in order.items" class="row">
                                        <div class="col-md-6 col-xs-6">
                                            <h3>{{item.productName}}</h3>
                                        </div>
                                        <p>{{item.productNumber}}</p>
                                        <p><b>Locatie:</b>{{item.warehouseLocation}}</p>
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
                                <div style="display:flex; justify-content:center;">
                                    <div v-if="order.paid">
                                        <h4 class="text-center"><span class="label label-success">Betaald</span></h4>
                                        <a @click="OrderShipped(order)" class="btn btn-success text-center">Download PDF</a>
                                    </div>
                                    <div v-else>
                                        <h4 class="text-center"><span class="label label-danger">Nog niet betaald</span></h4>
                                    </div>
                                    <div style="display:flex; flex-direction: column; align-items:center;" v-if="order.dispatched">
                                        <h4 class="text-center"><span class="label label-success">Verzonden</span></h4>
                                        <b>Verwachte berzorgdatum: </b>{{ order.expectedArrival }}
                                    </div>
                                    <div v-else>
                                        <h4 class="text-center"><span class="label label-danger">Nog niet verzonden</span></h4>
                                        <a @click="OrderShipped(order)" class="btn btn-success text-center">Zet order op verzonden</a>
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
    export default  {
        name: 'order',
        components: {Navbar},
        data() {
            return {
                user:{},
                orders:[],
                loading: true,
                wrong: false
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.user = data;
            });
            //axios.get('api/order/worker').then(({data}) => {
            //    this.orders = data.orders;
            //});
            this.connect();
        },
        methods: {
            connect() {
                this.socket = new WebSocket("ws://localhost:8080/webshop/worker");
                this.socket.onopen = () => {
                    this.socket.onmessage = ({data}) => {
                        this.orders = (JSON.parse(data)).orders;
                        if (this.loading) {
                            this.loading = false;
                        }
                        else{
                            this.$swal.fire({
                                // position: 'top-end',
                                //type: 'success',
                                title: 'Nieuwe update binnengekomen',
                                //showConfirmButton: false,
                                timer: 1000,
                                backdrop: `
                                            rgba(0,0,0,0.0)
                                            url("https://media1.giphy.com/media/xUA7bheu9ndssiYgHm/giphy.gif")
                                            top right
                                            no-repeat
                                          `
                            })
                        }
                    };
                };
            },
            OrderShipped(order){
                axios.post('api/order/sent',order).then(({data}) => {
                    order = data.order;
                    this.$swal.fire(
                        'Updated!',
                        'order.id:' + order.id + ' verzonden met verwachte bezorgdatum: ' + order.expectedArrival,
                        'success'
                    );
                    this.socket.send(JSON.stringify(order));
                });
            }

        }
    }
</script>