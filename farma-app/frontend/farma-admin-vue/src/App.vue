<template>
    <div id="app" class="container-fluid">
        <div class="site-info">
            <h3>FARMA Admin Panel</h3>
        </div>
        <nav>
            <router-link class="btn btn-primary" to="/">All Pharmacies</router-link>
            <router-link class="btn btn-primary" to="/add-user">Add Pharmacy</router-link>
            <router-link class="btn btn-primary" to="/add-pharmacist">Add Pharmacist</router-link>
        </nav>
        <br/>
        <router-view/>
    </div>
</template>
 
<script>
export default {
  name: "app"
};
</script>
 
<style>
.site-info {
  color: black;
  margin-bottom: 20px;
}
 
.btn-primary {
  margin-right: 15px;
}
 
.container-fluid {
  text-align: center;
}
</style>