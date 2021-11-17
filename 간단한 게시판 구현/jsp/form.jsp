<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<title>게시판::등록폼</title>
</head>
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
	<div class="fs-2 mb-3"><i class="bi bi-chat-right-dots"></i></div>
	<div>
		<form class="border p-3 bg-light mt-3" method="post" action="register.jsp">
			<div class="form-label mb-3">
				<label class="form-label">title</label>
				<input type="text" class="form-control" name="title" />
			</div>
			<div class="form-label mb-3">
				<label class="form-label">writer</label>
				<input type="text" class="form-control" name="writer" />
			</div>
			<div class="mb-3">
				<label class="form-label">content</label>
				<textarea rows="5" class="form-control" name="content"></textarea>
			</div>
			<div class="mb-3 text-end">
				<button class="btn btn-primary">Post!</button>
			</div>
		</form>
	</div>
</div>
</body>
</html>