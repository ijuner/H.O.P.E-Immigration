<template>
  <div class="user-profile">
    <div class="information-card">
      <h3>Basic Information</h3>
      <nut-skeleton width="200px" height="15px" animated v-if="!basicInfo" />
      <div v-if="basicInfo">
        <p>
          <span>age</span>
          {{ basicInfo.age }}
        </p>
        <p>
          <span>marriage Status</span>
          {{ basicInfo.marriageStatus }}
        </p>
        <p>
          <span>immigration Status</span>
          {{ basicInfo.immigrationStatus }}
        </p>
      </div>
      <a href="javascript:void(0)" @click="update('basic')">update</a>
    </div>

    <div class="information-card">
      <h3>Spouse Information</h3>
      <nut-skeleton width="200px" height="15px" animated v-if="!spouseInfo" />
      <div v-if="spouseInfo">
        <p>
          <span>education Level</span>
          {{ spouseInfo.educationLevel }}
        </p>
        <p>
          <span>program</span>
          {{ spouseInfo.program }}
        </p>
        <p>
          <span>official Language</span>
          {{ spouseInfo.officialLanguage }}
        </p>
        <p>
          <span>codeNOC</span>
          {{ spouseInfo.codeNOC }}
        </p>
      </div>
      <a href="javascript:void(0)" @click="update('spouse')">update</a>
    </div>

    <div class="information-card">
      <h3>Language Ability</h3>
      <nut-skeleton width="200px" height="15px" animated v-if="!languageInfo" />
      <div v-if="languageInfo">
        <div class="sub-information-card" v-for="lan in languageInfo" :key="lan.idLanguageAbility">
          <p>
            <span>English</span>
            {{ lan.english }}
          </p>
          <p>
            <span>French</span>
            {{ lan.french }}
          </p>
          <p>
            <span>obtained Term</span>
            {{ lan.obtainedTerm }}
          </p>
        </div>
      </div>
      <a href="javascript:void(0)" @click="update('language')">update</a>
    </div>
    <div class="information-card">
      <h3>Education experience</h3>
      <nut-skeleton width="200px" height="15px" animated v-if="!educationInfo" />
      <div v-if="educationInfo">
        <div class="sub-information-card" v-for="l in educationInfo" :key="l.idEducationExperience">
          <p>
            <span>educationLevel</span>
            {{ l.educationLevel }}
          </p>
          <p>
            <span>program</span>
            {{ l.program }}
          </p>
          <p>
            <span>address</span>
            {{ l.address }}
          </p>
        </div>
      </div>
      <a href="javascript:void(0)" @click="update('education')">update</a>
    </div>
    <div class="information-card">
      <h3>Work experience</h3>
      <nut-skeleton width="200px" height="15px" animated v-if="!workInfo" />
      <div v-if="educationInfo">
        <div class="sub-information-card" v-for="w in workInfo" :key="w.idWorkExperience">
          <p>
            <span>job Title</span>
            {{ w.jobTitle }}
          </p>
          <p>
            <span>English</span>
            {{ w.English }}
          </p>
          <p>
            <span>Amount Wage</span>
            {{ w.amtWage }}
          </p>
          <p>
            <span>job Type</span>
            {{ w.jobType }}
          </p>
          <p>
            <span>Location</span>
            {{ w.workplaceLocation }}
          </p>
          <p>
            <span>dateStart</span>
            {{ w.dateStart }}
          </p>
        </div>
      </div>
      <a href="javascript:void(0)" @click="update('work')">update</a>
    </div>
    <div class="information-card">
      <h3>extra Information</h3>
      <nut-skeleton width="200px" height="15px" animated v-if="!extraInfo" />
      <div v-if="extraInfo">
        <p>
          <span>education Level</span>
          {{ extraInfo.PNP_flg }}
        </p>
        <p>
          <span>program</span>
          {{ extraInfo.relatives_flg }}
        </p>
        <p>
          <span>official Language</span>
          {{ extraInfo.offer_flg }}
        </p>
        <p>
          <span>codeNOC</span>
          {{ extraInfo.bilingual_flg }}
        </p>
      </div>
      <a href="javascript:void(0)" @click="update('extra')">update</a>
    </div>
  </div>
</template>

<script lang="ts" setup name="UserProfilePage">
  import { useUserStore } from '@/store/modules/user';

  const router = useRouter();
  const userStore = useUserStore();

  let basicInfo = ref();
  let spouseInfo = ref();
  let languageInfo = ref();
  let educationInfo = ref([{}]);
  let workInfo = ref([{}]);
  let extraInfo = ref({});
  const update = (page) => {
    router.push({ path: '/my/profile/' + page });
  };

  onMounted(() => {
    const basic = userStore.info.clientBasicInfo;
    basicInfo.value = basic;

    const spouse = userStore.info.spouseInfo;
    spouseInfo.value = spouse;

    const language = userStore.info.languageAbilities;
    languageInfo.value = language;

    const education = userStore.info.educationExperiences;
    educationInfo.value = education;

    const work = userStore.info.workExperiences;
    workInfo.value = work;

    const info = userStore.info.extraInfo;
    extraInfo.value = info;
  });
</script>

<style lang="scss">
  .user-profile {
    div {
      width: 100%;
      position: relative;

      a {
        position: absolute;
        right: 10px;
        bottom: 1px;
      }
    }

    .information-card {
      background-color: rgb(247, 241, 233);
      margin-top: 30px;
      border-radius: 5px;
      box-shadow: 2px 2px 5px gray;
      p {
        padding: 20px;
        position: relative;

        span {
          position: absolute;
          font-size: 0.5rem;
          left: 10px;
          top: 0px;
        }
      }

      .sub-information-card {
        margin: 10px;
        background-color: rgb(198, 207, 207);
        width: auto;
      }
    }
  }
</style>
