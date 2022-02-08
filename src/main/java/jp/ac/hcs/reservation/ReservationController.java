package jp.ac.hcs.reservation;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.hospital.Hospital_MedicalForm;
import jp.ac.hcs.user.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserService userService;

	// TODO 予約キャンセル機能
	/**
	 * 予約をキャンセルする機能、予約日3日前になるとできない
	 *
	 * @return 履歴確認画面
	 */
	@PostMapping("")
	public String getReservationDelete() {
		return null;
	}

	// TODO 予約画面
	/**
	 * 予約情報を入力し予約を送信する画面
	 *
	 * @return 予約画面
	 */
	@PostMapping("/reservationsSend")
	public String getReservationSend(Hospital_MedicalForm hmForm, Model model, Principal principal) {

		String result = null;
		ReservationEntity entity = new ReservationEntity();

		try {
			result = "reservation/reservationsSend";
			log.info(hmForm + "病院情報取得確認");
			// List<String> medicalList = new ArrayList<String>();
			// TODO 病院IDから診療科IDを取ってくる
			entity = reservationService.getHospitalMedical(hmForm.getHospital_id());
			// medicalList = reservationService.getMedicalList(hmForm.getMedical_name());
			model.addAttribute("reservationEntity", entity);
			model.addAttribute("hmForm", hmForm);
			// model.addAttribute("medicalList", medicalList);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(principal.getName() + "予約画面遷移：想定外のエラー");
			return "errorMessage";
		}

		return result;
	}

	// TODO 予約機能
	/**
	 * 入力された予約情報を保存する機能
	 *
	 * @return
	 */
	@PostMapping("/reservationsSave")
	public String getReservationSave(ReservationForm reservationForm, Principal principal, Model model) {

		ReservationEntity reservationEntity = new ReservationEntity();
		boolean resultInsert = true;
		String user_name = "";
		log.info(reservationForm.getHospital_id() + "：予約確認：病院ID");
		log.info(reservationForm.getHospital_name() + "：予約確認：病院名");
		log.info(reservationForm.getMedical_id() + "：予約確認：診療科ID");
		log.info(reservationForm.getMedical_name() + "：予約確認：診療科名");
		log.info(reservationForm.getReservation_date() + "：予約確認：日");
		log.info(reservationForm.getReservation_time() + "：予約確認：時");

		try {
			user_name = userService.getUserOne(principal.getName());
			reservationForm.setUser_id(principal.getName());
			reservationForm.setUser_name(user_name);
			resultInsert = reservationService.insertReservation(reservationForm);
			reservationEntity = reservationService.selectReservation(principal.getName());
			model.addAttribute("entity", reservationEntity);
			log.info(principal.getName() + "予約機能：正常");
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.info(principal.getName() + "予約機能：異常");
			return "errorMessage";
		} catch (ParseException e) {
			e.printStackTrace();
			log.info(principal.getName() + "予約機能：異常");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(principal.getName() + "予約機能：想定外のエラー");
			return "errorMessage";
		}

		if (!resultInsert) {
			log.info("確認用メッセージ");
			return "errorMessage";
		}

		return "reservation/reservationsList";
	}

	// TODO 予約管理画面
	/**
	 * 送信した予約を管理する機能
	 *
	 * @return 予約管理画面
	 */
	@GetMapping("/reservationsList")
	public String getReservations(Model model, Principal principal) {
		String result = null;

		ReservationEntity entity = new ReservationEntity();

		// TODO 権限チェック

		try {
			entity = reservationService.selectReservation(principal.getName());
			model.addAttribute("entity", entity);
			result = "reservation/reservationsList";
		} catch (DataAccessException e) {
			e.printStackTrace();
			return "errorMessage";
		}

		return result;
	}

	// TODO 予約検索機能
	/**
	 * 条件に合致する予約を表示する
	 *
	 * @return 予約管理画面
	 */
	public String getReservationSelect() {
		// entity =
		// reservationService.searchReservation(principal.getName(),hospital_name,
		// medical_name, reservation_date);

		return null;
	}

	/**
	 * 病院ユーザが予約を確認する機能
	 *
	 */
	@GetMapping("/reservation/check")
	public String checkReservation(Principal principal, Model model) {

		// 病院IDから予約を取得
		ReservationEntity reservationEntity = new ReservationEntity();
		// service作成
		try {
			reservationEntity = reservationService.getReservationHospital(principal.getName());
		} catch (DataAccessException e) {
			log.info("病院予約取得：異常");
			return "errorMessage";
		} catch (Exception e) {
			e.printStackTrace();
			return "errorMessage";
		}

		model.addAttribute("reservationEntity", reservationEntity);
		// TODO 病院に対応する予約を表示するHTMLが必要
		return null;
	}

//	/**
//	 * 確認用エラー表示画面
//	 *
//	 */
//	@GetMapping("/test")
//	public String error() {
//		log.info("エラー表示");
//		return "errorMessage";
//	}


}
