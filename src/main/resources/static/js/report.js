$(function() {
	$("#report_content").bind("blur", function() {
		var report_content = $(this).val();
		check_report_content(report_content);
		$('#report').prop("disabled", false);
	});
});

function check_report_content(str) {
	$("#err_report_content label").remove();
	$("#err_report_content br").remove();
	var _result = true;
	var report_content = $.trim(str);

	if (report_content.length == 0) {
		$("#err_report_content").append("<br><label>報告内容を入力してください。</label>");
		_result = false;
	} else if (report_content.length >= 1000) {
		$("#err_report_content").append("<br><label>報告内容は1000文字以内で入力してください。</label>");
		_result = false;
	}
	return _result;
}

function do_check() {
	var result = true;
	var check_result = true;

	// エラーメッセージ初期化
	$("#err_report_content label").remove();
	$("#err_report_content br").remove();

	// テキストエリア
	var report_content = $("#report_content").val();
	result = check_report_content(report_content);
	if (!result) {
		check_result = result;
	}

	return check_result;
}


function do_submit() {
	var result = do_check();
	var check = true;

	if (result) {
		alert("就職活動申請（報告）を新規作成しました。");
		check = true;
	} else {
		alert("エラーがあります。\n画面を確認してください。");
		//新規作成ボタン非活性
		$('#report').prop("disabled", true);
		check = false;
	}

	return check;
}