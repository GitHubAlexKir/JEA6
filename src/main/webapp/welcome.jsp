<html>
<body>
<b-container id="app">
    <b-row>
        <b-col>
            <b-navbar
                    toggleable="md"
                    type="dark"
                    variant="info">
                <b-navbar-brand href="#">Alcohol Gigant</b-navbar-brand>
               <!-- <b-navbar-nav>
                    <b-nav-item :to="{ name: 'home' }">home</b-nav-item>
                    <b-nav-item :to="{ name: 'login' }">login</b-nav-item>
                </b-navbar-nav> -->
            </b-navbar>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <router-view/>
        </b-col>
    </b-row>
</b-container>
<script src="./js/app.js"></script>
</body>
</html>