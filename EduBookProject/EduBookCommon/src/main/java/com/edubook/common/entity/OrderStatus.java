package com.edubook.common.entity;

public enum OrderStatus {
	NEW{
		@Override
		public String defaultDescription() {
			return "Đơn hàng đã được đặt thành công";
		}
	}, CANCELLED{
		@Override
		public String defaultDescription() {
			return "Đơn hàng đã bị hủy";
		}
	}, PROCESSING{
		@Override
		public String defaultDescription() {
			return "Đơn hàng đang được xử lý";
		}
	}, PACKAGED{
		@Override
		public String defaultDescription() {
			return "Đơn hàng đã được đóng gói thành công";
		}
	}, PICKED{
		@Override
		public String defaultDescription() {
			return "Người vận chuyển đã lấy đơn hàng";
		}
	}, SHIPPING{
		@Override
		public String defaultDescription() {
			return "Đơn hàng đang được vận chuyển";
		}
	},DELIVERED{
		@Override
		public String defaultDescription() {
			return "Đơn hàng đã được giao thành công";
		}
	}, RETURNED{
		@Override
		public String defaultDescription() {
			return "Sản phẩm đã được trả lại";
		}
	}, PAID{
		@Override
		public String defaultDescription() {
			return "Khách hàng đã thanh toán cho đơn hàng này";
		}
	}, REFUNDED{
		@Override
		public String defaultDescription() {
			return "Khách hàng đã được hoàn tiền";
		}
	},RETURN_REQUESTED{
		@Override
		public String defaultDescription() {
			return "Khách hàng yêu cầu trả lại sản phẩm";
		}
	};
	
	public abstract String defaultDescription();
	
}
