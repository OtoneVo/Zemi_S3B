package jp.ac.hcs.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserForm {

	/**
	 * ユーザID
	 */
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	@Length(max = 254, message = "{length_check}")
	private String user_id;

	/**
	 * パスワード
	 */
	@NotBlank(message = "{require_check}")
	@Length(min = 6, max = 254, message = "{length_check}")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "{pattern_check}")
	private String encrypted_password;

	/**
	 * ユーザ名
	 */
	@NotBlank(message = "{require_check}")
	@Length(max = 50, message = "{max_length_check}")
	private String user_name;

	/**
	 * ユーザ権限
	 */
	private String user_permission;

	/**
	 * 身体性別
	 */
	@NotBlank(message = "{require_check}")
	private String gender;

	/**
	 * 年齢
	 */
	@NotBlank(message = "{require_check}")
	private int age;

	/**
	 * 生年月日（年）
	 */
	@NotBlank(message = "{require_check}")
	private String birth_year;

	/**
	 * 生年月日（月）
	 */
	@NotBlank(message = "{require_check}")
	private String birth_month;

	/**
	 * 生年月日（日）
	 */
	@NotBlank(message = "{require_check}")
	private String birth_day;

	/**
	 * 住所
	 */
	@NotBlank(message = "{require_check}")
	private String address;

	/**
	 * 電話番号
	 */
	@NotBlank(message = "{require_check}")
	private String phone_number;
}