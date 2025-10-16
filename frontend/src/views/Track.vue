<template>
  <section style="padding:1rem">
    <h2>{{ $t('track') }}</h2>
    <label>User ID <input v-model.number="userId" type="number"/></label>
    <button @click="load" :disabled="loading">{{ loading ? 'Loading…' : 'Load' }}</button>
    <p v-if="loading" role="status">Fetching applications…</p>
    <div v-if="error" role="alert" style="color:#b30000">{{ error }}</div>
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
const loading = ref(false)
const error = ref('')
async function load(){
  loading.value = true
  error.value = ''
  try {
    const r = await axios.get('http://localhost:8080/api/applications/'+userId.value)
    apps.value = r.data
  } catch (err) {
    apps.value = []
    const message = err?.response?.data?.message || err?.message || 'Could not load applications.'
    error.value = message
  } finally {
    loading.value = false
  }
}
</script>
