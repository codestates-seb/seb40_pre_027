import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

const PaginationComponent = styled.div`
  .pagination {
    width: 100%;
    margin-top: 2rem;
    display: flex;
    justify-content: space-between;
    padding: 1.8rem;
    .page {
      display: flex;
      .dot {
        padding: 0.5rem 0.8rem;
      }
      button {
        box-shadow: 0;
        border: 1px solid #d9d9d9;
        background: #fff;
        border-radius: 5px;
        :hover {
          background-color: #d9d9d9;
        }
      }
      .page-items {
        border: 1px solid #d9d9d9;
        width: 23px;
        height: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 5px;
        margin: 3px;
        :not(&.check) {
          cursor: pointer;
        }

        :hover:not(&.check) {
          background-color: #d9d9d9;
        }
      }
    }

    .per {
      display: flex;
      align-items: center;
      .per-items {
        width: 30px;
        height: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 5px;
        margin-right: 5px;
        border: 1px solid #d9d9d9;
        :not(&.check) {
          cursor: pointer;
        }

        :hover:not(&.check) {
          background-color: #d9d9d9;
        }
      }
    }
    .check {
      background: rgb(244, 130, 37);
      color: #fff;
    }
  }
`;

function Pagination({
  size,
  sizeHandler,
  currentPage,
  currentPageHandler,
  paginationLength,
}) {
  const pagArr = Array(
    Math.ceil((paginationLength ? paginationLength : 1) / (size ? size : 15))
  )
    .fill()
    .map((v, i) => i + 1);
  const [pagination, setPagination] = useState(pagArr);

  useEffect(() => {
    if (pagination.length > 5) setPagination(pagination.slice(0, 5));
  }, []);
  const pers = [15, 30, 50];
  const pag = (page, pageArr) => {
    const pageNxt = page + 1 > pageArr.length ? null : page + 1;
    const pageNxtt = page + 2 > pageArr.length ? null : page + 2;
    if (page >= 3) {
      return [page - 2, page - 1, page, pageNxt, pageNxtt];
    } else return pageArr.slice(0, 5);
  };
  const pageNextBtn = () => {
    if (currentPage + 1 <= pagArr.length) {
      currentPageHandler(currentPage + 1);
      setPagination(pag(currentPage + 1, pagArr));
    }
  };
  return (
    <PaginationComponent>
      <div className="pagination">
        <div className="page">
          {pagination.length &&
            pagination.map((v, i) => {
              if (v !== null) {
                return (
                  <div
                    className={
                      currentPage === v ? 'page-items check' : 'page-items'
                    }
                    onClick={() => {
                      currentPageHandler(v);
                      setPagination(pag(v, pagArr));
                    }}
                    key={i}
                  >
                    {v}
                  </div>
                );
              }
            })}
          <div className="dot">...</div>
          <button onClick={pageNextBtn}>next</button>
        </div>
        <div className="per">
          {pers.map((v, i) => (
            <div
              className={size === v ? 'per-items check' : 'per-items'}
              onClick={() => sizeHandler(v)}
              key={i}
            >
              {v}
            </div>
          ))}
          <span>per page</span>
        </div>
      </div>
    </PaginationComponent>
  );
}

export default Pagination;
