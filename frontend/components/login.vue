<template>
    <div>
        <div  class="login-form">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">Login</div>
                        <div class="card-body">
                            <form @submit.prevent="loginSubmit()">
                                                <div class="form-group row">
                                                    <label for="email" class="col-md-4 col-form-label text-md-right">E-Mail Address</label>
                                                    <div class="col-md-6">
                                                        <input type="text" v-model="UserLogin.email" id="email" class="form-control" name="email" required autofocus>
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label for="password" class="col-md-4 col-form-label text-md-right">Password</label>
                                                    <div class="col-md-6">
                                                        <input type="password" id="password" class="form-control" v-model="UserLogin.password" name="password" required>
                                                        <p v-if="wrong">Login gegevens zijn onjuist.</p>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 offset-md-4">
                                                    <button type="submit" class="btn btn-primary">
                                                        Login
                                                    </button>
                                                </div>
                                            </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div  class="login-form">
                                <div class="row justify-content-center">
                                    <div class="col-md-8">
                                        <div class="card">
                                            <div class="card-header">Register</div>
                                            <div class="card-body">
                                            <form @submit.prevent="registerSubmit()">
                                                <div class="form-group row">
                                                    <label for="email" class="col-md-4 col-form-label text-md-right">E-Mail</label>
                                                    <div class="col-md-6">
                                                        <input type="text" v-model="UserRegister.email" id="registerEmail" class="form-control" name="email" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="password" class="col-md-4 col-form-label text-md-right">Wachtwoord</label>
                                                    <div class="col-md-4">
                                                        <input type="password" id="password1" class="form-control" v-model="UserRegister.password1" name="password1" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="password" class="col-md-4 col-form-label text-md-right">Herhaal wachtwoord</label>
                                                    <div class="col-md-4">
                                                        <input type="password" id="password2" class="form-control" v-model="UserRegister.password2" name="password2" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="firstName" class="col-md-4 col-form-label text-md-right">Voornaam</label>
                                                    <div class="col-md-4">
                                                        <input type="text" id="firstName" class="form-control" v-model="UserRegister.firstName" name="firstName" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="lastName" class="col-md-4 col-form-label text-md-right">Achternaam</label>
                                                    <div class="col-md-4">
                                                        <input type="text" id="lastName" class="form-control" v-model="UserRegister.lastName" name="lastName" required>
                                                    </div>
                                                </div>
                                                <!-- AdressInformation -->
                                                <div class="form-group row">
                                                    <label for="addressee" class="col-md-4 col-form-label text-md-right">Ontvanger</label>
                                                    <div class="col-md-6">
                                                        <input type="text" v-model="UserRegister.addressInformationDTO.addressee" id="addressee" class="form-control" name="addressee" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="address" class="col-md-4 col-form-label text-md-right">adres</label>
                                                    <div class="col-md-6">
                                                        <input type="text" v-model="UserRegister.addressInformationDTO.address" id="address" class="form-control" name="address" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="zip" class="col-md-4 col-form-label text-md-right">Postcode</label>
                                                    <div class="col-md-2">
                                                        <input type="text" v-model="UserRegister.addressInformationDTO.zip" id="zip" class="form-control" name="zip" required>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="city" class="col-md-4 col-form-label text-md-right">Plaats</label>
                                                    <div class="col-md-4">
                                                        <input type="text" v-model="UserRegister.addressInformationDTO.city" id="city" class="form-control" name="city" required>
                                                    </div>
                                                </div>

                                                <div class="col-md-6 offset-md-4">
                                                    <input type="checkbox" name="checkbox" value="check" id="agree" required>
                                                    Ik heb de Algemene voorwaarden en het Privacybeleid gelezen en ga ermee akkoord.
                                                    <button type="submit" class="btn btn-primary">
                                                        Maak account aan
                                                    </button>
                                                </div>
                                            </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
    </div>
</template>

<script>
    import axios from '../axios';
    export default {
        name: 'login',
        data() {
            return {
                UserLogin: {
                    email: null,
                    password: null
                },
                UserRegister:{
                    email: null,
                    password1: null,
                    password2: null,
                    firstName: null,
                    lastName: null,
                    addressInformationDTO: {
                        addressee: null,
                        address: null,
                        zip: null,
                        city: null
                    }
                },
                wrong: false
            }
        },
        methods: {
            loginSubmit(){
                axios.post('api/jwt/login', this.UserLogin).then(({data}) => {
                        localStorage.setItem('token', data.data);
                        console.log(data.data);
                        console.log(data);
                        this.wrong = false;
                        location.reload();

                }).catch(() => {
                    this.wrong = true;
                })
            },
            registerSubmit(){
                axios.post('api/jwt/register', this.UserRegister).then(({data}) => {
                    location.reload();

                }).catch(() => {
                    this.wrong = true;
                })
            }
        }
    }
</script>