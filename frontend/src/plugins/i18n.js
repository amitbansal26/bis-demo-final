import { createI18n } from 'vue-i18n'
const messages = {
  // added keys

  // added keys

  // added keys

  // added keys

  // added keys

  // added keys

  // added keys

  // added keys

  hi: {
    chat: 'चैट',
    gis: 'जीआईएस',
    payments: 'भुगतान',
    uploads: 'अपलोड',
    export: 'निर्यात',
    piSchedule: 'पीआई शेड्यूलिंग',
    queues: 'कतारें',
    officer: 'अधिकारी',
    title: 'मानकऑनलाइन डेमो',
    home: 'मुखपृष्ठ',
    register: 'पंजीकरण',
    apply: 'आवेदन करें',
    track: 'स्थिति देखें',
    name: 'नाम',
    mobile: 'मोबाइल',
    email: 'ईमेल',
    password: 'पासवर्ड',
    submit: 'जमा करें',
    scheme: 'योजना',
    isNumber: 'आईएस संख्या',
    product: 'उत्पाद',
    createApp: 'आवेदन बनाएं'
  },
  en: {
    chat: 'Chat',
    gis: 'GIS',
    payments: 'Payments',
    uploads: 'Uploads',
    export: 'Export',
    piSchedule: 'PI Scheduling',
    queues: 'Queues',
    officer: 'Officer',
    title: 'Manakonline Demo',
    home: 'Home',
    register: 'Register',
    apply: 'Apply',
    track: 'Track',
    name: 'Name',
    mobile: 'Mobile',
    email: 'Email',
    password: 'Password',
    submit: 'Submit',
    scheme: 'Scheme',
    isNumber: 'IS Number',
    product: 'Product',
    createApp: 'Create Application'
  }
}
export const i18n = createI18n({
  legacy: false,
  locale: 'hi',
  fallbackLocale: 'en',
  messages
})
