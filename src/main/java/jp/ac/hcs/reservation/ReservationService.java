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
	 *
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
	 * 病院IDに対応する予約を取得する機能
	 */
	public ReservationEntity getReservationHospital(String hospital_id) {

		ReservationEntity reservationEntity = new ReservationEntity();

		// repository作成
		try {
			reservationEntity = reservationRepository.hospitalReservation(hospital_id);
			reservationEntity = reservationRepository.hospitalReservationAdd(reservationEntity);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return reservationEntity;
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

	public ReservationEntity hospitalInfo(String hospital_id) {

		ReservationEntity hospitalInfo;

		try {
			hospitalInfo = reservationRepository.hospitalInfo(hospital_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			hospitalInfo = null;
		}

		return hospitalInfo;
	}

	/**
	 * 病院予約新規作成するメソッド
	 *
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
	 * 予約画面遷移時に取得した診療科データをカンマ区切りで配列に格納する機能
	 *
	 * @param medical_name
	 * @return medicalList
	 */
	//	public List<String> getMedicalList(String medical_name) {
	//
	//		List<String> medicalList = new ArrayList<String>();
	//		String medicals = "";
	//
	//		medicals = medical_name;
	//
	//		String[] medicalAddList = medicals.split(",");
	//		for (int i = 0; i < medicalAddList.length; i++) {
	//			medicalList.add(medicalAddList[i]);
	//		}
	//
	//		return medicalList;
	//	}

	/**
	 * 病院IDから診療科IDと診療科名を取得する
	 */
	public ReservationEntity getHospitalMedical(String hospital_id) {

		ReservationEntity entity = new ReservationEntity();

		try {
			entity = reservationRepository.hospitalMedical(hospital_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return entity;
	}

	/**
	 * reservationFormに入力されたデータをreservationDataにsetする機能
	 *
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
