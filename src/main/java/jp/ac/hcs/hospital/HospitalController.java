package jp.ac.hcs.hospital;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	/**
	 * 病院の一覧画面に遷移する
	 *
	 * @param principal ログイン中のユーザ情報
	 * @param model     モデル情報
	 * @return hospitalList 正常：病院一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/hospitalList")
	public String getHospitals(Principal principal, Model model) {

		try {
			List<Map<String, Object>> hospitalList = hospitalService.getHospitals();
			List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalList);
			Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(hospitalList, HMList);
			model.addAttribute("HMEntity", HMEntity);
			log.info(principal.getName() + "：病院一覧画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院一覧画面：異常");
			e.printStackTrace();
			return "errorMessage";
		}

		return "hospital/hospitalList";
	}

	// TODO 病院検索機能
	/**
	 * 条件に合致する病院を一覧表示する
	 *
	 * @param hospital_name 病院名
	 * @param address       病院住所
	 * @param medical_name  診療科名
	 * @param principal     ログイン中のユーザ情報
	 * @param model         モデル情報
	 * @return hospitalList 正常：病院一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/hospitalList/search")
	public String getHospitalSearch(String hospital_name, String address, String medical_name, Principal principal,
			Model model) {

		List<Map<String, Object>> searchHospitalList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> HMList = new ArrayList<Map<String, Object>>();

		try {
			searchHospitalList = hospitalService.getHospitalSearch(hospital_name, address, medical_name);
			HMList = hospitalService.getHospitalMedicals(searchHospitalList);
			Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(searchHospitalList, HMList);
			if (searchHospitalList.isEmpty()) {
				model.addAttribute("message", "該当する病院はありませんでした。");
			}
			model.addAttribute("HMEntity", HMEntity);
			log.info(principal.getName() + "：病院検索：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院検索：異常");
			e.printStackTrace();
			return "errorMessage";
		}

		return "hospital/hospitalList";
	}

	/**
	 * 病院管理画面に遷移する
	 *
	 * @return hospitalControl 病院管理画面
	 */
	@GetMapping("/hospital/management")
	public String hospitalcontrol(Principal principal, Model model) {

		List<Map<String, Object>> hospitalList = hospitalService.getHospitals();
		List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalList);
		Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(hospitalList, HMList);
		model.addAttribute("HMEntity", HMEntity);

		return "hospital/hospitalControl";
	}

	// TODO 病院新規登録画面
	/**
	 * 病院登録画面に遷移する
	 *
	 * @param principal ログイン中のユーザ情報
	 * @return hospitalInsert 病院新規登録画面
	 */
	@GetMapping("/hospitalList/insert")
	public String getHospitalRegistration(Principal principal) {
		log.info(principal.getName() + "：病院新規登録");
		return "/hospital/hospitalInsert";
	}

	// TODO 病院新規登録機能
	/**
	 * システムに病院を登録する（管理者用）
	 *
	 * @param hForm     入力された病院情報
	 * @param principal ログイン中のユーザ情報
	 * @param model     モデル情報
	 * @return hospitalList 正常：病院一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/hospitalList/insert")
	public String getHospitalInsert(HospitalForm hForm, Principal principal, Model model) {

		try {
			List<Map<String, Object>> hospitalList = hospitalService.getHospitalInsert(hForm);
			List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalList);
			Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(hospitalList, HMList);
			model.addAttribute("HMEntity", HMEntity);
			log.info(principal.getName() + "：病院新規登録：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院新規登録：異常");
			return "errorMessage";
		}

		return "hospital/hospitalList";
	}

	// TODO 病院詳細画面
	/**
	 * 病院詳細を表示する
	 *
	 * @param hospital_id 詳細表示する病院の病院ID
	 * @param principal   ログイン中のユーザ情報
	 * @param model       モデル情報
	 * @return hospitalDetail 病院詳細画面
	 */
	@GetMapping("/hospitalList/detail/{id}")
	public String getHospitalDetail(@PathVariable("id") String hospital_id, Principal principal, Model model) {
		// 病院IDに対応する病院の詳細情報を取得する

		try {
			List<Map<String, Object>> hospitalDetailList = hospitalService.getHospitalDetail(hospital_id);
			List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalDetailList);
			Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(hospitalDetailList, HMList);
			log.info(principal.getName() + "：病院詳細画面：正常");
			model.addAttribute("HMEntity", HMEntity);
			log.info("確認：" + HMEntity);
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院詳細画面：異常");
			return "errorMessage";
		}
		return "hospital/hospitalDetail";
	}

	@GetMapping("/hospital/detail")
	public String getHospitalPermissionDetail(Principal principal, Model model) {

		try {
			List<Map<String, Object>> hospitalDetailList = hospitalService.getHospitalDetail(principal.getName());
			List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalDetailList);
			Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(hospitalDetailList, HMList);
			log.info(principal.getName() + "：病院詳細画面：正常");
			model.addAttribute("HMEntity", HMEntity);
			log.info("確認：" + HMEntity);
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院詳細画面：異常");
			return "errorMessage";
		}
		return "hospital/hospitalDetail";
	}

	// TODO 病院情報変更
	/**
	 * 詳細画面に表示している病院の情報を変更する
	 *
	 * @param	hospital_id		詳細表示する病院の病院ID
	 * @param	principal		ユーザ情報
	 * @param	model			モデル情報
	 * @return hospitalChange	正常：病院情報変更画面	異常：エラーメッセージ表示画面
	 */
	@PostMapping("/hospitalList/change/{id}")
	public String getHospitalUpdate(@PathVariable("id") String hospital_id, Principal principal, Model model) {

		try {
			List<Map<String, Object>> hospitalDetailList = hospitalService.getHospitalDetail(hospital_id);
			List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalDetailList);
			Hospital_medicalEntity HMEntity = hospitalService.getHospitalMedicalSplit(hospitalDetailList, HMList);
			log.info(principal.getName() + "：病院変更画面：正常");
			model.addAttribute("HMEntity", HMEntity);
			log.info("確認：" + HMEntity);
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院変更画面：異常");
			return "errorMessage";
		}

		return "hospital/hospitalChange";
	}

	/**
	 * 変更画面で入力された情報に変更する
	 *
	 * @param	hmForm			入力された変更する病院情報
	 * @param	principal		ユーザ情報
	 * @param	model			モデル情報
	 * @return hospitalList	正常：病院詳細画面	errorMessage	異常：エラーメッセージ表示画面
	 */
	@PostMapping("/hospital/change")
	public String hospitalChange(Hospital_MedicalForm hmForm, Principal principal, Model model) {
		boolean result = true;

		try {
			result = hospitalService.getHospitalUpdate(hmForm);
		} catch (DataIntegrityViolationException e) {
			log.info(principal.getName() + "：病院変更画面：異常");
			return "errorMessage";
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院変更画面：異常");
			return "errorMessage";
		}

		if (result) {
			log.info(principal.getName() + "病院削除：正常");
			return getHospitals(principal, model);
		}

		model.addAttribute("message", "変更に失敗しました。再試行してください。");
		return "hospital/hospitalList";
	}

	// TODO 病院削除機能
	/**
	 * 詳細画面に表示している病院を削除する
	 *
	 * @param hospital_id 削除する病院の病院ID
	 * @param principal   ログイン中のユーザ情報
	 * @param model       モデル情報
	 * @return hospitalList 正常：病院一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/hospitalList/delete")
	public String getHospitalDelete(@RequestParam("hospital_id") String hospital_id, Principal principal, Model model) {

		boolean result = true;

		try {
			result = hospitalService.getDeleteHospital(hospital_id);
		} catch (DataAccessException e) {
			log.info(principal.getName() + "病院削除：異常");
			return "errorMessage";
		}

		if (result) {
			log.info(principal.getName() + "病院削除：正常");
			return hospitalcontrol(principal, model);
		}
		model.addAttribute("message", "削除に失敗しました。再試行してください。");
		return hospitalcontrol(principal, model);
	}

}
