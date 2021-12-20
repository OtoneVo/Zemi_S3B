package jp.ac.hcs.hospital;

import lombok.Data;

@Data
public class Hospital_medicalData {

	/**
	 * 病院ID	主キー、自動採番
	 */
	private String hospital_id;

	/**
	 * 病院名	非NULL、50文字以内
	 */
	private String hospital_name;

	/**
	 * 暗号化パスワード	非NULL、4文字以上254文字以内、半角英数字のみ
	 */
	private String encrypted_password;

	/**
	 * 病院住所	非NULL、254文字以内
	 */
	private String address;

	/**
	 * 病院電話番号	非NULL、20文字以内
	 */
	private String phone_number;

	/**
	 * 予約可能人数	非NULL、3文字以内
	 */
	private String number_of_reservations;

	/**
	 * 予約済み人数	非NULL、デフォルト0
	 */
	private int reservations_count;

	/**
	 * 概要	非NULL
	 */
	private String overview;

	/**
	 * 診療科ID
	 */
	private String medical_id;

	/**
	 * 診療科名
	 */
	private String medical_name;

}
