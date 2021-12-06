package jp.ac.hcs.hospital;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 病院情報のデータを管理する - hospital_Listテーブル
 */
@Repository
public class HospitalRepository {

	/** SQL病院情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM hospital_List ORDER BY hospital_id";

	/** SQL病院検索結果取得 */
	private static final String SQL_SELECT_SEARCH = "SELECT * FROM hospital_List "
			+ "WHERE CASE WHEN hospital_name <> '' THEN hospital_name = ?"
			+ "WHEN address <> '' THEN address = ?"
			+ "ORDER BY hospital_id";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * hospital_Listテーブルの全要素を取得
	 *
	 * @return hospitalEntity	取得した病院データ
	 * @throws	DataAccessException	データベースエラー
	 */
	public HospitalEntity selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
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
	 * Hospital_Listテーブルから取得したデータをHospitalEntity形式にマッピングする
	 *
	 * @param	resultList 		Hospital_Listテーブルから取得したデータ
	 * @return HospitalEntity	変換されたhospitalEntityデータ
	 */
	private HospitalEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {
		HospitalEntity hospitalEntity = new HospitalEntity();

		for (Map<String, Object> map : resultList) {
			HospitalData data = new HospitalData();
			data.setHospital_id((String) map.get("hospital_id"));
			data.setHospital_name((String) map.get("hospital_name"));
			data.setEncrypted_password((String) map.get("encrypted_password"));
			data.setAddress((String) map.get("address"));
			data.setPhone_number((String) map.get("phone_number"));
			data.setNumber_of_reservations((String) map.get("number_of_reservations"));
			data.setReservations_count((Integer) map.get("reservations_count"));

			hospitalEntity.getHospitalList().add(data);
		}
		return hospitalEntity;
	}

}
