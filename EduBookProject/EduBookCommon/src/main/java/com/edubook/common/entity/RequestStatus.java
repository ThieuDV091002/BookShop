package com.edubook.common.entity;

public enum RequestStatus {
	PROCESSING{
		@Override
		public String defaultDescription() {
			return "Yêu cầu đang chờ được xử lý";
		}
	}, ACCEPTED{
		@Override
		public String defaultDescription() {
			return "Yêu cầu được chấp nhận";
		}
	}, REJECTED{
		@Override
		public String defaultDescription() {
			return "Yêu cầu bị từ chối";
		}
	};
	
	public abstract String defaultDescription();
}
