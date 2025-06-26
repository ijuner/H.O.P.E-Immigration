<template>
  <config-provider :theme-vars="themeVars">
    <div class="app">
      <div class="hero-section">
        <img src="/src/assets/logo.png" alt="H.O.P.E Logo" class="logo" />
        <h1 class="hero-title">Welcome to H.O.P.E</h1>
        <p class="hero-subtitle">Canada Immigration Assistance App</p>
      </div>
      <nut-row>
        <nut-col :span="24">
          <div class="section">
            <h2>About H.O.P.E</h2>
            <p
              >H.O.P.E is designed to assist immigrants in Canada. Our app provides comprehensive guidance and resources to help you through
              the immigration process.</p
            >
          </div>
        </nut-col>
      </nut-row>
      <nut-row>
        <nut-col :span="24">
          <div class="section">
            <h2>Features</h2>
            <ul>
              <li>Step-by-step immigration guides</li>
              <li>Resource center with essential documents</li>
              <li>Personalized support from immigration experts</li>
            </ul>
          </div>
        </nut-col>
      </nut-row>
      <nut-row>
        <nut-col :span="24">
          <div class="section gallery">
            <nut-row>
              <nut-col :span="12">
                <nut-image src="/src/assets/11.png" width="100%" height="auto" />
              </nut-col>
              <nut-col :span="12">
                <nut-image src="/src/assets/22.png" width="100%" height="auto" />
              </nut-col>
            </nut-row>
            <nut-row>
              <nut-col :span="12">
                <nut-image src="/src/assets/33.png" width="100%" height="auto" />
              </nut-col>
              <nut-col :span="12">
                <nut-image src="/src/assets/44.png" width="100%" height="auto" />
              </nut-col>
            </nut-row>
          </div>
        </nut-col>
      </nut-row>
      <nut-row>
        <nut-col :span="24">
          <div class="section contact">
            <h2>Contact Us</h2>
            <p>If you have any questions or need further assistance, please contact us at:</p>
            <p>Email: support@hopeapp.ca</p>
            <p>Phone: +1 234 567 890</p>
          </div>
        </nut-col>
      </nut-row>
      <footer class="footer">
        <p>&copy; 2024 H.O.P.E - All Rights Reserved</p>
      </footer>
    </div>
  </config-provider>
  <div class="btn-wrap">
    <nut-button shape="square" size="small" type="default" @click="changeLang('zh-cn')">
      {{ $t('language.zh') }}
    </nut-button>
    <nut-button shape="square" size="small" type="default" @click="changeLang('en-us')">
      {{ $t('language.en') }}
    </nut-button>
  </div>
</template>

<script lang="ts" setup name="HomePage">
  import { setLang } from '@/i18n';

  const themeVars = ref({
    'primary-color': '#004aad',
  });
  const changeLang = (type) => {
    setLang(type);
  };
</script>
<style lang="scss">
  .app {
    text-align: center;
    font-family: Arial, sans-serif;
  }
  .hero-section {
    background-color: #004aad;
    color: white;
    padding: 50px 0;
  }
  .logo {
    width: 100px;
    height: auto;
    margin: 20px 0;
  }
  .hero-title {
    font-size: 2.5em;
    margin: 20px 0;
  }
  .hero-subtitle {
    font-size: 1.2em;
  }
  .section {
    background-color: white;
    padding: 20px;
    margin: 20px 0;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  .gallery {
    display: flex;
    flex-wrap: wrap;
  }
  .contact {
    text-align: left;
  }
  .footer {
    background-color: #004aad;
    color: white;
    padding: 10px 0;
  }
  @media (min-width: 768px) {
    .logo {
      width: 150px;
    }
    .hero-title {
      font-size: 3em;
    }
    .hero-subtitle {
      font-size: 1.5em;
    }
  }
</style>
