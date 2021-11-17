<%@page import="com.sample.dao.ReplyDao"%>
<%@page import="com.sample.vo.Board"%>
<%@page import="java.util.List"%>
<%@page import="com.sample.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<title>게시판::리스트</title>
</head>
<body>

<%	
	final int rowsPerPages = 5;
	final int PagesPerBlock = 5;
	
	int currentPageNo = Integer.parseInt(request.getParameter("cpno"));
	
	int begin = (currentPageNo - 1) * rowsPerPages + 1; // 1, 6, 11, ...
	int end = currentPageNo * rowsPerPages;				// 10, 20, 30 ... 
	
	BoardDao boardDao = new BoardDao();
	List<Board> boards = boardDao.getAllBoards(begin, end);
%>
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
	<div class="row mb-4">
		<div class="col text-center">
			<div><strong class="display-5"><span class="badge rounded-pill bg-primary">What's new?</span></strong>
			</div>
		</div>
	</div>
	<div class="container">
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>#</th>
				<th>title</th>
				<th>writer</th>
				<th>views</th>
				<th>likes</th>
				<th>date</th>
			</tr>
		</thead>
		<tbody>
<%
	ReplyDao replyDao = new ReplyDao();
	
	for (Board board : boards) {
	int repliesCount = replyDao.getRepliesCount(board.getBoardNo());
%>
			<tr>
				<td><%=board.getBoardNo() %></td>
				<td><a href="detail.jsp?no=<%=board.getBoardNo()%>&cpno=<%=currentPageNo%>" style="text-decoration: none; color: black;"><%=board.getTitle() %></a>
				<span class="badge rounded-pill bg-light text-dark"><%=repliesCount %></span>
				</td>
				<td><%=board.getWriter() %></td>
				<td><%=board.getViewCount() %></td>
				<td><%=board.getLikeCount() %></td>
				<td><%=board.getCreatedDate() %></td>
			</tr>
<%
	}
%>
		</tbody>
	</table>
<%
	int totalRecords = boardDao.getBoardCount();
	int totalPages = (int)(Math.ceil((double)totalRecords/rowsPerPages));
	int totalBlocks = (int)(Math.ceil((double)totalPages/PagesPerBlock));
	int currentBlock = (int)(Math.ceil((double)currentPageNo/PagesPerBlock));
	
	int beginPageNo = (currentBlock - 1) * PagesPerBlock + 1;
	int endPageNo = currentBlock * PagesPerBlock;
	
	if (currentBlock == totalBlocks) {
		endPageNo = totalPages;
	}
%>
	<nav aria-label="Page navigation example">
  		<ul class="pagination justify-content-center" style="padding-top: 20px;">
    	<li class="page-item disabled">
      		<a class="page-link" href="list.jsp?cpno=<%=(currentBlock - 1) * PagesPerBlock + 1%>" class="<%=currentBlock == 1 ? "disabled" : ""%>">Previous</a>
    	</li>
<%
	for (int pno = beginPageNo; pno <= endPageNo; pno++) {
%>
		<li class="page-item"><a class="page-link" href="list.jsp?cpno=<%=pno %>" class="<%=pno == currentPageNo ? "active" : ""%>"><%=pno %></a></li>
<%
	}
%>
		<li class="page-item">
	      <a class="page-link" href="list.jsp?cpno=<%=currentBlock * PagesPerBlock + 1%>" class="<%=currentBlock == totalBlocks ? "disabled" : ""%>">Next</a>
	    </li>
	  	</ul>
	</nav>
	<div class="text-end">
		<a class="btn btn-primary" href="form.jsp" role="button"><i class="bi bi-chat-right-quote"></i> Write</a>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>