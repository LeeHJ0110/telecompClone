package com.kh.six.confirm.service;

import lombok.Data;

@Data
public class ConfirmPageVo {
    private int listCount;      //전체 게시글 갯수
    private int pageLimit;      //페이징 영역에 나타낼 페이지 버튼 최대갯수
    private int boardLimit;     //한 페이지에 보여줄 게시글 최대갯수

    private int maxPage;        //마지막 페이지 (전체 게시글 기준)
    private int startPage;      //페이징 영역의 시작페이지
    private int endPage;        //페이징 영역의 마지막페이지
    private int offset;         //SQL 에 사용될 값 (몇개를 건너뛰고 읽을지)

    private String currentPage; //현재 페이지
    private String memberNo;    //신청내역 조회시 필요한 맴버 정보
    private String selectTable; //조회하는 테이블이 어떤 테이블인지
    private String sort;        //정렬기준
    private boolean isEmployee; //관리자 확인

    public void setConfirmPageVo(int listCount,int pageLimit, int boardLimit) {
        this.listCount = listCount;
        this.pageLimit = pageLimit;
        this.boardLimit = boardLimit;
        if(currentPage == null){
            currentPage = "1";
        }

        this.maxPage = (int)Math.ceil((double) this.listCount / this.boardLimit);
        this.startPage = (Integer.parseInt(this.currentPage) - 1) / this.pageLimit * this.pageLimit + 1;
        this.endPage = startPage + this.pageLimit - 1;
        if(endPage > maxPage) {
            endPage = maxPage;
        }
        this.offset = this.boardLimit * (Integer.parseInt(this.currentPage) -1);
    }
}
