<template>
  <section style="padding:1rem;max-width:520px">
    <h2>Payments (UPI/NetBanking) - Mock</h2>
    <label>App Ref <input v-model="appRef"></label><br/>
    <label>Amount (₹) <input type="number" v-model.number="amount"></label><br/>
    <select v-model="mode"><option>UPI</option><option>NETBANKING</option></select><br/>
    <button @click="init" :disabled="initLoading">{{ initLoading ? 'Initializing…' : 'Init' }}</button>
    <p v-if="initLoading" role="status">Creating mock payment request…</p>
    <div v-if="initError" role="alert" style="color:#b30000">{{ initError }}</div>
    <div v-if="txn.txnId">
      <p>Txn: {{ txn.txnId }}</p>
      <p v-if="txn.intentUrl"><a :href="txn.intentUrl" target="_blank">Open UPI Intent</a></p>
      <button @click="poll" :disabled="pollLoading">{{ pollLoading ? 'Checking…' : 'Poll Status' }}</button>
      <p v-if="pollLoading" role="status">Checking transaction status…</p>
      <div v-if="pollError" role="alert" style="color:#b30000">{{ pollError }}</div>
      <pre>{{ status }}</pre>
    </div>
  </section>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const appRef = ref('APP-001')
const amount = ref(100.0)
const mode = ref('UPI')
const txn = ref({})
const status = ref('')
const initLoading = ref(false)
const pollLoading = ref(false)
const initError = ref('')
const pollError = ref('')
async function init(){
  initLoading.value = true
  initError.value = ''
  pollError.value = ''
  status.value = ''
  try {
    const r = await axios.post('http://localhost:8080/api/public/payments/init', {appRef:appRef.value, amountInPaise: Math.round(amount.value*100), mode: mode.value})
    txn.value = r.data
  } catch (err) {
    txn.value = {}
    const message = err?.response?.data?.message || err?.message || 'Unable to initiate payment.'
    initError.value = message
  } finally {
    initLoading.value = false
  }
}
async function poll(){
  if (!txn.value?.txnId) {
    pollError.value = 'No transaction to poll yet.'
    return
  }
  pollLoading.value = true
  pollError.value = ''
  try {
    const r = await axios.get('http://localhost:8080/api/public/payments/status/'+txn.value.txnId)
    status.value = JSON.stringify(r.data, null, 2)
  } catch (err) {
    const message = err?.response?.data?.message || err?.message || 'Unable to fetch payment status.'
    pollError.value = message
  } finally {
    pollLoading.value = false
  }
}
</script>
