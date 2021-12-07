package jp.ac.hcs.medical;

import java.util.List;

import lombok.Data;

/**
 * 1件分病院と診療科情報
 */
@Data
public class MedicalData {

	/**
	 * 診療科ID	主キー、外部キー(診療科テーブル)
	 */
	private List<String> medical_id;

	/**
	 * 病院ID	主キー、外部キー(病院テーブル)
	 */
	private String hospital_id;

}
