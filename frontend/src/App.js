// src/app.js
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './auth/LoginForm'; // LoginForm 컴포넌트 import
import Homepage from './pages/Homepage';
import CommunityPage from './pages/CommunityPage';
import PlaylistPage from './pages/PlaylistPage';
import FindFriendPage from './pages/FindFriendPage';
import Navigation from './components/Navigation.js'


const App = () => {
    return (
        <BrowserRouter>
          <Navigation />
            <Routes>
                <Route path="/login"  element={<LoginForm />} />
                <Route path="/"  element={<Homepage />} />
                <Route path="/community" element={<CommunityPage />} />
                <Route path="/playlist" element={<PlaylistPage />} />
                <Route path="/findfriend" element={<FindFriendPage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
