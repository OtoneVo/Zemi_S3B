package jp.ac.hcs.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ情報を取得する
	 *
	 * @return userEntity	取得したユーザデータ
	 */
	public UserEntity getUsers() {

		UserEntity userEntity = new UserEntity();

		try {
			userEntity = userRepository.selectAll();
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userEntity;

	}

	/**
	 * ユーザ情報を削除する
	 *
	 * @return userEntity	取得したユーザデータ
	 */
	public UserEntity userDelete(String user_id) {

		UserEntity userEntity = new UserEntity();

		try {
			userEntity = userRepository.userDelete(user_id);
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userEntity;

	}

	/**
	 * ユーザ情報更新画面に遷移する
	 *
	 * @return userData	取得したユーザデータ
	 */
	public UserData userUpdatetRansition(String user_id) {

		UserData userData = new UserData();

		try {
			userData = userRepository.userUpdatetRansition(user_id);
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userData;

	}

	/**
	 * ユーザ名を更新する
	 *
	 * @return userData	取得したユーザデータ
	 */
	public UserData updateUserName(String user_id, String user_name) {

		UserData userData = new UserData();

		try {
			userData = userRepository.updateUserName(user_id, user_name);
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userData;

	}

	/**
	 * 住所を更新する
	 *
	 * @return userData	取得したユーザデータ
	 */
	public UserData updateAddress(String user_id, String address) {

		UserData userData = new UserData();

		try {
			userData = userRepository.updateAddress(user_id, address);
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userData;

	}

	/**
	 * 電話番号を更新する
	 *
	 * @return userData	取得したユーザデータ
	 */
	public UserData updatePhoneNumber(String user_id, String phone_number) {

		UserData userData = new UserData();

		try {
			userData = userRepository.updatePhoneNumber(user_id, phone_number);
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userData;

	}
}