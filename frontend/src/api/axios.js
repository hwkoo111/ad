import axios from 'axios';

// axios 인스턴스 생성
const api = axios.create({
  baseURL: 'http://localhost:8080', // 기본 URL 설정
});

// 요청 인터셉터: 요청 전에 토큰을 헤더에 추가
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;