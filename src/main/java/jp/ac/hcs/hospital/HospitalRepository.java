package jp.ac.hcs.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * 病院情報のデータを管理する - hospital_Listテーブル
 */
@Repository
public class HospitalRepository {

	@Autowired
	PasswordEncoder passwordEncoder;

	/** SQL病院情報全件取得 */
	private static final String SQL_SELECT_HOSPITAL_ALL = "SELECT * FROM hospital_List ORDER BY hospital_id";

	/** SQL病院IDに対応する病院を一件取得 */
	private static final String SQL_SELECT_HOSPITAL_ONE = "SELECT * FROM hospital_list WHERE hospital_id = ?";

	/** SQL病院IDに対応する診療科名を取得 */
	private static final String SQL_SELECT_HOSPITAL_MEDICAL = "SELECT H.hospital_id, M.medical_name FROM hospital_List H, hospital_medical_List HM, medical_List M WHERE H.hospital_id = HM.hospital_id AND HM.medical_id = M.medical_id AND H.hospital_id = ?";

	private static final String SQL_SEARCH_MEDICAL = "SELECT DISTINCT H. hospital_id, H.hospital_name, H.address, H.phone_number, H.reservations_count, H.overview FROM hospital_list H, hospital_medical_list HM, medical_list M WHERE H.hospital_id = HM.hospital_id AND HM.medical_id = M.medical_id AND (hospital_name LIKE ? AND H.address LIKE ? AND medical_name LIKE ?) IS NOT FALSE";

	/** SQL病院新規登録 */
	private static final String SQL_INSERT_HOSPITAL = "INSERT INTO hospital_List(hospital_id, hospital_name, encrypted_password, address, phone_number, number_of_reservations, overview) VALUES(?, ?, ?, ?, ?, ?, ?)";

	/** SQL診療科新規登録 */
	private static final String SQL_INSERT_MEDICAL = "INSERT INTO hospital_medical_list(hospital_id, medical_id) VALUES (?, ?)";

	/** SQL病院削除 */
	private static final String SQL_DELETE_HOSPITAL = "DELETE FROM hospital_list WHERE hospital_id = ?";

	/** SQL病院診療科削除 */
	private static final String SQL_DELETE_MEDICAL_HOSPITAL = "DELETE FROM hospital_medical_list WHERE hospital_id = ?";

	/** SQL病院情報更新 */
	private static final String SQL_UPDATE_HOSPITAL = "UPDATE hospital_list "
			+ "SET hospital_name = CASE WHEN ? IS NULL THEN hospital_name WHEN ? IS NOT NULL THEN ? END, "
			+ "encrypted_password = CASE WHEN ? IS NULL THEN encrypted_password WHEN ? IS NOT NULL THEN ? END, "
			+ "address = CASE WHEN ? IS NULL THEN address WHEN ? IS NOT NULL THEN ? END, "
			+ "phone_number = CASE WHEN ? IS NULL THEN phone_number WHEN ? IS NOT NULL THEN ? END, "
			+ "number_of_reservations = CASE WHEN ? IS NULL THEN number_of_reservations WHEN ? IS NOT NULL THEN ? END, "
			+ "overview = CASE WHEN ? IS NULL THEN overview WHEN ? IS NOT NULL THEN ? END "
			+ "WHERE hospital_id = ?";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * hospital_Listテーブルの全要素を取得
	 *
	 * @return resultList				取得した病院データ
	 * @throws	DataAccessException		データベースエラー
	 */
	public List<Map<String, Object>> selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_HOSPITAL_ALL);

		return resultList;

	}

	/**
	 * hospital_idに対応する病院をhospital_listテーブルから一件取得
	 *
	 * @param	hospital_id	病院ID
	 * @return	resultList	取得した病院データ
	 * @throws	DataAccessException	データベースエラー
	 */
	public List<Map<String, Object>> selectOne(String hospital_id) throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_HOSPITAL_ONE, hospital_id);

		return resultList;
	}

	/**
	 * hospital_idに対応するmedical_nameを取得
	 *
	 * @param	hospital_id				病院ID
	 * @return	HMList					病院IDと診療科名を格納したリスト
	 * @throws	DataAccessException		データベースエラー
	 */
	public List<Map<String, Object>> hospitalMedicaiList(String hospital_id) throws DataAccessException {

		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();

		HMList = jdbc.queryForList(SQL_SELECT_HOSPITAL_MEDICAL, hospital_id);

		return HMList;
	}

	/**
	 * Hospital_Listテーブルから条件に合致する要素を取得
	 *
	 * @param	hospital_name	病院名
	 * @param	address			病院住所
	 * @return	resultList		取得した病院データ
	 */
	public List<Map<String, Object>> searchHospital(String hospital_name, String address, String medical_name)
			throws DataAccessException {

		String searchHospital = "";
		String searchAddress = "";
		String searchMedical = "";

		if (hospital_name.isEmpty() == false) {
			searchHospital = "%" + hospital_name + "%";
		} else {
			searchHospital = null;
		}
		if (address.isEmpty() == false) {
			searchAddress = "%" + address + "%";
		} else {
			searchAddress = null;
		}
		if (medical_name.isEmpty() == false) {
			searchMedical = "%" + medical_name + "%";
		} else {
			searchMedical = null;
		}

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SEARCH_MEDICAL, searchHospital,
				searchAddress, searchMedical);

		return resultList;
	}

	//TODO
	/**
	 * hospital_table新規登録
	 *
	 * @param	form
	 * @return	Entity
	 */
	public List<Map<String, Object>> insertHospital(Hospital_medicalData hmData) throws DataAccessException {

		jdbc.update(SQL_INSERT_HOSPITAL, hmData.getHospital_id(), hmData.getHospital_name(),
				hmData.getEncrypted_password(), hmData.getAddress(), hmData.getPhone_number(),
				hmData.getNumber_of_reservations(), hmData.getOverview());

		if (hmData.getMedical_id().contains(",")) {
			String[] splitMedical = hmData.getMedical_id().split(",");
			for (int count = 0; count < splitMedical.length; count++) {
				jdbc.update(SQL_INSERT_MEDICAL, hmData.getHospital_id(), splitMedical[count]);
			}
		} else {
			jdbc.update(SQL_INSERT_MEDICAL, hmData.getHospital_id(), hmData.getMedical_id());
		}
		return selectAll();
	}

	/**
	 * 病院診療科テーブルに診療科を新規登録する
	 *
	 * @param hospital_id	登録する診療科をもつ病院の病院ID
	 * @param medical_id	登録する診療科の診療科ID
	 *
	 *
	 */
	public int insertMedical(String hospital_id, String medical_id) throws DataAccessException {
		int number = 0;

		if (medical_id.contains(",")) {
			String[] splitMedical = medical_id.split(",");
			for (int count = 0; count < splitMedical.length; count++) {
				number += jdbc.update(SQL_INSERT_MEDICAL, hospital_id, splitMedical[count]);
			}
		} else {
			number += jdbc.update(SQL_INSERT_MEDICAL, hospital_id, medical_id);
		}

		return number;
	}

	/**
	 * 選択された病院IDに対応する病院を削除する
	 *
	 * @param	hospital_id	削除する病院の病院ID
	 * @return	number		削除したデータ件数
	 * @throws	DataAccessException	データベースエラー
	 */
	public int deleteHospital(String hospital_id) throws DataAccessException {

		int number = 0;
		number += jdbc.update(SQL_DELETE_HOSPITAL, hospital_id);

		return number;
	}

	/**
	 * 病院の診療科を削除する
	 *
	 * @param hospital_id	削除する診療科をもつ病院の病院ID
	 * @return number		削除件数
	 * @throws DataAccessException
	 */
	public int deleteHospitalMedical(String hospital_id) throws DataAccessException {
		int number = 0;
		number += jdbc.update(SQL_DELETE_MEDICAL_HOSPITAL, hospital_id);

		return number;
	}

	/**
	 * 病院IDに対応する病院の情報を更新する
	 *
	 *
	 */
	public int updateHospital(Hospital_MedicalForm hmForm) throws DataAccessException {
		int number = 0;
		String hospital_name = "";
		String address = "";
		String phone_number = "";
		String number_of_reservations = "";
		String overview = "";

		if (hmForm.getHospital_name().isEmpty()) {
			hospital_name = null;
		} else if (hmForm.getHospital_name().isEmpty() == false) {
			hospital_name = hmForm.getHospital_name();
		}

		if (hmForm.getAddress().isEmpty()) {
			address = null;
		} else if (hmForm.getAddress().isEmpty() == false) {
			address = hmForm.getAddress();
		}

		if (hmForm.getPhone_number().isEmpty()) {
			phone_number = null;
		} else if (hmForm.getPhone_number().isEmpty() == false) {
			phone_number = hmForm.getAddress();
		}

		if (hmForm.getNumber_of_reservations().isEmpty()) {
			number_of_reservations = null;
		} else if (hmForm.getNumber_of_reservations().isEmpty() == false) {
			number_of_reservations = hmForm.getNumber_of_reservations();
		}

		if (hmForm.getOverview().isEmpty()) {
			overview = null;
		} else if (hmForm.getOverview().isEmpty() == false) {
			overview = hmForm.getOverview();
		}

		number += jdbc.update(SQL_UPDATE_HOSPITAL, hospital_name, hospital_name,
				hospital_name,
				hmForm.getEncrypted_password(), hmForm.getEncrypted_password(), hmForm.getEncrypted_password(),
				address,
				address, address, phone_number, phone_number,
				phone_number,
				number_of_reservations, number_of_reservations,
				number_of_reservations, overview,
				overview, overview, hmForm.getHospital_id());

		return number;
	}

}
