package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sample.vo.Job;
import static com.sample.util.ConnectionUtils.getConnection;

public class JobDao {
	
	public int getEmployeesCountByJobId(String jobId) throws SQLException {
		String sql = "select count(*) cnt "
				   + "from jobs j, employees e "
				   + "where j.job_id = e.job_id "
				   + "and j.job_id = ? ";
		
		int employeesCount = 0;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, jobId);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		employeesCount = rs.getInt("cnt");
		
		return employeesCount;
	}
	
	public Job getJobDetailByJobId(String jobId) throws SQLException {
		
		String sql = "select job_id, job_title, min_salary, max_salary "
				   + "from jobs ";
		
		Job job = null;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			job = new Job();
			job.setId(rs.getString("job_id"));
			job.setTitle(rs.getString("job_title"));
			job.setMinSalary(rs.getInt("min_salary"));
			job.setMaxSalary(rs.getInt("max_salary"));
			
			return job;
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return job;
	}
	
	/**
	 * 모든 직종정보를 반환한다
	 * @return 직종정보 목록
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public List<Job> getAllJobs() throws SQLException {
		String sql = "select job_id, job_title, min_salary, max_salary "
				   + "from jobs "
				   + "order by job_id asc ";
		
		List<Job> jobs = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Job job = new Job();
			
			job.setId(rs.getString("job_id"));
			job.setTitle(rs.getString("job_title"));
			job.setMinSalary(rs.getInt("min_salary"));
			job.setMaxSalary(rs.getInt("max_salary"));
			
			jobs.add(job);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return jobs;
	}

}
