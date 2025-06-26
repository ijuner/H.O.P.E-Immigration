import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { showToast } from '@nutui/nutui';
import '@nutui/nutui/dist/packages/toast/style';
import { useUserStore } from '@/store/modules/user';

const service: AxiosInstance = axios.create({
  withCredentials: false,
  timeout: 10000,
  baseURL: import.meta.env.VITE_BASE_API,
});

service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const userStore = useUserStore();
    if (userStore.token) {
      config.headers.setAuthorization(userStore.token);
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  },
);

service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;
    if (res.code !== 0) {
      showToast.fail(res.msg);
      return Promise.reject(res.msg || 'Error');
    } else {
      return res.data;
    }
  },
  (error: AxiosError) => {
    console.log('err' + error);
    showToast.fail(error.message);
    return Promise.reject(error.message);
  },
);
const urlEncode = (url, obj) => {
  if (!obj) {
    return import.meta.env.VITE_BASE_API + url;
  }
  const params = Object.keys(obj)
    .map((key) => {
      return encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]);
    })
    .join('&');
  if (url.indexOf('?') > -1) {
    return import.meta.env.VITE_BASE_API + url + params;
  }
  return import.meta.env.VITE_BASE_API + url + '?' + params;
};
export const http = {
  get<T = any>(url: string, param: any, config?: AxiosRequestConfig): Promise<T> {
    return service.get(urlEncode(url, param), config);
  },
  post<T = any>(url: string, data?: object, config?: AxiosRequestConfig): Promise<T> {
    return service.post(url, data, config);
  },

  put<T = any>(url: string, data?: object, config?: AxiosRequestConfig): Promise<T> {
    return service.put(url, data, config);
  },

  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return service.delete(url, config);
  },
};

export default service;
