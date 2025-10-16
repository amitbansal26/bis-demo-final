<template>
  <section style="padding:1rem;max-width:520px">
    <h2>Upload + AV Scan (stub)</h2>
    <input type="file" @change="onFile">
    <button :disabled="!file" @click="send">Upload</button>
    <pre v-if="resp">{{ resp }}</pre>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const file = ref(null)
const resp = ref('')
function onFile(e){ file.value = e.target.files[0] }
async function send(){
  const fd = new FormData()
  fd.append('file', file.value)
  const r = await axios.post('http://localhost:8080/api/public/upload', fd, { headers: {'Content-Type':'multipart/form-data'} })
  resp.value = JSON.stringify(r.data, null, 2)
}
</script>
