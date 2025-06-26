import { http } from '@/utils/request';

const pageSize = import.meta.env.VITE_BASE_PAGE_SIZE;

export function dictionary(keywords?) {
  return http.get(`/api/common/dictionary`, { key: keywords });
}

/**
 * login
 * @returns UseAxiosReturn
 */
export function loginPassword(fromdata) {
  return http.post(`/api/login`, fromdata);
}

/**
 * logout
 * @returns UseAxiosReturn
 */
export function logout() {
  return http.post(`/api/logout`);
}

/**
 * get policy newses
 * @param page current pagenumber
 * @param lang language select
 * @param size date count size
 * @returns collection of policy
 */
export function policies(_page, _lang) {
  return http.get(`/api/policy/news/list`, {
    page: _page,
    lang: _lang,
    size: pageSize,
  });
}

/**
 * get policy newse by policy id
 * @param id Policy id
 * @returns Entity of policy
 */
export function policy(id) {
  return http.get(`/api/policy/news/content/${id}`, {});
}

/**
 * register a new customer
 * @param fromdata form data
 * @returns result of register
 */
export function register(fromdata) {
  return http.post(`/api/register`, fromdata);
}

export function programConditions() {
  return http.get(`/api/program/conditions`, {});
}

export function programs(condition) {
  return http.get(`/api/programs`, condition);
}

export function program(id) {
  return http.get(`/api/program/${id}`, {});
}

export function myScore() {
  return http.get(`/api/report/my`, {});
}

export function getDocument(type) {
  return http.get(`/api/document/get`, type);
}

export function createDocument(type) {
  return http.get(`/api/document/generate`, type);
}

/**
 * update client info
 * @returns UseAxiosReturn
 */
export function updateClientBasicInfo(clientInfo) {
  return http.post(`/api/UpdateClientBasic`, clientInfo);
}
/**
 * update client language ability
 * @returns UseAxiosReturn
 */
export function updateLanguageAbility(languageAbility) {
  return http.post(`/api/UpdateLanguageAbility`, languageAbility);
}

export function deleteLanguageAbility(id) {
  return http.post(`/api/deleteLanguageAbility/${id}`);
}
/**
 * update client spouse info
 * @returns UseAxiosReturn
 */
export function UpdateSpouseInfo(spuseInfo) {
  return http.post(`/api/UpdateSpouseInfo`, spuseInfo);
}

export function updateEducationExp(educationExp) {
  return http.post(`/api/UpdateEducationExp`, educationExp);
}

export function deleteEducationExp(id) {
  return http.post(`/api/deleteEducationExp/${id}`);
}

export function updateWorkExp(educationExp) {
  return http.post(`/api/UpdateWorkExp`, educationExp);
}

export function deleteWorkExp(id) {
  return http.post(`/api/deleteWorkExp/${id}`);
}

export function updateExtraInfo(educationExp) {
  return http.post(`/api/UpdateExtraInfo`, educationExp);
}

export function assessment(programId) {
  return http.post(`/api/assessment/${programId}`);
}

export function reports(reportId) {
  return http.get(`/api/report/${reportId}`, {});
}
