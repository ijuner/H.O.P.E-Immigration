<template>
  <div v-html="details.data.content"> </div>
</template>

<script lang="ts" setup name="ListPage">
  import { program } from '@/api';

  const route = useRoute(); // use for get params
  const router = useRouter(); // use for direct to other pate

  const programId = route.params.programId;

  onMounted(() => {});

  let details = reactive<any>({ data: {} });
  watch(
    () => router,
    () => {
      program(programId).then((res) => {
        details.data = res;
      });
    },
    { deep: true, immediate: true },
  );
</script>

<style lang="scss" scoped>
  .nut-card {
    padding: 15px;
    border-bottom: 1px solid #e5e5e5;
  }

  .program-card {
    padding: 15px;
    border-bottom: 1px solid #e5e5e5;
    cursor: pointer;
  }
</style>
