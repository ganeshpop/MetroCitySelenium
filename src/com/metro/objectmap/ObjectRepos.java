package com.metro.objectmap;

public class ObjectRepos {

    public static class HomePage {
        public static final String login_button_classname = "button-primary";
    }

    public static class LoginPage {
        public static final String card_id_text_box_id = "cardId";
        public static final String password_text_box_id = "password";
        public static final String login_button_classname = "button-block";
    }

    public static class MenuPage {
        public static final String message_container_css_selector = "body > div > div > main > section > div > div > div.pricing-header.text-center > h2";
    }

    public static class NavBar {
        public static final String logout_button_css_selector = "body > nav > ul > li:nth-child(8) > a";
        public static final String recharge_button_css_selector = "body > nav > ul > li:nth-child(4) > a";
        public static final String swipe_in_button_css_selector = "body > nav > ul > li:nth-child(5) > a";
        public static final String swipe_out_button_css_selector = "body > nav > ul > li:nth-child(6) > a";
    }

	public static class RechargePage {
		public static final String recharge_button_css_selector = "body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-cta.mb-8 > input";
		public static final String amount_text_box_xpath = "/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/ul/form/li/span/input";
		public static final String balance_container_xpath = "/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/div/div/span[2]";
	}

	public static class RechargeStatusPage {
		public static final String updated_balance_container_xpath = "/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/div/div/span[2]";
		public static final String error_container_id = "amount.errors";
	}

	public static class SwipeInPage {
		public static final String stations_classname = "rad-label";
		public static final String swipe_in_button_classname = "button-block";
	}

	public static class SwipeInStatusPage {
		public static final String message_container_xpath = "/html/body/div/div/main/section/div/div/div[2]/div/div/div/div[1]/h4";
	}

	public static class SwipeOutPage {
		public static final String stations_classname = "rad-label";
		public static final String swipe_out_button_classname = "button-block";
	}

	public static class SwipeOutStatusPage {
		public static final String message_container_css_selector = "body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-main > div > h4:nth-child(1)";
	}
}
