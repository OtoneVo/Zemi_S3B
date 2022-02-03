package jp.ac.hcs.reservation;

import lombok.Data;

@Data
public class ReservationData {

	/** 病院ID 主キー*/
	private String hospital_id;
	/** 病院名*/
	private String hospital_name;
	/** 診療科ID 主キー */
	private String medical_id;
	/** 診療科名*/
	private String medical_name;
	/** ユーザID 主キー */
	private String user_id;
	/** ユーザ名 */
	private String user_name;
	/** 予約日 主キー */
	private String reservation_date;
	/** 予約時間 */
	private String reservation_time;
	/** 予約可能人数 */
	private String number_of_reservations;
	/** 予約人数 */
	private String reservations_count;

}
