import React, { useState, useEffect } from 'react';
import '../styles/CommunityPage.css';  // CSS 파일 import

//로딩 중과 게시글 없을 때를 구분해서 오류처리 필요
//페이지 버튼 다음 게시글 없으면 숫자 안 넘어가게(page 숫자 +1 안되게) 조정

const CommunityPosts = () => {
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch(`/api/community/posts?page=${page}`);

        if (!response.ok) {
          throw new Error('게시글을 불러오는 데 실패했습니다.');
        }

        const data = await response.json();

        if (data === "게시글이 없습니다.") {
          setPosts([]);
          setErrorMessage('게시글이 없습니다.');
        } else {
          setPosts(data.content); // Assuming 'content' is the field with the posts array
        }
      } catch (error) {
        setErrorMessage(error.message);
      }
    };

    fetchPosts();
  }, [page]);

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

  return (
    <div className="community-posts">
      <h1 className="heading">커뮤니티 게시글</h1>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {posts.length === 0 ? (
        <p className="loading-message">로딩 중...</p>
      ) : (
        <ul className="post-list">
          {posts.map((post) => (
            <li key={post.postId} className="post-item">
              <h3 className="post-title">{post.title}</h3>
              <p className="post-nickname">{post.nickname}</p>
              <p className="post-date">{post.createdAt}</p>
            </li>
          ))}
        </ul>
      )}

      <div className="pagination">
        <button
          onClick={() => handlePageChange(page - 1)}
          disabled={page <= 0}
          className="pagination-btn">
          이전
        </button>
        <span className="page-info">Page {page + 1}</span>
        <button
          onClick={() => handlePageChange(page + 1)}
          className="pagination-btn">
          다음
        </button>
      </div>
    </div>
  );
};

export default CommunityPosts;
