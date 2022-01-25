package jp.ac.hcs.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			UserData userData = userService.userUpdatetRansition(principal.getName());
			model.addAttribute("userEntity", userEntity);
			model.addAttribute("userData", userData);
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
	public String userDelete(@RequestParam("user_id") String user_id, Principal principal, Model model) {

		try {
			UserEntity userEntity = userService.userDelete(user_id);
			UserData userData = userService.userUpdatetRansition(principal.getName());
			model.addAttribute("userEntity", userEntity);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報削除機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報削除機能：異常");
			return "errorMessage";
		}

		return "user/userList";

	}

	/**
	 * ユーザ管理画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userManagement	正常：ユーザ管理画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/userList/UserManagement")
	public String management(Principal principal, Model model) {

		try {
			UserData userData = userService.userUpdatetRansition(principal.getName());
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ管理画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ管理画面：異常");
			return "errorMessage";
		}
		return "user/userManagement";

	}

	/**
	 * ユーザ情報更新画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/update")
	public String userUpdatetRansition(@RequestParam("user_id") String user_id, Principal principal, Model model) {

		try {
			UserData userData = userService.userUpdatetRansition(user_id);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報更新画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：異常");
			return "errorMessage";
		}
		return "user/userUpdate";

	}

	/**
	 * ユーザ情報更新画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/userList/update/{user_id}")
	public String userUpdateOne(@PathVariable("user_id") String user_id, Principal principal, Model model) {

		try {
			UserData userData = userService.userUpdatetRansition(user_id);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報更新画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：異常");
			return "errorMessage";
		}
		return "user/userUpdate";

	}

	/**
	 * ユーザ名を更新する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/updateUserName")
	public String updateUserName(@RequestParam("user_id") String user_id, @RequestParam("user_name") String user_name,
			Principal principal, Model model) {

		try {
			UserData userData = userService.updateUserName(user_id, user_name);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報更新機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新機能：異常");
			return "errorMessage";
		}

		return "user/userUpdate";

	}

	/**
	 * 住所を更新する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/updateAddress")
	public String updateAddress(@RequestParam("user_id") String user_id, @RequestParam("address") String address,
			Principal principal, Model model) {

		try {
			UserData userData = userService.updateAddress(user_id, address);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報更新機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新機能：異常");
			return "errorMessage";
		}

		return "user/userUpdate";

	}

	/**
	 * 電話番号を更新する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/updatePhonenNumber")
	public String updatePhoneNumber(@RequestParam("user_id") String user_id,
			@RequestParam("phone_number") String phone_number,
			Principal principal, Model model) {

		try {
			UserData userData = userService.updatePhoneNumber(user_id, phone_number);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報更新機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新機能：異常");
			return "errorMessage";
		}

		return "user/userUpdate";

	}

	/**
	 * パスワードを更新する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/updatePassword")
	public String updatePassword(@RequestParam("user_id") String user_id, @RequestParam("password") String password,
			Principal principal, Model model) {

		try {
			UserData userData = userService.updatePassword(user_id, password);
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "：ユーザ情報更新機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新機能：異常");
			return "errorMessage";
		}

		return "user/userUpdate";

	}

	/**
	 * ユーザ情報更新画面に遷移する
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/userList/userInsert")
	public String userInsertOne(String user_id, Principal principal, Model model) {

		try {
			log.info(principal.getName() + "：ユーザ情報更新画面：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：異常");
			return "errorMessage";
		}
		return "user/userInsert";

	}

}