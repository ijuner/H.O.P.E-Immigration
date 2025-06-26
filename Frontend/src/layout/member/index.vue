<template>
  <van-nav-bar :title="$t($route.meta.title as string)" :left-arrow="!tabbarVisible" @click-left="goBack" />
  <nut-drag direction="y">
    <nut-fixed-nav v-model:visible="visible" type="left" :selected="menuSelect">
      <template #list>
        <ul class="nut-fixed-nav__list side_menu">
          <li class="nut-fixed-nav__list-item" @click="menuSelect('/my')"> Dashboard </li>
          <li class="nut-fixed-nav__list-item" @click="menuSelect('/my/profile')"> Profile </li>
          <li class="nut-fixed-nav__list-item" @click="menuSelect('/my/document')"> Document </li>
          <!-- <li class="nut-fixed-nav__list-item" @click="menuSelect('/my/setting')"> Setting </li> -->
          <li class="nut-fixed-nav__list-item" @click="menuSelect('/home')"> Home </li>
        </ul>
      </template>
      <template #btn>
        <RectLeft color="#fff" />
        <span class="text">{{ '' }}</span>
      </template>
    </nut-fixed-nav>
  </nut-drag>
  <div class="main-page" :class="{ tabbar: tabbarVisible, border: showBorder }">
    <RouterView v-slot="{ Component }" v-if="$route.meta.keepAlive">
      <keep-alive>
        <component :is="Component" :key="$route.path" />
      </keep-alive>
    </RouterView>
    <RouterView v-if="!$route.meta.keepAlive" :key="$route.path" />
  </div>
</template>

<script lang="ts" setup name="MemberLayoutPage">
  import { useRouter } from 'vue-router';
  import { useUserStore } from '@/store/modules/user';
  import { RectLeft } from '@nutui/icons-vue';

  const tabbarVisible = ref(true);

  const showBorder = ref(true);
  const visible = ref(false);

  const userStore = useUserStore();
  const router = useRouter();

  if (userStore.getUserInfo == null) {
    router.push({ path: '/login' });
  }
  const goBack = () => {
    router.go(-1);
  };
  const menuSelect = (page) => {
    router.push({ path: page });
  };
</script>

<style scoped lang="scss">
  .side_menu {
    padding: 0px !important;
    flex-wrap: wrap;
    top: 10vw;
  }

  .nut-fixed-nav__list-item {
    padding: 0px 10vw 0px 10vw !important;
    background: white;
    height: 50px !important;
    min-width: unset !important;
  }

  .main-page {
    margin: 5vw;
    background-color: white;
  }

  :deep(.nut-fixed-nav__btn) {
    left: -80px !important;
  }
</style>
