<template>
  <section style="padding:1rem;max-width:520px">
    <h2>Upload + AV Scan (stub)</h2>
    <input type="file" @change="onFile">
    <button :disabled="!file || loading" @click="send">{{ loading ? 'Uploading…' : 'Upload' }}</button>
    <p v-if="loading" role="status">Uploading file and waiting for scan result…</p>
    <div v-if="error" role="alert" style="color:#b30000">{{ error }}</div>
    <pre v-if="resp">{{ resp }}</pre>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const file = ref(null)
const resp = ref('')
const error = ref('')
const loading = ref(false)
function onFile(e){
  file.value = e.target.files[0]
  error.value = ''
  resp.value = ''
}
async function send(){
  if (!file.value) return
  const fd = new FormData()
  fd.append('file', file.value)
  loading.value = true
  error.value = ''
  resp.value = ''
  try {
    const r = await axios.post('http://localhost:8080/api/public/upload', fd, { headers: {'Content-Type':'multipart/form-data'} })
    resp.value = JSON.stringify(r.data, null, 2)
  } catch (err) {
    const message = err?.response?.data?.message || err?.message || 'Upload failed. Please try again.'
    error.value = message
  } finally {
    loading.value = false
  }
}
</script>
