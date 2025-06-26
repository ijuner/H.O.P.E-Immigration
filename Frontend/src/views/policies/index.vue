<template>
  <nut-infinite-loading
    v-model="infinityValue"
    :has-more="hasMore"
    @load-more="loadMore"
    load-txt="Loading..."
    load-more-txt="No More data"
  >
    <div class="test" v-for="(p, index) in list" :key="index"
      ><span>{{ p.updateTime }}</span>
      <a href="#" @click="(_e) => toDetails(p.id)">
        <nut-cell><nut-ellipsis :content="p.title" direction="end" /></nut-cell></a
    ></div>
  </nut-infinite-loading>
</template>

<script lang="ts" setup name="PoliciesPage">
  import { policies } from '@/api';
  import { getLang } from '@/i18n';

  const infinityValue = ref(false);
  const hasMore = ref(true);
  const router = useRouter();

  let page = 1;
  const Policy = {
    id: '',
    title: '',
    updateTime: '',
  };
  let list = ref([Policy]);

  onMounted(() => {
    getPolicies();
  });

  // get policies by paggining
  const getPolicies = () => {
    policies(page, getLang()).then((res) => {
      list.value = list.value.concat(res.list);
      if (page < res.totalPage) {
        page++;
      } else {
        hasMore.value = false;
      }
    });
  };

  // event of click the policy link
  const toDetails = (index) => {
    router.push({ path: '/policy/' + index });
  };

  // scorll down the page , load more data
  const loadMore = () => {
    setTimeout(() => {
      if (hasMore.value) {
        getPolicies();
        infinityValue.value = false;
      }
    }, 1000);
  };
</script>

<style lang="scss" scoped></style>
