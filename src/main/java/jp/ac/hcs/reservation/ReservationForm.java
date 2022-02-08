package jp.ac.hcs.reservation;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReservationForm {

	/**
	 * 病院ID
	 */
	@NotBlank(message = "{require_check}")
	private String hospital_id;

	/**
	 * 病院名
	 */
	@NotBlank(message = "{require_check}")
	private String hospital_name;

	/**
	 *	診療科ID
	 */
	@NotBlank(message = "{require_check}")
	private String medical_id;

	/**
	 * 診療科名
	 */
	@NotBlank(message = "{require_check}")
	private String medical_name;

	/**
	 * ユーザID
	 */
	@NotBlank(message = "{require_check}")
	private String user_id;

	/**
	 * ユーザ名
	 */
	@NotBlank(message = "{require_check}")
	private String user_name;

	/**
	 * 予約日時
	 */
	@NotBlank(message = "{require_check}")
	private String reservation_date;

	/**
	 * 予約時間
	 */
	@NotBlank(message = "{require_check}")
	private String reservation_time;

}
