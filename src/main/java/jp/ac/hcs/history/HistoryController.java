package jp.ac.hcs.history;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HistoryController {

	//TODO 履歴確認画面
	/**
	 * 履歴確認画面へ遷移する
	 *
	 * @return	履歴確認画面
	 */
	public String getHistorys() {
		return "/historyList";
	}

	//TODO 履歴検索機能
	/**
	 * 条件に合致する履歴を表示する
	 *
	 * @return	履歴確認画面
	 */
	public String getHistorySearch() {
		return null;
	}
}
