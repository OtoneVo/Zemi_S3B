package jp.ac.hcs.hospitalTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import jp.ac.hcs.hospital.HospitalForm;
import jp.ac.hcs.hospital.HospitalRepository;
import jp.ac.hcs.hospital.HospitalService;
import jp.ac.hcs.hospital.Hospital_MedicalForm;
import jp.ac.hcs.hospital.Hospital_medicalData;
import jp.ac.hcs.hospital.Hospital_medicalEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class hospitalServiceTest {

	@Autowired
	HospitalService hospitalService;

	@SpyBean
	HospitalRepository hospitalRepository;

	@Test
	void getHospitalsの正常系テスト() {
		// 1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		// 2.Do
		hospitalList = hospitalService.getHospitals();
		// 3.Assert
		assertNotNull(hospitalList);
		// 4.Logs
		log.warn("[getHospitalsメソッドの正常系テスト]hospitalList:" + hospitalList);
	}

	@Test
	void getHospitalsの異常系テスト() {
		//1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).selectAll();
		//2.Do
		hospitalList = hospitalService.getHospitals();
		//3.Assert
		assertNull(hospitalList);
		// 4.Logs
		log.warn("[getHospitalsメソッドの異常系テスト]hosptialList:" + hospitalList);
	}

	@Test
	void getHospitalMedicalsの正常系テスト() {
		//1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();
		//2.Do
		hospitalList = hospitalService.getHospitals();
		HMList = hospitalService.getHospitalMedicals(hospitalList);
		//3.Assert
		assertNotNull(HMList);
		//4.Logs
		log.warn("[getHospitalMedicalsの正常系テスト]HMList:" + HMList);
	}

	@Test
	void getHospitalMedicalsの異常系テスト() {
		//1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();
		String hospital_id = "test";
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).hospitalMedicaiList(hospital_id);
		//2.Do
		hospitalList = hospitalService.getHospitals();
		HMList = hospitalService.getHospitalMedicals(hospitalList);
		//3.Assert
		assertNull(HMList);
		//4.Logs
		log.warn("[getHospitalMedicalsの異常系テスト]HMList:" + HMList);
	}

	@Test
	void getHospitalMedicalSplitの正常系テスト() {
		//1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();
		Hospital_medicalEntity HMEntity = new Hospital_medicalEntity();
		//2.Do
		hospitalList = hospitalService.getHospitals();
		HMList = hospitalService.getHospitalMedicals(hospitalList);
		HMEntity = hospitalService.getHospitalMedicalSplit(hospitalList, HMList);
		//3.Assert
		assertNotNull(HMEntity);
		//4.Logs
		log.warn("[getHospitalMedicalSplitの正常系テスト]HMEntity:" + HMEntity);
	}

	@Test
	void getHospitalSearchの正常系テスト() {
		//1.Ready
		List<Map<String, Object>> searchHospitalList = new ArrayList<Map<String, Object>>();
		String hospital_name = "札幌市立病院";
		String address = "札幌市中央区北10条東5丁目";
		String medical_name = "外科";
		//2.Do
		searchHospitalList = hospitalService.getHospitalSearch(hospital_name, address, medical_name);
		//3.Assert
		assertNotNull(searchHospitalList);
		//4.Logs
		log.warn("[getHospitalSearchの正常系テスト]searchHospitalList" + searchHospitalList);
	}

	@Test
	void getHospitalSearchの異常系テスト() {
		//1.Ready
		List<Map<String, Object>> searchHospitalList = new ArrayList<Map<String, Object>>();
		String hospital_name = "札幌市立病院";
		String address = "札幌市中央区北10条東5丁目";
		String medical_name = "外科";
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).searchHospital(hospital_name,
				address, medical_name);
		//2.Do
		searchHospitalList = hospitalService.getHospitalSearch(hospital_name, address, medical_name);
		//3.Assert
		assertNull(searchHospitalList);
		//4.Logs
		log.warn("[getHospitalSearchの異常系テスト]searchHospitalList" + searchHospitalList);
	}

	@Test
	void getHospitalInsertの正常系テスト() {
		//1.Ready
		HospitalForm hForm = new HospitalForm();
		hForm.setHospital_id("sapporo@hosp.ac.jp");
		hForm.setHospital_name("札幌市立病院");
		hForm.setEncrypted_password("password");
		hForm.setAddress("札幌市東区北3条西2丁目");
		hForm.setPhone_number("000-0000-0000");
		hForm.setNumber_of_reservations("20");
		hForm.setOverview("概要");
		hForm.setMedical_id("1");
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		//2.Do
		hospitalList = hospitalService.getHospitalInsert(hForm);
		//3.Assert
		assertNotNull(hospitalList);
		//4.Logs
		log.warn("[getHospitalInsertの正常系テスト]hospitalList" + hospitalList);
	}

	@Test
	void getHospitalInsertの異常系テスト() {
		//1.Ready
		HospitalForm hForm = new HospitalForm();
		hForm.setHospital_id("sapporo@hosp.ac.jp");
		hForm.setHospital_name("札幌市立病院");
		hForm.setEncrypted_password("password");
		hForm.setAddress("札幌市東区北3条西2丁目");
		hForm.setPhone_number("000-0000-0000");
		hForm.setNumber_of_reservations("20");
		hForm.setOverview("概要");
		hForm.setMedical_id("1");
		Hospital_medicalData hmData = new Hospital_medicalData();
		hmData.setHospital_id(hForm.getHospital_id());
		hmData.setHospital_name(hForm.getHospital_name());
		hmData.setEncrypted_password(hForm.getEncrypted_password());
		hmData.setAddress(hForm.getAddress());
		hmData.setPhone_number(hForm.getPhone_number());
		hmData.setNumber_of_reservations(hForm.getNumber_of_reservations());
		hmData.setOverview(hForm.getOverview());
		hmData.setMedical_id(hForm.getMedical_id());
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).insertHospital(hmData);
		//2.Do
		hospitalList = hospitalService.getHospitalInsert(hForm);
		//3.Assert
		assertNull(hospitalList);
		//4.Logs
		log.warn("[getHospitalInsertの異常系テスト]hospitalList" + hospitalList);
	}

	@Test
	void getHospitalDetailの正常系テスト() {
		//1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		String hospital_id = "sapporo@hosp.ac.jp";
		//2.Do
		hospitalList = hospitalService.getHospitalDetail(hospital_id);
		//3.Assert
		assertNotNull(hospitalList);
		//4.Logs
		log.warn("[getHospitalDetailの正常系テスト]hospitlaList:" + hospitalList);
	}

	@Test
	void getHospitalDetailの異常系テスト() {
		//1.Ready
		List<Map<String, Object>> hospitalList = new ArrayList<Map<String, Object>>();
		String hospital_id = "sapporo@hosp.ac.jp";
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).selectOne(hospital_id);
		//2.Do
		hospitalList = hospitalService.getHospitalDetail(hospital_id);
		//3.Assert
		assertNull(hospitalList);
		//4.logs
		log.warn("[getHospitalDetailの異常系テスト]hospitalList" + hospitalList);
	}

	@Test
	void getDeleteHospitalの削除成功正常系テスト() {
		//1.Ready
		String hospital_id = "sapporo@hosp.ac.jp";
		boolean result = true;
		//2.Do
		result = hospitalService.getDeleteHospital(hospital_id);
		//3.Assert
		assertTrue(result);
		//4.Logs
		log.warn("[getDeleteHospitalの削除成功正常系テスト]result" + result);
	}

	@Test
	void getDeleteHospitalの削除失敗正常系テスト() {
		//1.Ready
		String hospital_id = "test";//存在しない病院ID
		boolean result = true;
		//2.Do
		result = hospitalService.getDeleteHospital(hospital_id);
		//3.Assert
		assertFalse(result);
		//4.Logs
		log.warn("[getDeleteHospitalの削除失敗正常系テスト]result:" + result);
	}

	@Test
	void getDeleteHospitalの異常系テスト() {
		//1.Ready
		String hospital_id = "sapporo@hosp.ac.jp";
		boolean result = true;
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).deleteHospital(hospital_id);
		//2.Do
		result = hospitalService.getDeleteHospital(hospital_id);
		//3.Assert
		assertFalse(result);
		//4.Logs
		log.warn("[getHospitalDeleteの異常系テスト]result:" + result);
	}

	@Test
	void getHospitalUpdateの正常系テスト() {
		//1.Ready
		Hospital_MedicalForm hmForm = new Hospital_MedicalForm();
		hmForm.setHospital_id("sapporo@hosp.ac.jp");
		hmForm.setHospital_name("札幌市立病院");
		hmForm.setEncrypted_password("password");
		hmForm.setAddress("札幌市東区北3条西2丁目");
		hmForm.setPhone_number("000-0000-0000");
		hmForm.setNumber_of_reservations("20");
		hmForm.setOverview("概要");
		boolean result = true;
		//2.Do
		result = hospitalService.getHospitalUpdate(hmForm);
		//3.Assert
		assertTrue(result);
		//4.Logs
		log.warn("[getHospitalUpdateの正常系テスト]result" + result);
	}

	@Test
	void getHospitalUpdateの異常系テスト() {
		//1.Ready
		Hospital_MedicalForm hmForm = new Hospital_MedicalForm();
		hmForm.setHospital_id("sapporo@hosp.ac.jp");
		hmForm.setHospital_name("札幌市立病院");
		hmForm.setEncrypted_password("password");
		hmForm.setAddress("札幌市東区北3条西2丁目");
		hmForm.setPhone_number("000-0000-0000");
		hmForm.setNumber_of_reservations("20");
		hmForm.setOverview("概要");
		boolean result = true;
		doThrow(new DataAccessResourceFailureException("")).when(hospitalRepository).updateHospital(hmForm);
		//2.Do
		result = hospitalService.getHospitalUpdate(hmForm);
		//3.Assert
		assertFalse(result);
		//4.Logs
		log.warn("[getHospitalUpdateの異常系テスト]result:" + result);
	}

}
