<template>
  <section style="padding:1rem">
    <h2>Officer Queues</h2>
    <label>Scheme
      <select v-model="scheme">
        <option value="">All</option>
        <option>Scheme-I</option>
        <option>Scheme-III</option>
        <option>Scheme-IV</option>
        <option>Scheme-X</option>
      </select>
    </label>
    <button @click="load" :disabled="loading">{{ loading ? 'Loading…' : 'Load' }}</button>
    <p v-if="loading" role="status">Fetching queue…</p>
    <div v-if="error" role="alert" style="color:#b30000">{{ error }}</div>
    <table border="1" cellpadding="6" style="margin-top:1rem">
      <thead><tr><th>ID</th><th>Scheme</th><th>Product</th><th>Status</th></tr></thead>
      <tbody>
        <tr v-for="a in apps" :key="a.id"><td>{{ a.id }}</td><td>{{ a.scheme }}</td><td>{{ a.product }}</td><td>{{ a.status }}</td></tr>
      </tbody>
    </table>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const scheme = ref('')
const apps = ref([])
const loading = ref(false)
const error = ref('')
async function load(){
  loading.value = true
  error.value = ''
  try {
    const r = await axios.get('http://localhost:8080/api/officer/queue', { params: { scheme: scheme.value || null }})
    apps.value = r.data
  } catch (err) {
    apps.value = []
    const message = err?.response?.data?.message || err?.message || 'Unable to load queues.'
    error.value = message
  } finally {
    loading.value = false
  }
}
</script>
