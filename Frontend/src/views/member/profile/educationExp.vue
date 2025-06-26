<template>
  <div class="update-basic-info">
    <div class="top">
      <nut-indicator :size="6" :current="4" />
    </div>
    <div class="form" v-if="!showForm">
      <div v-for="data in dataList" :key="data.idEducationExperience" class="data-row">
        <p>{{ data?.program }}</p>
        <p>{{ data?.address }}</p>
        <span class="float-right">
          <nut-button shape="square" size="mini" type="info" @click="edit(data)">Edit</nut-button>
          <nut-button shape="square" size="mini" type="danger" @click="deleteEducation(data)">delete</nut-button>
        </span>
      </div>
    </div>
    <div class="text-center">
      <br />
      <br />
      <nut-button type="default" @click="addNew">Add New</nut-button>
    </div>
    <div class="form form-data" v-if="showForm">
      <nut-form :model-value="formData">
        <input type="hidden" name="idEducationExperience" :value="formData.idEducationExperience" />
        <nut-form-item>
          <nut-input v-model="formData.program" placeholder="your program name " type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-cell title=" your level of education" :desc="elVal" @click="msClick" class="nut-cell-nopadding" />
          <nut-action-sheet v-model:visible="elShow" :menu-items="educationLevels" @choose="choose" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.address" placeholder="address" type="text" />
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
  import { updateEducationExp, deleteEducationExp, dictionary } from '@/api';

  const userStore = useUserStore();
  const router = useRouter();
  const showForm = ref(false);

  const formData = ref({
    idEducationExperience: 0,
    educationLevel: '',
    program: '',
    address: '',
  });
  const dataList = ref([]);
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
    const education = userStore.info.educationExperiences;
    if (education) {
      dataList.value = education;
    }
    dictionary('educationLevel').then((e) => {
      educationLevels.value = e.educationLevel;
    });
  });
  const previous = () => {
    router.push({ path: '/my/profile/language' });
  };

  const cancel = () => {
    // clear form data
    formData.value = {
      idEducationExperience: 0,
      educationLevel: '',
      program: '',
      address: '',
    };
    showForm.value = false;
  };
  const submit = () => {
    // save data
    updateEducationExp(formData.value).then((res) => {
      // update success, update the datalist
      if (formData.value.idEducationExperience === 0) {
        formData.value.idEducationExperience = res.idEducationExperience;
        dataList.value.push(formData.value);
      }

      // set data on formdata
      formData.value = {
        idEducationExperience: 0,
        educationLevel: '',
        program: '',
        address: '',
      };
      // show form
      showForm.value = false;
    });
  };

  const deleteEducation = (data) => {
    deleteEducationExp(data.idEducationExperience).then(() => {
      let indexToRemove = dataList.value.findIndex((item) => item.idEducationExperience === data.idEducationExperience);
      if (indexToRemove !== -1) {
        dataList.value.splice(indexToRemove, 1);
      }
    });
  };

  const next = () => {
    // go next page
    router.push({ path: '/my/profile/work' });
  };
  const edit = (data) => {
    // set data on formdata
    formData.value = data;
    elVal.value = educationLevels.value.find((e) => (e.code = data?.educationLevel)).name;
    // show form
    showForm.value = true;
  };

  const addNew = () => {
    // set data on formdata
    formData.value = {
      idEducationExperience: 0,
      educationLevel: '',
      program: '',
      address: '',
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

    .nut-cell-nopadding {
      padding: 0 !important;
    }
  }
</style>
