package jp.ac.hcs.hospital;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * タスク情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class HospitalEntity {

	/** タスク情報のリスト */
	private List<HospitalData> HospitalList = new ArrayList<HospitalData>();

}
