
<template>
  <button class="btn" @click="toggle" :aria-label="`Switch to ${nextLabel} mode`">
    <span v-if="theme === 'light'">🌞 Light</span>
    <span v-else>🌙 Dark</span>
  </button>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const theme = ref('light')
const STORAGE_KEY = 'bis.theme'

function apply(t) {
  document.documentElement.setAttribute('data-theme', t)
  theme.value = t
}

function load() {
  const saved = localStorage.getItem(STORAGE_KEY)
  if (saved === 'light' || saved === 'dark') return saved
  // Prefer system scheme on first load
  return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
}

function toggle() {
  const next = theme.value === 'light' ? 'dark' : 'light'
  localStorage.setItem(STORAGE_KEY, next)
  apply(next)
}

onMounted(() => {
  apply(load())
})

const nextLabel = computed(() => theme.value === 'light' ? 'dark' : 'light')
</script>
