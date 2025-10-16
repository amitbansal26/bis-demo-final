<template>
  <section style="padding:1rem">
    <h2>CSV Export (Admin: admin/admin)</h2>
    <a :href="csvUrl" target="_blank">Download Applications CSV</a>
    <p>When prompted by browser, supply Basic Auth credentials.</p>
  </section>
</template>
<script setup>
const csvUrl = 'http://admin:admin@localhost:8080/api/admin/export/applications.csv'
</script>
