package jp.ac.hcs.hospital;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.medical.MedicalData;
import jp.ac.hcs.medical.MedicalEntity;

/**
 * 病院情報のデータを管理する - hospital_Listテーブル
 */
@Repository
public class HospitalRepository {

	@Autowired
	PasswordEncoder passwordEncoder;

	/** SQL病院情報全件取得 */
	private static final String SQL_SELECT_HOSPITAL_ALL = "SELECT * FROM hospital_List ORDER BY hospital_id";

	/** 病院にある診療科を取得 */
	private static final String SQL_SELECT_HOSPITAL_MEDICAL = "SELECT medical_id FROM hospital_medical_List WHERE hospital_id = (SELECT hospital_id FROM hospital_List WHERE hospital_id = '?')";

	/** SQL診療科全件取得 */
	private static final String SQL_SELECT_MEDICAL_ALL = "SELECT * FROM medical_List ORDER BY medical_id";

	/** SQL病院診療科全件取得 */
	private static final String SQL_SELECT_HOSPITAL_MEDICAL_ALL = "SELECT * FROM hospital_medical_List";

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
	public HospitalEntity selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_HOSPITAL_ALL);

		HospitalEntity hospitalEntity = mappingSelectResult(resultList);

		return hospitalEntity;

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
	 * Hospital_medical_Listからmedical_idを取得する
	 *
	 * @param	hospital_id	病院ID
	 * @return	medical_id	診療科ID
	 */
	public String selectHospitalMedical(String hospital_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_HOSPITAL_MEDICAL, hospital_id);
		String medical_id = (String) resultList.get(0).get("hospital_id");

		return medical_id;
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

	/**
	 * Hospital_Listに入力されたデータを格納する
	 *
	 * @param	hospitalData	入力された病院データ
	 * @param	medicalData		入力された診療科データ
	 * @return	rowNumber		入力件数
	 */
	/*
	public int insertOne(HospitalData hospitalData) throws DataAccessException {

		int rowNumber = jdbc.update(SQL_INSERT_HOSPITAL, hospitalData.getHospital_id(), hospitalData.getHospital_name(),
				passwordEncoder.encode(hospitalData.getEncrypted_password()), hospitalData.getAddress(),
				hospitalData.getPhone_number(), hospitalData.getNumber_of_reservations());

		List<String> medicalList = Arrays.asList(hospitalData.getMedical_id().split(",", 0));

		for (String medicalId : medicalList) {
			if (medicalId != null && medicalId.isEmpty() == false) {
				rowNumber += jdbc.update(SQL_INSERT_MEDICAL, hospitalData.getHospital_id(),
						hospitalData.getMedical_id());
			}
		}

		return rowNumber;
	}
	*/

	/**
	 * Medical_Listの内容を取得する
	 *
	 * @return	medicalEntity	取得した診療科データ
	 */
	public MedicalEntity selectMedicals() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_MEDICAL_ALL);
		MedicalEntity medicalEntity = mappingSelectMedicalResult(resultList);

		return medicalEntity;
	}

	/**
	 * Medical_Listテーブルから取得したデータをMedicalEntity形式にマッピングする
	 *
	 * @param	resultList 		Medical_Listテーブルから取得したデータ
	 * @return medicalEntity	変換されたmedicalEntityデータ
	 */
	private MedicalEntity mappingSelectMedicalResult(List<Map<String, Object>> resultList) throws DataAccessException {
		MedicalEntity medicalEntity = new MedicalEntity();

		for (Map<String, Object> map : resultList) {
			MedicalData mData = new MedicalData();
			mData.setMedical_id((String) map.get("medical_id"));
			mData.setMedical_name((String) map.get("medical_name"));

			medicalEntity.getMedicalList().add(mData);

		}
		return medicalEntity;
	}

	/**
	 * hospital_medical_Listの内容を取得する
	 *
	 * @return	hospital_medicalEntity	取得した病院診療科テーブルのデータ
	 */
	public Hospital_medicalEntity getHospital_medicalEntity() {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_HOSPITAL_MEDICAL_ALL);
		Hospital_medicalEntity hospital_medicalEntity = mappingSelectHospitalMedicalResult(resultList);

		return hospital_medicalEntity;
	}

	/**
	 * hospital_medical_Listから取得したデータをHospital_medicalEntity形式にマッピングする
	 *
	 * @param	resultList 		hospital_medical_Listテーブルから取得したデータ
	 * @return hospital_medicalEntity	変換されたHospital_medicalEntityデータ
	 */
	private Hospital_medicalEntity mappingSelectHospitalMedicalResult(List<Map<String, Object>> resultList)
			throws DataAccessException {
		Hospital_medicalEntity hospital_medicalEntity = new Hospital_medicalEntity();

		for (Map<String, Object> map : resultList) {
			Hospital_medicalData HMData = new Hospital_medicalData();

			HMData.setHospital_id((String) map.get("hospital_id"));
			HMData.setMedical_id((String) map.get("medical_id"));

			hospital_medicalEntity.getHospital_medicalList().add(HMData);

		}

		return hospital_medicalEntity;
	}

}
