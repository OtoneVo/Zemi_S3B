package jp.ac.hcs.reservation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
	/** 予約一覧取得 一般用 */
	private static final String SQL_RESERVATION_LIST = "SELECT R.hospital_id, H.hospital_name, R.medical_id, M.medical_name, R.user_id, U.user_name, R.reservation_date, R.reservation_time"
			+ " FROM m_user U, hospital_list H, reservation_list R,medical_list M, hospital_medical_list HM"
			+ " WHERE R.hospital_id = H.hospital_id AND R.hospital_id = HM.hospital_id"
			+ " AND R.medical_id = M.medical_id AND R.medical_id = HM.medical_id"
			+ " AND U.user_id = R.user_id AND R.user_id = ?";
	/** 予約一覧検索取得 一般用 */
	private static final String SQL_RESERVATION_SEARCH_LIST = "SELECT DISTINCT R.hospital_id, H.hospital_name, R.medical_id, M.medical_name, R.user_id, U.user_name, R.reservation_date, R.reservation_time"
			+ " FROM m_user U, hospital_list H, reservation_list R,medical_list M, hospital_medical_list HM"
			+ " WHERE R.hospital_id = H.hospital_id AND R.hospital_id = HM.hospital_id"
			+ " AND R.medical_id = M.medical_id AND R.medical_id = HM.medical_id"
			+ " AND U.user_id = R.user_id AND R.user_id = ?"
			+ " AND (H.hospital_name LIKE ? AND M.medical_name LIKE ? AND R.reservation_date LIKE ?)";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * ログインしているユーザの予約情報をDBから取得する処理
	 * @param user_id
	 * @return entity
	 */
	public ReservationEntity reservationListAll(String user_id) {
		System.out.println(user_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_RESERVATION_LIST, user_id);
		ReservationEntity entity = mappingListResult(resultList);
		return entity;
	}

	/**
	 *ログインしているユーザの予約情報の検索結果をDBから取得する処理
	 * @param search
	 * @param user_id
	 * @return entity
	 */
public ReservationEntity reservationListSearch(String user_id, String hospital_name, String medical_name,Date reservation_date) {
		System.out.println(user_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_RESERVATION_SEARCH_LIST, user_id, hospital_name, medical_name, reservation_date);
		ReservationEntity entity = mappingListResult(resultList);
		return entity;
	}


	/**
	 * 検索結果をリストに格納する処理
	 * @param resultList
	 * @return entity
	 */
	private ReservationEntity mappingListResult(List<Map<String, Object>> resultList) {
		ReservationEntity entity = new ReservationEntity();

		for (Map<String, Object> reservationList : resultList) {
			ReservationData data = new ReservationData();
			data.setHospital_id("hospital_id");
			data.setHospital_name("hospital_name");
			data.setMedical_id("medical_id");
			data.setMedical_name("medical_name");
			data.setUser_id("user_id");
			data.setUser_name("user_name");
			data.setReservation_date("reservation_date");
			data.setReservation_time("reservation_time");

			entity.getReservationList().add(data);

		}

		return entity;

	}

}
