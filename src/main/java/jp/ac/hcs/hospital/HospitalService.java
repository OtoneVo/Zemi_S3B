package jp.ac.hcs.hospital;

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
	public HospitalEntity getHospitals() {

		HospitalEntity hospitalEntity = new HospitalEntity();

		try {
			hospitalEntity = hospitalRepository.selectAll();
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			hospitalEntity = null;
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
			hospitalEntity = null;
		}
		return hospitalEntity;
	}

}
