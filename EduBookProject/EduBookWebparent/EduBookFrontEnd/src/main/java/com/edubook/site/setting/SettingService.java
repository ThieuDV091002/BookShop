package com.edubook.site.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edubook.common.entity.Setting;
import com.edubook.common.entity.SettingCategory;

@Service
public class SettingService {

	@Autowired
	private SettingRepository repo;
	
	public PaymentSettingBag getPaymentSetting() {
		List<Setting> settings = repo.findByCategory(SettingCategory.PAYMENT);
		return new PaymentSettingBag(settings);
	}
}
