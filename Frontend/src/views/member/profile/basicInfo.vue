<template>
  <div class="update-basic-info">
    <div class="top">
      <nut-indicator :size="6" :current="1" />
    </div>
    <div class="form">
      <nut-form :model-value="formData">
        <input type="hidden" name="idClientBasic" :value="formData.idClientBasic" />
        <nut-form-item>
          <nut-input v-model="formData.age" placeholder="Age" type="number" />
        </nut-form-item>
        <nut-form-item>
          <nut-cell title="Your marital status" :desc="msVal" @click="msClick" class="nut-cell-nopadding" />
          <nut-action-sheet v-model:visible="msShow" :menu-items="maritalStatus" @choose="choose" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.immigrationStatus" placeholder="ImmigrationStatus" type="text" />
        </nut-form-item>
        <div class="button-bar">
          <nut-button size="small" @click="previous">prev</nut-button>
          <nut-button size="small" @click="submit">save & next</nut-button>
        </div>
      </nut-form>
    </div>
  </div>
</template>

<script lang="ts" setup name="UpdateBasicInfoPage">
  import { ref } from 'vue';
  import { useUserStore } from '@/store/modules/user';
  import { dictionary, updateClientBasicInfo } from '@/api';

  const userStore = useUserStore();
  const router = useRouter();
  const msShow = ref(false);
  const msVal = ref('');
  const maritalStatus = ref();

  const msClick = () => {
    msShow.value = true;
  };
  const choose = (item) => {
    formData.value.marriageStatus = item.value;
    msVal.value = item.name;
  };
  const formData = ref({
    idClientBasic: '',
    age: '',
    marriageStatus: '',
    immigrationStatus: '',
  });

  onMounted(() => {
    const basicInfo = userStore.info.clientBasicInfo;
    formData.value = {
      idClientBasic: basicInfo.idClientBasic,
      age: basicInfo?.age,
      marriageStatus: basicInfo?.marriageStatus,
      immigrationStatus: basicInfo?.immigrationStatus,
    };

    dictionary('maritalStatus').then((e) => {
      maritalStatus.value = e.maritalStatus;
      msVal.value = maritalStatus.value.find((e) => (e.code = basicInfo?.marriageStatus)).name;
    });
  });

  const previous = () => {
    router.push({ path: '/my/profile' });
  };

  const submit = () => {
    // save and go next page
    updateClientBasicInfo({
      idClientBasic: formData.value.idClientBasic,
      age: formData.value.age,
      marriageStatus: formData.value.marriageStatus,
      immigrationStatus: formData.value.immigrationStatus,
    }).then(() => {
      router.push({ path: '/my/profile/spouse' });
    });
  };
</script>

<style lang="scss">
  .update-basic-info {
    .top {
      width: 100;
      text-align: center;
    }

    .button-bar {
      display: flex;
      justify-content: space-between;
    }

    .nut-cell-nopadding {
      padding: 0 !important;
    }
  }
</style>
