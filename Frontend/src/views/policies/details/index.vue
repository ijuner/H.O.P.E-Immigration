<template>
  <section class="info">
    <div>
      <p>{{ details.data?.title }}</p>
      <p>{{ details.data?.updateTime }}</p>
    </div>
    <div v-html="details.data?.content"> </div>
  </section>
</template>

<script setup lang="ts">
  import { policy } from '@/api';

  const route = useRoute(); // use for get params
  const router = useRouter(); // use for direct to other pate

  const policyId = route.params.policyId;

  if (policyId === '' || policyId === null) {
    // todo need redirect to list page
    router.push({ path: '/policies' });
  }

  let details = reactive<any>({ data: {} });

  watch(
    () => router,
    () => {
      policy(policyId).then((res) => {
        details.data = res;
      });
    },
    { deep: true, immediate: true },
  );
</script>

<style lang="scss" scoped>
  .nut-swiper-item {
    line-height: 500px;
    img {
      width: 100%;
      height: 100%;
    }
  }

  .info {
    padding: 20px;
    .price {
      color: #f2270c;
      display: inline-block;
      font-size: 32px;
      em {
        font-size: 56px;
        font-style: normal;
      }
    }
  }
</style>
