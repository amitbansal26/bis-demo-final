<template>
  <section style="padding:1rem;max-width:520px">
    <h2>Payments (UPI/NetBanking) - Mock</h2>
    <label>App Ref <input v-model="appRef"></label><br/>
    <label>Amount (₹) <input type="number" v-model.number="amount"></label><br/>
    <select v-model="mode"><option>UPI</option><option>NETBANKING</option></select><br/>
    <button @click="init">Init</button>
    <div v-if="txn.txnId">
      <p>Txn: {{ txn.txnId }}</p>
      <p v-if="txn.intentUrl"><a :href="txn.intentUrl" target="_blank">Open UPI Intent</a></p>
      <button @click="poll">Poll Status</button>
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
async function init(){
  const r = await axios.post('http://localhost:8080/api/public/payments/init', {appRef:appRef.value, amountInPaise: Math.round(amount.value*100), mode: mode.value})
  txn.value = r.data
}
async function poll(){
  const r = await axios.get('http://localhost:8080/api/public/payments/status/'+txn.value.txnId)
  status.value = JSON.stringify(r.data, null, 2)
}
</script>
