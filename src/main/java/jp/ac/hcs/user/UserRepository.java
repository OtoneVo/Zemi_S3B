package jp.ac.hcs.user;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * ユーザ情報のデータを管理する - m_userテーブル
 */
@Repository
public class UserRepository {

	@Autowired
	PasswordEncoder passwordEncoder;

	/** SQLユーザ情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user ORDER BY user_id";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * m_userテーブルの全要素を取得
	 *
	 * @return hospitalEntity	取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserEntity selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);

		return userEntity;

	}

	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {
		UserEntity userEntity = new UserEntity();

		for (Map<String, Object> map : resultList) {

			UserData userData = new UserData();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
			String birth_date = sdf.format(map.get("birth_date"));

			userData.setUser_id((String) map.get("user_id"));
			userData.setUser_name((String) map.get("user_name"));
			userData.setUser_permission((String) map.get("user_permission"));
			userData.setGender((String) map.get("gender"));
			userData.setAge((int) map.get("age"));
			userData.setBirth_date(birth_date);
			userData.setAddress((String) map.get("address"));
			userData.setPhone_number((String) map.get("phone_number"));

			userEntity.getUserList().add(userData);
		}
		return userEntity;
	}
}
