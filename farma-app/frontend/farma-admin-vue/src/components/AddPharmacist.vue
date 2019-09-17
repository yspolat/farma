<template>
  <div class="submitform">
    <div v-if="!submitted">
        <div class="form-group">
          <label for="name">Pharmacist Name: </label>
          <input type="text" class="form-control" id="name" required v-model="pharmacy.name" name="name">
        </div>
        <div class="form-group">
          <label for="name">Pharmacist Lastname: </label>
          <input type="text" class="form-control" id="lastName" required v-model="pharmacy.lastName" name="lastName">
        </div>
        <div class="form-group">
          <label for="name">E-mail </label>
          <input type="text" class="form-control" id="email" required v-model="pharmacy.email" name="email">
        </div>
        <div class="form-group">
          <label for="name">Password: </label>
          <input type="text" class="form-control" id="password" required v-model="pharmacy.password" name="password">
        </div>
        <div class="form-group">
            <label for="country">Pharmacy : </label>
            <select v-model="selected_pharm">
                <option v-for="pharmacy in pharmacies" v-bind:value="{ id: pharmacy.id, text: pharmacy.name }">
                  {{ pharmacy.name }}
                </option>
            </select>
        </div>                
        <button v-on:click="savePharmacy" class="btn btn-success">Add</button>
    </div>
    
    <div v-else>
      <h4>You successfully created pharmacy!</h4>
      <button class="btn btn-success" v-on:click="newPharmacy">Add</button>
    </div>
  </div>
</template>
 
<script>
import http from "../http-common";
 
export default {
  name: "add-pharmacy",
  data() {
    return {
    selected_pharm: '',
    pharmacies: [
      {id: 1, name: 'CENTRAL PHARMACY'},
      {id: 2, name: 'MIDTOWN PHARMACY'}
    ],
    selected_prov: '',
      pharmacy: {
        id: 0,
        name: "",
        age: 0,
        active: false
      },
      submitted: false
    };
  },
  methods: {
    savePharmacy() {
      var data = {
        name: this.pharmacy.name,
        lastName: this.pharmacy.lastName,
        password: this.pharmacy.password,
        email: this.pharmacy.email,
        pharmacy: this.selected_pharm.id
      };
 
      http
        .post("/pharmacist", data)
        .then(response => {
          this.pharmacy.id = response.data.id;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
 
      this.submitted = true;
    },
    newPharmacy() {
      this.submitted = false;
      this.pharmacy = {};
    }
  }
};
</script>
 
<style>
.submitform {
  max-width: 300px;
  margin: auto;
}
</style>