export const routes = [
  {
    path: '/',
    redirect: '/home',
    component: () => import('@/layout/basic/index.vue'),

    children: [
      {
        path: 'home',
        component: () => import('@/views/home/index.vue'),
        meta: {
          title: 'tabbar.home',
          keepAlive: true,
        },
      },
      {
        path: 'policies',
        component: () => import('@/views/policies/index.vue'),
        meta: {
          title: 'tabbar.policies',
          keepAlive: true,
        },
      },
      {
        path: 'programs',
        component: () => import('@/views/programs/index.vue'),
        meta: {
          title: 'tabbar.programs',
          keepAlive: true,
        },
      },
      {
        path: 'demo',
        component: () => import('@/views/demo/index.vue'),
        meta: {
          title: 'tabbar.demo',
          keepAlive: true,
        },
      },
      {
        path: 'policy/:policyId',
        component: () => import('@/views/policies/details/index.vue'),
        meta: {
          title: 'policies.details',
          border: false,
        },
      },
      {
        path: 'program/:programId',
        component: () => import('@/views/programs/details/index.vue'),
        meta: {
          title: 'program.details',
          border: false,
        },
      },
      {
        path: 'login',
        component: () => import('@/views/login/index.vue'),
        meta: {
          title: 'user.login.title',
          keepAlive: true,
        },
      },
      {
        path: 'register',
        component: () => import('@/views/login/register.vue'),
        meta: {
          title: 'user.register.title',
          keepAlive: true,
        },
      },
      {
        path: 'register-success',
        component: () => import('@/views/login/register-success.vue'),
        meta: {
          title: 'user.register.successed',
          keepAlive: true,
        },
      },
    ],
  },
  {
    path: '/',
    redirect: '/my',
    component: () => import('@/layout/member/index.vue'),

    children: [
      {
        path: 'my',
        component: () => import('@/views/member/index.vue'),
        meta: {
          title: 'tabbar.my',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile',
        component: () => import('@/views/member/profile/index.vue'),
        meta: {
          title: 'tabbar.profile.index',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile/basic',
        component: () => import('@/views/member/profile/basicInfo.vue'),
        meta: {
          title: 'tabbar.profile.basic',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile/spouse',
        component: () => import('@/views/member/profile/spouseInfo.vue'),
        meta: {
          title: 'tabbar.profile.spouse',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile/language',
        component: () => import('@/views/member/profile/languageAbility.vue'),
        meta: {
          title: 'tabbar.profile.language',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile/education',
        component: () => import('@/views/member/profile/educationExp.vue'),
        meta: {
          title: 'tabbar.profile.education',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile/work',
        component: () => import('@/views/member/profile/workExperience.vue'),
        meta: {
          title: 'tabbar.profile.work',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/profile/extra',
        component: () => import('@/views/member/profile/extraInfo.vue'),
        meta: {
          title: 'tabbar.profile.extra',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'my/document',
        component: () => import('@/views/member/document/index.vue'),
        meta: {
          title: 'tabbar.document',
          keepAlive: true,
          requiresAuth: true,
        },
      },
      {
        path: 'report/:reportId',
        component: () => import('@/views/member/report/index.vue'),
        meta: {
          title: 'tabbar.report',
          border: false,
        },
      },
    ],
  },
  // will redirect to homepage when no path matched
  {
    path: '/:pathMatch(.*)',
    redirect: '/Home',
  },
];

export default routes;
