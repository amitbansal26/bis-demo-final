
<template>
  <section style="padding:1rem">
    <h2>कार्य सूची (Tasks)</h2>
    <div style="margin-bottom:0.5rem">
      <button @click="load">Reload</button>
    </div>
    <table v-if="tasks.length" border="1" cellpadding="6">
      <thead><tr><th>ID</th><th>Name</th><th>Assignee</th><th>Action</th></tr></thead>
      <tbody>
        <tr v-for="t in tasks" :key="t.id">
          <td>{{ t.id }}</td><td>{{ t.name }}</td><td>{{ t.assignee }}</td>
          <td><button @click="complete(t.id)">Complete</button></td>
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
async function load(){
  const r = await axios.get('http://localhost:8085/workflow/tasks')
  tasks.value = r.data || []
}
async function complete(id){
  await axios.post('http://localhost:8085/workflow/complete/'+id)
  await load()
}
load()
</script>
