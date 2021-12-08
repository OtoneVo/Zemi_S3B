package jp.ac.hcs.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	 * @return	userList	正常：ユーザ一覧画面 errorMessage 異常：エラーメッセージ表示画面
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

	/**
	 * ユーザ情報を一件削除する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userList	正常：ユーザ一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/delete")
	public String userDelete(String user_id, Principal principal, Model model) {

		try {
			UserEntity userEntity = userService.userDelete(user_id);
			model.addAttribute("userEntity", userEntity);
			log.info(principal.getName() + "：ユーザ一覧画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ一覧画面：異常");
			return "errorMessage";
		}

		return "user/userList";

	}

	/**
	 * 管理画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userList	正常：ユーザ一覧画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/userList/UserManagement")
	public String management(Principal principal, Model model) {

		try {
			log.info(principal.getName() + "：ユーザ一覧画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ一覧画面：異常");
			return "errorMessage";
		}
		return "user/userManagement";

	}

}