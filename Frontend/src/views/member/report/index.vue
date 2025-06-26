<template>
  <section class="report-info">
    <div class="user-score-dashboard">
      <h3 class="text-center">{{ details.programName }} </h3>
      <div class="score-card">
        <div class="score-summary">
          <span class="score-value max-score">{{ details.lastInviteScore }}</span>
          <span class="score-value">{{ details.score }}</span>
        </div>
        <div class="score-list">
          <div v-for="report in details.reportScores" :key="report.id" class="score-item">
            <span class="category">{{ report.targetField }}</span>
            <span class="score">{{ report.score }}</span>
          </div>
        </div>
        <div class="text-paragraph">
          <h4>Suggestion</h4>
          <p>{{ details.content }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
  import { reports } from '@/api';

  const route = useRoute(); // use for get params
  const router = useRouter(); // use for direct to other pate

  const reportId = route.params.reportId;

  if (reportId === '' || reportId === null) {
    // need redirect to my page
    router.push({ path: '/member' });
  }

  let details = ref({});

  watch(
    () => router,
    () => {
      reports(reportId).then((res) => {
        details.value = res;
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

  .user-score-dashboard {
    font-family: Arial, sans-serif;
    padding: 16px;
    background-color: #f5f5f5;
    max-width: 100%;
    box-sizing: border-box;
  }

  .report-title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 16px;
    color: #333;
  }

  .score-card {
    background-color: #fff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .card-header {
    padding: 16px;
    background-color: #f9f9f9;
    border-bottom: 1px solid #eee;
  }

  .card-header h2 {
    font-size: 18px;
    margin: 0;
    color: #333;
  }

  .card-header p {
    font-size: 14px;
    color: #666;
    margin: 4px 0 0;
  }

  .score-summary {
    display: flex;
    justify-content: space-between;
    padding: 16px;
    background-color: #fff;
    border-bottom: 1px solid #eee;
  }

  .score-value {
    font-size: 24px;
    font-weight: bold;
    color: #fa2c19;
  }

  .score-value.max-score {
    color: #ffa500;
  }

  .score-list {
    padding: 8px 0;
  }

  .score-item {
    display: flex;
    justify-content: space-between;
    padding: 12px 16px;
    border-bottom: 1px solid #f0f0f0;
  }

  .score-item:last-child {
    border-bottom: none;
  }

  .category {
    font-size: 14px;
    color: #333;
  }

  .score {
    font-size: 16px;
    color: #333;
    font-weight: bold;
  }
</style>
