//学生用ボタン
$('#btn-std').click(function() {
	return do_submit_std(this);
});
//修正用ボタン
$('#btn-fix').click(function() {
	return do_submit_check(this);
});
//担任用ボタン
$('#btn-tea').click(function() {
	return do_submit(this);
});

//所属クラス用チェック（担任用）
$(function() {
	$("#member_class").bind("blur", function() {
		var member_class = $(this).val();
		check_member_class(member_class);
	});
});

function check_member_class(str) {
	$("#err_member_class label").remove();
	var _result = true;
	var member_class = $.trim(str);

	if (member_class.length == 0) {
		$("#err_member_class").append("<label>クラスを選択してください。</label>");
		_result = false;
	}
	return _result;
}
//出席番号用チェック（担任用）
$(function() {
	$("#class_number").bind("blur", function() {
		var class_number = $(this).val();
		check_class_number(class_number);
	});
});

function check_class_number(str) {
	$("#err_class_number label").remove();
	var _result = true;
	var class_number = $.trim(str);

	if (class_number.length == 0) {
		$("#err_class_number").append("<label>出席番号を選択してください。</label>");
		_result = false;
	}
	return _result;
}
//氏名用チェック（担任用）
$(function() {
	$("#user_id").bind("blur", function() {
		var user_id = $(this).val();
		check_user_id(user_id);
	});
});

function check_user_id(str) {
	$("#err_user_id label").remove();
	var _result = true;
	var user_id = $.trim(str);

	if (user_id.length == 0) {
		$("#err_user_id").append("<label>氏名を選択してください。</label>");
		_result = false;
	}
	return _result;
}
//開始日用チェック
$(function() {
	$("#start_date").bind("blur", function() {
		var start_date = $(this).val();
		check_start_date(start_date);
	});
});

function check_start_date(str) {
	$("#err_start_date label").remove();
	$("#err_date label").remove();
	var _result = true;
	var start_date = $.trim(str);

	if (start_date.length == 0) {
		$("#err_start_date").append("<label>開始日を選択してください。</label>");
		_result = false;
	}
	return _result;
}
//開始時間用チェック
$(function() {
	$("#start_time").bind("blur", function() {
		var start_time = $(this).val();
		check_start_time(start_time);
	});
});

function check_start_time(str) {
	$("#err_start_time label").remove();
	$("#err_date label").remove();
	var _result = true;
	var start_time = $.trim(str);

	if (start_time.length == 0) {
		$("#err_start_time").append("<label>開始時間を選択してください。</label>");
		_result = false;
	}
	return _result;
}
//終了日用チェック
$(function() {
	$("#end_date").bind("blur", function() {
		var end_date = $(this).val();
		check_end_date(end_date);
	});
});

//終了時間用チェック
$(function() {
	$("#end_time").bind("blur", function() {
		$("#err_date label").remove();
	});
});

function check_end_date(str) {
	$("#err_end_date label").remove();
	$("#err_date label").remove();
	var _result = true;
	var end_date = $.trim(str);

	if (end_date.length == 0) {
		$("#err_end_date").append("<label>終了日を選択してください。</label>");
		_result = false;
	}
	return _result;
}
//企業名用チェック
$(function() {
	$("#company_name").bind("blur", function() {
		var company_name = $(this).val();
		check_company_name(company_name);
	});
});

function check_company_name(str) {
	$("#err_company_name label").remove();
	var _result = true;
	var company_name = $.trim(str);

	if (company_name.length == 0) {
		$("#err_company_name").append("<label>企業名1は必須入力です。</label>");
		_result = false;
	} else if (company_name.length > 50) {
		$("#err_company_name").append("<label>企業名は50文字以内で入力してください</label>");
		_result = false;
	}
	return _result;
}
//内容用チェック
$(function() {
	$("input[name='job_search_content_category']").bind("change", function() {
		var job_search_content_category = $(this).val();
		check_job_search_content_category(job_search_content_category);
	});
});

function check_job_search_content_category(str) {
	$("#err_job_search_content_category label").remove();
	$("#err_otherText label").remove();
	var _result = true;
	var _check_count = 0;
	var job_search_content_category = $.trim(str);



	$("input[type='checkbox']").each(function() {
		if ($(this).prop('checked')) {
			_check_count = _check_count + 1;
			$("input[name='job_search_content_category']:checked").each(function() {
				job_search_content_category = $(this).val();
			});
		}
	});
	if (_check_count == 0) {
		$("#err_job_search_content_category").append("<label>内容区分を1つ選択してください。</label>");
		_result = false;
	}
	switch (job_search_content_category) {
		case '1':
		case '2':
		case '3':
			$("#err_otherText label").remove();
			break;
		case '9':
			//内容その他用チェック
			$(function() {
				$("#otherText").bind("blur", function() {
					var otherText = $(this).val();
					check_otherText(otherText);
				});
			});
			break;
	}

	return _result;
}


function check_otherText(str) {
	$("#err_otherText label").remove();
	var _result = true;
	var otherText = $.trim(str);

	if (otherText.match(/^[ 　\r\n\t]*$/)) {
		$("#err_otherText").append("<label>内容を入力してください。</label>");
		_result = false;
	} else if(otherText.length > 20) {
		$("#err_otherText").append("<label>内容は20文字以内で入力してください。</label>");
		_result = false;
	}
	return _result;
}
//場所用チェック
$(function() {
	$("#location").bind("blur", function() {
		var location = $(this).val();
		check_location(location);
	});
});

function check_location(str) {
	$("#err_location label").remove();
	var _result = true;
	var location = $.trim(str);

	if (location.length == 0) {
		$("#err_location").append("<label>場所を入力してください。</label>");
		_result = false;
	} else if (location.length > 254) {
		$("#err_location").append("<label>場所は254文字以内で入力してください。</label>");
		_result = false;
	}
	return _result;
}


//欠課区分選択用チェック
$(function() {
	$("#job_search_absence_category").bind("change blur", function() {
		var job_search_absence_category = $(this).val();
		check_job_search_absence_category(job_search_absence_category);
	});
});
//開始日用チェック
function check_absence_start_date(str) {
	$("#err_absence_start_date label").remove();
	$("#err_absence_date label").remove();
	var _result = true;
	var absence_start_date = $.trim(str);

	if (absence_start_date.length == 0) {
		$("#err_absence_start_date").append("<label>欠課開始日を入力してください。</label>");
		_result = false;
	}
	return _result;
}
//終了日用チェック
function check_absence_end_date(str) {
	$("#err_absence_end_date label").remove();
	$("#err_absence_date label").remove();
	var _result = true;
	var absence_end_date = $.trim(str);

	if (absence_end_date.length == 0) {
		$("#err_absence_end_date").append("<label>欠課終了日を入力してください。</label>");
		_result = false;
	}
	return _result;
}
//開始時間用チェック
function check_absence_start_time(str) {
	$("#err_absence_start_time label").remove();
	$("#err_absence_date label").remove();
	var _result = true;
	var absence_start_time = $.trim(str);

	if (absence_start_time.length == 0) {
		$("#err_absence_start_time").append("<label>欠課開始時間を入力してください。</label>");
		_result = false;
	}
	return _result;
}
//終了日用チェック
function check_absence_end_time(str) {
	$("#err_absence_end_time label").remove();
	$("#err_absence_date label").remove();
	var _result = true;
	var absence_end_time = $.trim(str);

	if (absence_end_time.length == 0) {
		$("#err_absence_end_time").append("<label>欠課終了時間を入力してください。</label>");
		_result = false;
	}
	return _result;
}
//項目選択後エラーを出す処理
function check_job_search_absence_category(id) {

	var _result = true;
	var _id = id;

	switch (_id) {

		case '0':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			break;
		case '1':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			$(function() {
				$("#absence_start_date").bind("blur", function() {
					var absence_start_date = $(this).val();
					check_absence_start_date(absence_start_date);
				});
			});
			//終了日
			$(function() {
				$("#absence_end_date").bind("blur", function() {
					var absence_end_date = $(this).val();
					check_absence_end_date(absence_end_date);
				});
			});
			break;
		case '2':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			$(function() {
				$("#absence_start_date").bind("blur", function() {
					var absence_start_date = $(this).val();
					check_absence_start_date(absence_start_date);
				});
			})
			//開始時間
			$(function() {
				$("#absence_start_time").bind("blur", function() {
					var absence_start_time = $(this).val();
					check_absence_start_time(absence_start_time);
				});
			});
			break;
		case '3':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			$(function() {
				$("#absence_start_date").bind("blur", function() {
					var absence_start_date = $(this).val();
					check_absence_start_date(absence_start_date);
				});
			})
			//開始時間
			$(function() {
				$("#absence_start_time").bind("blur", function() {
					var absence_start_time = $(this).val();
					check_absence_start_time(absence_start_time);
				});
			});
			break;
		case '4':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			$(function() {
				$("#absence_start_date").bind("blur", function() {
					var absence_start_date = $(this).val();
					check_absence_start_date(absence_start_date);
				});
			});
			//終了日
			$(function() {
				$("#absence_end_date").bind("blur", function() {
					var absence_end_date = $(this).val();
					check_absence_end_date(absence_end_date);
				});
			});
			//開始時間
			$(function() {
				$("#absence_start_time").bind("blur", function() {
					var absence_start_time = $(this).val();
					check_absence_start_time(absence_start_time);
				});
			});
			//終了時間
			$(function() {
				$("#absence_end_time").bind("blur", function() {
					var absence_end_time = $(this).val();
					check_absence_end_time(absence_end_time);
				});
			});
			break;
	}
	return _result;
}

//欠課日時各種送信時チェック
function check_job_search_absence_category_submit(id) {
	var _id = id;
	var _result = true;
	var start_date = true;
	var start_time = true;
	var end_date = true;
	var end_time = true;
	var absence_start_date = $("#absence_start_date").val();
	var absence_start_time = $("#absence_start_time").val();
	var absence_end_date = $("#absence_end_date").val();
	var absence_end_time = $("#absence_end_time").val();
	switch (_id) {

		case '0':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			break;
		case '1':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			start_date = check_absence_start_date(absence_start_date);
			//終了日
			end_date = check_absence_end_date(absence_end_date);
			break;
		case '2':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			start_date = check_absence_start_date(absence_start_date);
			//開始時間
			start_time = check_absence_start_time(absence_start_time);
			break;
		case '3':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			start_date = check_absence_start_date(absence_start_date);
			//開始時間
			start_time = check_absence_start_time(absence_start_time);
			break;
		case '4':
			$("#err_absence_start_date label").remove();
			$("#err_absence_start_time label").remove();
			$("#err_absence_end_date label").remove();
			$("#err_absence_end_time label").remove();
			$("#err_absence_date label").remove();
			//開始日
			start_date = check_absence_start_date(absence_start_date);
			//終了日
			end_date = check_absence_end_date(absence_end_date);
			//開始時間
			start_time = check_absence_start_time(absence_start_time);
			//終了時間
			end_time = check_absence_end_time(absence_end_time);
			break;
	}
	if (!start_date) {
		_result = false;
	}
	if (!end_date) {
		_result = false;
	}
	if (!start_time) {
		_result = false;
	}
	if (!end_time) {
		_result = false;
	}
	return _result;
}

//備考用チェック
$(function() {
	$("#remarks").bind("blur", function() {
		var remarks = $(this).val();
		check_remarks(remarks);
	});
});

function check_remarks(str) {
	$("#err_textarea label").remove();
	var _result = true;
	var remarks = $.trim(str);

	if (remarks.length >= 1000) {
		$("#err_textarea").append("<label>備考は1000文字以内で入力してください。</label>");
		_result = false;
	}
	return _result;
}

$(function() {
	$("input[id^='job_search_content_category']").click(function() {
		var flag = $(this).prop('checked');
		$("input[id^='job_search_content_category']").prop('checked', false);
		if (flag) $(this).prop('checked', true);
	});
});


//担任用送信時チェック
function do_check() {
	var result = true;
	var check_result = true;

	// クラス用送信時チェック
	var member_class = $("#member_class").val();
	result = check_member_class(member_class);
	if (!result) {
		check_result = result;
	}
	// 出席番号用送信時チェック
	var class_number = $("#class_number").val();
	result = check_class_number(class_number);
	if (!result) {
		check_result = result;
	}

	// 氏名用送信時チェック
	var user_id = $("#user_id").val();
	result = check_user_id(user_id);
	if (!result) {
		check_result = result;
	}
	// 開始日用送信時チェック
	var start_date = $("#start_date").val();
	result = check_start_date(start_date);
	if (!result) {
		check_result = result;
	}
	// 開始時間用送信時チェック
	var start_time = $("#start_time").val();
	result = check_start_time(start_time);
	if (!result) {
		check_result = result;
	}

	// 終了日用送信時チェック
	var end_date = $("#end_date").val();
	result = check_end_date(end_date);
	if (!result) {
		check_result = result;
	}
	var end_time = $("#end_time").val();

	//開始・終了日時の比較
	if (start_date > end_date) {
		$("#err_date label").remove();
		$("#err_date").append("<label>適切な日時を選択してください。</label>");
		check_result = false;
	}
	if (start_date == end_date) {
		if (end_time.match(/^[ 　\r\n\t]*$/)) {
		} else {
			if (start_time > end_time) {
				$("#err_date label").remove();
				$("#err_date").append("<label>適切な日時を選択してください。</label>");
				check_result = false;
			}
		}
	}
	// 企業名用送信時チェック
	var company_name = $("#company_name").val();
	result = check_company_name(company_name);
	if (!result) {
		check_result = result;
	}
	// 内容区分用送信時チェック
	var job_search_content_category = $("input[name='job_search_content_category']:checked").val() || 0;
	result = check_job_search_content_category(job_search_content_category);
	if (!result) {
		check_result = result;
	}
	// その他内容チェック
	var otherText = $("#otherText").val();
	result = check_otherText(otherText);
	$("input[name='job_search_content_category']:checked").each(function() {
	});
	switch (job_search_content_category) {
		case '1':
		case '2':
		case '3':
			$("#err_otherText label").remove();
			break;
		case '9':
			if (!result) {
				check_result = result;
			}
			break;
	}

	// 場所用送信時チェック
	var location = $("#location").val();
	result = check_location(location);
	if (!result) {
		check_result = result;
	}
	// 欠課区分用送信時チェック
	var job_search_absence_category = $("#job_search_absence_category").val();
	result = check_job_search_absence_category_submit(job_search_absence_category);

	if (!result) {
		check_result = result;
	}
	//欠課日時チェック
	var absence_start_date = $("#absence_start_date").val();
	var absence_start_time = $("#absence_start_time").val();
	var absence_end_date = $("#absence_end_date").val();
	var absence_end_time = $("#absence_end_time").val();

	switch (job_search_absence_category) {
		case '0':
			break;
		case '1':
			if (absence_start_date > absence_end_date) {
				$("#err_absence_date label").remove();
				$("#err_absence_date").append("<label>適切な欠課日時を選択してください。</label>");
				check_result = false;
			}
		case '2':
		case '3':
			break;
		case '4':
			if (absence_start_date > absence_end_date) {
				$("#err_absence_date label").remove();
				$("#err_absence_date").append("<label>適切な欠課日時を選択してください。</label>");
				check_result = false;
			}
			if (absence_start_date == absence_end_date) {
				if (absence_end_time.match(/^[ 　\r\n\t]*$/)) {
				} else {
					if (absence_start_time > absence_end_time) {
						$("#err_absence_date label").remove();
						$("#err_absence_date").append("<label>適切な欠課日時を選択してください。</label>");
						check_result = false;
					}
				}
			}
			break;
	}

	// 備考用送信時チェック
	var remarks = $("#remarks").val();
	result = check_remarks(remarks);
	if (!result) {
		check_result = result;
	}
	return check_result;
}


//学生＆修正用送信時チェック
function submit_check() {
	var result = true;
	var check_result = true;

	// 開始日用送信時チェック
	var start_date = $("#start_date").val();
	result = check_start_date(start_date);
	if (!result) {
		check_result = result;
	}
	// 開始時間用送信時チェック
	var start_time = $("#start_time").val();
	result = check_start_time(start_time);
	if (!result) {
		check_result = result;
	}

	// 終了日用送信時チェック
	var end_date = $("#end_date").val();
	result = check_end_date(end_date);
	if (!result) {
		check_result = result;
	}
	var end_time = $("#end_time").val();

	//開始・終了日時の比較
	if (start_date > end_date) {
		$("#err_date label").remove();
		$("#err_date").append("<label>適切な日時を選択してください。</label>");
		check_result = false;
	}
	if (start_date == end_date) {
		if (end_time.match(/^[ 　\r\n\t]*$/)) {
		} else {
			if (start_time > end_time) {
				$("#err_date label").remove();
				$("#err_date").append("<label>適切な日時を選択してください。</label>");
				check_result = false;
			}
		}
	}

	// 企業名用送信時チェック
	var company_name = $("#company_name").val();
	result = check_company_name(company_name);
	if (!result) {
		check_result = result;
	}
	// 内容区分用送信時チェック
	var job_search_content_category = $("input[name='job_search_content_category']:checked").val() || 0;
	result = check_job_search_content_category(job_search_content_category);
	if (!result) {
		check_result = result;
	}
	// その他内容チェック
	var otherText = $("#otherText").val();
	result = check_otherText(otherText);
	$("input[name='job_search_content_category']:checked").each(function() {
	});
	switch (job_search_content_category) {
		case '1':
		case '2':
		case '3':
			$("#err_otherText label").remove();
			break;
		case '9':
			if (!result) {
				check_result = result;
			}
			break;
	}

	// 場所用送信時チェック
	var location = $("#location").val();
	result = check_location(location);
	if (!result) {
		check_result = result;
	}
	// 欠課区分用送信時チェック
	var job_search_absence_category = $("#job_search_absence_category").val();
	result = check_job_search_absence_category_submit(job_search_absence_category);

	if (!result) {
		check_result = result;
	}

	//欠課日時チェック
	var absence_start_date = $("#absence_start_date").val();
	var absence_start_time = $("#absence_start_time").val();
	var absence_end_date = $("#absence_end_date").val();
	var absence_end_time = $("#absence_end_time").val();

	switch (job_search_absence_category) {
		case '0':
			break;
		case '1':
			if (absence_start_date > absence_end_date) {
				$("#err_absence_date label").remove();
				$("#err_absence_date").append("<label>適切な欠課日時を選択してください。</label>");
				check_result = false;
			}
		case '2':
		case '3':
			break;
		case '4':
			if (absence_start_date > absence_end_date) {
				$("#err_absence_date label").remove();
				$("#err_absence_date").append("<label>適切な欠課日時を選択してください。</label>");
				check_result = false;
			}
			if (absence_start_date == absence_end_date) {
				if (absence_end_time.match(/^[ 　\r\n\t]*$/)) {
				} else {
					if (absence_start_time > absence_end_time) {
						$("#err_absence_date label").remove();
						$("#err_absence_date").append("<label>適切な欠課日時を選択してください。</label>");
						check_result = false;
					}
				}
			}
			break;
	}


	// 備考用送信時チェック
	var remarks = $("#remarks").val();
	result = check_remarks(remarks);
	if (!result) {
		check_result = result;
	}
	return check_result;
}

//申請ボタン非活性解除
$('input,select').change(function() {
	$('#btn-std').prop("disabled", false);
	$('#btn-tea').prop("disabled", false);
	$('#btn-fix').prop("disabled", false);
});

function do_submit(btn) {
	var check = false;
	var result = do_check();
	if (result) {
		alert("就職活動申請（申請）を新規作成しました。");
		check = true;
	} else {
		alert("エラーがあります。\n画面上部を確認してください。");
		//申請ボタン非活性
		$(btn).prop("disabled", true);
		check = false;
	}
	return check;
}

function do_submit_check(btn) {

	var result = submit_check();
	if (result) {
		alert("就職活動申請（申請）を修正しました。");
		check = true;
	} else {
		alert("エラーがあります。\n画面上部を確認してください。");
		//申請ボタン非活性
		$(btn).prop("disabled", true);
		check = false;
	}

	return check;
}
function do_submit_std(btn) {

	var result = submit_check();
	if (result) {
		alert("就職活動申請（申請）を新規作成しました。");
		check = true;
	} else {
		alert("エラーがあります。\n画面上部を確認してください。");
		//申請ボタン非活性
		$(btn).prop("disabled", true);
		check = false;
	}

	return check;
}