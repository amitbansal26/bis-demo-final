
<template>
  <section style="padding:1rem">
    <h2>कार्य सूची (Tasks)</h2>
    <div style="margin-bottom:0.5rem">
      <button @click="load" :disabled="loading">{{ loading ? 'Loading…' : 'Reload' }}</button>
    </div>
    <p v-if="loading" role="status">Loading tasks…</p>
    <div v-if="error" role="alert" style="color:#b30000">{{ error }}</div>
    <table v-if="tasks.length" border="1" cellpadding="6">
      <thead><tr><th>ID</th><th>Name</th><th>Assignee</th><th>Action</th></tr></thead>
      <tbody>
        <tr v-for="t in tasks" :key="t.id">
          <td>{{ t.id }}</td><td>{{ t.name }}</td><td>{{ t.assignee }}</td>
          <td>
            <button @click="complete(t.id)" :disabled="completingId === t.id">{{ completingId === t.id ? 'Completing…' : 'Complete' }}</button>
            <div v-if="actionError && actionErrorId === t.id" role="alert" style="color:#b30000">{{ actionError }}</div>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else>कोई लंबित कार्य नहीं</p>
  </section>
</template>
<script setup>
import axios from 'axios'
import { ref } from 'vue'
const tasks = ref([])
const loading = ref(false)
const error = ref('')
const completingId = ref(null)
const actionError = ref('')
const actionErrorId = ref(null)
async function load(){
  loading.value = true
  error.value = ''
  try {
    const r = await axios.get('http://localhost:8085/workflow/tasks')
    tasks.value = r.data || []
  } catch (err) {
    tasks.value = []
    const message = err?.response?.data?.message || err?.message || 'Unable to load tasks.'
    error.value = message
  } finally {
    loading.value = false
  }
}
async function complete(id){
  completingId.value = id
  actionError.value = ''
  actionErrorId.value = null
  try {
    await axios.post('http://localhost:8085/workflow/complete/'+id)
    await load()
  } catch (err) {
    const message = err?.response?.data?.message || err?.message || 'Unable to complete the task.'
    actionError.value = message
    actionErrorId.value = id
  } finally {
    completingId.value = null
  }
}
load()
</script>
