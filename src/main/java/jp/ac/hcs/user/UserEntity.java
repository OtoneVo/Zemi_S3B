package jp.ac.hcs.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 病院情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class UserEntity {

	/** ユーザ情報のリスト */
	private List<UserData> UserList = new ArrayList<UserData>();

}