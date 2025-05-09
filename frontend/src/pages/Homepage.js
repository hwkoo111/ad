import React, { useEffect, useState } from 'react';

const MovieList = () => {
  const [movies, setMovies] = useState({}); // 영화 데이터를 저장할 상태
  const [errorMessage, setErrorMessage] = useState(''); // 에러 메시지 상태

  useEffect(() => {
    // 영화 데이터를 API에서 받아오기
    const fetchMovies = async () => {
      try {
        const response = await fetch('/movie'); //
        if (!response.ok) {
          throw new Error('영화 데이터를 가져오는 데 실패했습니다.');
        }
        const data = await response.json(); // JSON 응답 파싱
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

      {/* 상영작 카테고리 */}
      <div>
        <h3>상영작</h3>
        <div className="movie-category">
          {movies['상영작'].map((movie, index) => (
            <div key={index} className="movie-item">
              <img src={movie.posterUrl || 'https://via.placeholder.com/150'} alt={movie.title} />
              <p>{movie.title}</p>
            </div>
          ))}
        </div>
      </div>

      {/* 호러 카테고리 */}
      <div>
        <h3>호러</h3>
        <div className="movie-category">
          {movies['호러'].map((movie, index) => (
            <div key={index} className="movie-item">
              <img src={movie.posterUrl || 'https://via.placeholder.com/150'} alt={movie.title} />
              <p>{movie.title}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MovieList;
