<template>
  <section style="padding:1rem">
    <h2>GIS Widget (Leaflet)</h2>
    <div id="map" style="height: 420px;"></div>
  </section>
</template>
<script setup>
import { onMounted } from 'vue'
import * as L from 'leaflet'
import 'leaflet/dist/leaflet.css'
onMounted(()=>{
  const map = L.map('map').setView([26.8467, 80.9462], 12) // Lucknow default
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
  }).addTo(map)
  L.marker([26.8467,80.9462]).addTo(map).bindPopup('Sample Location')
})
</script>
