package jp.ac.hcs.reservation;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 予約情報の一覧を管理するエンティティクラス
 */
@Data
public class ReservationEntity {

	private List<ReservationData> ReservationList = new ArrayList<ReservationData>();
}
