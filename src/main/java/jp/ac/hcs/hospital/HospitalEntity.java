package jp.ac.hcs.hospital;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 病院情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class HospitalEntity {

	/** 病院情報のリスト */
	private List<HospitalData> HospitalList = new ArrayList<HospitalData>();

}
