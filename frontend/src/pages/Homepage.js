import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import logo from '../assets/logo.png'
import '../styles/Homepage.css';  // CSS 파일 import

const MovieList = () => {
  const [movies, setMovies] = useState({}); // 영화 데이터를 저장할 상태
  const [errorMessage, setErrorMessage] = useState(''); // 에러 메시지 상태
  const location = useLocation();
  
  useEffect(() => {
    // location.state를 통해 전달된 메시지 알림
    if (location.state?.successMessage) {
      alert(location.state.successMessage);
    }
  }, [location]);

  useEffect(() => {
    // 영화 데이터를 API에서 받아오기
    const fetchMovies = async () => {
      try {
        const response = await fetch('/api/movie');
        if (!response.ok) {
          throw new Error('영화 데이터를 가져오는 데 실패했습니다.');
        }
        const data = await response.json(); // JSON 응답 파싱
        console.log(data);
        setMovies(data); // 영화 데이터를 상태에 저장
      } catch (error) {
        setErrorMessage(error.message); // 에러 메시지 처리
      }
    };

    fetchMovies(); // 컴포넌트가 마운트될 때 API 호출
  }, []); // 빈 배열을 두면 컴포넌트 마운트 시 한 번만 호출

  // 영화 데이터가 없으면 로딩 상태 표시
  if (Object.keys(movies).length === 0) {
    return <div>로딩 중...</div>;
  }

  return (
      <div>
        {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>} {/* 에러 메시지 표시 */}

        <div className="movie-container">
          {/* 상영작 카테고리 */}
          <div className="movie-category">
            <h3>상영작</h3>
            <div className="movie-items">
              {movies['상영작'].map((movie, index) => (
                <div key={index} className="movie-item">
                  <img src={movie.posterUrl || logo} alt={movie.title} />
                  <p>{movie.title}</p>
                </div>
              ))}
            </div>
          </div>

          {/* 호러 카테고리 */}
          <div className="movie-category">
            <h3>호러</h3>
            <div className="movie-items">
              {movies['호러'].map((movie, index) => (
                <div key={index} className="movie-item">
                  <img src={movie.posterUrl || logo} alt={movie.title} />
                  <p>{movie.title}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  };

  export default MovieList;
