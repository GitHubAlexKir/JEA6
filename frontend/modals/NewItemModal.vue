<template>
    <div class="modal fade" id="itemModal" tabindex="-1" role="dialog" aria-labelledby="ItemModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="itemModalLongTitle">Maak item aan</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">X</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form @submit.prevent="save()">
                        <div class="form-group row">
                            <label for="productName" class="col-md-4 col-form-label text-md-right">Naam</label>
                            <div class="col-md-6">
                                <input type="text" v-model="item.productName" id="productName" class="form-control" name="productName" required autofocus>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="price" class="col-md-4 col-form-label text-md-right">Prijs</label>
                            <div class="col-md-6">
                                <input type="number" min="0.01" step="0.01" id="price" class="form-control" v-model="item.price" name="price" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="productNumber" class="col-md-4 col-form-label text-md-right">Productnummer</label>
                            <div class="col-md-6">
                                <input type="number" id="productNumber" class="form-control" v-model="item.productNumber" name="productNumber" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="stock" class="col-md-4 col-form-label text-md-right">Voorraad</label>
                            <div class="col-md-6">
                                <input type="number" id="stock" class="form-control" v-model="item.stock" name="stock" required>
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
                item:{
                    productNumber: null,
                    price: null,
                    productName: null,
                    stock: null
                }
            }
        },

        methods: {
            save() {
                axios.post('api/item', this.item).then((response) => {
                    let timerInterval;
                    this.$swal.fire({
                        title: 'Item is aangemaakt!',
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

            beforeOpen() {

            },

            beforeClose() {

            }
        }
    }
</script>