package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sample.vo.Department;
import com.sample.vo.Employee;
import com.sample.vo.Job;

import static com.sample.util.ConnectionUtils.getConnection;

public class EmployeeDao {
	
	public List<Employee> getAllManagers() throws SQLException {
		String sql = "select e.employee_id, e.first_name, e.last_name, d.department_id, d.department_name "
				   + "from employees e, departments d "
				   + "where e.department_id = d.department_id "
				   + "and e.employee_id in (select distinct manager_id "
				   + "                      from employees) "
				   + "order by d.department_id asc ";
		
		List<Employee> managers = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Employee manager = new Employee();
			Department department = new Department();
			
			manager.setId(rs.getInt("employee_id"));
			manager.setFirstName(rs.getString("first_name"));
			manager.setLastName(rs.getString("last_name"));
			
			department.setId(rs.getInt("department_id"));
			department.setName(rs.getString("department_name"));
			manager.setDepartment(department);
			
			managers.add(manager);
		}
		
		return managers;
	}
	
	/**
	 * 전체 사원수를 반환한다
	 * @return 사원수
	 * @throws SQLException
	 */
	public int getEmployeesCount() throws SQLException {
		String sql = "select count(*) cnt "
				   + "from employees ";
					
		int employeeCount = 0;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		employeeCount = rs.getInt("cnt");
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employeeCount;
	}
	
	public void deleteEmployee(int employeeId) throws SQLException {
		String sql = "delete from employees "
				   + "where employee_id = ? ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, employeeId);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	/**
	 * 지정된 사용자정보를 저장한다
	 * @param employee	새 사용자 정보
	 * @throws SQLException	데이터베이스 엑세스 작업 중 오류 발생했을 때
	 */
	public void insertEmployee(Employee employee) throws SQLException {
		String sql = "insert into employees "
				   + "(employee_id, first_name, last_name, email, phone_number, job_id, salary, "
				   + "commission_pct, manager_id, department_id, hire_date) "
				   + "values "
				   + "(employees_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate) ";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, employee.getFirstName());
		pstmt.setString(2, employee.getLastName());
		pstmt.setString(3, employee.getEmail());
		pstmt.setString(4, employee.getPhoneNumber());
		pstmt.setString(5, employee.getJob().getId());
		pstmt.setDouble(6, employee.getSalary());
		pstmt.setDouble(7, employee.getCommissionPct());
		pstmt.setInt(8, employee.getManager().getId());
		pstmt.setInt(9, employee.getDepartment().getId());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public int getEmployeeCountByManager(int employeeId) throws SQLException {
		
		String sql = "select count(*) cnt "
				   + "from employees e, employees m "
				   + "where m.employee_id = e.manager_id "
				   + "and m.employee_id = ? ";
		
		int empCount = 0;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, employeeId);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		empCount = rs.getInt("cnt");
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return empCount;
	}
	
	public List<Employee> getEmployeesByManagerId(int managerId) throws SQLException {
		
		String sql = "select e.employee_id, e.first_name, e.last_name, e.email, e.phone_number "
				   + "from employees e, employees m "
				   + "where e.manager_id = m.employee_id "
				   + "and m.employee_id = ? "
				   + "order by e.employee_id ";
		
		List<Employee> employees = new ArrayList<Employee>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, managerId);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Employee employee = new Employee();
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setEmail(rs.getString("email"));
			employee.setPhoneNumber(rs.getString("phone_number"));
			
			employees.add(employee);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employees;
	}
	
	public Employee getEmployeeById(int employeeId) throws SQLException {
		
		String sql = "select e.employee_id, e.first_name, e.last_name, e.phone_number, e.email, j.job_id, j.job_title, e.salary, e.commission_pct, m.first_name mgr_first, m.last_name mgr_last, d.department_id, d.department_name "
				   + "from employees e, employees m, departments d, jobs j "
				   + "where e.department_id = d.department_id "
				   + "and e.job_id = j.job_id "
				   + "and m.employee_id = e.manager_id "
				   + "and e.employee_id = ? ";
		
		Employee employee = null;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, employeeId);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			employee = new Employee();
			Employee manager = new Employee();
			Job job = new Job();
			Department department = new Department();
			
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setPhoneNumber(rs.getString("phone_number"));
			employee.setEmail(rs.getString("email"));
			
			job.setId(rs.getString("job_id"));
			job.setTitle("job_title");
			employee.setJob(job);
			
			employee.setSalary(rs.getDouble("salary"));
			employee.setCommissionPct(rs.getDouble("commission_pct"));

			manager.setFirstName(rs.getString("mgr_first"));
			manager.setLastName(rs.getString("mgr_last"));
			employee.setManager(manager);
			
			department.setId(rs.getInt("department_id"));
			department.setName(rs.getString("department_name"));
			
			employee.setDepartment(department);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employee;
	}
	
	/**
	 * 모든 사원 정보를 반환한다
	 * @return 사원 정보
	 * @throws SQLException
	 */
	public List<Employee> getAllEmployees(int begin, int end) throws SQLException {
		
		List<Employee> employees = new ArrayList<Employee>();
		
		String sql = "select rn, employee_id, first_name, last_name, phone_number, email, job_id, department_id, department_name "
				   + "from (select row_number () over (order by e.employee_id desc) rn, "
			       + "       e.employee_id, e.first_name, e.last_name, e.phone_number, e.email, "
				   + "       e.job_id, d.department_id, d.department_name "
				   + "       from employees e, departments d "
				   + "       where e.department_id = d.department_id(+)) "
				   + "where rn >= ? and rn <= ? " ;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, begin);
		pstmt.setInt(2, end);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Employee employee = new Employee();
			Department department = new Department();
			Job job = new Job();
			
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setPhoneNumber(rs.getString("phone_number"));
			employee.setEmail(rs.getString("email"));
			
			job.setId(rs.getString("job_id"));
			employee.setJob(job);
			
			department.setId(rs.getInt("department_id"));
			department.setName(rs.getString("department_name"));
			employee.setDepartment(department);
			
			employees.add(employee);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employees;
 	}
	
	public List<Employee> getEmployeesByJobId(String jobId) throws SQLException {
		String sql = "select employee_id, first_name, last_name, email, phone_number, hire_date "
				   + "from employees "
				   + "where job_id = ? "
				   + "order by employee_id asc ";
					
		List<Employee> employees = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, jobId);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Employee employee = new Employee();
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setEmail(rs.getString("email"));
			employee.setPhoneNumber(rs.getString("phone_number"));
			employee.setHireDate(rs.getDate("hire_date"));
			
			employees.add(employee);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employees;
	}
	
	/**
	 * 지정된 부서아이디에 소속된 사원정보를 반환한다.
	 * @param departmentId 부서아이디
	 * @return 사원정보 목록
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생했을 때
	 */
	public List<Employee> getEmployeesByDepartmentId(int departmentId) throws SQLException {
		
		String sql = "select employee_id, first_name, last_name, email, phone_number, hire_date "
				   + "from employees "
				   + "where department_id = ? "
				   + "order by first_name asc ";
					
		List<Employee> employees = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, departmentId);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Employee employee = new Employee();
			employee.setId(rs.getInt("employee_id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setEmail(rs.getString("email"));
			employee.setPhoneNumber(rs.getString("phone_number"));
			employee.setHireDate(rs.getDate("hire_date"));
			
			employees.add(employee);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return employees;
	}
}
