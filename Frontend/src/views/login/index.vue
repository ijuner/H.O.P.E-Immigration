<template>
  <div class="login">
    <nut-form ref="ruleForm" :model-value="formData">
      <nut-form-item required prop="name" :rules="[{ required: true, message: $t('errorMsg.username') }]">
        <nut-input v-model="formData.name" placeholder="username" />
      </nut-form-item>
      <nut-form-item required prop="pwd" :rules="[{ required: true, message: $t('errorMsg.password') }]">
        <nut-input v-model="formData.pwd" placeholder="Password" type="password" />
      </nut-form-item>
      <!-- <a href="https://www.example.com">Forget Password</a> -->
      <nut-button block type="info" @click="submit"> {{ $t('btn.login') }} </nut-button>
    </nut-form>
    <nut-button block type="info" @click="register"> {{ $t('btn.register') }} </nut-button>
  </div>
</template>

<script lang="ts" setup name="LoginPage">
  import router from '@/router';
  import { reactive, ref } from 'vue';
  import { useUserStore } from '@/store/modules/user';
  import { showToast } from '@nutui/nutui';

  const userStore = useUserStore();
  const formData = reactive({
    name: '',
    pwd: '',
  });
  const ruleForm = ref<any>(null);
  const submit = () => {
    ruleForm.value.validate().then(async ({ valid, errors }: any) => {
      if (valid) {
        const userInfo = await userStore.login(formData);
        if (userInfo) {
          showToast.success('Login successed');
          router.push({ path: '/my' });
        }
      } else {
        console.log('error submit!!', errors);
      }
    });
  };

  const register = () => {
    router.push({ path: '/register' });
  };
</script>

<style scoped lang="scss">
  .login {
    padding: 20px;

    h2 {
      letter-spacing: 10px;
      text-align: center;
    }

    .nut-form-item {
      margin-bottom: 20px;
      border-radius: 20px;
      background: #f2f3f5;

      input {
        background: transparent;
      }
    }
  }
</style>
