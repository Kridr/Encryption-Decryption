import java.util.Scanner;

class Time {

    int hour;
    int minute;
    int second;

    public static Time noon() {
        Time time = new Time();
        time.hour = 12;
        time.minute = 0;
        time.second = 0;

        return time;
    }

    public static Time midnight() {
        Time time = new Time();
        time.hour = 0;
        time.minute = 0;
        time.second = 0;

        return time;
    }

    public static Time ofSeconds(long seconds) {
        int oneDaySeconds = (int) seconds % 86_400;

        int hour = oneDaySeconds / 3600;
        oneDaySeconds -= hour * 3600;
        int minute = oneDaySeconds / 60;
        int second = oneDaySeconds % 60;

        Time time = new Time();

        time.hour = hour;
        time.minute = minute;
        time.second = second;

        return time;
    }

    public static Time of(int hour, int minute, int second) {
        if (hour <= 23 && hour >= 0 && minute <= 59 && minute >= 0 && second <= 59 && second >= 0) {
            Time time = new Time();

            time.hour = hour;
            time.minute = minute;
            time.second = second;

            return time;
        } else {
            return null;
        }
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final String type = scanner.next();
        Time time = null;

        switch (type) {
            case "noon":
                time = Time.noon();
                break;
            case "midnight":
                time = Time.midnight();
                break;
            case "hms":
                int h = scanner.nextInt();
                int m = scanner.nextInt();
                int s = scanner.nextInt();
                time = Time.of(h, m, s);
                break;
            case "seconds":
                time = Time.ofSeconds(scanner.nextInt());
                break;
            default:
                time = null;
                break;
        }

        if (time == null) {
            System.out.println(time);
        } else {
            System.out.println(String.format("%s %s %s", time.hour, time.minute, time.second));
        }
    }
}