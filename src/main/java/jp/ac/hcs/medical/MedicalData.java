package jp.ac.hcs.medical;

import lombok.Data;

/**
 * 1件分の診療科情報
 */
@Data
public class MedicalData {

	/**
	 * 診療科ID	主キー
	 */
	private String medical_id;

	/**
	 * 診療科名
	 */
	private String medical_name;

}
