// src/app.js
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './auth/LoginForm'; // LoginForm 컴포넌트 import
import Homepage from './pages/Homepage';
import CommunityPage from './pages/CommunityPage';
import PlaylistPage from './pages/PlaylistPage';
import FindFriendPage from './pages/FindFriendPage';
import PlaylistDetailPage from './pages/PlaylistDetailPage'; 
import Navigation from './components/Navigation.js'
import SignupPage from './pages/SignupPage';
import { AuthProvider } from './auth/AuthContext';

const App = () => {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navigation />
        <Routes>
          <Route path="/login" element={<LoginForm />} />
          <Route path="/" element={<Homepage />} />
          <Route path="/community" element={<CommunityPage />} />
          <Route path="/playlist" element={<PlaylistPage />} />
          <Route path="/findfriend" element={<FindFriendPage />} />
          <Route path="/playlist/view/:playListId" element={<PlaylistDetailPage />} />
          <Route path="/signup" element={<SignupPage />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
};


export default App;
