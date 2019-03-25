<template>
    <button @click="logout()">Logout</button>
</template>

<script>
    export default {
        methods: {
            logout() {
              localStorage.clear();
              window.location = '/1/';
            }
        }
    }
</script>