<%@page import="java.util.List"%>
<%@page import="com.sample.dao.ReplyDao"%>
<%@page import="com.sample.vo.BoardReply"%>
<%@page import="com.sample.vo.Board"%>
<%@page import="com.sample.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<title>게시판::상세</title>
</head>

<%
	int pageNo = Integer.parseInt(request.getParameter("cpno"));
	int boardNo = Integer.parseInt(request.getParameter("no"));
	BoardDao boardDao = new BoardDao();
	Board board = new Board();
	
	board = boardDao.getBoardDetail(boardNo);
	boardDao.increaseViews(board);
%>
<body>
	<nav class="navbar navbar-light navbar-expand-sm mb-3" style="background-color: #e3f2fd;">
		<div class="container">
			<div class="collapse navbar-collapse" id="navbar-2">
				<ul class="navbar-nav">
					<li class="nav-item"><a href="/sample/board/list.jsp?cpno=1" class="nav-link active">Home</a></li>
					<li class="nav-item"><a href="/sample/board/form.jsp" class="nav-link">New!</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">
		<h1 class="mb-3">#<%=boardNo %> details</h1>
		<table class="table table-bordered">
			<colgroup>
				<col width="10%">
				<col width="40%">
				<col width="10%">
				<col width="40%">
			</colgroup>
			<tbody>
				<tr>
					<th>#</th>
					<td><%=boardNo %></td>
					<th>Date</th>
					<td><%=board.getCreatedDate() %></td>
				</tr>
				<tr>
					<th>Title</th>
					<td><%=board.getTitle() %></td>
					<th>Writer</th>
					<td><%=board.getWriter() %></td>
				</tr>
				<tr>
					<th>Views</th>
					<td><%=board.getViewCount() %></td>
					<th>Likes</th>
					<td><%=board.getLikeCount() %> <a href="like.jsp?no=<%=board.getBoardNo() %>&cpno=<%=pageNo %>" class="badge rounded-pill bg-primary" style="text-decoration: none;">like it!</a></td>
				</tr>
				<tr>
					<th>Content</th>
					<td colspan="3"><%=board.getContent() %></td>
				</tr>
			</tbody>
		</table>
		<div class="text-end">
			<a href="" class="badge rounded-pill bg-light text-dark" style="text-decoration: none;">edit</a>
			<a href="delete.jsp?no=<%=board.getBoardNo() %>&cpno=<%=pageNo %>" class="badge rounded-pill bg-warning text-dark" style="text-decoration: none;">delete</a>
			<a href="list.jsp?cpno="<%=pageNo %> class="badge rounded-pill bg-info text-dark" style="text-decoration: none;">back to lists</a>
		</div>
		<div>
			<form class="border p-3 bg-light mt-3" method="post" action="reply.jsp">
				<input type="hidden" name="boardNo" value="<%=boardNo%>"/>
				<input type="hidden" name="cpno" value="<%=pageNo%>"/>
				<div class="form-group mb-3">
					<label class="form-label">comment-writer</label>
					<input type="text" class="form-control" name="writer"/>
				</div>
				<div class="form-group mb-3">
					<textarea rows="3" class="form-control" name="content"/></textarea>
				</div>
				<div class="text-end">
					<button type="submit" class="btn btn-primary">leave a comment!</button>
				</div>
			</form>
		</div>
		<div class="mt-3">
			<table class="table table-bordered">
				<tbody>
					<h4>comments</h4>
					<colgroup>
						<col width="*">
						<col width="10%">
						<col width="10%">
						<col width="8%">
					</colgroup>
<%
	BoardReply boardReply = new BoardReply();
	ReplyDao replyDao = new ReplyDao();
	
	List<BoardReply> replies = replyDao.getAllReplies(boardNo);
	
	if (replies.isEmpty()) {
%>
		<tr>
			<td class="text-center" colspan="5">There is no comment yet.</td>
		</tr>
<% 
	} else {
		for (BoardReply reply : replies) {
%>
					<tr>
						<td><%=reply.getReplyContent() %></td>
						<td><%=reply.getReplyWriter() %></td>
						<td><%=reply.getCreatedDate() %></td>
						<td class="text-center">
						<a href="deleteReply.jsp?boardNo=<%=boardNo %>&replyNo=<%=reply.getReplyNo() %>&cpno=<%=pageNo %>" 
						class="badge rounded-pill bg-warning text-dark" style="text-decoration: none;">delete</a></td>
					</tr>
<%
		}
	}
%>
				</tbody>
			</table>
		</div>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>