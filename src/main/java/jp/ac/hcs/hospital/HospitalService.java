package jp.ac.hcs.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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

		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();

		try {
			hospitalList = hospitalRepository.selectAll();
		} catch (DataAccessException e) {
			log.info("全件取得：異常");
			throw e;
		} catch (Exception e) {
			log.info("全件取得：想定外のエラー");
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
				log.info("病院・診療科取得：異常");
				throw e;
			} catch (Exception e) {
				log.info("病院・診療科取得：想定外のエラー");
				throw e;
			}
		}
		return HMList;
	}

	/**
	 * 病院一覧に表示する病院データと診療科データをHospital_medicalEntity形式にマッピングする
	 *
	 * @param	hospitalList	病院一覧から取得した病院データ
	 * @param	medicalList		診療科一覧から取得した診療科データ
	 * @return	HMEntity		マッピングしたHospital_medicalEntity形式のデータ
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
			hmData.setOverview((String) map.get("overview"));

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
	 * @param	medical_name	診療科名
	 * @return	searchHospitalList	取得した病院データ
	 */
	public List<Map<String, Object>> getHospitalSearch(String hospital_name, String address, String medical_name) {

		List<Map<String, Object>> searchHospitalList = new ArrayList<Map<String, Object>>();

		try {
			searchHospitalList = hospitalRepository.searchHospital(hospital_name, address, medical_name);
		} catch (DataAccessException e) {
			log.info("病院検索：異常");
			throw e;
		} catch (Exception e) {
			log.info("病院検索：想定外のエラー");
			throw e;
		}
		return searchHospitalList;
	}

	/**
	 * 病院を新規登録する
	 *
	 * @param	hospitalData	入力された病院データ
	 * @return
	 */
	public List<Map<String, Object>> getHospitalInsert(HospitalForm hForm) {

		Hospital_medicalData hmData = new Hospital_medicalData();
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();

		hmData.setHospital_id(hForm.getHospital_id());
		hmData.setHospital_name(hForm.getHospital_name());
		hmData.setEncrypted_password(hForm.getEncrypted_password());
		hmData.setAddress(hForm.getAddress());
		hmData.setPhone_number(hForm.getPhone_number());
		hmData.setNumber_of_reservations(hForm.getNumber_of_reservations());
		hmData.setOverview(hForm.getOverview());
		hmData.setMedical_id(hForm.getMedical_id());

		try {
			hospitalList = hospitalRepository.insertHospital(hmData);
		} catch (DataAccessException e) {
			log.info("病院新規登録：異常");
			throw e;
		} catch (Exception e) {
			log.info("病院新規登録：想定外のエラー");
			throw e;
		}

		return hospitalList;
	}

	/**
	 * 病院詳細を表示する
	 *
	 * @param	hospital_id		取得する病院の病院ID
	 * @return	hospitalList	取得した病院データ
	 */
	public List<Map<String, Object>> getHospitalDetail(String hospital_id) {

		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();

		try {
			hospitalList = hospitalRepository.selectOne(hospital_id);
		} catch (DataAccessException e) {
			log.info("病院詳細：異常");
			throw e;
		} catch (Exception e) {
			log.info("病院詳細：想定外のエラー");
			throw e;
		}

		return hospitalList;

	}

	/**
	 * 病院を削除する
	 */
	public boolean getDeleteHospital(String hospital_id) {

		int number = 0;

		try {
			number = hospitalRepository.deleteHospital(hospital_id);
		} catch (DataAccessException e) {
			log.info("病院削除：異常");
			throw e;
		} catch (Exception e) {
			log.info("病院削除：想定外のエラー");
			throw e;
		}

		if (number == 0) {
			return false;
		}

		return true;

	}

	/**
	 * 病院の情報を更新する
	 */
	public boolean getHospitalUpdate(Hospital_MedicalForm hmForm) {
		int number = 0;

		try {
			number += hospitalRepository.updateHospital(hmForm);
			number += hospitalRepository.deleteHospitalMedical(hmForm.getHospital_id());
			number += hospitalRepository.insertMedical(hmForm.getHospital_id(), hmForm.getMedical_id());
		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (DataAccessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		if (number == 0) {
			return false;
		}

		return true;

	}
}
