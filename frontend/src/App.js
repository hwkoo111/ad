import React, { useEffect, useState } from 'react';
import axios from 'axios';
import LoginPage from './pages/LoginPage';

function App() {
    const [securedData, setSecuredData] = useState('');

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            axios.get('/api/test-secured', {
                headers: {
                    Authorization: token  // 예: Bearer eyJ...
                }
            }).then((res) => {
                setSecuredData(res.data);
            }).catch((err) => {
                setSecuredData("인증 실패 또는 토큰 만료");
            });
        }
    }, []);

    return (
        <div className="App">
            <h1>MovieTalk 로그인</h1>
            <LoginPage />
            <div style={{ marginTop: '30px' }}>
                <h3>보안 API 응답:</h3>
                <p>{securedData}</p>
            </div>
        </div>
    );
}

export default App;