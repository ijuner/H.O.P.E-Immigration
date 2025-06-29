import { useAxios } from '@vueuse/integrations/useAxios';
/**
 * reactive useFetchApi
 */

export default function useAxiosApi(url: string, config: any) {
  return useAxios(url, config);
}
