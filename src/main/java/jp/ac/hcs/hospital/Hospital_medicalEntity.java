package jp.ac.hcs.hospital;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 病院情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class Hospital_medicalEntity {

	/** 病院情報のリスト */
	private List<Hospital_medicalData> Hospital_medicalList = new ArrayList<Hospital_medicalData>();

}
