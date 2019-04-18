<template>
    <div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="reviewModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="itemModalLongTitle">Maak review aan</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">X</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form @submit.prevent="save()">
                        <div class="form-group row">
                            <label for="appreciation" class="col-md-4 col-form-label text-md-right">Waardering (1 t/m 5)</label>
                            <div class="col-md-6">
                                <input type="number" v-model="review.appreciation" id="appreciation" class="form-control" name="appreciation" required autofocus>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="content" class="col-md-4 col-form-label text-md-right">Beschrijving</label>
                            <div class="col-md-6">
                                <input type="text" id="content" class="form-control" v-model="review.content" name="content" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">
                                Maak item aan
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Sluiten</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from '../axios';
    export default {
        data() {
            return {
                width: "30%",
                height: "90%",
                maxWidth: 420,
                itemId: null,
                review:{
                    author: null,
                    content: null,
                    appreciation: null
                }
            }
        },
        created() {
            axios.get('api/jwt/user').then(({data}) => {
                this.review.author = data.email;
            });
        }
        ,
        methods: {
            save() {
                axios.post('api/item/review/' + this.itemId, this.review).then((response) => {
                    let timerInterval;
                    this.$swal.fire({
                        title: 'Review is aangemaakt!',
                        html: 'pagina wordt herladen',
                        timer: 1000,
                        onBeforeOpen: () => {
                            this.$swal.showLoading();
                            timerInterval = setInterval(() => {
                                this.$swal.getContent().querySelector('strong')
                                    .textContent = Swal.getTimerLeft()
                            }, 100)
                        },
                        onClose: () => {
                            clearInterval(timerInterval);
                            location.reload();
                        }
                    }).then((result) => {
                        if (
                            // Read more about handling dismissals
                            result.dismiss === Swal.DismissReason.timer
                        ) {
                            console.log('I was closed by the timer')
                        }
                    });
                });
            },

            beforeOpen(e) {
                console.log(e);
            },
            setItem(item){
                this.itemId = item.id;
            },

            beforeClose() {

            }
        }
    }
</script>