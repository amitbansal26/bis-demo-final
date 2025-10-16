<template>
  <div class="error-boundary">
    <div v-if="hasError" role="alert" class="error-boundary__message">
      <h2>Something went wrong.</h2>
      <p>{{ errorMessage }}</p>
      <button type="button" @click="reset">Try again</button>
    </div>
    <div v-else :key="renderKey" class="error-boundary__content">
      <slot />
    </div>
  </div>
</template>

<script setup>
import { onErrorCaptured, ref } from 'vue'

const hasError = ref(false)
const errorMessage = ref('')
const renderKey = ref(0)

function reset() {
  hasError.value = false
  errorMessage.value = ''
  renderKey.value += 1
}

onErrorCaptured((err) => {
  console.error('Captured render error:', err)
  errorMessage.value = err?.message || 'An unexpected error occurred.'
  hasError.value = true
  return false
})
</script>

<style scoped>
.error-boundary__message {
  border: 1px solid #f5c2c7;
  background-color: #f8d7da;
  color: #842029;
  padding: 1rem;
  border-radius: 4px;
}

.error-boundary__message button {
  margin-top: 0.5rem;
}
</style>
