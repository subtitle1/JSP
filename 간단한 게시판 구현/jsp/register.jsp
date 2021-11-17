<%@page import="java.util.Date"%>
<%@page import="com.sample.dao.BoardDao"%>
<%@page import="com.sample.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String title = request.getParameter("title");
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");
	
	Board board = new Board();
	BoardDao boardDao = new BoardDao();
	
	board.setTitle(title);
	board.setWriter(writer);
	board.setContent(content);
	
	boardDao.insertBoard(board);
	
	response.sendRedirect("list.jsp?cpno=1");
%>