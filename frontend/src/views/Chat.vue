<template>
  <section style="padding:1rem;max-width:520px">
    <h2>In-app Chat</h2>
    <input v-model="from" placeholder="Name" />
    <input v-model="text" placeholder="Message" @keyup.enter="send" />
    <button @click="send">Send</button>
    <ul>
      <li v-for="(m,i) in msgs" :key="i"><strong>{{ m.from }}:</strong> {{ m.text }}</li>
    </ul>
  </section>
</template>
<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'

const from = ref('user')
const text = ref('')
const msgs = ref([])
let client = null

onMounted(()=>{
  client = new Client({
    webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
    reconnectDelay: 2000
  })
  client.onConnect = () => {
    client.subscribe('/topic/messages', (msg)=>{
      msgs.value.push(JSON.parse(msg.body))
    })
  }
  client.activate()
})
onBeforeUnmount(()=> client && client.deactivate())

function send(){
  if(!text.value) return
  client.publish({ destination:'/app/chat', body: JSON.stringify({from: from.value, text: text.value}) })
  text.value = ''
}
</script>
