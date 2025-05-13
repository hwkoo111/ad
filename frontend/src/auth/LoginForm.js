// src/auth/LoginForm.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/LoginForm.css';  // CSS 파일 import

const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const navigate = useNavigate();

    // 로그인 처리 함수
    const handleLogin = async (event) => {
        event.preventDefault();

        // 로그인 API 요청
        try {
            const response = await fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username,
                    password,
                }),
            });

            if (response.ok) {
                const data = await response.json();
                const token = data.token;  // 백엔드에서 받은 JWT 토큰
                localStorage.setItem('token', token); // JWT 토큰을 로컬스토리지에 저장

                            // 로그인 성공 후 메시지 설정
                setSuccessMessage('로그인 성공!');
//              setSuccessMessage(token)
                setErrorMessage('');  // 이전의 오류 메시지 초기화
                navigate('/');

                } else {
                // 응답 코드에 따른 상세 에러 메시지 처리
                const data = await response.json();
                console.log('Error Data:', data); // 응답 데이터를 콘솔에 출력

                if (response.status === 401) {
                    setErrorMessage('잘못된 아이디 또는 비밀번호입니다.');
                    } else if (response.status === 500) {
                        setErrorMessage('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                    } else {
                        setErrorMessage(data.error || '로그인 실패');
                    }
                }
         } catch (error) {
            console.error('Login error:', error); // 실제 오류 메시지를 콘솔에 출력
            setErrorMessage('서버와의 연결에 실패했습니다. 네트워크 상태를 확인해주세요.');
         }
    };
     return (
            <div className="login-container">
                <h2>로그인</h2>
                <form onSubmit={handleLogin} className="login-form">
                    <div className="form-group">
                        <label htmlFor="username">사용자명:</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            className="input-field"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">비밀번호:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="input-field"
                        />
                    </div>
                    {successMessage && <p className="success-message">{successMessage}</p>}
                    {errorMessage && <p className="error-message">{errorMessage}</p>}
                    <button type="submit" className="submit-btn">로그인</button>
                </form>
            </div>
        );
};

export default LoginForm;
