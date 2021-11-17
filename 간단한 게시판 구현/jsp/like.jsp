<%@page import="com.sample.vo.Board"%>
<%@page import="com.sample.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	int pageNo = Integer.parseInt(request.getParameter("cpno"));
	int boardNo = Integer.parseInt(request.getParameter("no"));

	BoardDao boardDao = new BoardDao();
	Board board = new Board();
	
	board = boardDao.getBoardDetail(boardNo);
	boardDao.increaseLikes(board);
	
	response.sendRedirect("detail.jsp?no=" + boardNo +"&cpno=" + pageNo);
%>
