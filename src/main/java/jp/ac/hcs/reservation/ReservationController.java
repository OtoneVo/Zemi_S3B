package jp.ac.hcs.reservation;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@PostMapping("/reservationList/delete/{date}")
	public String getReservationDelete(@RequestParam String hospital_id, @RequestParam String medical_id,
			@PathVariable("date") Date reservation_date, Principal principal, Model model) {

		boolean result = true;
		ReservationEntity reservationEntity = new ReservationEntity();

		log.info("病院ID：" + hospital_id + "診療科ID：" + medical_id + "予約日時：" + reservation_date);

		try {
			result = reservationService.deleteReservation(principal.getName());
			reservationEntity = reservationService.selectReservation(principal.getName());
			model.addAttribute("entity", reservationEntity);
		} catch (DataAccessException e) {
			log.info("予約削除：異常");
			return "errorMessage";
		} catch (Exception e) {
			log.info("予約削除：想定外のエラー");
			return "errorMessage";
		}

		if (result) {
			log.info("予約削除：失敗");
			return "errorMessage";
		}

		return "reservation/reservationsList";
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
			// TODO 病院IDから診療科IDを取ってくる
			entity = reservationService.getHospitalMedical(hmForm.getHospital_id());
			model.addAttribute("reservationEntity", entity);
			model.addAttribute("hmForm", hmForm);
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

		if (reservationForm.getHospital_id() == null || reservationForm.getHospital_name() == null
				|| reservationForm.getMedical_id() == null || reservationForm.getReservation_date() == null
				|| reservationForm.getReservation_time() == null) {
			log.info("入力項目不足");
			return "errorMessage";
		}

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
	@PostMapping("/reservationList/search")
	public String getReservationSelect(@RequestParam String hospital_name, @RequestParam String medical_name,
			@RequestParam Date reservation_date, Principal principal, Model model) {

		log.info("取得確認:" + hospital_name, medical_name, reservation_date);
		ReservationEntity reservationEntity = new ReservationEntity();
		reservationEntity = reservationService.searchReservation(principal.getName(), hospital_name,
				medical_name, reservation_date);

		model.addAttribute("reservationEntity", reservationEntity);

		return null;
	}

	/**
	 * 病院ユーザが予約を確認する機能
	 *
	 */
	@GetMapping("/reservation/hospitalCheck")
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

		return "reservation/reservationHospitalList";
	}

}
