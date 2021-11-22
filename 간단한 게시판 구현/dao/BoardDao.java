package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.sample.util.ConnectionUtils.getConnection;
import com.sample.vo.Board;

public class BoardDao {
	
	public void increaseViews(Board board) throws SQLException {
		String sql = "update tb_web_board "
				   + "set board_view_count = ? "
				   + "where board_no = ? ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, board.getViewCount() + 1);
		pstmt.setInt(2, board.getBoardNo());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
		
	}
	
	public void deleteBoard(int boardNo) throws SQLException {
		String sql = "delete from tb_web_board "
				   + "where board_no = ? ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, boardNo());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public void increaseLikes(Board board) throws SQLException {
		String sql = "update tb_web_board "
				   + "set board_like_count = board_like_count + 1 "
				   + "where board_no = ? ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, board.getBoardNo());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public int getBoardCount() throws SQLException {
		String sql = "select count(*) cnt "
				   + "from tb_web_board ";
		
		int boardCount = 0;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		boardCount = rs.getInt("cnt");
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return boardCount;
	}
	
	public Board getBoardDetail(int boardNo) throws SQLException {
		String sql = "select board_no, board_title, board_writer, board_view_count, board_like_count, "
			       + "board_content, board_deleted, board_created_date "
				   + "from tb_web_board "
				   + "where board_no = ? ";
		
		Board board = null;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, boardNo);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			board = new Board();
			
			board.setBoardNo(rs.getInt("board_no"));
			board.setTitle(rs.getString("board_title"));
			board.setWriter(rs.getString("board_writer"));
			board.setViewCount(rs.getInt("board_view_count"));
			board.setLikeCount(rs.getInt("board_like_count"));
			board.setContent(rs.getString("board_content"));
			board.setDeleted(rs.getString("board_deleted"));
			board.setCreatedDate(rs.getDate("board_created_date"));
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return board;
	}
	
	public List<Board> getAllBoards(int begin, int end) throws SQLException {
		String sql = "select rn, board_no, board_title, board_writer, board_view_count, board_like_count, board_created_date "
				   + "from (select row_number () over (order by board_no desc) rn, "
			       + "      board_no, board_title, board_writer, board_view_count, board_like_count, board_created_date "
				   + "      from tb_web_board) "
				   + "where rn >= ? and rn <= ? ";
		
		List<Board> boards = new ArrayList<Board>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, begin);
		pstmt.setInt(2, end);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Board board = new Board();
			
			board.setBoardNo(rs.getInt("board_no"));
			board.setTitle(rs.getString("board_title"));
			board.setWriter(rs.getString("board_writer"));
			board.setViewCount(rs.getInt("board_view_count"));
			board.setLikeCount(rs.getInt("board_like_count"));
			board.setCreatedDate(rs.getDate("board_created_date"));
			
			boards.add(board);
		}
		
		rs.close();
		pstmt.close();
		connection.close();		
		
		return boards;
	}

	public void insertBoard(Board board) throws SQLException {
		String sql = "insert into tb_web_board "
				   + "(board_no, board_title, board_writer, board_content, board_created_date) "
				   + "values (web_board_seq.nextval, ?, ?, ?, sysdate) ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setString(1, board.getTitle());
		pstmt.setString(2, board.getWriter());
		pstmt.setString(3, board.getContent());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
}
