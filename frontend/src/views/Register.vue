<template>
  <section style="padding:1rem;max-width:480px">
    <h2>{{ $t('register') }}</h2>
    <form @submit.prevent="register">
      <label>{{ $t('name') }} <input v-model="name" required/></label><br/>
      <label>{{ $t('mobile') }} <input v-model="mobile" required/></label><br/>
      <label>{{ $t('email') }} <input v-model="email" type="email" required/></label><br/>
      <label>{{ $t('password') }} <input v-model="password" type="password" required/></label><br/>
      <button type="submit" :disabled="loading">{{ loading ? 'Submitting…' : $t('submit') }}</button>
    </form>
    <p v-if="loading" role="status">Submitting your request…</p>
    <div v-if="error" role="alert" style="color:#b30000">{{ error }}</div>
    <pre v-if="resp">{{ resp }}</pre>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const name = ref('')
const mobile = ref('')
const email = ref('')
const password = ref('')
const resp = ref('')
const loading = ref(false)
const error = ref('')
async function register() {
  loading.value = true
  error.value = ''
  resp.value = ''
  try {
    const r = await axios.post('http://localhost:8080/api/public/register',{name:name.value,mobile:mobile.value,email:email.value,password:password.value})
    resp.value = JSON.stringify(r.data,null,2)
  } catch (err) {
    const message = err?.response?.data?.message || err?.message || 'Unable to register right now. Please try again.'
    error.value = message
  } finally {
    loading.value = false
  }
}
</script>
