/**
 * ユーザ削除確認
 */
function delete_user() {
	var result = window.confirm("選択したユーザを削除します。よろしいですか？")
	if(result) {
		return true;
	} else {
		return false;
	}
}