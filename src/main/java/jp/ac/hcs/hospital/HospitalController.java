package jp.ac.hcs.hospital;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	/**
	 * 病院の一覧画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	hospitalList	正常：病院一覧画面 errorMessage	異常：エラーメッセージ表示画面
	 */
	@GetMapping("/hospitalList")
	public String getHospitals(Principal principal, Model model) {

		try {
			//病院テーブル全件取得
			List<Map<String, Object>> hospitalList = hospitalService.getHospitals();
			//病院Idをもとに診療科名を取得
			List<Map<String, Object>> HMList = hospitalService.getHospitalMedicals(hospitalList);
			//病院と診療科名を結合
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

	//TODO 病院検索機能
	/**
	 * 条件に合致する病院を一覧表示する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @param	hospital_name	病院名
	 * @param	address			病院住所
	 * @return	hospitalList	正常：病院一覧画面 errorMessage	異常：エラーメッセージ表示画面
	 */
	@PostMapping("/hospitalList/search")
	public String getHospitalSearch(String hospital_name, String address,
			Principal principal, Model model) {

		try {
			HospitalEntity hospitalEntity = hospitalService.getHospitalSearch(hospital_name, address);
			if (hospitalEntity == null) {
				model.addAttribute("message", "該当する病院はありませんでした。");
			}
			model.addAttribute("hospitalEntity", hospitalEntity);
			log.info(principal.getName() + "：病院検索：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院検索：異常");
			return "errorMessage";
		}

		return "hospital/hospitalList";
	}

	//TODO 病院新規登録画面
	/**
	 * 病院登録画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @return	hospitalInsert	病院新規登録画面
	 */
	@GetMapping("/hospitalList/insert")
	public String getHospitalRegistration(Principal principal) {
		log.info(principal.getName() + "：病院新規登録");
		return "/hospital/hospitalInsert";
	}

	//TODO 病院新規登録機能
	/**
	 * システムに病院を登録する（管理者用）
	 *
	 *@param	form			入力された病院情報
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	hospitalList	病院一覧画面
	 */

	/*
	@PostMapping("/hospitalList/insert")
	public String getHospitalInsert(HospitalForm hForm, Principal principal, Model model) {

		try {
			HospitalEntity hospitalEntity = hospitalService.getHospitalInsert(hForm);
			model.addAttribute("hospitalEntity", hospitalEntity);
			log.info(principal.getName() + "：病院新規登録：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：病院新規登録：異常");
			return "errorMessage";
		}

		return "hospital/hospitalList";
	}
	*/
	//TODO 病院詳細画面
	/**
	 * 病院詳細画面に遷移する
	 *
	 * @return	病院詳細画面
	 */
	@GetMapping("/hospitalList/detail")
	public String getHospitalDetail() {
		return "hospital/hospitalDetail";
	}

	//TODO 病院情報変更
	/**
	 * 詳細画面に表示している病院の情報を変更する
	 *
	 * @return	病院詳細画面
	 */
	@PostMapping("/hospitalList/detail")
	public String getHospitalUpdate() {
		return "hospital/hospitalDetail";
	}

	//TODO 病院削除機能
	/**
	 * 詳細画面に表示している病院を削除する
	 *
	 * @return	病院一覧画面
	 */
	@PostMapping("/hospitalList/delete")
	public String getHospitalDelete() {
		return "hospital/hospitalList";
	}

}
