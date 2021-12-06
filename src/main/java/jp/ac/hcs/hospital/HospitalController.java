package jp.ac.hcs.hospital;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

		HospitalEntity hospitalEntity = hospitalService.getHospitals();

		if (hospitalEntity == null) {
			log.info(principal.getName() + "：病院一覧画面：異常");
			return "errorMessage";
		} else {
			model.addAttribute("hospitalEntity", hospitalEntity);
			log.info(principal.getName() + "：病院一覧画面：正常");
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
	public String getHospitalSelect(String hospital_name, String address, Principal principal, Model model) {

		HospitalEntity hospitalEntity = hospitalService.getHospitalSearch(hospital_name, address);

		if (hospitalEntity == null) {
			log.info(principal.getName() + "：病院検索：異常");
			return "errorMessage";
		} else {
			model.addAttribute("hospitalEntity", hospitalEntity);
			log.info(principal.getName() + "：病院検索：正常");
		}
		return "hospital/hospitalList";
	}

	//TODO 診療科判定画面
	//TODO 診療科判定機能

	//TODO 履歴確認画面
	//TODO 履歴検索機能
	//TODO 予約キャンセル機能

	//TODO 予約画面
	//TODO 予約機能

	//TODO 予約確認画面
	//TODO 予約検索機能

	//TODO 病院新規登録画面
	//TODO 病院新規登録機能

	//TODO ユーザ新規登録画面
	//TODO ユーザ新規東特機能

	//TODO ユーザ一覧画面
	//TODO ユーザ検索機能

	//TODO ユーザ詳細画面
	//TODO ユーザ情報変更機能
	//TODO ユーザ削除機能

	//TODO 病院詳細画面
	//TODO 病院情報変更
	//TODO 病院削除機能

}
