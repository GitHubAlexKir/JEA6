<template>
    <div>
        <navbar ref="navbar"></navbar>
        <div>
            <div class="md-primary">
                <div class="md-toolbar-row">
                    <span class="md-title">Chat</span>
                </div>
            </div>
            <div>
                <div>
                    <label>Berichten</label>
                    <div v-for="message in messages">
                        <h6><i>{{message.received}}</i> <b>{{message.sender}}</b>:{{message.content}}</h6>
                    </div>
                </div>
                <div>
                    <label for="addressee">Your message</label>
                    <input type="text" v-model="message.content" id="addressee" class="form-control" name="addressee" required>
                    <button class="md-primary md-raised" v-on:click="sendMessage()">Versturen</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Navbar from "./navbar";
    import axios from '../axios';
    export default {
        name: 'Chat',
        components:{ Navbar},
        data () {
            return {
                messages:[],
                message: {
                    sender: 'testing',
                    received: '',
                    content: ''
                }
            }
        },
        mounted(){
            axios.get('api/jwt/user').then(({data}) => {
                this.message.sender = data.email;
            });
            this.connect();
        },
        methods: {
            connect() {
                this.socket = new WebSocket("ws://localhost:8080/webshop/chat");
                this.socket.onopen = () => {
                    this.status = "connected";
                    this.messages.push({
                            sender: 'server',
                            received: '',
                            content: 'Connected to ws://localhost:8080/webshop/chat'
                    });

                    this.socket.onmessage = ({data}) => {
                        this.messages.push(JSON.parse(data));
                    };
                };
            },
            disconnect() {
                this.socket.close();
            },
            sendMessage() {
                this.socket.send(JSON.stringify(this.message));
                this.message = {
                    sender: this.message.sender,
                    received: '',
                    content: ''
                };
            }
        }
    }
</script>