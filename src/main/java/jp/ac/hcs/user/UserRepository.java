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
 * ユーザ情報を管理する - m_userテーブル
 */
@Repository
public class UserRepository {

	@Autowired
	PasswordEncoder passwordEncoder;

	/** SQLユーザ情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user ORDER BY user_id";

	/** SQLユーザ情報全件取得 */
	private static final String SQL_USER_DELETE = "DELETE FROM m_user WHERE user_id = ?";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * m_userテーブルに存在する全てのユーザ情報を取得
	 *
	 * @return hospitalEntity	取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserEntity selectAll() throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);

		return userEntity;

	}

	/**
	 * m_userテーブルのユーザ情報を一件削除
	 *
	 * @return userEntity	取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserEntity userDelete(String user_id) throws DataAccessException {

		jdbc.update(SQL_USER_DELETE, user_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);

		return userEntity;

	}

	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {

		//必要な変数を宣言
		UserEntity userEntity = new UserEntity();
		UserData userData = new UserData();

		for (Map<String, Object> map : resultList) {

			//必要な変数を宣言
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
			String birth_date = sdf.format(map.get("birth_date"));

			//UserDataに適切な型に変換し、セットする
			userData.setUser_id((String) map.get("user_id"));
			userData.setUser_name((String) map.get("user_name"));
			userData.setUser_permission((String) map.get("user_permission"));
			userData.setGender((String) map.get("gender"));
			userData.setAge((int) map.get("age"));
			userData.setBirth_date(birth_date);
			userData.setAddress((String) map.get("address"));
			userData.setPhone_number((String) map.get("phone_number"));

			//ユーザ権限表示変換
			switch (userData.getUser_permission()) {
			case "HOSPITAL":
				userData.setUser_permission("病院");
				break;
			case "GENERAL":
				userData.setUser_permission("一般");
				break;
			case "ADMIN":
				userData.setUser_permission("管理者");
				break;
			}

			//身体性別表示変換
			switch (userData.getGender()) {
			case "1":
				userData.setGender("男");
				break;
			case "2":
				userData.setGender("女");
				break;
			}

			userEntity.getUserList().add(userData);

		}

		return userEntity;

	}

}
