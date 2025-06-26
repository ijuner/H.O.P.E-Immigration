<template>
  <div class="avatar">
    <nut-avatar size="large">
      <My />
    </nut-avatar>
    <p>Hello Henry <Edit size="10" /></p>
    <span class="logout" @click="logout">{{ $t('btn.logout') }}</span>
  </div>
  <div class="line-bar" v-for="report in list" :key="report.id">
    <p class="text-center">{{ report.programName }}</p>
    <Line :data="report.chartSets" v-if="report.chartSets" @click="viewReport(report.id)" />
    <div v-if="!report.chartSets" class="text-center" @click="goAssessment(report.programId)">
      <h2>Haven't done score assessment yet? Click here to start the assessment.</h2>
      <nut-skeleton width="250px" height="15px" animated row="5" />
    </div>
  </div>
</template>

<script lang="ts" setup name="MemberPage">
  import { useUserStore } from '@/store/modules/user';
  import { myScore, assessment, reports } from '@/api';
  import { showToast } from '@nutui/nutui';
  import { My, Edit } from '@nutui/icons-vue';
  import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
  import { Line } from 'vue-chartjs';

  ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);
  const router = useRouter();
  const userStore = useUserStore();
  let list = ref();
  const logout = () => {
    // clear loged user information
    userStore.logout().then(() => {
      showToast.success('logout successed');
      router.push({ path: '/login' });
    });
  };
  const goAssessment = (programId) => {
    // todo sent a assessment requestion
    assessment(programId).then((res) => {
      // assessment success
      showToast.success('assess success');
      myScore().then((res) => {
        list.value = res;
      });
    });
  };
  const viewReport = (reportId) => {
    router.push({ path: `/report/${reportId}` });
  };
  onMounted(() => {
    myScore().then((res) => {
      list.value = res;
    });
  });
</script>

<style lang="scss">
  .avatar {
    text-align: center;
    position: relative;
  }

  .logout {
    position: absolute;
    top: 5px;
    right: 5px;
  }
  .line-bar {
    padding: 10px;
    margin-top: 50px;
    border-radius: 5px;
    box-shadow:
      10px 10px 10px gray,
      -10px -10px 10px gray;
  }

  .text-center {
    text-align: center;
  }
</style>
