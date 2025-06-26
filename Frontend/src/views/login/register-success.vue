<template>
  <div class="registerSucess">
    <h2>{{ $t('user.register.successedNotice') }} </h2></div
  >
  <a href="/login">Go Login</a>
</template>

<script lang="ts" setup name="RegisterSucessPage"></script>

<style scoped lang="scss">
  .registerSucess {
    padding: 20px;

    h2 {
      text-align: center;
    }
  }
</style>
