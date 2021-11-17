package com.sample.dao;
import static com.sample.util.ConnectionUtils.getConnection;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;

import com.sample.vo.Department;
import com.sample.vo.Employee;
import com.sample.vo.Location;

public class DepartmentDao {
	
	/**
	 * 변경된 부서정보가 포함된 부서정보를 전달받아 테이블에 반영한다.
	 * @param department 부서정보
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public void updateDepartment(Department department) throws SQLException {
		String sql = "update departments "
				   + "set "
				   + "	department_name = ?, "
				   + "	manager_id = ?, "
				   + "	location_id = ? "
				   + "where department_id = ? ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setString(1, department.getName());
		pstmt.setObject(2, department.getManager().getId(), JDBCType.NUMERIC);
		pstmt.setInt(3, department.getLocation().getId());
		pstmt.setInt(4, department.getId());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	/**
	 * 지정된 부서아이디에 해당하는 부서정보를 삭제한다
	 * @param departmentId 부서아이디
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public void deleteDepartment(int departmentId) throws SQLException {
		String sql = "delete from departments "
				   + "where department_id = ? ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, departmentId);
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	/**
	 * 지정된 부서이름과 부서소재지 아이디를 부서정보에 추가한다
	 * @param departmentName 부서명
	 * @param locationId 부서소재지 아이디
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public void insertDepartmnet(String departmentName, int locationId) throws SQLException {
		String sql = "insert into departments (department_id, department_name, location_id) "
				   + "values (departments_seq.nextval, ?, ?) ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setString(1, departmentName);
		pstmt.setInt(2, locationId);
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}

	/**
	 * 지정된 부서아이디에 소속된 직원수를 반환한다
	 * @param departmentId 부서아이디
	 * @return 직원수
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public int getEmployeesCountById(int departmentId) throws SQLException {
		
		String sql = "select count(*) cnt "
				   + "from employees "
				   + "where department_id = ? ";
		
		int employeesCount = 0;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, departmentId);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		employeesCount = rs.getInt("cnt");
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employeesCount;
	}
	
	/**
	 * 지정된 부서아이디에 해당하는 부서의 상세정보를 반환한다.
	 * @param departmentId 부서아이디
	 * @return 부서정보
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public Department getDepartmentById(int departmentId) throws SQLException {
		String sql = "select D.department_id, D.department_name, E.employee_id, E.first_name, E.last_name, L.location_id, L.city "
				   + "from departments D, employees E, locations L "
				   + "where D.manager_id = E.employee_id(+) "
				   + "and D.location_id = L.location_id "
				   + "and D.department_id = ? ";
		
		Department department = null;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, departmentId);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			department = new Department();
			Employee manager = new Employee();
			Location location = new Location();
			
			manager.setId(rs.getInt("employee_id"));
			manager.setFirstName(rs.getString("first_name"));
			manager.setLastName(rs.getString("last_name"));
			
			location.setCity(rs.getString("city"));
			location.setId(rs.getInt("location_id"));
			
			department.setId(rs.getInt("department_id"));
			department.setName(rs.getString("department_name"));
			department.setManager(manager);
			department.setLocation(location);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return department;
	}
	
	/**
	 * 전체 부서목록을 반환한다.
	 * @return 부서정보 목록
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public List<Department> getAllDepartments() throws SQLException {
		String sql = "select D.department_id, D.department_name, E.employee_id, E.first_name, E.last_name, L.location_id, L.city "
				   + "from departments D, employees E, locations L "
				   + "where D.manager_id = E.employee_id(+) "
				   + "and D.location_id = L.location_id "
				   + "order by D.department_id asc ";
		
		List<Department> departments = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Department department = new Department();
			Employee manager = new Employee();
			Location location = new Location();
			

			manager.setId(rs.getInt("employee_id"));
			manager.setFirstName(rs.getString("first_name"));
			manager.setLastName(rs.getString("last_name"));
			

			location.setId(rs.getInt("location_id"));
			location.setCity(rs.getString("city"));
			

			department.setId(rs.getInt("department_id"));
			department.setName(rs.getString("department_name"));
			

			department.setManager(manager);
			department.setLocation(location);
			

			departments.add(department);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return departments;
	}
}
