<template>
  <div>
    <header style="display:flex;gap:1rem;align-items:center;justify-content:space-between;padding:1rem;border-bottom:1px solid #ddd">
      <h1>{{ t('title') }}</h1>
      <div>
        <select v-model="$i18n.locale">
          <option value="hi">हिन्दी</option>
          <option value="en">English</option>
        </select>
      </div>
      <nav style="padding:0.5rem 1rem; border-bottom:1px solid #eee; display:flex; gap:0.75rem; flex-wrap:wrap">
      <router-link to="/">{{ t('home') }}</router-link>
      <router-link to="/register">{{ t('register') }}</router-link>
      <router-link to="/apply">{{ t('apply') }}</router-link>
      <router-link to="/track">{{ t('track') }}</router-link>
      <router-link v-if="hasRole('OFFICER') || hasRole('ADMIN')" to="/queues">Queues</router-link>
      <router-link v-if="hasRole('OFFICER') || hasRole('ADMIN')" to="/pi">PI Scheduling</router-link>
      <router-link v-if="hasRole('ADMIN')" to="/export">CSV/Excel</router-link>
          <router-link to="/tasks">कार्य</router-link>
    </nav>
    </header>
    <ErrorBoundary>
      <router-view />
    </ErrorBoundary>
  </div>
</template>
<script setup>
import { useI18n } from 'vue-i18n'
import ErrorBoundary from './components/ErrorBoundary.vue'
const { t } = useI18n()
</script>
