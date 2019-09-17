import Vue from "vue";
import Router from "vue-router";
import PharmaciesList from "./components/PharmaciesList.vue";
import AddPharmacy from "./components/AddPharmacy.vue"
import AddPharmacist from "./components/AddPharmacist.vue"
import Pharmacy from "./components/Pharmacy.vue"
 
Vue.use(Router);
 
export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "pharmacies",
      alias: "/pharmacy",
      component: PharmaciesList,
      children: [
        {
          path: "/pharmacy/:id",
          name: "pharmacy-details",
          component: Pharmacy,
          props: true
        }
      ]
    },
    {
      path: "/add-user",
      name: "add-user",
      component: AddPharmacy
    },
    {
      path: "/add-pharmacist",
      name: "add-pharmacist",
      component: AddPharmacist
    }
  ]
});