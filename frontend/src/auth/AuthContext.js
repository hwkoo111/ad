import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [nickname, setNickname] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    const savedNickname = localStorage.getItem('nickname');
    setIsLoggedIn(!!token);
    if (savedNickname) setNickname(savedNickname);
  }, []);
  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn, nickname, setNickname }}>
      {children}
    </AuthContext.Provider>
  );
};