package jp.ac.hcs.hospital;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class Hospital_MedicalForm {

	/**
	 * 病院ID
	 */
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	@Length(max = 254, message = "{length_check}")
	private String hospital_id;

	/**
	 * 病院名
	 */
	@NotBlank(message = "{require_check}")
	@Length(max = 50, message = "{max_length_check}")
	private String hospital_name;

	/**
	 * パスワード
	 */
	@NotBlank(message = "{require_check}")
	@Length(min = 6, max = 254, message = "{length_check}")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "{pattern_check}")
	private String encrypted_password;

	/**
	 * 住所
	 */
	@NotBlank(message = "{require_check}")
	@Length(max = 254, message = "{max_length_check}")
	private String address;

	/**
	 * 電話番号
	 */
	@NotBlank(message = "{require_check}")
	private String phone_number;

	/**
	 * 予約可能人数
	 */
	@NotBlank(message = "{require_check}")
	@Length(max = 3, message = "{max_length_check}")
	private String number_of_reservations;

	/**
	 * 予約済み人数
	 */
	@NotBlank(message = "{require_check}")
	private int reservations_count;

	/**
	 * 概要
	 */
	@NotBlank(message = "{require_check}")
	private String overview;

	/**
	 * 診療科ID
	 */
	@NotBlank(message = "{require_check}")
	private String medical_id;
	
	/**
	 * 診療科名
	 */
	@NotBlank(message = "{require_check}")
	private String medical_name;

}
