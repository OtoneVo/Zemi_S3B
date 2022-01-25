package jp.ac.hcs.user;

import java.util.Date;

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
	private String gender;

	/**
	 * 年齢
	 */
	private int age;

	/**
	 * 生年月日
	 */
	private Date birth_date;
}