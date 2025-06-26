<template>
  <div class="register">
    <nut-form ref="ruleForm" :model-value="formData">
      <nut-form-item
        required
        prop="name"
        :rules="[
          { required: true, message: $t('user.register.require.name') },
          { validator: lengthValidator, message: $t('user.register.validator.nameLength', { nameLength: 3 }), length: 3 },
        ]"
      >
        <nut-input v-model="formData.name" class="nut-input-text" type="text" placeholder="username" />
      </nut-form-item>
      <nut-form-item required prop="email" :rules="[{ required: true, message: $t('user.register.require.email') }]">
        <nut-input v-model="formData.email" class="nut-input-text" type="text" placeholder="email" />
      </nut-form-item>
      <nut-form-item required prop="phoneNumber" :rules="[{ required: true, message: $t('user.register.require.phoneNumber') }]">
        <nut-input v-model="formData.phoneNumber" class="nut-input-text" type="text" placeholder="phoneNumber" />
      </nut-form-item>
      <nut-form-item required prop="pwd" :rules="[{ required: true, message: $t('user.register.require.password') }]">
        <nut-input v-model="formData.pwd" class="nut-input-text" type="password" placeholder="password" />
      </nut-form-item>
      <nut-form-item
        required
        prop="confirmPassword"
        :rules="[
          { required: true, message: $t('user.register.require.confirmPassword') },
          { validator: confirmPasswordValidator, message: $t('user.register.validator.confirmPassword') },
        ]"
      >
        <nut-input v-model="formData.confirmPassword" class="nut-input-text" type="password" placeholder="confirmPassword" />
      </nut-form-item>
      <nut-button block type="info" @click="submit"> {{ $t('btn.register') }} </nut-button>
    </nut-form>
    <nut-button block type="info" @click="cancel"> {{ $t('btn.cancel') }} </nut-button>
  </div>
</template>

<script lang="ts" setup name="RegisterPage">
  import { register } from '@/api';

  const router = useRouter();

  let formData = ref({
    name: '',
    email: '',
    pwd: '',
    confirmPassword: '',
    phoneNumber: '',
  });
  const ruleForm = ref();

  const confirmPasswordValidator = () => {
    if (formData.value.confirmPassword === formData.value.pwd) return true;
    return false;
  };

  const lengthValidator = (a, b) => {
    if (a.length >= b.length) {
      return true;
    } else {
      return false;
    }
  };
  const submit = () => {
    ruleForm.value?.validate().then(({ valid, errors }) => {
      if (valid) {
        register(formData.value).then(() => {
          //if no error occured, go success page
          router.push({ path: '/register-success' });
        });
      } else {
        console.warn('error:', errors);
      }
    });
  };

  const cancel = () => {
    router.push({ path: '/login' });
  };
</script>

<style scoped lang="scss">
  .register {
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
