package jp.ac.hcs.user;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ一覧画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報削除機能：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ管理画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：想定外のエラー");
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
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ情報更新画面：想定外のエラー");
			return "errorMessage";
		}

		return "user/userUpdate";

	}

	/**
	 * ユーザ新規登録（ログイン画面から）
	 */
	@GetMapping("/userList/userInsertLogin")
	public String userInsertLogin(UserForm userForm, Principal principal, Model model) {
		return "user/userInsert";
	}

	/**
	 * ユーザ新規登録
	 */
	@PostMapping("/userList/userInsertLogin")
	public String userInsertLogin(@ModelAttribute @Validated UserForm userForm, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return userInsertOne(userForm, principal, model);
		}

		Date birth_date = null;
		UserEntity userEntity = new UserEntity();

		try {
			birth_date = userService.birthday(userForm.getBirth_year(), userForm.getBirth_month(),
					userForm.getBirth_day());
		} catch (ParseException e) {
			log.info(principal.getName() + "：ユーザ新規登録画面：異常");
			return "errorMessage";
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ新規登録画面：想定外のエラー");
			return "errorMessage";
		}

		try {
			userEntity = userService.userInsert(userForm, birth_date);
		} catch (DataAccessException e) {
			log.info(principal.getName() + "ユーザ新規登録画面：異常");
			e.printStackTrace();
			return "errorMessage";
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ新規登録画面：想定外のエラー");
			return "errorMessage";
		}

		return "/login";
	}

	/**
	 * ユーザ新規登録（遷移）
	 *
	 * @param	principal		ログイン中のユーザ情報
	 * @param	model			モデル情報
	 * @return	userUpdate	正常：ユーザ情報更新画面 errorMessage 異常：エラーメッセージ表示画面
	 */
	@GetMapping("/userList/userInsert")
	public String userInsertOne(UserForm userForm, Principal principal, Model model) {
		return "user/userInsert";
	}

	/**
	 * ユーザ新規登録（処理）
	 *
	 * @param	userForm	新規登録画面で入力された新規ユーザの情報
	 * @param	principal	ログイン中のユーザ情報
	 * @param	model		モデル情報
	 * @return	正常：ユーザ管理画面	異常：エラーメッセージ表示画面
	 */
	@PostMapping("/userList/userInsert")
	public String userInsertOne(@ModelAttribute @Validated UserForm userForm, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return userInsertOne(userForm, principal, model);
		}

		Date birth_date = null;
		UserEntity userEntity = new UserEntity();

		try {
			birth_date = userService.birthday(userForm.getBirth_year(), userForm.getBirth_month(),
					userForm.getBirth_day());
		} catch (ParseException e) {
			log.info(principal.getName() + "：ユーザ新規登録画面：異常");
			return "errorMessage";
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ新規登録画面：想定外のエラー");
			return "errorMessage";
		}

		try {
			userEntity = userService.userInsert(userForm, birth_date);
			UserData userData = userService.userUpdatetRansition(principal.getName());
			model.addAttribute("userEntity", userEntity);
			model.addAttribute("userData", userData);
		} catch (DataAccessException e) {
			log.info(principal.getName() + "ユーザ新規登録画面：異常");
			e.printStackTrace();
			return "errorMessage";
		} catch (Exception e) {
			log.info(principal.getName() + "：ユーザ新規登録画面：想定外のエラー");
			return "errorMessage";
		}

		return getUsers(principal, model);
	}

	/**
	 * ユーザ検索
	 *
	 * @param	user_id
	 * @param	user_name
	 * @param	user_permission
	 * @param	gender
	 * @param	phone_number
	 * @return	userManagement
	 */
	@PostMapping("/userList/search")
	public String userSearch(String user_id, String user_name, String user_permission, String gender,
			String phone_number, Principal principal, Model model) {

		UserEntity userEntity = new UserEntity();
		UserData userData = new UserData();

		try {
			userEntity = userService.getUserSearch(user_id, user_name, user_permission, gender, phone_number);
			model.addAttribute("userEntity", userEntity);
			userData = userService.userUpdatetRansition(principal.getName());
			model.addAttribute("userData", userData);
			log.info(principal.getName() + "ユーザ検索機能：正常");
		} catch (DataAccessException e) {
			log.info(principal.getName() + "ユーザ検索機能：異常");
			return "errorMessage";
		}
		return "user/userList";
	}

}