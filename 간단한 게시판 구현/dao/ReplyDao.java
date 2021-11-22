package com.sample.dao;

import static com.sample.util.ConnectionUtils.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sample.vo.Board;
import com.sample.vo.BoardReply;

public class ReplyDao {
	
	public int getRepliesCount(int boardNo) throws SQLException {
		
		String sql= "select count(*) cnt "
				  + "from tb_web_board_reply "
				  + "where board_no = ? ";
		
		int repliesCount = 0;
		Connection connection = getConnection();
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	    pstmt.setInt(1, boardNo);
	    
	    ResultSet rs = pstmt.executeQuery();
	    
	    rs.next();
	    repliesCount = rs.getInt("cnt");
	    
	    rs.close();
	    pstmt.close();
	    connection.close();
	    
		return repliesCount;
	}
	
	public BoardReply getReply(int replyNo) throws SQLException {
		String sql = "select reply_no, reply_writer, reply_content "
				   + "from tb_web_board_reply "
				   + "where reply_no = ? ";
		BoardReply reply = null;
		Connection connection = getConnection();
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	    pstmt.setInt(1, replyNo);
	    ResultSet rs = pstmt.executeQuery();
	    
	    if (rs.next()) {
	    	reply = new BoardReply();
	    	
	    	reply.setReplyNo(rs.getInt("reply_no"));
	    	reply.setReplyWriter(rs.getString("reply_writer"));
	    	reply.setReplyContent(rs.getString("reply_content"));
	    }
	   
	    rs.close();
	    pstmt.close();
	    connection.close();
	    
		return reply;
	}
	
	public void deleteReply(int replyNo) throws SQLException {
		String sql = "delete from tb_web_board_reply "
				   + "where reply_no = ? ";
		
		Connection connection = getConnection();
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	      
	    pstmt.setInt(1, replyNo);
	    pstmt.executeUpdate();
	    
	    pstmt.close();
	    connection.close();
	}
	
	public void insertReply(BoardReply reply) throws SQLException {
	    String sql = "insert into tb_web_board_reply "
	               + "(reply_no, reply_writer, reply_content, board_no, reply_created_date) "
	               + "values (web_reply_seq.nextval, ?, ?, ?, sysdate) ";
	      
	    Connection connection = getConnection();
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	      
	    pstmt.setString(1, reply.getReplyWriter());
	    pstmt.setString(2, reply.getReplyContent());
	    pstmt.setInt(3, reply.getBoard().getBoardNo());
	      
	    pstmt.executeUpdate();
	      
	    pstmt.close();
	    connection.close();
	}

	public List<BoardReply> getAllReplies(int boardNo) throws SQLException {
		String sql = "select r.reply_no, r.reply_writer, r.reply_content, r.reply_created_date, b.board_no "
				   + "from tb_web_board_reply r, tb_web_board b "
				   + "where b.board_No = r.board_no "
				   + "and b.board_no = ? "
				   + "order by reply_no desc ";
		
		List<BoardReply> replies = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, boardNo);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			BoardReply reply = new BoardReply();
			Board board = new Board();
			
			reply.setReplyNo(rs.getInt("reply_no"));
			reply.setReplyWriter(rs.getString("reply_writer"));
			reply.setReplyContent(rs.getString("reply_content"));
			reply.setCreatedDate(rs.getDate("reply_created_date"));
			board.setBoardNo(rs.getInt("board_no"));
			reply.setBoard(board);
			
			replies.add(reply);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
			
		return replies;
	}
}
