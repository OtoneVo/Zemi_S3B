package jp.ac.hcs.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationController {

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
	public String getReservationSend() {
		return null;
	}

	//TODO 予約機能
	/**
	 * 入力された予約情報を保存する機能
	 *
	 * @return
	 */
	public String getReservationSave() {
		return null;
	}

	//TODO 予約管理画面
	/**
	 * 送信した予約を管理する機能
	 *
	 * @return	予約管理画面
	 */
	public String getReservations() {
		return null;
	}

	//TODO 予約検索機能
	/**
	 * 条件に合致する予約を表示する
	 *
	 * @return	予約管理画面
	 */
	public String getReservationSelect() {
		return null;
	}
}
