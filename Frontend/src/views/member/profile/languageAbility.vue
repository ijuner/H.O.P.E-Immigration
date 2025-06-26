<template>
  <div class="update-basic-info">
    <div class="top">
      <nut-indicator :size="6" :current="3" />
    </div>
    <div class="form" v-if="!showForm">
      <div v-for="data in dataList" :key="data.idLanguageAbility" class="data-row">
        {{ data?.english }} {{ data?.french }} {{ data?.obtainedTerm }}
        <span class="float-right">
          <nut-button shape="square" size="mini" type="info" @click="edit(data)">Edit</nut-button>
          <nut-button shape="square" size="mini" type="danger" @click="deleteLanguage(data)">delete</nut-button>
        </span>
      </div>
    </div>
    <div class="text-center">
      <nut-button type="default" @click="addNew">Add New</nut-button>
    </div>
    <div class="form form-data" v-if="showForm">
      <nut-form :model-value="formData">
        <input type="hidden" name="idLanguageAbility" :value="formData.idLanguageAbility" />
        <nut-form-item>
          <nut-input v-model="formData.english" placeholder="your english " type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.french" placeholder="your french" type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.obtainedTerm" placeholder="obtained term" type="text" />
        </nut-form-item>
        <nut-space style="margin: 10px">
          <nut-button size="small" @click="cancel">cancel</nut-button>
          <nut-button size="small" @click="submit">save</nut-button>
        </nut-space>
      </nut-form>
    </div>
    <nut-space style="margin: 10px" class="button-bar">
      <nut-button size="small" @click="previous">prev</nut-button>
      <nut-button size="small" @click="next">next</nut-button>
    </nut-space>
  </div>
</template>

<script lang="ts" setup name="UpdateBasicInfoPage">
  import { ref } from 'vue';
  import { useUserStore } from '@/store/modules/user';
  import { updateLanguageAbility, deleteLanguageAbility } from '@/api';
  import { showToast } from '@nutui/nutui';

  const userStore = useUserStore();
  const router = useRouter();
  const showForm = ref(false);

  const formData = ref({
    idLanguageAbility: 0,
    english: '',
    french: '',
    obtainedTerm: '',
  });
  const dataList = ref([]);

  onMounted(() => {
    const languages = userStore.info.languageAbilities;
    if (languages) {
      dataList.value = languages;
    }
  });
  const previous = () => {
    router.push({ path: '/my/profile/spouse' });
  };

  const cancel = () => {
    // clear form data
    formData.value = {
      idLanguageAbility: 0,
      english: '',
      french: '',
      obtainedTerm: '',
    };
    showForm.value = false;
  };
  const submit = () => {
    // save data
    updateLanguageAbility(formData.value).then(() => {
      // update success, update the datalist
      if (formData.value.idLanguageAbility === 0) {
        dataList.value.push(formData.value);
      }

      // set data on formdata
      formData.value = {
        idLanguageAbility: 0,
        english: '',
        french: '',
        obtainedTerm: '',
      };
      // show form
      showForm.value = false;
    });
  };

  const deleteLanguage = (data) => {
    deleteLanguageAbility(data.idLanguageAbility).then(() => {
      let indexToRemove = dataList.value.findIndex((item) => item.idLanguageAbility === data.idLanguageAbility);
      if (indexToRemove !== -1) {
        dataList.value.splice(indexToRemove, 1);
      }
    });
  };

  const next = () => {
    // go next page
    router.push({ path: '/my/profile/education' });
  };
  const edit = (data) => {
    // set data on formdata
    formData.value = data;
    // show form
    showForm.value = true;
  };

  const addNew = () => {
    // set data on formdata
    formData.value = {
      idLanguageAbility: 0,
      english: '',
      french: '',
      obtainedTerm: '',
    };
    // show form
    showForm.value = true;
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
    .float-right {
      float: right;
    }

    .data-row {
      padding: 3vw;
      text-wrap: nowrap;
      line-height: 2rem;
    }

    .button-bar {
      display: flex;
      justify-content: space-between;
    }
  }
</style>
