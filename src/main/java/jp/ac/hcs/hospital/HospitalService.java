package jp.ac.hcs.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
	public List<Map<String, Object>> getHospitals() {

		//HospitalEntity hospitalEntity = new HospitalEntity();

		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();

		try {
			hospitalList = hospitalRepository.selectAll();
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return hospitalList;

	}

	/**
	 * 病院IDから診療科名を取得する
	 *
	 * @param hospital_id	病院ID
	 * @return	Hospital_medicalEntity
	 */
	public List<Map<String, Object>> getHospitalMedicals(List<Map<String, Object>> hospitalList) {

		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();

		for (int count = 0; count < hospitalList.size(); count++) {
			String hospitalId = (String) hospitalList.get(count).get("hospital_id");
			try {
				HMList.addAll(hospitalRepository.hospitalMedicaiList(hospitalId));
			} catch (DataAccessException e) {
				log.info("全件取得:異常発生");
				throw e;
			}
		}
		return HMList;
	}

	/**
	 * 病院が所持する診療科名をカンマ区切りで格納する
	 */
	public Hospital_medicalEntity getHospitalMedicalSplit(List<Map<String, Object>> hospitalList,
			List<Map<String, Object>> HMList) {
		Hospital_medicalEntity HMEntity = new Hospital_medicalEntity();

		for (Map<String, Object> map : hospitalList) {
			Hospital_medicalData hmData = new Hospital_medicalData();

			hmData.setHospital_id((String) map.get("hospital_id"));
			hmData.setHospital_name((String) map.get("hospital_name"));
			hmData.setEncrypted_password((String) map.get("encrypted_password"));
			hmData.setAddress((String) map.get("address"));
			hmData.setPhone_number((String) map.get("phone_number"));
			hmData.setNumber_of_reservations((String) map.get("number_of_reservations"));
			hmData.setReservations_count((Integer) map.get("reservations_count"));

			String medicalName = "";

			for (int num = 0; num < HMList.size(); num++) {
				if (hmData.getHospital_id().equals(HMList.get(num).get("hospital_id"))) {
					if (medicalName.isEmpty()) {
						medicalName = (String) HMList.get(num).get("medical_name");
					} else {
						medicalName += "," + (String) HMList.get(num).get("medical_name");
					}
				}
			}
			hmData.setMedical_name(medicalName);

			HMEntity.getHospital_medicalList().add(hmData);
		}

		return HMEntity;
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
}
