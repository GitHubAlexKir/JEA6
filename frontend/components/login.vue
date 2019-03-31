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
        }
        }
    }
</script>