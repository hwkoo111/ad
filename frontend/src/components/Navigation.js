import React, { useContext } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';
import '../styles/Navigation.css';
import profile from "../assets/mainpage/user.png"

const Navigation = () => {
  const { isLoggedIn, setIsLoggedIn, nickname ,setNickname  } = useContext(AuthContext);
  const navigate = useNavigate();

const handleLogout = async () => {
  const confirmed = window.confirm('정말 로그아웃하시겠습니까?');
  if (confirmed) {
    try {
      await fetch('http://localhost:8080/api/auth/logout', {
        method: 'POST',
        credentials: 'include',
      });
    } catch (e) {
      console.error('로그아웃 요청 실패', e);
    }
    localStorage.removeItem('token');
    localStorage.removeItem('nickname');
    setIsLoggedIn(false);
    setNickname('');
    alert('로그아웃되었습니다.');
    navigate('/');
  }
};


  return (
    <nav>
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
              <NavLink to= "/mypage">
                <img src= {profile} alt="Profile"className="user"></img>
              </NavLink>

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
