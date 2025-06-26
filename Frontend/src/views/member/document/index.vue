<template>
  <div class="user-document">
    <div class="information-card">
      <h3>Study Plan</h3>
      <div>
        <a href="#" @click="generate('sp')">Generate</a>
        <PDF :src="pdf_url" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup name="UserProfilePage">
  import PDF from 'pdf-vue3';
  import { createDocument, getDocument } from '@/api';

  const pdf_url = ref();

  const router = useRouter();

  const generate = (type) => {
    createDocument({ type: type }).then((res) => {
      pdf_url.value = 'http://localhost:8877/' + res;
    });
  };

  onMounted(() => {
    getDocument({ type: 'sp' }).then((res) => {
      pdf_url.value = 'http://localhost:8877/' + res;
    });
  });
</script>

<style lang="scss">
  .user-document {
  }
</style>
