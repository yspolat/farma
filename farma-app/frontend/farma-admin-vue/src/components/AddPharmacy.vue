<template>
  <div class="submitform">
    <div v-if="!submitted">
        <div class="form-group">
          <label for="name">Pharmacy Name: </label>
          <input type="text" class="form-control" id="name" required v-model="pharmacy.name" name="name">
        </div>
        <div class="form-group">
            <label for="country">Country : </label>
            <select v-model="selected_count">
                <option v-for="country in countries" v-bind:value="{ id: country.id, text: country.name }">
                  {{ country.name }}
                </option>
            </select>
        </div>  
        <div class="form-group">
            <label for="province">Province : </label>
            <select v-model="selected_prov">
                <option v-for="province in provinces" v-bind:value="{ id: province.id, text: province.name }">
                  {{ province.name }}
                </option>
            </select>
        </div>         
          <div class="form-group">
            <label for="district">District : </label>
            <select v-model="selected_dist">
                <option v-for="district in districts" v-bind:value="{ id: district.id, text: district.name }">
                  {{ district.name }}
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
    selected_dist: '',
    districts: [
      {id: 1, name: 'Adalar'},
      {id: 2, name: 'Arnavutköy'},
      {id: 3, name: 'Ataşehir'}
    ],
    countries: [
      {id: 1, name: 'Turkey'}
    ],
    selected_count: '',
    provinces: [
      {id: 1, name: 'Istanbul'}
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
        district: this.selected_dist.id,
        country: this.selected_count.id,
        province: this.selected_prov.id
      };
 
      http
        .post("/pharmacy", data)
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