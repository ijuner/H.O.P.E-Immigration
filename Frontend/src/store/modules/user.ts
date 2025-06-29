import { loginPassword, logout } from '@/api';
import { useCookies } from '@vueuse/integrations/useCookies';
import { defineStore } from 'pinia';

const { VITE_TOKEN_KEY } = import.meta.env;
const token = useCookies().get(VITE_TOKEN_KEY as string);

interface StoreUser {
  token: string;
  info: Record<any, any>;
}

export const useUserStore = defineStore({
  id: 'app-user',
  state: (): StoreUser => ({
    token: token,
    info: {},
  }),
  getters: {
    getUserInfo(): any {
      return this.info || {};
    },
  },
  actions: {
    setInfo(info: any) {
      this.token = info.token ? info.token : '';
      this.info = info.user ? info.user : '';
    },
    login(loginForm) {
      return new Promise((resolve) => {
        loginPassword(loginForm).then((res) => {
          this.setInfo(res);
          resolve(res);
        });
      });
    },
    logout() {
      return new Promise((resolve) => {
        logout().then((res) => {
          this.setInfo({});
          resolve(res);
        });
      });
    },
  },
  persist: {
    key: 'token',
    storage: localStorage,
    paths: ['token'],
  },
});
