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

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * hospital_Listテーブルの全要素を取得
	 *
	 * @return hospitalEntity
	 */
	public HospitalEntity selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		HospitalEntity hospitalEntity = mappingSelectResult(resultList);

		return hospitalEntity;

	}

	/**
	 * Taskテーブルから取得したデータをTaskEntity形式にマッピングする
	 *
	 * @param resultList Taskテーブルから取得したデータ
	 * @return TaskEntity
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
