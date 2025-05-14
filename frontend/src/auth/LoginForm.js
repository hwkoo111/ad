import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext'; // ✅ 추가
import '../styles/LoginForm.css';  // CSS 파일 import

const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();
  const { setIsLoggedIn, setNickname } = useContext(AuthContext);

  const handleLogin = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch('/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const data = await response.json();
        const token = data.token;
        const nickname = data.nickname;
        console.log(data);
        localStorage.setItem('token', token);
        localStorage.setItem('nickname', nickname); // ✅ 저장
        setIsLoggedIn(true);
        setNickname(nickname); // ✅ context에 저장
        console.log(nickname);
        alert('로그인 성공!')
        setSuccessMessage('로그인 성공!');
        setErrorMessage('');
        navigate('/'); // 홈으로 이동
      } else {
        setErrorMessage('잘못된 아이디 또는 비밀번호입니다.');
      }
    } catch (error) {
      setErrorMessage('서버와의 연결에 실패했습니다.');
    }
  };

  return (
    <div className="login-container">
      <h2>로그인</h2>
      <form onSubmit={handleLogin} className="login-form">
        <div className="form-group">
          <label htmlFor="username">사용자명:</label>
          <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} required className="input-field" />
        </div>
        <div className="form-group">
          <label htmlFor="password">비밀번호:</label>
          <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} required className="input-field" />
        </div>
        {successMessage && <p className="success-message">{successMessage}</p>}
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <button type="submit" className="submit-btn">로그인</button>
      </form>
    </div>
  );
};

export default LoginForm;
