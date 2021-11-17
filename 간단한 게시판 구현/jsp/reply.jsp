<%@page import="com.sample.dao.BoardDao"%>
<%@page import="com.sample.vo.Board"%>
<%@page import="com.sample.vo.BoardReply"%>
<%@page import="com.sample.dao.ReplyDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int pageNo = Integer.parseInt(request.getParameter("cpno"));
	int boardNo = Integer.parseInt(request.getParameter("boardNo"));
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");
	
	BoardDao boardDao = new BoardDao();
	ReplyDao replyDao = new ReplyDao();
	
	Board board = new Board();
	BoardReply reply = new BoardReply();
	
	board = boardDao.getBoardDetail(boardNo);
	
	reply.setReplyWriter(writer);
	reply.setReplyContent(content);
	reply.setBoard(board);
	
	replyDao.insertReply(reply);
	
	response.sendRedirect("detail.jsp?no="+boardNo+"&cpno="+pageNo);
%>