/**
 * 病院変更画面の診療科のチェックボックスにチェックをつけるJavaScript
 */
$(function() {
	let documents = [document.getElementById("medical_name")];
	let foo = 0;

	for (foo; foo < documents.length; foo++) {

		if (documents[foo].value.indexOf("外科") != -1) {
			document.getElementById('surgical').checked = true;
		} else {
			document.getElementById('surgical').checked = false;
		}

		if (documents[foo].value.indexOf("内科") != -1) {
			document.getElementById('internal_medicine').checked = true;
		} else {
			document.getElementById('internal_medicine').checked = false;
		}

		if (documents[foo].value.indexOf("皮膚科") != -1) {
			document.getElementById('hihu').checked = true;
		} else {
			document.getElementById('hihu').checked = false;
		}

		if (documents[foo].value.indexOf("精神科") != -1) {
			document.getElementById('sei').checked = true;
		} else {
			document.getElementById('sei').checked = false;
		}

		if (documents[foo].value.indexOf("産婦人科") != -1) {
			document.getElementById('san').checked = true;
		} else {
			document.getElementById('san').checked = false;
		}

		if (documents[foo].value.indexOf("脳神経外科") != -1) {
			document.getElementById('nou').checked = true;
		} else {
			document.getElementById('nou').checked = false;
		}

	}
});