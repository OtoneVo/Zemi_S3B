package jp.ac.hcs.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ情報を取得する
	 *
	 * @return hospitalEntity	取得したユーザデータ
	 */
	public UserEntity getUsers() {

		UserEntity userEntity = new UserEntity();

		try {
			userEntity = userRepository.selectAll();
		} catch (DataAccessException e) {
			log.info("全件取得:異常発生");
			throw e;
		}
		return userEntity;

	}
}