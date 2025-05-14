import React, { useContext } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';
import '../styles/Navigation.css';

const Navigation = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    navigate('/');
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
              <span className="welcome-text">환영합니다!</span>
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
