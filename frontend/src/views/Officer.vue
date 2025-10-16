<template>
  <section style="padding:1rem">
    <h2>Officer Inbox (Basic Auth: officer/officer)</h2>
    <button @click="load">Load</button>
    <table border="1" cellpadding="6">
      <thead><tr><th>ID</th><th>Scheme</th><th>Product</th><th>Status</th><th>Docs</th><th>Action</th></tr></thead>
      <tbody>
        <tr v-for="a in apps" :key="a.id">
          <td>{{ a.id }}</td><td>{{ a.scheme }}</td><td>{{ a.product }}</td><td>{{ a.status }}</td><td><button @click="loadDocs(a)">Docs</button></td>
          <td>
            <select v-model="a._next">
              <option>UNDER_SCRUTINY</option>
              <option>PI_SCHEDULED</option>
              <option>LICENSE_GRANTED</option>
              <option>REJECTED</option>
            </select>
            <button @click="act(a)">Apply</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="selectedApp" style="margin-top:1rem">
      <h3>Documents for App #{{ selectedApp.id }}</h3>
      <ul>
        <li v-for="d in docsMap[selectedApp.id] || []" :key="d.id"><a :href="d.url" target="_blank">{{ d.name }}</a> ({{ d.type }})</li>
      </ul>
    </div>

    <div style="margin-top:1rem">
      <h3>Documents</h3>
      <label>App ID <input v-model.number="docAppId" type="number"/></label>
      <button @click="docsFor">Load Docs</button>
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
async function docsFor(){ const r = await axios.get('http://localhost:8080/api/documents/'+docAppId.value); docs.value = r.data }
async function load(){
  const r = await axios.get('http://localhost:8080/api/officer/inbox', {
    auth: { username:'officer', password:'officer' }
  })
  apps.value = r.data
}
async function loadDocs(a){ const r = await axios.get('http://localhost:8080/api/documents/'+a.id); docsMap.value[a.id] = r.data; selectedApp.value = a }
async function act(a){
  const r = await axios.post('http://localhost:8080/api/officer/act', {appId:a.id, action:a._next||'UNDER_SCRUTINY', details:'via dashboard'}, {
    auth: { username:'officer', password:'officer' }
  })
  last.value = JSON.stringify(r.data, null, 2)
  await load()
}
</script>
