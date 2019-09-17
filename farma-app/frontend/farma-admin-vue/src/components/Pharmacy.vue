<template>
  <div v-if="this.pharmacy">
    <h4>Pharmacy</h4>
    <div>
      <label>Pharmacy ID: </label> {{this.pharmacy.id}}
    </div>
    <div>
      <label>Pharmacy Name: </label> {{this.pharmacy.name}}
    </div>
    <div>
      <label>Active: </label> {{this.pharmacy.active}}
    </div>
    <span v-if="this.pharmacy.active"
      v-on:click="updateActive(false)"
      class="button is-small btn-primary">Inactive</span>
    <span v-else
      v-on:click="updateActive(true)"
      class="button is-small btn-primary">Active</span>  
  </div>
  <div v-else>
    <br/>
    <p>Please click on a pharmacy...</p>
  </div>
</template>
 
<script>
import http from "../http-common";
 
export default {
  name: "pharmacy",
  props: ["pharmacy"],
  methods: {
    /* eslint-disable no-console */
    updateActive(status) {
      var data = {
        id: this.pharmacy.id,
        name: this.pharmacy.name,
        active: status
      };
 
      http
        .put("/pharmacy/" + this.pharmacy.id, data)
        .then(response => {
          this.pharmacy.active = response.data.active;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>