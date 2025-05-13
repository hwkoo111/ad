import axios from 'axios';

// 로그인 API 요청 함수
export const fetchUser = async (username, password) => {
    try {
        // POST 요청을 보내서 로그인 시도
        const response = await axios.post('/login', {
            username,
            password,
        });

        if (response.status === 200) {
            const { token } = response.data;  // 응답에서 JWT 토큰 추출
            return { success: true, token };  // 로그인 성공 시 토큰 반환
        } else {
            return { success: false, error: '로그인 실패' };  // 실패 시 반환
        }
    } catch (error) {
        // 예외 처리: 네트워크 오류나 서버 오류
        console.error('Login error:', error);
        return { success: false, error: '서버와의 연결에 실패했습니다.' };
    }
};
