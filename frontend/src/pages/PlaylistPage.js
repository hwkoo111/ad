import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Pagination from '../components/Pagination';  // Pagination 컴포넌트
import styles from "../styles/PlaylistPage.css";  // 스타일 적용 (필요시)

const PlaylistPage = () => {
  const [playlists, setPlaylists] = useState([]);  // 플레이리스트 목록 상태
  const [totalItems, setTotalItems] = useState(0);  // 전체 아이템 수
  const [currentPage, setCurrentPage] = useState(1);  // 현재 페이지 상태
  const itemCountPerPage = 10;  // 페이지당 아이템 개수
  const pageCount = 5;  // 페이지네이션에서 표시할 페이지 수

  // 페이지 변경 시 실행될 함수
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // 현재 페이지에 맞는 데이터만 표시하기 위한 로직
  const indexOfLastItem = currentPage * itemCountPerPage;
  const indexOfFirstItem = indexOfLastItem - itemCountPerPage;
  const currentItems = playlists.slice(indexOfFirstItem, indexOfLastItem);

  // 전체 페이지 수 계산
  const totalPages = Math.ceil(totalItems / itemCountPerPage);

  useEffect(() => {
    const fetchPlaylists = async () => {
      try {
        const response = await axios.get('/api/playlist/view');  // 서버에서 전체 데이터 가져오기
        setPlaylists(response.data);  // 받아온 데이터 저장
        setTotalItems(response.data.length);  // 전체 아이템 수 설정
      } catch (error) {
        console.error('Error fetching playlists:', error);
      }
    };

    fetchPlaylists();
  }, []);  // 컴포넌트가 처음 렌더링될 때 데이터 가져오기

  return (
    <div className={styles.container}>
      <h1>플레이리스트 목록</h1>

      {/* 플레이리스트 목록 출력 */}
      <ul className="playlist-list">
      {currentItems.map((playlist, index) => (
        <li key={index} className="playlist-item">
          <div className="playlist-row">
            <span className="playlist-name">{playlist.playListName}</span>
            <span className="playlist-date">{playlist.playListDate}</span>
            <span className="playlist-member">작성자: {playlist.memberNickname}</span>
          </div>
        </li>
      ))}
      </ul>

      {/* 페이지네이션 컴포넌트 */}
      <Pagination
        totalItems={totalItems}
        itemCountPerPage={itemCountPerPage}
        pageCount={pageCount}
        currentPage={currentPage}
        totalPages={totalPages}  // totalPages 추가
        onPageChange={handlePageChange}  // 페이지 변경 함수 전달
      />
    </div>
  );
};

export default PlaylistPage;
