import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Register from '../views/Register.vue'
import Apply from '../views/Apply.vue'
import Track from '../views/Track.vue'
import Upload from '../views/Upload.vue'
import Payments from '../views/Payments.vue'
import Officer from '../views/Officer.vue'
import ExportCsv from '../views/ExportCsv.vue'
import MapView from '../views/MapView.vue'
import Chat from '../views/Chat.vue'
import Tasks from '../views/Tasks.vue'
import OfficerQueues from '../views/OfficerQueues.vue'
import PISchedule from '../views/PISchedule.vue'
import ApplySchemeI from '../views/ApplySchemeI.vue'
import ApplySchemeIV from '../views/ApplySchemeIV.vue'
import ApplySchemeX from '../views/ApplySchemeX.vue'
import { useKeycloak, hasRole } from '../plugins/auth'

const routes = [
  { path: '/tasks', component: Tasks, meta: { requiresAuth: true } },
  { path: '/queues', component: OfficerQueues, meta: { requiresAuth: true, roles: ['OFFICER', 'ADMIN'] } },
  { path: '/pi', component: PISchedule, meta: { requiresAuth: true, roles: ['OFFICER', 'ADMIN'] } },
  { path: '/apply-scheme-i', component: ApplySchemeI },
  { path: '/apply-scheme-iv', component: ApplySchemeIV },
  { path: '/apply-scheme-x', component: ApplySchemeX },
  { path: '/upload', component: Upload },
  { path: '/payments', component: Payments },
  { path: '/officer', component: Officer, meta: { requiresAuth: true, roles: ['OFFICER', 'ADMIN'] } },
  { path: '/export', component: ExportCsv, meta: { requiresAuth: true, roles: ['ADMIN'] } },
  { path: '/map', component: MapView },
  { path: '/chat', component: Chat },
  { path: '/', component: Home },
  { path: '/register', component: Register },
  { path: '/apply', component: Apply },
  { path: '/track', component: Track }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const kc = useKeycloak()
  if (to.meta && to.meta.requiresAuth) {
    if (!kc.authenticated) {
      kc.login({ redirectUri: window.location.origin + to.fullPath })
      return
    }
    const roles = to.meta.roles || []
    if (roles.length && !roles.some(r => hasRole(r))) {
      return next('/') // insufficient role
    }
  }
  next()
})

export default router
