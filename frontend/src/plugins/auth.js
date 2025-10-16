import Keycloak from 'keycloak-js'
import axios from 'axios'

const keycloak = new Keycloak({
  url: import.meta.env.VITE_KC_URL || 'http://localhost:8081',
  realm: import.meta.env.VITE_KC_REALM || 'bis-demo',
  clientId: import.meta.env.VITE_KC_CLIENT || 'bis-frontend'
})

let initialized = false

export async function initKeycloak(){
  if (initialized) return keycloak
  try {
    await keycloak.init({ onLoad: 'check-sso', pkceMethod: 'S256', silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html' })
    axios.interceptors.request.use(async (config) => {
      if (keycloak.authenticated) {
        if (keycloak.isTokenExpired(30)) await keycloak.updateToken(60)
        config.headers = config.headers || {}
        config.headers['Authorization'] = `Bearer ${keycloak.token}`
      }
      return config
    })
    initialized = true
  } catch (e) {
    console.warn('Keycloak init failed', e)
  }
  return keycloak
}

export function useKeycloak(){ return keycloak }


export function tokenRoles(){
  try{
    const tp = (keycloak && keycloak.tokenParsed) ? keycloak.tokenParsed : null
    const realm = (tp && tp.realm_access && tp.realm_access.roles) ? tp.realm_access.roles : []
    const client = (tp && tp.resource_access && tp.resource_access[keycloak.clientId] && tp.resource_access[keycloak.clientId].roles) ? tp.resource_access[keycloak.clientId].roles : []
    return Array.from(new Set([...(realm||[]), ...(client||[])]))
  }catch(e){ return [] }
}

export function hasRole(role){
  return tokenRoles().includes(role)
}
