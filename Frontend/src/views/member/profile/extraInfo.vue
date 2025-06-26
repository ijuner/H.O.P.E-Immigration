<template>
  <div class="update-basic-info">
    <div class="top">
      <nut-indicator :size="6" :current="6" />
    </div>
    <div class="form">
      <nut-form :model-value="formData">
        <input type="hidden" name="idExtraInfo" v-model="formData.idExtraInfo" />
        <nut-form-item>
          Did you relatived?
          <nut-radio-group v-model="formData.relatives_flg" direction="horizontal">
            <nut-radio label="YES">YES</nut-radio>
            <nut-radio label="NO">NO</nut-radio>
          </nut-radio-group>
        </nut-form-item>
        <nut-form-item>
          Did you have invation letter from Province?
          <nut-radio-group v-model="formData.pnp_flg" direction="horizontal">
            <nut-radio label="YES">YES</nut-radio>
            <nut-radio label="NO">NO</nut-radio>
          </nut-radio-group>
        </nut-form-item>
        <nut-form-item>
          Did you have an offer?
          <nut-radio-group v-model="formData.offer_flg" direction="horizontal">
            <nut-radio label="YES">YES</nut-radio>
            <nut-radio label="NO">NO</nut-radio>
          </nut-radio-group>
        </nut-form-item>
        <nut-form-item>
          Did you have Frence cetificate?
          <nut-radio-group v-model="formData.bilingual_flg" direction="horizontal">
            <nut-radio label="YES">YES</nut-radio>
            <nut-radio label="NO">NO</nut-radio>
          </nut-radio-group>
        </nut-form-item>
        <div class="button-bar">
          <nut-button size="small" @click="previous">prev</nut-button>
          <nut-button size="small" @click="submit">save</nut-button>
        </div>
      </nut-form>
    </div>
  </div>
</template>

<script lang="ts" setup name="UpdateBasicInfoPage">
  import { ref } from 'vue';
  import { useUserStore } from '@/store/modules/user';
  import { updateExtraInfo } from '@/api';
  import { showToast } from '@nutui/nutui';

  const userStore = useUserStore();
  const router = useRouter();
  // const msShow = ref(false);
  // const msVal = ref('');

  // const msClick = () => {
  //   msShow.value = true;
  // };
  // const choose = (item) => {
  //   formData.value.marriageStatus = item.value;
  //   msVal.value = item.name;
  // };
  const formData = ref({
    idExtraInfo: 0,
    relatives_flg: '',
    pnp_flg: '',
    offer_flg: '',
    bilingual_flg: '',
  });

  onMounted(() => {
    const extra = userStore.info.extraInfo;
    formData.value = {
      idExtraInfo: extra?.idExtraInfo,
      relatives_flg: extra?.relatives_flg,
      pnp_flg: extra?.pnp_flg,
      offer_flg: extra?.offer_flg,
      bilingual_flg: extra.bilingual_flg,
    };
  });

  const previous = () => {
    router.push({ path: '/my/profile/work' });
  };

  const submit = () => {
    // save and go next page
    updateExtraInfo(formData.value).then(() => {
      //router.push({ path: '/my/profile' });
      showToast.success('Save success');
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

      view {
        flex: 6;
      }
    }

    .nut-cell-nopadding {
      padding: 0 !important;
    }
  }
</style>
