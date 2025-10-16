<template>
  <main style="padding:1rem">
    <p><router-link to="/register">{{ $t('register') }}</router-link> | 
       <router-link to="/apply">{{ $t('apply') }}</router-link> | 
       <router-link to="/track">{{ $t('track') }}</router-link> | <router-link to="/upload">Upload</router-link> | <router-link to="/payments">Payments</router-link> | <router-link to="/officer">Officer</router-link> | <router-link to="/export">CSV</router-link> | <router-link to="/map">GIS</router-link> | <router-link to="/chat">Chat</router-link> | <router-link to="/queues">Queues</router-link> | <router-link to="/pi">PI Scheduling</router-link> | <router-link to="/apply-scheme-i">GoL Form</router-link> | <router-link to="/apply-scheme-iv">CoC Form</router-link> | <router-link to="/apply-scheme-x">OTR Form</router-link></p>
  </main>
</template>
