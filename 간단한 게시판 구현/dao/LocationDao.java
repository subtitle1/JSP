package com.sample.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static com.sample.util.ConnectionUtils.getConnection;

import com.sample.vo.Location;

public class LocationDao {

	/**
	 * 모든 부서 소재지 정보를 반환한다.
	 * @return 부서소재지 목록
	 * @throws SQLException 데이터베이스 엑세스 오류 시 발생한다
	 */
	public List<Location> getAllLocations() throws SQLException {
		String sql = "select location_id, street_address, postal_code, city, state_province, country_id "
				   + "from locations "
				   + "order by city asc ";
		
		List<Location> locations = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs =  pstmt.executeQuery();
		
		while (rs.next()) {
			Location location = new Location();
			location.setId(rs.getInt("location_id"));
			location.setStreetAddress(rs.getString("street_address"));
			location.setPostalCode(rs.getString("postal_code"));
			location.setStateProvince(rs.getString("state_province"));
			location.setCity(rs.getString("city"));
			location.setCountryId(rs.getString("country_id"));
			
			locations.add(location);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return locations;
	}
}
