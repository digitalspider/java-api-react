package au.com.digitalspider.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {

	public static final String BASE_API = "/api/v1";

	public static final String SYSPROP_DIR_USER = System.getProperty("user.dir");
	public static final String SYSPROP_DIR_TMP = System.getProperty("java.io.tmpdir");

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_MANAGER = "MANAGER";

	public static final String PATTERN_DD_MM_YYYY = "dd/MM/YYYY";
	public static final String PATTERN_DD_MM_YYYY_HH_MM = "dd/MM/YYYY HH:mm";
	public static final String PATTERN_YYYYMMDD = "YYYYMMdd";
	public static final String PATTERN_YYYYMMDDHHMM = "YYYYMMdd'T'HHMM";
	public static final String PATTERN_YYYY_MM_DD = "YYYY-MM-dd";
	public static final String PATTERN_YYYY_MM_DD_HH_MM = "YYYY-MM-dd HH:mm";
	public static final DateFormat FORMAT_ISO_LONG_DATE = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
	public static final DateFormat FORMAT_ISO_LONG_DATE_TIME = new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM);
	public static final DateFormat FORMAT_ISO_SHORT_DATE = new SimpleDateFormat(PATTERN_YYYYMMDD);
	public static final DateFormat FORMAT_ISO_SHORT_DATE_TIME = new SimpleDateFormat(PATTERN_YYYYMMDDHHMM);
	public static final DateFormat FORMAT_AUS_DATE = new SimpleDateFormat(PATTERN_DD_MM_YYYY);
	public static final DateFormat FORMAT_AUS_DATE_TIME = new SimpleDateFormat(PATTERN_DD_MM_YYYY_HH_MM);

	public static final String FILE_EXTENSION_PNG = ".png";

}
