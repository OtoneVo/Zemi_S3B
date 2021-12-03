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
	 * @return	hospitalList	病院一覧画面
	 */
	@GetMapping("/hospitalList")
	public String getHospitals(Principal principal, Model model) {

		HospitalEntity hospitalEntity = hospitalService.getHospitals();

		if (hospitalEntity == null) {
			model.addAttribute("errorMessage", "正常に処理が完了しませんでした。");
			log.info(principal.getName() + "：病院一覧画面：異常");
		} else {
			model.addAttribute("hospitalEntity", hospitalEntity);
			log.info(principal.getName() + "：病院一覧画面：正常");
		}
		return "hospital/hospitalList";
	}

}
