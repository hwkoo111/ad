import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import '../styles/Homepage.css';  // CSS 파일 import
import searchicon from "../assets/mainpage/search.png"
import logo from "../assets/logo.png"

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

  return (
      <div>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>} {/* 에러 메시지 표시 */}
      <div className='movietalk'>MOVIETALK</div>
      <div className='image-43'></div>
      <div className="search-container">
        <input type="text" className="searchbar" placeholder="Search" />
        <img src={searchicon} alt="검색 아이콘" className="search-icon" />
      </div>

      <div className='movie-container'>
        {/* 상영작 */}
        <h3>상영작</h3>
        {movies['상영작'] && movies['상영작'].length > 0 ? (
          <div className='screenMovie'>
            <div className="movie-items">
              {movies['상영작'].map((movie, index) => (
                <div key={index} className="movie-item">
                  <img src={movie.posterUrl || logo} alt={movie.title} />
                  <p>{movie.title}</p>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <p>상영작 데이터가 없습니다.</p>
        )}
        <h3>액션</h3>
        {/* 액션 */}
        {movies['액션'] && movies['액션'].length > 0 ? (
          <div className='actionMovie'>
            <div className="movie-items">
              {movies['액션'].map((movie, index) => (
                <div key={index} className="movie-item">
                  <img src={movie.posterUrl || logo} alt={movie.title} />
                  <p>{movie.title}</p>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <p>액션 영화 데이터가 없습니다.</p>
        )}
        <h3>드라마</h3>
        {/* 드라마 */}
        {movies['드라마'] && movies['드라마'].length > 0 ? (
          <div className='dramaMovie'>
            <div className="movie-items">
              {movies['드라마'].map((movie, index) => (
                <div key={index} className="movie-item">
                  <img src={movie.posterUrl || logo} alt={movie.title} />
                  <p>{movie.title}</p>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <p>드라마 영화 데이터가 없습니다.</p>
        )}
        <h3>호러</h3>
        {/* 호러 */}
        {movies['호러'] && movies['호러'].length > 0 ? (
          <div className='horrorMovie'>
            <div className="movie-items">
              {movies['호러'].map((movie, index) => (
                <div key={index} className="movie-item">
                  <img src={movie.posterUrl || logo} alt={movie.title} />
                  <p>{movie.title}</p>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <p>호러 영화 데이터가 없습니다.</p>
        )}
      </div>
    </div>
  );
  };

  export default MovieList;
