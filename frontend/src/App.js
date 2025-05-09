// src/app.js
import React from 'react';
import { BrowserRouter as BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './auth/LoginForm'; // LoginForm 컴포넌트 import
import Homepage from './pages/Homepage';

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login"  element={<LoginForm />} />
                <Route path="/"  element={<Homepage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
