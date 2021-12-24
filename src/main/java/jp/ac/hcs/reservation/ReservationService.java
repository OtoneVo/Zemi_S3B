package jp.ac.hcs.reservation;

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
		} catch(DataAccessException e) {
			e.printStackTrace();
			selectReservation = null;
		}

		return selectReservation;
	}

	public ReservationEntity searchReservation(String user_id, String hospital_name, String medical_name,Date reservation_date) {
		ReservationEntity searchReservation;

		try {
			searchReservation = reservationRepository.reservationListSearch(user_id, hospital_name, medical_name, reservation_date);
		} catch(DataAccessException e) {
			e.printStackTrace();
			searchReservation = null;
		}

		return searchReservation;
	}
}
