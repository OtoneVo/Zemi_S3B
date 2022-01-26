package jp.ac.hcs.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 * ユーザ新規登録
	 *
	 * @param	userForm
	 * @return	userEntity
	 */
	public int userInsert(UserForm userForm, Date birth_date) {
		int number = 0;

		try {
			number = userRepository.insertOne(userForm, birth_date);
		} catch (DataAccessException e) {
			log.info("ユーザ追加：異常");
			throw e;
		}

		return number;
	}

	/**
	 * userFormの生年月日を結合する
	 * @throws ParseException
	 */
	public Date birthday(String birth_year, String birth_month, String birth_day) throws ParseException {

		String strDate = birth_year + "/" + birth_month + "/" + birth_day;
		Date date = null;

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			date = sdFormat.parse(strDate);
		} catch (ParseException e) {
			throw e;
		}

		return date;
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

	/**
	 * パスワードを更新する
	 *
	 * @return userData	取得したユーザデータ
	 */
	public UserData updatePassword(String user_id, String password) {

		UserData userData = new UserData();

		try {
			userData = userRepository.updatePassword(user_id, password);
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userData;

	}

}