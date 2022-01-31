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
	 *	診療科ID
	 */
	@NotBlank(message = "{require_check}")
	private String medical_id;

	/**
	 * ユーザID
	 */
	@NotBlank(message = "{require_check}")
	private String user_id;

	/**
	 * 予約日時
	 */
	private String reservation_date;

	/**
	 * 予約時間
	 */
	private String reservation_time;

}
