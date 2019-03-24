<template>
    <div>
<h1>secured Web Application</h1>
    <a href="<%=request.getContextPath() %>/secure/index.jsp" >go to secured page</a>
    <br/><br/><br/>
      
    <div class="register">
    <form @submit.prevent="registerSubmit()">
    <fieldset>
        <legend>Register</legend>
    first name:<br>
    <input type="text" name="name" v-model="register.firstName" value="Alex">
    <br>
    last name:<br>
    <input type="text" name="name" v-model="register.lastName" value="Kir">
    <br>
    email:<br>
    <input type="text" name="name" v-model="register.email" value="@testing.com">
    <br>
    password:<br>
    <input type="password" name="password1" v-model="register.password1" value="">
    <br>
    Repeat password:<br>
    <input type="password" name="password2" v-model="register.password2" value="">
    <br><br>
    <input type="submit" value="UserDTO"></fieldset></form></div>
       
    <br/><br/><br/>
     
    <div class="login">
         <form @submit.prevent="loginSubmit()">
            <fieldset>
              <legend>Login</legend>
                  
              <div>
                <label for="email">Email</label>
                <input type="text" id="email" name="email" v-model="login.email"/>
              </div>
              <div>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" v-model="login.password"/>
              </div>
                  
              <div class="buttonRow">
                <input type="submit" value="Login" />
              </div>
                
            </fieldset>
          </form>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                register: {
                    firstName: null,
                    lastName: null,
                    email: null,
                    password1: null,
                    password2: null
                },
                login: {
                    email: null,
                    password: null
                }
            }
        },
        methods: {
            registerSubmit() {
                axios.post('/1/api/auth/register', this.register).then((response) => {
                    if(response.status == 200) {
                        window.location.href = '/1/secured';
                    }
                })
            },
            loginSubmit() {
                axios.post('/1/api/auth/login', this.login).then((response) => {
                    if(response.status == 200) {
                        window.location.href = '/1/admin';
                    }
                    else {
                        alert('Incorrect credentials!');
                    }
                })
            }
        }
    }
</script>