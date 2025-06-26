<template>
  <div class="update-basic-info">
    <div class="top">
      <nut-indicator :size="6" :current="5" />
    </div>
    <div class="form" v-if="!showForm">
      <div v-for="data in dataList" :key="data.idWorkExperience" class="data-row">
        {{ data?.jobTitle }} {{ data?.jobType }} [{{ data?.dateStart }}]
        <span class="float-right">
          <nut-button shape="square" size="mini" type="info" @click="edit(data)">Edit</nut-button>
          <nut-button shape="square" size="mini" type="danger" @click="deleteWork(data)">delete</nut-button>
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
        <input type="hidden" name="idWorkExperience" :value="formData.idWorkExperience" />
        <nut-form-item>
          <nut-input v-model="formData.jobTitle" placeholder="your job Title " type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.English" placeholder="your English" type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.codeNOC" placeholder="codeNOC" type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.amtWage" placeholder="your amount wage " type="number" />
        </nut-form-item>
        <nut-form-item>
          <nut-textarea v-model="formData.txtDuties" autosize="true" :max-length="200"> {{ formData.txtDuties }} </nut-textarea>
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.jobType" placeholder="jobType" type="text" />
        </nut-form-item>
        <nut-form-item>
          <nut-input v-model="formData.workplaceLocation" placeholder="your workplace Location " type="text" />
        </nut-form-item>
        <nut-form-item>
          <input type="date" name="" id="" v-model="formData.dateStart" />
          <!-- <nut-button @click="show = true">open</nut-button>
          <nut-popup v-model:visible="show" position="bottom">
            <nut-date-picker v-model="val" :min-date="min" :max-date="max" :three-dimensional="false" @confirm="confirm" />
          </nut-popup> -->
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
  import { updateWorkExp, deleteWorkExp } from '@/api';

  const userStore = useUserStore();
  const router = useRouter();
  const showForm = ref(false);

  const formData = ref({
    idWorkExperience: 0,
    jobTitle: '',
    English: '',
    codeNOC: '',
    amtWage: '',
    txtDuties: '',
    jobType: '',
    workplaceLocation: '',
    dateStart: '',
  });
  const dataList = ref([]);
  // const show = ref(false);
  // const min = new Date(1950, 1, 1);
  // const max = new Date(2025, 10, 1);
  // const val = ref(formData.value.dateStart);
  // const confirm = ({ selectedValue }) => {
  //   formData.value.dateStart = selectedValue;
  //   show.value = false;
  // };

  onMounted(() => {
    const work = userStore.info.workExperiences;
    if (work) {
      dataList.value = work;
    }
  });
  const previous = () => {
    router.push({ path: '/my/profile/education' });
  };

  const cancel = () => {
    // clear form data
    formData.value = {
      idWorkExperience: 0,
      jobTitle: '',
      English: '',
      codeNOC: '',
      amtWage: '',
      txtDuties: '',
      jobType: '',
      workplaceLocation: '',
      dateStart: '',
    };
    showForm.value = false;
  };
  const submit = () => {
    // save data
    updateWorkExp(formData.value).then((res) => {
      // update success, update the datalist
      if (formData.value.idWorkExperience === 0) {
        formData.value.idWorkExperience = res.idWorkExperience;
        dataList.value.push(formData.value);
      }

      // set data on formdata
      formData.value = {
        idWorkExperience: 0,
        jobTitle: '',
        English: '',
        codeNOC: '',
        amtWage: '',
        txtDuties: '',
        jobType: '',
        workplaceLocation: '',
        dateStart: '',
      };
      // show form
      showForm.value = false;
    });
  };

  const deleteWork = (data) => {
    deleteWorkExp(data.idWorkExperience).then(() => {
      let indexToRemove = dataList.value.findIndex((item) => item.idWorkExperience === data.idWorkExperience);
      if (indexToRemove !== -1) {
        dataList.value.splice(indexToRemove, 1);
      }
    });
  };

  const next = () => {
    // go next page
    router.push({ path: '/my/profile/extra' });
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
      idWorkExperience: 0,
      jobTitle: '',
      English: '',
      codeNOC: '',
      amtWage: '',
      txtDuties: '',
      jobType: '',
      workplaceLocation: '',
      dateStart: '',
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
