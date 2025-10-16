<template>
  <section style="padding:1rem;max-width:520px">
    <h2>{{ $t('apply') }}</h2>
    <form @submit.prevent="create">
      <label>{{ $t('scheme') }} 
        <select v-model="scheme" required>
          <option>Scheme-I</option>
          <option>Scheme-III</option>
          <option>Scheme-IV</option>
          <option>Scheme-X</option>
        </select>
      </label><br/>
      <label>{{ $t('isNumber') }} <input v-model="isNumber" required/></label><br/>
      <label>{{ $t('product') }} <input v-model="product" required/></label><br/>
      <label>User ID <input v-model.number="userId" required type="number"/></label><br/>
      <button type="submit" :disabled="loading">{{ loading ? 'Submitting…' : $t('createApp') }}</button>
    </form>
    <p v-if="loading" role="status">Creating application…</p>
    <div v-if="error" role="alert" style="color:#b30000">{{ error }}</div>
    <pre v-if="resp">{{ resp }}</pre>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const scheme = ref('Scheme-I')
const isNumber = ref('IS 1234')
const product = ref('Sample Product')
const userId = ref(1)
const resp = ref('')
const loading = ref(false)
const error = ref('')
async function create(){
  loading.value = true
  error.value = ''
  resp.value = ''
  try {
    const r = await axios.post('http://localhost:8080/api/applications/create',{scheme:scheme.value,isNumber:isNumber.value,product:product.value,userId:userId.value})
    resp.value = JSON.stringify(r.data,null,2)
  } catch (err) {
    const message = err?.response?.data?.message || err?.message || 'Unable to create the application.'
    error.value = message
  } finally {
    loading.value = false
  }
}
</script>
