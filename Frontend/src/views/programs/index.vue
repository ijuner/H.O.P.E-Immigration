<template>
  <nut-menu>
    <nut-menu-item v-model="val1" :options="options1" />
    <nut-menu-item v-model="val2" :options="options2" @change="onChange" />
  </nut-menu>
  <div v-for="(item, index) in list" :key="index">
    <div @click="(_e) => toDetails(item.id)" class="program-card">
      {{ item.name }}
    </div>
  </div>
  <nut-empty description="404" v-if="!list" />
</template>

<script lang="ts" setup name="ListPage">
  import { programConditions, programs } from '@/api';

  const val1 = ref();
  const val2 = ref();
  const list = ref();
  const options1 = ref([{ text: '', value: '' }]);
  const options2 = ref([{ text: '', value: '' }]);
  onMounted(() => {
    programConditions().then((res) => {
      if (res) {
        Object.keys(res).flatMap((province) => {
          options1.value.push({ text: province, value: province });
          res[province].forEach((o2) => {
            options2.value.push({
              text: o2.name,
              value: o2.code,
            });
          });
        });
        val1.value = options1.value[0].value;
        search();
      }
    });
  });

  const onChange = (val) => {
    val2.value = val;
    search();
  };

  const router = useRouter();

  const search = () => {
    programs({
      code: val1.value,
      type: val2.value,
    }).then((res) => {
      list.value = res;
    });
  };
  const toDetails = (index) => {
    router.push({ path: '/program/' + index });
  };
</script>

<style lang="scss" scoped>
  .nut-card {
    padding: 15px;
    border-bottom: 1px solid #e5e5e5;
  }

  .program-card {
    padding: 15px;
    border-bottom: 1px solid #e5e5e5;
    cursor: pointer;
  }
</style>
