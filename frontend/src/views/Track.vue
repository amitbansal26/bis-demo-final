<template>
  <section style="padding:1rem">
    <h2>{{ $t('track') }}</h2>
    <label>User ID <input v-model.number="userId" type="number"/></label>
    <button @click="load">Load</button>
    <ul>
      <li v-for="a in apps" :key="a.id">
        #{{ a.id }} - {{ a.scheme }} - {{ a.product }} - {{ a.status }}
      </li>
    </ul>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const userId = ref(1)
const apps = ref([])
async function load(){
  const r = await axios.get('http://localhost:8080/api/applications/'+userId.value)
  apps.value = r.data
}
</script>
