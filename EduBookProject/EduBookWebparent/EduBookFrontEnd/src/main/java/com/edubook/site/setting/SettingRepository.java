package com.edubook.site.setting;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.edubook.common.entity.Setting;
import com.edubook.common.entity.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String>{

	public List<Setting> findByCategory(SettingCategory category);
	
	//@Query("SELECT s FROM Setting s WHERE s.key = :key")
	public Setting findByKey(String key);

}
