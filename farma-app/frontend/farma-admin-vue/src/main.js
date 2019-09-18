import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'jquery';
import 'popper.js';
import 'bootstrap';
import './assets/app.scss';
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
