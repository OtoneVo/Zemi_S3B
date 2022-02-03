package jp.ac.hcs.reservation;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	/**
	 * ログイン中のユーザの予約リストを全件取得するメソッド
	 * @param user_id
	 * @return selectReservation
	 */
	public ReservationEntity selectReservation(String user_id) {
		ReservationEntity selectReservation;

		try {
			selectReservation = reservationRepository.reservationListAll(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			selectReservation = null;
		}

		return selectReservation;
	}

	/**
	 * 予約の検索を行う機能
	 *
	 * @param user_id
	 * @param hospital_name
	 * @param medical_name
	 * @param reservation_date
	 * @return
	 */
	public ReservationEntity searchReservation(String user_id, String hospital_name, String medical_name,
			Date reservation_date) {
		ReservationEntity searchReservation;

		try {
			searchReservation = reservationRepository.reservationListSearch(user_id, hospital_name, medical_name,
					reservation_date);
		} catch (DataAccessException e) {
			e.printStackTrace();
			searchReservation = null;
		}

		return searchReservation;
	}

	/**
	 * 病院予約新規作成するメソッド
	 * @param form
	 * @return
	 * @throws ParseException
	 */
	public boolean insertReservation(ReservationForm form) throws ParseException {

		int rowNumber = 0;

		ReservationData data = chenge(form);

		try {
			rowNumber = reservationRepository.reservationInsert(data);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return rowNumber > 0;

	}

	/**
	 * reservationFormに入力されたデータをreservationDataにsetする機能
	 * @param form
	 * @return data
	 */
	private ReservationData chenge(ReservationForm form) {

		ReservationData data = new ReservationData();

		data.setHospital_id(form.getHospital_id());
		data.setHospital_name(form.getHospital_name());
		data.setMedical_id(form.getMedical_id());
		data.setMedical_name(form.getMedical_name());
		data.setUser_id(form.getUser_id());
		data.setUser_name(form.getUser_name());
		data.setReservation_date(form.getReservation_date());
		data.setReservation_time(form.getReservation_time());

		return data;
	}
}
