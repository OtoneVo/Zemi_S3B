package jp.ac.hcs.reservation;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.hospital.Hospital_MedicalForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	//TODO 予約キャンセル機能
	/**
	 * 予約をキャンセルする機能、予約日3日前になるとできない
	 *
	 * @return	履歴確認画面
	 */
	@PostMapping("")
	public String getReservationDelete() {
		return null;
	}

	//TODO 予約画面
	/**
	 * 予約情報を入力し予約を送信する画面
	 *
	 * @return	予約画面
	 */
	@PostMapping("/reservationsSend")
	public String getReservationSend(@RequestParam Hospital_MedicalForm HMForm, Model model,
			Principal principal) {

		String result = null;

		try {
			result = "reservation/reservationsSend";
			log.info(HMForm + "病院情報取得確認");
			model.addAttribute("");
		} catch (Exception e) {
			log.info(principal.getName() + "予約画面遷移：想定外のエラー");
			return "errorMessage";
		}

		return result;
	}

	//TODO 予約機能
	/**
	 * 入力された予約情報を保存する機能
	 *
	 * @return
	 */
	@PostMapping("/reservationsSave")
	public String getReservationSave(ReservationForm reservationForm, Principal principal, Model model) {

		ReservationEntity reservationEntity = new ReservationEntity();
		boolean resultInsert = true;

		try {
			resultInsert = reservationService.insertReservation(reservationForm);
			log.info(principal.getName() + "予約機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "予約機能：異常");
			return "errorMessage";
		} catch (ParseException e) {
			log.info(principal.getName() + "予約機能：異常");
		} catch (Exception e) {
			log.info(principal.getName() + "予約機能：想定外のエラー");
			return "errorMessage";
		}

		if (resultInsert) {
			return "";
		}

		return null;
	}

	//TODO 予約管理画面
	/**
	 * 送信した予約を管理する機能
	 *
	 * @return	予約管理画面
	 */
	@GetMapping("/reservationsList")
	public String getReservations(Model model, Principal principal) {
		String result = null;

		ReservationEntity entity = new ReservationEntity();

		//TODO 権限チェック

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

	//TODO 予約検索機能
	/**
	 * 条件に合致する予約を表示する
	 *
	 * @return	予約管理画面
	 */
	public String getReservationSelect() {
		//		entity = reservationService.searchReservation(principal.getName(),hospital_name, medical_name, reservation_date);

		return null;
	}
}
