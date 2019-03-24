<template>
    <button @click="logout">Logout</button>
</template>

<script>
    export default {
        methods: {
            logout() {
                axios.post('/1/api/auth/logout', this.form).then((response) => {
                    window.location.href = '/1/home';
                })
            }
        }
    }
</script>