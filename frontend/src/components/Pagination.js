import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import styles from "../styles/Pagination.css";

export default function Pagination({
  totalItems,
  itemCountPerPage,
  pageCount,
  currentPage,
  totalPages,
  onPageChange
}) {
  const [start, setStart] = useState(1);

  useEffect(() => {
    if (currentPage === start + pageCount) setStart((prev) => prev + pageCount);
    if (currentPage < start) setStart((prev) => prev - pageCount);
  }, [currentPage, pageCount, start]);

  // 페이지 번호를 1부터 totalPages까지 계산
  const pageNumbers = [];
  for (let i = start; i <= Math.min(start + pageCount - 1, totalPages); i++) {
    pageNumbers.push(i);
  }

  return (
    <div className={styles.wrapper}>
      <ul>
        {/* 이전 버튼 */}
        <li className={`${styles.move} ${currentPage === 1 && styles.invisible}`}>
          <Link to="#" onClick={() => onPageChange(currentPage - 1)}>이전</Link>
        </li>

        {/* 페이지 번호들 */}
        {pageNumbers.map((number) => (
          <li key={number}>
            <Link
              className={`${styles.page} ${currentPage === number && styles.active}`}
              to="#"
              onClick={() => onPageChange(number)}
            >
              {number}
            </Link>
          </li>
        ))}

        {/* 다음 버튼 */}
        <li className={`${styles.move} ${currentPage === totalPages && styles.invisible}`}>
          <Link to="#" onClick={() => onPageChange(currentPage + 1)}>다음</Link>
        </li>
      </ul>
    </div>
  );
}
