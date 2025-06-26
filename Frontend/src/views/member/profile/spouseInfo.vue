<template>
  <div class="update-basic-info">
    <div class="top">
      <nut-indicator :size="6" :current="2" />
    </div>
    <div class="form">
      <nut-form :model-value="formData">
        <input type="hidden" name="idSpouseInfo" :value="formData.idSpouseInfo" />
        <nut-form-item>
          <nut-cell title=" your level of education" :desc="elVal" @click="msClick" class="nut-cell-nopadding" />
          <nut-action-sheet v-model:visible="elShow" :menu-items="educationLevels" @choose="choose" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.program" placeholder="program" type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.officialLanguage" placeholder="officialLanguage" type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.codeNOC" placeholder="codeNOC" type="text" />
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
  import { dictionary, UpdateSpouseInfo } from '@/api';

  const userStore = useUserStore();
  const router = useRouter();

  const formData = ref({
    idSpouseInfo: '',
    educationLevel: '',
    program: '',
    officialLanguage: '',
    codeNOC: '',
  });
  const elShow = ref(false);
  const elVal = ref('');
  const educationLevels = ref();
  const msClick = () => {
    elShow.value = true;
  };
  const choose = (item) => {
    formData.value.educationLevel = item.value;
    elVal.value = item.name;
  };

  onMounted(() => {
    const spouse = userStore.info.spouseInfo;
    formData.value = spouse;
    dictionary('educationLevel').then((e) => {
      educationLevels.value = e.educationLevel;
      elVal.value = educationLevels.value.find((e) => (e.code = spouse?.educationLevel)).name;
    });
  });
  const previous = () => {
    router.push({ path: '/my/profile/basic' });
  };
  const submit = () => {
    // save and go next page
    UpdateSpouseInfo({
      idSpouseInfo: formData.value.idSpouseInfo,
      educationLevel: formData.value.educationLevel,
      program: formData.value.program,
      officialLanguage: formData.value.officialLanguage,
      codeNOC: formData.value.codeNOC,
    }).then(() => {
      router.push({ path: '/my/profile/language' });
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
