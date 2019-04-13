<template>
    <div>
    <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">AlcoholGigant</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"> <span class="glyphicon glyphicon-shopping-cart"></span> {{ cart.length }} - Items<span class="caret"></span></a>
                    <ul class="dropdown-menu dropdown-cart" role="menu">
                        <li><a class="text-center" @click="toggleCartDisplay()">Toon {{cartDisplaySettings}} details</a></li>
                        <li class="divider"></li>
                        <!-- show less items -->
                        <div v-if="showLess">
                            <div v-for="(item, index) in cart">
                                <li>
                                <span class="item">
                                   <span class="item-left">
                                       <span class="item-info">
                                           <span>{{item.productName}} ${{item.price}} <button @click="removeFromCart(index)" class="btn btn-xs btn-danger pull-right">x</button></span>
                                       </span>
                                   </span>
                                </span>
                                </li>
                            </div>
                        </div>
                        <div v-else>
                        <div v-for="(item, index) in cart">
                            <li>
                                <span class="item">
                                   <span class="item-left">
                                       <img src="http://lorempixel.com/50/50/" alt="" />
                                       <span class="item-info">
                                           <span>{{item.productName}}</span>
                                           <span>${{item.price}}</span>
                                       </span>
                                   </span>
                                   <span class="item-right">
                                       <button @click="removeFromCart(index)" class="btn btn-xs btn-danger pull-right">x</button>
                                   </span>
                                </span>
                            </li>
                            </div>
                        </div>
                        <li class="divider"></li>
                        <li><a class="text-center">Totaal: ${{ cartTotal }}</a></li>
                        <li class="divider"></li>
                        <li><a class="text-center" @click="createOrder()">Bestellen en betalen</a></li>
                        <li class="divider"></li>
                        <li><a class="text-center" @click="clearCart()"><font color="red"> Winkelwagen legen</font></a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
    </nav>
    </div>
</template>
<script>
    import axios from '../axios';
    export default {
        name: 'navbar',
        data() {
            return {
                order:{
                    items: '',
                    userEmail: '',
                    dispatched: '0',
                    addressInformationDTO: {}
                },
                cart:[],
                cartTotal: 0,
                showLess: false,
                cartDisplaySettings: 'minder'
            }
        },
        created() {
            let cart = localStorage.getItem('cart');
            if (cart) {
                this.cart = JSON.parse(localStorage.getItem('cart'));
                this.setTotalPrice();
            }
            axios.get('api/jwt/user').then(({data}) => {
                this.order.userEmail = data.email;
                this.order.addressInformationDTO = data.addressInformation;
            });
        },
        methods: {
            logout() {
                localStorage.clear();
                location.reload();
            },
            toggleCartDisplay(){
                if (this.showLess){
                    this.cartDisplaySettings = 'minder';
                    this.showLess = false;
                }else {
                    this.cartDisplaySettings = 'meer';
                    this.showLess = true;
                }
            },
            addToCart(item){
                this.cart.push(item);
                this.saveCartToLocalStorage();
            },
            removeFromCart(index){
                this.$delete(this.cart,index);
                this.saveCartToLocalStorage();
            },
            clearCart(){
                this.$swal.fire({
                    title: 'Weet je dit zeker?',
                    text: "Je kunt dit niet terugdraaien!",
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Ja, leeg winkelwagen.'
                }).then((result) => {
                    if (result.value) {
                        this.cart = [];
                        this.setTotalPrice();
                        this.saveCartToLocalStorage();
                        this.$swal.fire(
                            'Geleegd!',
                            'Jouw winkelwagen is geleegd.',
                            'success'
                        )
                    }
                });
            },
            saveCartToLocalStorage(){
                localStorage.setItem('cart', JSON.stringify(this.cart));
                this.setTotalPrice();
            },
            setTotalPrice(){
                var total = 0;
                this.cart.forEach(e => {
                    total += e.price;
                });
                this.cartTotal = total.toFixed(2);
            },
            createOrder(){
                this.order.items = this.cart;
                console.log(this.order);
                axios.post('api/order', this.order).then(({data}) => {
                    this.cart = [];
                    this.saveCartToLocalStorage();
                    this.$swal.fire({
                        position: 'top-end',
                        type: 'success',
                        title: 'Bestelling is aangemaakt',
                        showConfirmButton: false,
                        timer: 1500
                    })

                }).catch((e) => {
                    console.log('ERROR' + e);
                })
            },
        }
    }
</script>
<style>
    body {
        padding-top: 65px;
    }
    ul.dropdown-cart{
        min-width:250px;
    }
    ul.dropdown-cart li .item{
        display:block;
        padding:3px 10px;
        margin: 3px 0;
    }
    ul.dropdown-cart li .item:hover{
        background-color:#f3f3f3;
    }
    ul.dropdown-cart li .item:after{
        visibility: hidden;
        display: block;
        font-size: 0;
        content: " ";
        clear: both;
        height: 0;
    }

    ul.dropdown-cart li .item-left{
        float:left;
    }
    ul.dropdown-cart li .item-left img,
    ul.dropdown-cart li .item-left span.item-info{
        float:left;
    }
    ul.dropdown-cart li .item-left span.item-info{
        margin-left:10px;
    }
    ul.dropdown-cart li .item-left span.item-info span{
        display:block;
    }
    ul.dropdown-cart li .item-right{
        float:right;
    }
    ul.dropdown-cart li .item-right button{
        margin-top:14px;
    }
</style>