<template>
  <section style="padding:1rem;max-width:520px">
    <h2>PI Scheduling</h2>
    <label>App ID <input v-model.number="appId" type="number" /></label><br/>
    <label>Inspector <input v-model="inspector" /></label><br/>
    <label>Region <input v-model="region" /></label><br/>
    <label>Date/Time <input v-model="when" type="datetime-local" /></label><br/>
    <button @click="schedule">Schedule</button>
    <pre v-if="resp">{{ resp }}</pre>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const appId = ref(1)
const inspector = ref('officer1')
const region = ref('North')
const when = ref('')
const resp = ref('')
async function schedule(){
  const epoch = when.value ? new Date(when.value).getTime() : Date.now()
  const r = await axios.post('http://localhost:8080/api/officer/pi/schedule', {appId: appId.value, inspector: inspector.value, region: region.value, epochMillis: epoch})
  resp.value = JSON.stringify(r.data, null, 2)
}
</script>
