<template>
  <div class="app-shell">
    <header>
      <h1>{{ t('title') }}</h1>
      <div class="app-shell">
        <select v-model="$i18n.locale">
          <option value="hi">हिन्दी</option>
          <option value="en">English</option>
        </select>
      </div>
      <nav>
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
import { hasRole } from './plugins/auth'
import ThemeToggle from './components/ThemeToggle.vue'
import ErrorBoundary from './components/ErrorBoundary.vue'

const { t } = useI18n()
</script>
