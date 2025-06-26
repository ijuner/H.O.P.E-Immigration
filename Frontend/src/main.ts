import { createApp } from 'vue';
import App from './App.vue';
import { i18n } from '@/i18n';
import router from '@/router';
import store from '@/store';
import './assets/font/iconfont.css';
import './assets/app.css';
import './styles/index.scss';

const app = createApp(App);

app.use(router);

app.use(i18n);
app.use(store);

app.mount('#app');
