package kelvin.fiveminsurvival.survival.world;

public class Seasons {
	public static final int SPRING = 0, SUMMER = 1, WINTER = 2, FALL = 3;
	
	public static final double monthSize = 8.0;
	public static final double year = monthSize * 12.0;
	
	public static int getMonth(double day) {
		return (int)((day % year) / monthSize) + 1;
	}
	
	public static boolean isNight(long time) {
		return time % 24000 > 12000 && time % 24000 < 23000;
	}
	
	public static int getSeason(double day) {
		int month = getMonth(day);
		if (month == 1 || month == 2 || month == 12) {
			return Seasons.WINTER;
		}
		if (month == 3 || month == 4 || month == 5) {
			return Seasons.SPRING;
		}
		if (month == 6 || month == 7 || month == 8) {
			return Seasons.SUMMER;
		}
		return Seasons.FALL;
	}
	
	
	public static boolean isBloodMoon(long day) {
		int DAY = (int)day % (int)year;
        return DAY == monthSize * 11 - 5; // end of fall
    }
	
	public static boolean isHarvestMoon(long day) {
		int DAY = (int)day % (int)year;
        return DAY == monthSize * 8 - 5; // end of summer
    }
	
	public static boolean isBlueMoon(long day) {
		int DAY = (int)day % (int)year;
        return DAY == monthSize * 5 - 5; // end of spring
    }
	
	public static boolean isDeathMoon(long day) {
		int DAY = (int)day % (int)year;
        return DAY == monthSize * 2 - 5; // end of winter
    }
}
