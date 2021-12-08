package jp.ac.hcs.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * ユーザ一覧画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	hospitalList	正常：ユーザ一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/userList")
	public String getUsers(Principal principal, Model model) {

		try {
			UserEntity userEntity = userService.getUsers();
			model.addAttribute("userEntity", userEntity);
			log.info(principal.getName() + "：ユーザ一覧画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ一覧画面：異常");
			return "errorMessage";
		}
		return "user/userList";

	}
}