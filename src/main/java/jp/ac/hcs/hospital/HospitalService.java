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
	 * @return hospitalEntity
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

}
