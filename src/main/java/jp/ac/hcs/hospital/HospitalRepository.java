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

	/** SQL病院IDに対応する診療科名を取得 */
	private static final String SQL_SELECT_HOSPITAL_MEDICAL = "SELECT H.hospital_id, M.medical_name FROM hospital_List H, hospital_medical_List HM, medical_List M WHERE H.hospital_id = HM.hospital_id AND HM.medical_id = M.medical_id AND H.hospital_id = ?";

	/** SQL病院検索結果取得 */
	private static final String SQL_SELECT_SEARCH = "SELECT * FROM hospital_List "
			+ "WHERE CASE WHEN hospital_name <> '' THEN hospital_name LIKE '%?%'"
			+ "WHEN address <> '' THEN address = ?"
			+ "ORDER BY hospital_id";

	/** SQL病院新規登録 */
	private static final String SQL_INSERT_HOSPITAL = "INSERT INTO hospital_List(hospital_id, hospital_name, encrypted_password, address, phone_number, number_of_reservations) VALUES(?, ?, ?, ?, ?, ?)";

	/** SQL診療科新規登録 */
	private static final String SQL_INSERT_MEDICAL = "INSERT INTO hospital_medical_list(hospital_id, medical_id) VALUES (?, ?)";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * hospital_Listテーブルの全要素を取得
	 *
	 * @return hospitalEntity	取得した病院データ
	 * @throws	DataAccessException	データベースエラー
	 */
	public List<Map<String, Object>> selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_HOSPITAL_ALL);

		return resultList;

	}

	/**
	 * hospital_idに対応するmedical_nameを取得
	 *
	 *
	 */
	public List<Map<String, Object>> hospitalMedicaiList(String hospital_id) {

		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();

		HMList = jdbc.queryForList(SQL_SELECT_HOSPITAL_MEDICAL, hospital_id);

		return HMList;
	}

	/**
	 * Hospital_Listテーブルから条件に合致する要素を取得
	 *
	 * @param	hospital_name	病院名
	 * @param	address			病院住所
	 * @return	hospitalEntity	取得した病院データ
	 */
	public HospitalEntity selectSearch(String hospital_name, String address) throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_SEARCH, hospital_name, address);
		HospitalEntity hospitalEntity = mappingSelectResult(resultList);

		return hospitalEntity;
	}

	/**
	 * Hospital_Listテーブルから取得したデータをHospitalEntity形式にマッピングする
	 *
	 * @param	resultList 		Hospital_Listテーブルから取得したデータ
	 * @return HospitalEntity	変換されたhospitalEntityデータ
	 */
	private HospitalEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {
		HospitalEntity hospitalEntity = new HospitalEntity();

		for (Map<String, Object> map : resultList) {
			HospitalData hData = new HospitalData();

			hData.setHospital_id((String) map.get("hospital_id"));
			hData.setHospital_name((String) map.get("hospital_name"));
			hData.setEncrypted_password((String) map.get("encrypted_password"));
			hData.setAddress((String) map.get("address"));
			hData.setPhone_number((String) map.get("phone_number"));
			hData.setNumber_of_reservations((String) map.get("number_of_reservations"));
			hData.setReservations_count((Integer) map.get("reservations_count"));

			hospitalEntity.getHospitalList().add(hData);

		}
		return hospitalEntity;
	}

}
