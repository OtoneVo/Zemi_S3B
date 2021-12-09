package jp.ac.hcs.medical;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 診療科情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class MedicalEntity {

	/** 診療科情報のリスト */
	private List<MedicalData> MedicalList = new ArrayList<MedicalData>();

}
