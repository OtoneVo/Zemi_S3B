package jp.ac.hcs.user;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	/** SQLユーザ情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user WHERE user_permission <> 'ADMIN'";

	/** SQLユーザ情報一件削除 */
	private static final String SQL_USER_DELETE = "DELETE FROM m_user WHERE user_id = ?";

	/** SQLユーザ情報一件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM m_user WHERE user_id = ?";

	/** SQLユーザ名を一件更新 */
	private static final String SQL_USER_NAME_UPDATE = "UPDATE m_user SET user_name = ? WHERE user_id = ?";

	/** SQL住所を一件更新 */
	private static final String SQL_ADDRESS_UPDATE = "UPDATE m_user SET address = ? WHERE user_id = ?";

	/** SQL電話番号を一件更新 */
	private static final String SQL_PHONE_NUMBER_UPDATE = "UPDATE m_user SET phone_number = ? WHERE user_id = ?";

	/** SQLパスワードを一件更新 */
	private static final String SQL_PASSWORD_UPDATE = "UPDATE m_user SET encrypted_password = ? WHERE user_id = ?";

	/** SQLユーザ追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO m_user(user_id, encrypted_password, user_name, user_permission, gender, age, birth_date, address, phone_number) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/** SQLユーザ検索 */
	private static final String SQL_SEARCH_USER = "SELECT * FROM m_user WHERE user_id = CASE WHEN ? IS NULL THEN user_id ELSE ? END "
			+ "AND user_name = CASE WHEN ? IS NULL THEN user_name ELSE ? END "
			+ "AND user_permission = CASE WHEN ? IS NULL THEN user_permission ELSE ? END "
			+ "AND gender = CASE WHEN ? IS NULL THEN gender ELSE ? END "
			+ "AND phone_number = CASE WHEN ? IS NULL THEN phone_number ELSE ? END";

	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	PasswordEncoder passwordEncoder;

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
	 * m_userテーブルのユーザ情報を一件取得
	 *
	 * @return userData	取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserData userUpdatetRansition(String user_id) throws DataAccessException {

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, user_id);
		UserData userData = mappingSelectResultOne(resultList);

		return userData;

	}

	/**
	 * m_userテーブルに一般権限のユーザを一件追加する
	 *
	 * @param	userForm	追加するユーザの情報
	 */
	public int insertOne(UserForm userForm, Date birth_date) throws DataAccessException {

		int number = 0;

		number = jdbc.update(SQL_INSERT_ONE, userForm.getUser_id(),
				passwordEncoder.encode(userForm.getEncrypted_password()),
				userForm.getUser_name(), "GENERAL", userForm.getGender(), userForm.getAge(),
				birth_date, userForm.getAddress(), userForm.getPhone_number());

		return number;
	}

	/**
	 * m_userテーブルのユーザ情報を一件削除
	 *
	 * @return userEntity	取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserEntity userDelete(String user_id) throws DataAccessException {

		jdbc.update(SQL_USER_DELETE, user_id);

		return selectAll();

	}

	/**
	 * m_userテーブルのユーザ名を一件更新
	 *
	 * @return UserData 取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserData updateUserName(String user_id, String user_name) throws DataAccessException {

		jdbc.update(SQL_USER_NAME_UPDATE, user_name, user_id);

		return userUpdatetRansition(user_id);

	}

	/**
	 * m_userテーブルの住所を一件更新
	 *
	 * @return UserData 取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserData updateAddress(String user_id, String address) throws DataAccessException {

		jdbc.update(SQL_ADDRESS_UPDATE, address, user_id);

		return userUpdatetRansition(user_id);

	}

	/**
	 * m_userテーブルの電話番号を一件更新
	 *
	 * @return UserData 取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserData updatePhoneNumber(String user_id, String phone_number) throws DataAccessException {

		jdbc.update(SQL_PHONE_NUMBER_UPDATE, phone_number, user_id);

		return userUpdatetRansition(user_id);

	}

	/**
	 * m_userテーブルのパスワードをチェック
	 *
	 * @return UserData 取得したユーザデータ
	 * @throws	DataAccessException	データベースエラー
	 */
	public UserData updatePassword(String user_id, String password) throws DataAccessException {

		password = passwordEncoder.encode(password);
		jdbc.update(SQL_PASSWORD_UPDATE, password, user_id);

		return userUpdatetRansition(user_id);

	}

	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {

		//必要な変数を宣言
		UserEntity userEntity = new UserEntity();

		for (Map<String, Object> map : resultList) {

			//必要な変数を宣言
			UserData userData = new UserData();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
			String birth_date = sdf.format(map.get("birth_date"));

			//UserDataに適切な型に変換し、セットする
			userData.setUser_id((String) map.get("user_id"));
			userData.setUser_name((String) map.get("user_name"));
			userData.setEncrypted_password((String) map.get("encrypted_password"));
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

	private UserData mappingSelectResultOne(List<Map<String, Object>> resultList) throws DataAccessException {

		//必要な変数を宣言
		UserData userData = new UserData();

		for (Map<String, Object> map : resultList) {

			//必要な変数を宣言

			SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
			String birth_date = sdf.format(map.get("birth_date"));

			//UserDataに適切な型に変換し、セットする
			userData.setUser_id((String) map.get("user_id"));
			userData.setUser_name((String) map.get("user_name"));
			userData.setEncrypted_password((String) map.get("encrypted_password"));
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

		}

		return userData;

	}

	/**
	 * ユーザ検索機能
	 *
	 * @param	user_id
	 * @param	user_name
	 * @param	user_permission
	 * @param	gender
	 * @param	phone_number
	 * @return	userEntity
	 * @throws	DataAccessException
	 */
	public UserEntity userSearch(String user_id, String user_name, String user_permission, String gender,
			String phone_number) throws DataAccessException {

		String id = "";
		String name = "";
		String permission = "";
		String gen = "";
		String phone = "";

		if (user_id.isEmpty()) {
			id = null;
		} else {
			id = user_id;
		}

		if (user_name.isEmpty()) {
			name = null;
		} else {
			name = user_name;
		}

		if (user_permission.isEmpty()) {
			permission = null;
		} else {
			permission = user_permission;
		}

		if (gender.isEmpty()) {
			gen = null;
		} else {
			gen = gender;
		}

		if (phone_number.isEmpty()) {
			phone = null;
		} else {
			phone = phone_number;
		}

		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SEARCH_USER, id, id, name,
				name, permission, permission, gen, gen, phone, phone);
		UserEntity userEntity = mappingSelectResult(resultList);

		return userEntity;
	}

}
