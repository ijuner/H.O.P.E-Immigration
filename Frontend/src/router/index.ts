import { createRouter, createWebHistory, Router } from 'vue-router';
import routes from './routes';
import { useUserStore } from '@/store/modules/user';

const router: Router = createRouter({
  history: createWebHistory('/'),
  routes: routes,
});

router.beforeEach(async (_to, _from, next) => {
  const userStore = useUserStore();

  if (_to.meta.requiresAuth && Object.keys(userStore.info).length === 0) {
    // if router need auth and coustomer not logined, redirect to login
    next({ path: '/login' });
  }
  next();
});

export default router;
