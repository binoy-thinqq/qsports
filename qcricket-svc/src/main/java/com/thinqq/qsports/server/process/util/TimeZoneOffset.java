package com.thinqq.qsports.server.process.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeZoneOffset {

	public static class TimeZoneInfo{
		private int id;
		private String zoneId;
		private String country;

		private TimeZoneInfo(int id, String country, String zoneId) {
			super();
			this.id = id;
			this.zoneId = zoneId;
			this.country = country;
		}

		public int getId() {
			return id;
		}

		public String getZoneId() {
			return zoneId;
		}

		public String getCountry() {
			return country;
		}

	}
	private static Map<String, List<TimeZoneInfo>> TIME_ZONE_OFFSET = new HashMap<String, List<TimeZoneInfo>>();

	static {
		TIME_ZONE_OFFSET.put("AU", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(33,"AU","Australia/Lord_Howe"),
				new TimeZoneInfo(34,"AU","Antarctica/Macquarie"),
				new TimeZoneInfo(35,"AU","Australia/Hobart"),
				new TimeZoneInfo(36,"AU","Australia/Currie"),
				new TimeZoneInfo(37,"AU","Australia/Melbourne"),
				new TimeZoneInfo(38,"AU","Australia/Sydney"),
				new TimeZoneInfo(39,"AU","Australia/Broken_Hill"),
				new TimeZoneInfo(40,"AU","Australia/Brisbane"),
				new TimeZoneInfo(41,"AU","Australia/Lindeman"),
				new TimeZoneInfo(42,"AU","Australia/Adelaide"),
				new TimeZoneInfo(43,"AU","Australia/Darwin"),
				new TimeZoneInfo(44,"AU","Australia/Perth"),
				new TimeZoneInfo(45,"AU","Australia/Eucla"),
		}));
		TIME_ZONE_OFFSET.put("BD", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(51,"BD","Asia/Dhaka")
		}));

		TIME_ZONE_OFFSET.put("CA", Arrays.asList(new TimeZoneInfo[]{	
				new TimeZoneInfo(84,"CA","America/St_Johns"),
				new TimeZoneInfo(85,"CA","America/Halifax"),
				new TimeZoneInfo(86,"CA","America/Glace_Bay"),
				new TimeZoneInfo(87,"CA","America/Moncton"),
				new TimeZoneInfo(88,"CA","America/Goose_Bay"),
				new TimeZoneInfo(89,"CA","America/Blanc-Sablon"),
				new TimeZoneInfo(90,"CA","America/Montreal"),
				new TimeZoneInfo(91,"CA","America/Toronto"),
				new TimeZoneInfo(92,"CA","America/Nipigon"),
				new TimeZoneInfo(93,"CA","America/Thunder_Bay"),
				new TimeZoneInfo(94,"CA","America/Iqaluit"),
				new TimeZoneInfo(95,"CA","America/Pangnirtung"),
				new TimeZoneInfo(96,"CA","America/Resolute"),
				new TimeZoneInfo(97,"CA","America/Atikokan"),
				new TimeZoneInfo(98,"CA","America/Rankin_Inlet"),
				new TimeZoneInfo(99,"CA","America/Winnipeg"),
				new TimeZoneInfo(100,"CA","America/Rainy_River"),
				new TimeZoneInfo(101,"CA","America/Regina"),
				new TimeZoneInfo(102,"CA","America/Swift_Current"),
				new TimeZoneInfo(103,"CA","America/Edmonton"),
				new TimeZoneInfo(104,"CA","America/Cambridge_Bay"),
				new TimeZoneInfo(105,"CA","America/Yellowknife"),
				new TimeZoneInfo(106,"CA","America/Inuvik"),
				new TimeZoneInfo(107,"CA","America/Creston"),
				new TimeZoneInfo(108,"CA","America/Dawson_Creek"),
				new TimeZoneInfo(109,"CA","America/Vancouver"),
				new TimeZoneInfo(110,"CA","America/Whitehorse"),
				new TimeZoneInfo(111,"CA","America/Dawson"),
		}));

		TIME_ZONE_OFFSET.put("IN", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(195,"IN","Asia/Kolkata")
		}));
		TIME_ZONE_OFFSET.put("KE", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(205,"KE","Africa/Nairobi")
		}));
		TIME_ZONE_OFFSET.put("NL", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(277,"NL","Europe/Amsterdam")
		}));
		TIME_ZONE_OFFSET.put("PK", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(292,"PK","Asia/Karachi")
		}));
		TIME_ZONE_OFFSET.put("ZA", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(416,"ZA","Africa/Johannesburg")
		}));
		TIME_ZONE_OFFSET.put("LK", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(226,"LK","Asia/Colombo")
		}));
		TIME_ZONE_OFFSET.put("GB", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(162,"GB","Europe/London")
		}));
		TIME_ZONE_OFFSET.put("US", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(372,"US","America/New_York"),
				new TimeZoneInfo(373,"US","America/Detroit"),
				new TimeZoneInfo(374,"US","America/Kentucky/Louisville"),
				new TimeZoneInfo(375,"US","America/Kentucky/Monticello"),
				new TimeZoneInfo(376,"US","America/Indiana/Indianapolis"),
				new TimeZoneInfo(377,"US","America/Indiana/Vincennes"),
				new TimeZoneInfo(378,"US","America/Indiana/Winamac"),
				new TimeZoneInfo(379,"US","America/Indiana/Marengo"),
				new TimeZoneInfo(380,"US","America/Indiana/Petersburg"),
				new TimeZoneInfo(381,"US","America/Indiana/Vevay"),
				new TimeZoneInfo(382,"US","America/Chicago"),
				new TimeZoneInfo(383,"US","America/Indiana/Tell_City"),
				new TimeZoneInfo(384,"US","America/Indiana/Knox"),
				new TimeZoneInfo(385,"US","America/Menominee"),
				new TimeZoneInfo(386,"US","America/North_Dakota/Center"),
				new TimeZoneInfo(387,"US","America/North_Dakota/New_Salem"),
				new TimeZoneInfo(388,"US","America/North_Dakota/Beulah"),
				new TimeZoneInfo(389,"US","America/Denver"),
				new TimeZoneInfo(390,"US","America/Boise"),
				new TimeZoneInfo(391,"US","America/Shiprock"),
				new TimeZoneInfo(392,"US","America/Phoenix"),
				new TimeZoneInfo(393,"US","America/Los_Angeles"),
				new TimeZoneInfo(394,"US","America/Anchorage"),
				new TimeZoneInfo(395,"US","America/Juneau"),
				new TimeZoneInfo(396,"US","America/Sitka"),
				new TimeZoneInfo(397,"US","America/Yakutat"),
				new TimeZoneInfo(398,"US","America/Nome"),
				new TimeZoneInfo(399,"US","America/Adak"),
				new TimeZoneInfo(400,"US","America/Metlakatla"),
				new TimeZoneInfo(401,"US","Pacific/Honolulu")
		}));
		TIME_ZONE_OFFSET.put("ZW", Arrays.asList(new TimeZoneInfo[]{
				new TimeZoneInfo(418,"ZW","Africa/Harare")
		}));
	}
	public static List<TimeZoneInfo> getTimeZoneList(String countryCode){
		return TIME_ZONE_OFFSET.get(countryCode);
	}
}
