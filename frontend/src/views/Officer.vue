<template>
  <section style="padding:1rem">
    <h2>Officer Inbox (Basic Auth: officer/officer)</h2>
    <button @click="load" :disabled="inboxLoading">{{ inboxLoading ? 'Loading…' : 'Load' }}</button>
    <p v-if="inboxLoading" role="status">Loading inbox…</p>
    <div v-if="inboxError" role="alert" style="color:#b30000">{{ inboxError }}</div>
    <table border="1" cellpadding="6">
      <thead><tr><th>ID</th><th>Scheme</th><th>Product</th><th>Status</th><th>Docs</th><th>Action</th></tr></thead>
      <tbody>
        <tr v-for="a in apps" :key="a.id">
          <td>{{ a.id }}</td><td>{{ a.scheme }}</td><td>{{ a.product }}</td><td>{{ a.status }}</td><td><button @click="loadDocs(a)" :disabled="docsLoadingId === a.id">Docs</button> <span v-if="docsLoadingId === a.id" role="status">Loading…</span></td>
          <td>
            <select v-model="a._next">
              <option>UNDER_SCRUTINY</option>
              <option>PI_SCHEDULED</option>
              <option>LICENSE_GRANTED</option>
              <option>REJECTED</option>
            </select>
            <button @click="act(a)" :disabled="actingId === a.id">{{ actingId === a.id ? 'Applying…' : 'Apply' }}</button>
            <div v-if="actionError && actionErrorId === a.id" role="alert" style="color:#b30000">{{ actionError }}</div>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="selectedApp" style="margin-top:1rem">
      <h3>Documents for App #{{ selectedApp.id }}</h3>
      <div v-if="docsError" role="alert" style="color:#b30000">{{ docsError }}</div>
      <ul>
        <li v-for="d in docsMap[selectedApp.id] || []" :key="d.id"><a :href="d.url" target="_blank">{{ d.name }}</a> ({{ d.type }})</li>
      </ul>
    </div>

    <div style="margin-top:1rem">
      <h3>Documents</h3>
      <label>App ID <input v-model.number="docAppId" type="number"/></label>
      <button @click="docsFor" :disabled="docsForLoading">{{ docsForLoading ? 'Loading…' : 'Load Docs' }}</button>
      <p v-if="docsForLoading" role="status">Fetching documents…</p>
      <div v-if="docsForError" role="alert" style="color:#b30000">{{ docsForError }}</div>
      <ul>
        <li v-for="d in docs" :key="d.id"><a :href="d.url" target="_blank">{{ d.name }}</a> ({{ d.type }})</li>
      </ul>
    </div>

    <pre v-if="last">{{ last }}</pre>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const apps = ref([])
const last = ref('')
const docsMap = ref({})
const selectedApp = ref(null)
const docAppId = ref(1)
const docs = ref([])
const inboxLoading = ref(false)
const inboxError = ref('')
const docsLoadingId = ref(null)
const docsError = ref('')
const docsForLoading = ref(false)
const docsForError = ref('')
const actingId = ref(null)
const actionError = ref('')
const actionErrorId = ref(null)
async function docsFor(){
  docsForLoading.value = true
  docsForError.value = ''
  docsError.value = ''
  try {
    const r = await axios.get('http://localhost:8080/api/documents/'+docAppId.value)
    docs.value = r.data
  } catch (err) {
    docs.value = []
    const message = err?.response?.data?.message || err?.message || 'Unable to fetch documents.'
    docsForError.value = message
  } finally {
    docsForLoading.value = false
  }
}
async function load(){
  inboxLoading.value = true
  inboxError.value = ''
  try {
    const r = await axios.get('http://localhost:8080/api/officer/inbox', {
      auth: { username:'officer', password:'officer' }
    })
    apps.value = r.data
  } catch (err) {
    apps.value = []
    selectedApp.value = null
    docsMap.value = {}
    const message = err?.response?.data?.message || err?.message || 'Unable to load inbox.'
    inboxError.value = message
  } finally {
    inboxLoading.value = false
  }
}
async function loadDocs(a){
  docsLoadingId.value = a.id
  docsError.value = ''
  selectedApp.value = a
  try {
    const r = await axios.get('http://localhost:8080/api/documents/'+a.id)
    docsMap.value = { ...docsMap.value, [a.id]: r.data }
  } catch (err) {
    docsMap.value = { ...docsMap.value, [a.id]: [] }
    const message = err?.response?.data?.message || err?.message || 'Unable to load documents.'
    docsError.value = message
  } finally {
    docsLoadingId.value = null
  }
}
async function act(a){
  actingId.value = a.id
  actionError.value = ''
  actionErrorId.value = null
  try {
    const r = await axios.post('http://localhost:8080/api/officer/act', {appId:a.id, action:a._next||'UNDER_SCRUTINY', details:'via dashboard'}, {
      auth: { username:'officer', password:'officer' }
    })
    last.value = JSON.stringify(r.data, null, 2)
    await load()
  } catch (err) {
    last.value = ''
    const message = err?.response?.data?.message || err?.message || 'Unable to update the application.'
    actionError.value = message
    actionErrorId.value = a.id
  } finally {
    actingId.value = null
  }
}
</script>
