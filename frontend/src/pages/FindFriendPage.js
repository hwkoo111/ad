import React, { useState } from 'react';
import axios from 'axios';
import '../styles/FindFriendPage.css'; // CSS 파일을 따로 분리하여 관리
import logo from "../assets/logo.png"

const FindFriendPage = () => {
  const [nickname, setNickname] = useState('');
  const [movies, setMovies] = useState([]);
  const [error, setError] = useState('');

  const handleSearch = async () => {
    try {
      const response = await axios.get('/api/friend', {
        params: { nickname },
      });
      setMovies(response.data.moviePlaylistSummaryDtos);
      setError('');
    } catch (err) {
      setMovies([]);
      setError('회원이 존재하지 않거나 영화 조회 중 오류가 발생했습니다.');
    }
  };

  return (
    <div className="find-friend-container">
      <h2>친구의 영화제 조회</h2>
      <div className="search-bar">
        <input
          type="text"
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          placeholder="친구의 닉네임을 입력하세요"
        />
        <button onClick={handleSearch}>검색</button>
      </div>
      {error && <div className="error-message">{error}</div>}
      <div className="movies-list">
        {movies.length > 0 ? (
          movies.map((movie, index) => (
            <div key={index} className="movie-item">
              <img
                src={movie.posterUrl || logo}
                alt={movie.title}
                className="movie-poster"
              />
              <div className="movie-details">
                <h3>{movie.title}</h3>
                <p>{movie.titleEng}</p>
              </div>
            </div>
          ))
        ) : (
          <div className="no-movies">즐겨찾기 영화가 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default FindFriendPage;