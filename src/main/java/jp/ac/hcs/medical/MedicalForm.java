package jp.ac.hcs.medical;

import java.util.List;

import lombok.Data;

/**
 * フロントバックで診療科情報をやり取りする 各項目のデータ仕様はMedicalEntityを参照とする
 */
@Data
public class MedicalForm {

	/**
	 *	診療科ID
	 */
	private List<String> medical_id;

	/**
	 *	病院ID
	 */
	private String hospital_id;

}
