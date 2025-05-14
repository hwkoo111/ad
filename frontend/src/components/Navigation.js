import React, { useContext } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';
import '../styles/Navigation.css';

const Navigation = () => {
  const { isLoggedIn, setIsLoggedIn, nickname } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    const confirmed = window.confirm('정말 로그아웃하시겠습니까?');
   if (confirmed) {
    localStorage.removeItem('token');
    localStorage.removeItem('nickname'); // ✅ 닉네임도 제거
    setIsLoggedIn(false);
    alert('로그아웃되었습니다.'); // ✅ 알림창
    navigate('/');
  }
  };

  return (
    <nav>

      <div className="nav-logo">MovieTalk</div>
      
      <div className="nav-links">
        <NavLink to="/">Home</NavLink>
        <NavLink to="/community">Community</NavLink>
        <NavLink to="/playlist">Playlist</NavLink>
        <NavLink to="/findfriend">FindFriend</NavLink>
      </div>

      <div className="auth-container">
        <div className="auth-section">
          {isLoggedIn ? (
            <>
              <span className="welcome-text">{nickname}님 환영합니다!</span>
              <button className="logout-btn" onClick={handleLogout}>Logout</button>
            </>
          ) : (
            <>
              <NavLink to="/login" className="auth-link">Login</NavLink>
              <NavLink to="/signup" className="auth-link">Signup</NavLink>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navigation;
