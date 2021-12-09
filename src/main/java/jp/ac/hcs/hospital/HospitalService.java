package jp.ac.hcs.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.medical.MedicalEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HospitalService {

	@Autowired
	HospitalRepository hospitalRepository;

	/**
	 * 病院の情報を取得する
	 *
	 * @return hospitalEntity	取得した病院データ
	 */
	public HospitalEntity getHospitals() {

		HospitalEntity hospitalEntity = new HospitalEntity();

		try {
			hospitalEntity = hospitalRepository.selectAll();
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return hospitalEntity;

	}

	/**
	 * 検索条件に合致する病院を一覧表示する
	 *
	 * @param	hospital_name	病院名
	 * @param	address			病院住所
	 * @return	hospitalEntity	取得した病院データ
	 */
	public HospitalEntity getHospitalSearch(String hospital_name, String address) {

		HospitalEntity hospitalEntity = new HospitalEntity();

		try {
			hospitalEntity = hospitalRepository.selectSearch(hospital_name, address);
		} catch (DataAccessException e) {
			log.info("病院検索:異常発生");
			throw e;
		}
		return hospitalEntity;
	}

	/**
	 * 病院を新規登録する
	 *
	 * @param	hospitalData	入力された病院データ
	 * @return
	 */
	/*
	public HospitalEntity getHospitalInsert(HospitalForm hForm) {

		HospitalData hospitalData = new HospitalData();

		hospitalData.setHospital_id(hForm.getHospital_id());
		hospitalData.setHospital_name(hForm.getHospital_name());
		hospitalData.setEncrypted_password(hForm.getEncrypted_password());
		hospitalData.setAddress(hForm.getAddress());
		hospitalData.setPhone_number(hForm.getPhone_number());
		hospitalData.setNumber_of_reservations(hForm.getNumber_of_reservations());

		try {
			hospitalRepository.insertOne(hospitalData);
		} catch (DataAccessException e) {
			log.info("病院新規登録：異常");
			throw e;
		}

		return getHospitals();
	}
	*/
	/**
	 * 診療科テーブルの内容を取得する
	 *
	 * @return	medicalEntity	取得した診療科データ
	 */
	public MedicalEntity getMedicals() {
		MedicalEntity medicalEntity = new MedicalEntity();

		try {
			medicalEntity = hospitalRepository.selectMedicals();
		} catch (DataAccessException e) {
			log.info("診療科全件取得：異常");
			throw e;
		}

		return medicalEntity;
	}

}
