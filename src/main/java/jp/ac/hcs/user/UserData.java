package jp.ac.hcs.user;

import lombok.Data;

/**
 * 1件分のユーザ情報
 */
@Data
public class UserData {

	/**
	 * ユーザID(メールアドレス形式)
	 */
	private String user_id;

	/**
	 * 暗号化パスワード
	 */
	private String encrypted_password;

	/**
	 * ユーザ名
	 */
	private String user_name;

	/**
	 * ユーザ権限(管理者：ADMIN、一般：GENERAL、病院：HOSPITAL)
	 */
	private String user_permission;

	/**
	 * 身体性別
	 */
	private String gender;

	/**
	 * 年齢
	 */
	private int age;

	/**
	 * 生年月日
	 */
	private String birth_date;

	/**
	 * 住所
	 */
	private String address;

	/**
	 * 電話番号
	 */
	private String phone_number;

}