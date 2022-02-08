package jp.ac.hcs.reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	/** 予約新規作成 一般用 */
	private static final String SQL_RESERVATION_INSERT = "INSERT INTO reservation_list (hospital_id, user_id, medical_id, reservation_date, reservation_time) "
			+ "VALUES(?, ?, ?, ?, ?)";

	/** 予約状況更新 一般用 */
	private static final String SQL_RESERVATIONS_COUNT = "UPDATE hospital_list SET reservations_count = (SELECT reservations_count FROM hospital_list WHERE hospital_id = ?) + 1 WHERE hospital_id = ?";

	/** 予約用病院情報取得 */
	private static final String SQL_HOSPITAL_INFO = "SELECT H.hospital_id, H.hospital_name, M.medical_id, M.medical_name, H.number_of_reservations, H.RESERVATIONS_COUNT "
			+ "FROM hospital_list H, medical_list M, hospital_medical_list HM "
			+ "WHERE H.hospital_id = HM.hospital_id AND H.hospital_id = ? "
			+ "AND M.medical_id = HM.medical_id";

	/** 診療科ID取得 */
	private static final String SQL_MEDICAL_ID = "SELECT M.medical_id, M.medical_name FROM hospital_medical_list HM, medical_list M WHERE HM.medical_id = M.medical_id AND HM.hospital_id = ?";

	/** 病院IDに対応する予約取得 */
	private static final String SQL_HOSPITAL_RESERVATION = "SELECT * FROM reservation_list WHERE hospital_id = ?";

	/** 予約情報未取得項目取得 */
	private static final String SQL_RESERVATION_INFO = "SELECT H.hospital_name, U.user_name, M.medical_name FROM hospital_list H, m_user U, medical_list M WHERE H.hospital_id = ? AND U.user_id = ? AND M.medical_id = ?";

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
	 * 選択された病院情報をDBから取得する処理
	 * @param hospital_id
	 * @return
	 */
	public ReservationEntity hospitalInfo(String hospital_id) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_HOSPITAL_INFO, hospital_id);
		ReservationEntity entity = mappingListResult(resultList);
		return entity;
	}

	/**
	 *ログインしているユーザの予約情報の検索結果をDBから取得する処理
	 * @param search
	 * @param user_id
	 * @return entity
	 */
	public ReservationEntity reservationListSearch(String user_id, String hospital_name, String medical_name,
			Date reservation_date) {
		System.out.println(user_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_RESERVATION_SEARCH_LIST, user_id, hospital_name,
				medical_name, reservation_date);
		ReservationEntity entity = mappingListResult(resultList);
		return entity;
	}

	/**
	 * 対応する病院の予約情報を取得する
	 */
	public ReservationEntity hospitalReservation(String hospital_id) throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_HOSPITAL_RESERVATION, hospital_id);
		ReservationEntity reservationEntity = mappingListResult(resultList);

		return reservationEntity;
	}

	/**
	 * 不足している予約情報を取得する
	 */
	public ReservationEntity hospitalReservationAdd(ReservationEntity reservationEntity) throws DataAccessException {

		List<ReservationData> reservationList = reservationEntity.getReservationList();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		reservationList.size();

		for (int i = 0; i < reservationList.size(); i++) {
			log.info("病院ID確認：" + reservationList.get(i).getHospital_id());
			log.info("ユーザID確認：" + reservationList.get(i).getUser_id());
			log.info("診療科ID確認：" + reservationList.get(i).getMedical_id());

			String hospital_id = reservationList.get(i).getHospital_id();
			String user_id = reservationList.get(i).getUser_id();
			String medical_id = reservationList.get(i).getMedical_id();

			resultList = jdbc.queryForList(SQL_RESERVATION_INFO, hospital_id, user_id, medical_id);

			for (int count = 0; count < resultList.size(); count++) {
				reservationEntity.getReservationList().get(i)
						.setHospital_name((String) resultList.get(count).get("hospital_name"));
				reservationEntity.getReservationList().get(i)
						.setUser_name((String) resultList.get(count).get("user_name"));
				reservationEntity.getReservationList().get(i)
						.setMedical_name((String) resultList.get(count).get("medical_name"));
			}

		}

		log.info("予約情報取得確認：" + reservationEntity);
		return reservationEntity;
	}

	/**
	 *予約新規作成したデータをDBに保存する処理
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public int reservationInsert(ReservationData data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int rowNumber = 0;
		String date = data.getReservation_date();
		Date reservationDate = sdf.parse(date);
		sdf = new SimpleDateFormat("HH:mm");
		date = data.getReservation_time();
		Date reservationtime = sdf.parse(date);

		String medical_id = data.getMedical_id();
		medical_id = medical_id.substring(0, 1);

		rowNumber += jdbc.update(SQL_RESERVATION_INSERT,
				data.getHospital_id(),
				data.getUser_id(),
				medical_id,
				reservationDate,
				reservationtime);

		int result = jdbc.update(SQL_RESERVATIONS_COUNT, data.getHospital_id(), data.getHospital_id());

		System.out.println(result);

		if (result == 0) {
			rowNumber = 0;
		}
		return rowNumber;
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
			data.setHospital_id((String) reservationList.get("hospital_id"));
			data.setHospital_name((String) reservationList.get("hospital_name"));
			data.setMedical_id((String) reservationList.get("medical_id"));
			data.setMedical_name((String) reservationList.get("medical_name"));
			data.setUser_id((String) reservationList.get("user_id"));
			data.setUser_name((String) reservationList.get("user_name"));

			//TimeStonp型への変換
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
			String reservation_date = sdf.format(reservationList.get("reservation_date"));
			data.setReservation_date(reservation_date);

			sdf = new SimpleDateFormat("HH時mm分");
			String reservation_time = sdf.format(reservationList.get("reservation_time"));
			data.setReservation_time(reservation_time);

			entity.getReservationList().add(data);

		}
		return entity;

	}

	/**
	 * 病院IDから診療科IDを取得する機能
	 */
	public ReservationEntity hospitalMedical(String hospital_id) throws DataAccessException {

		ReservationEntity entity = new ReservationEntity();

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_MEDICAL_ID, hospital_id);
		for (Map<String, Object> reservationList : resultList) {
			ReservationData data = new ReservationData();
			data.setMedical_id((String) reservationList.get("medical_id"));
			data.setMedical_name((String) reservationList.get("medical_name"));

			entity.getReservationList().add(data);
		}

		return entity;
	}

}
