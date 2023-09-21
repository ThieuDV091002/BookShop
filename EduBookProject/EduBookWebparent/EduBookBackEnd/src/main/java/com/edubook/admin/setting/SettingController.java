package com.edubook.admin.setting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.common.entity.Setting;

@Controller
public class SettingController {

	@Autowired
	private SettingService service;
	
	@GetMapping("/payment")
	public String viewPayment(Model model) {
		return "settings/payment";
	}
	
	@PostMapping("/payment-save")
	public String savePaymentSetting(HttpServletRequest request, RedirectAttributes ra) {
		List<Setting> paymentSettings = service.getPaymentSetting();
		updateSettingValuesFromForm(request, paymentSettings);
		ra.addFlashAttribute("message", "Cài đặt cho phương thức thanh toán đã được lưu thành công!");
		return "redirect:/payment";
	}

	public void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
		for(Setting setting : listSettings) {
			String value = request.getParameter(setting.getKey());
			if(value != null) {
				setting.setValue(value);
			}
		}
		service.saveAll(listSettings);
	}
}
