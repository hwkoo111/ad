import React, { useEffect, useState } from 'react';
import { NavLink, useNavigate } from "react-router-dom";
import '../styles/Navigation.css'


const Navigation = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(null);
  const navigate = useNavigate();

  // 브라우저 저장소에서 토큰 유무 확인
  useEffect(() => {
    const token = localStorage.getItem('token');
    // console.log("token : " + token);
    setIsLoggedIn(!!token);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    navigate('/'); // 홈이나 로그인 페이지로 이동
  };

  return (
    <nav>
      <div className="auth-container">
        {isLoggedIn ? (
          <button className="logout-btn" onClick={handleLogout}>Logout</button>
        ) : (
          <div className="auth-section">
            <NavLink to="/login" className="auth-link">Login</NavLink>
            <NavLink to="/signup" className="auth-link">Signup</NavLink>
          </div>
        )}
      </div>
      <div>
        <NavLink to="/">
          Home
        </NavLink>
      </div>
      <div>
        <NavLink to="/community">
          Community
        </NavLink>
      </div>
      <div>
        <NavLink to="/playlist">
          Playlist
        </NavLink>
      </div>
      <div>
        <NavLink to="/findfriend">
          FindFriend
        </NavLink>
      </div>
    </nav>
  );
};

export default Navigation;
