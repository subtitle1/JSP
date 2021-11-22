<%@page import="com.sample.dao.ReplyDao"%>
<%@page import="com.sample.vo.BoardReply"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	int pageNo = Integer.parseInt(request.getParameter("cpno"));
	int boardNo = Integer.parseInt(request.getParameter("boardNo"));
	int replyNo = Integer.parseInt(request.getParameter("replyNo"));
	
	BoardReply boardReply = new BoardReply();
	ReplyDao replyDao = new ReplyDao();
	
	boardReply = replyDao.getReply(replyNo);
	replyDao.deleteReply(replyNo);
	
	response.sendRedirect("detail.jsp?no="+boardNo+"&cpno="+pageNo);
%>
