<template>
    <div class="list row">
        <div class="col-md-6">
            <h4>Pharmacies List</h4>
            <ul>
                <li v-for="(pharmacy, index) in pharmacies" :key="index">
                    <router-link :to="{
                            name: 'pharmacy-details',
                            params: { pharmacy: pharmacy, id: pharmacy.id }
                        }">
                            {{pharmacy.name}}
                    </router-link>
                </li>
            </ul>
        </div>
        <div class="col-md-6">
            <router-view @refreshData="refreshList"></router-view>
        </div>
    </div>
</template>
 
<script>
import http from "../http-common";
 
export default {
  name: "pharmacies-list",
  data() {
    return {
      pharmacies: []
    };
  },
  methods: {
    /* eslint-disable no-console */
    retrievePharmacies() {
      http
        .get("/pharmacy")
        .then(response => {
          this.pharmacies = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrievePharmacies();
    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.retrievePharmacies();
  }
};
</script>
 
<style>
.list {
  text-align: left;
  max-width: 450px;
  margin: auto;
}
</style>