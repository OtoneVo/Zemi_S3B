package jp.ac.hcs.user;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	//TODO ユーザ一覧画面
	/**
	 * 登録したユーザの一覧を表示する機能
	 *
	 * @return	ユーザ一覧画面
	 */
	public String getUsers() {
		return null;
	}

	//TODO ユーザ検索機能
	/**
	 * 条件に合致するユーザを表示する
	 * @return	ユーザ一覧画面
	 */
	public String getUserSearch() {
		return null;
	}

	//TODO ユーザ新規登録画面
	/**
	 * ユーザを登録する画面に遷移する
	 *
	 * @return	ユーザ新規登録画面
	 */
	public String getUserRegistration() {
		return null;
	}

	//TODO ユーザ新規登録機能
	/**
	 * 入力したユーザ情報を保存する機能
	 */
	public String getUserInsert() {
		return null;
	}

	//TODO ユーザ詳細画面
	/**
	 * ユーザの詳細情報を表示する画面に遷移する
	 *
	 * @return	ユーザ詳細画面
	 */
	public String getUserDetail() {
		return null;
	}

	//TODO ユーザ情報変更機能
	/**
	 * 保存しているユーザの情報を変更する
	 *
	 * @return	ユーザ詳細画面
	 */
	public String getUserUpdate() {
		return null;
	}

	//TODO ユーザ削除機能
	/**
	 * 詳細画面に表示しているユーザを削除する
	 *
	 * @return ユーザ一覧画面
	 */
	public String getUserDelete() {
		return null;
	}
}
