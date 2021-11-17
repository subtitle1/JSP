<%@page import="com.sample.vo.Board"%>
<%@page import="com.sample.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	int boardNo = Integer.parseInt(request.getParameter("no"));
	int pageNo = Integer.parseInt(request.getParameter("cpno"));

	BoardDao boardDao = new BoardDao();
	Board board = new Board();
	board.setBoardNo(boardNo);
	
	boardDao.deleteBoard(board);
	
	response.sendRedirect("list.jsp?no="+boardNo+"&cpno="+pageNo);
%>