package jp.ac.hcs.main;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PortalController {

	@RequestMapping("/")
	public String index(Principal principal) {
		log.info("トップ画面：" + principal.getName());
		return "index";
	}
}
