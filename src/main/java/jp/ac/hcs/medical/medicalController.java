package jp.ac.hcs.medical;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class medicalController {

	//TODO 診療科判定画面
	/**
	 * 診療科判定画面に遷移する
	 *
	 * @return	診療科判定画面
	 */
	@GetMapping("/medicalDecision")
	public String getMedicalDecision() {
		return "hospital/medicalDecision";
	}

	//TODO 診療科判定機能
	/**
	 * 診療科判定画面から取得した値をもとに診療科を判定し、その診療科を持つ病院を一覧表示する
	 *
	 * @return	病院一覧画面
	 */
	@PostMapping("/medicalDecision/result")
	public String getDecisionResult() {
		//判定画面から入力された値に評価値を設定し、その値の合計から診療科を判定する
		return "hospital/hospitalList";
	}
}
