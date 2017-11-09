package com.cglib.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:Neptune
 * @Description:DateUtil �ṩһЩ���õ�ʱ���뷨�ķ���
 */
public final class DateUtil {

    //����ʱ�����͸�ʽ
    private static final String DATETIME_FORMAT = OpslabConfig.DATETIME_FORMAT;

    //�������͸�ʽ
    private static final String DATE_FORMAT = OpslabConfig.DATE_FORMAT;

    //ʱ�����͵ĸ�ʽ
    private static final String TIME_FORMAT = OpslabConfig.TIME_FORMAT;

    //ע��SimpleDateFormat�����̰߳�ȫ��
    private static ThreadLocal<SimpleDateFormat> ThreadDateTime = new ThreadLocal<SimpleDateFormat>();
    private static ThreadLocal<SimpleDateFormat> ThreadDate = new ThreadLocal<SimpleDateFormat>();
    private static ThreadLocal<SimpleDateFormat> ThreadTime = new ThreadLocal<SimpleDateFormat>();

    private static SimpleDateFormat DateTimeInstance() {
        SimpleDateFormat df = ThreadDateTime.get();
        if (df == null) {
            df = new SimpleDateFormat(DATETIME_FORMAT);
            ThreadDateTime.set(df);
        }
        return df;
    }

    private static SimpleDateFormat DateInstance() {
        SimpleDateFormat df = ThreadDate.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_FORMAT);
            ThreadDate.set(df);
        }
        return df;
    }

    private static SimpleDateFormat TimeInstance() {
        SimpleDateFormat df = ThreadTime.get();
        if (df == null) {
            df = new SimpleDateFormat(TIME_FORMAT);
            ThreadTime.set(df);
        }
        return df;
    }


    /**
     * ��ȡ��ǰ����ʱ��
     *
     * @return ���ص�ǰʱ����ַ�ֵ
     */
    public static String currentDateTime() {
        return DateTimeInstance().format(new Date());
    }

    /**
     * ��ָ����ʱ���ʽ���ɳ�����
     *
     * @param date
     * @return
     */
    public static String dateTime(Date date) {
        return DateTimeInstance().format(date);
    }

    /**
     * ��ָ�����ַ����Ϊʱ������
     *
     * @param datestr
     * @return
     * @throws ParseException
     */
    public static Date dateTime(String datestr) throws ParseException {
        return DateTimeInstance().parse(datestr);
    }

    /**
     * ��ȡ��ǰ������
     *
     * @return
     */
    public static String currentDate() {
        return DateInstance().format(new Date());
    }

    /**
     * ��ָ����ʱ���ʽ���ɳ�����
     *
     * @param date
     * @return
     */
    public  static String date(Date date) {
        return DateInstance().format(date);
    }

    /**
     * ��ָ�����ַ����Ϊʱ������
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public  static Date date(String dateStr) throws ParseException {
        return DateInstance().parse(dateStr);
    }

    /**
     * ��ȡ��ǰ��ʱ��
     *
     * @return
     */
    public  static String currentTime() {
        return TimeInstance().format(new Date());
    }

    /**
     * ��ָ����ʱ���ʽ���ɳ�����
     *
     * @param date
     * @return
     */
    public  static String time(Date date) {
        return TimeInstance().format(date);
    }

    /**
     * ��ָ�����ַ����Ϊʱ������
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public  static Date time(String dateStr) throws ParseException {
        return TimeInstance().parse(dateStr);
    }


    /**
     * �ڵ�ǰʱ��Ļ��ϼӻ��ȥyear��
     *
     * @param year
     * @return
     */
    public  static Date year(int year) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(Calendar.YEAR, year);
        return Cal.getTime();
    }

    /**
     * ��ָ����ʱ���ϼӻ��ȥ����
     *
     * @param date
     * @param year
     * @return
     */
    public  static Date year(Date date, int year) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.YEAR, year);
        return Cal.getTime();
    }

    /**
     * �ڵ�ǰʱ��Ļ��ϼӻ��ȥ����
     *
     * @param month
     * @return
     */
    public  static Date month(int month) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(Calendar.MONTH, month);
        return Cal.getTime();
    }

    /**
     * ��ָ����ʱ���ϼӻ��ȥ����
     *
     * @param date
     * @param month
     * @return
     */
    public  static Date month(Date date, int month) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.MONTH, month);
        return Cal.getTime();
    }

    /**
     * �ڵ�ǰʱ��Ļ��ϼӻ��ȥ����
     *
     * @param day
     * @return
     */
    public  static Date day(int day) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(Calendar.DAY_OF_YEAR, day);
        return Cal.getTime();
    }

    /**
     * ��ָ����ʱ���ϼӻ��ȥ����
     *
     * @param date
     * @param day
     * @return
     */
    public  static Date day(Date date, int day) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.DAY_OF_YEAR, day);
        return Cal.getTime();
    }

    /**
     * �ڵ�ǰʱ��Ļ��ϼӻ��ȥ��Сʱ-֧�ָ�����
     *
     * @param hour
     * @return
     */
    public  static Date hour(float hour) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.MINUTE, (int) (hour * 60));
        return Cal.getTime();
    }

    /**
     * ���ƶ���ʱ���ϼӻ��ȥ��Сʱ-֧�ָ�����
     *
     * @param date
     * @param hour
     * @return
     */
    public  static Date hour(Date date, float hour) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.MINUTE, (int) (hour * 60));
        return Cal.getTime();
    }

    /**
     * �ڵ�ǰʱ��Ļ��ϼӻ��ȥ������
     *
     * @param minute
     * @return
     */
    public  static Date minute(int minute) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.MINUTE, minute);
        return Cal.getTime();
    }

    /**
     * ���ƶ���ʱ���ϼӻ��ȥ������
     *
     * @param date
     * @param minute
     * @return
     */
    public  static Date minute(Date date, int minute) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.MINUTE, minute);
        return Cal.getTime();
    }


    /**
     * �ж��ַ��Ƿ�Ϊ�����ַ�
     *
     * @param date �����ַ�
     * @return true or false
     */
    public  static boolean isDate(String date) {
        try {
            DateTimeInstance().parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static long subtract(Date date1, Date date2) {
        long cha = (date2.getTime() - date1.getTime()) / 1000;
        return cha;
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static long subtract(String date1, String date2) {
        long rs = 0;
        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long cha = (end.getTime() - start.getTime()) / 1000;
            rs = cha;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }


    /**
     * ʱ��date1��date2��ʱ��� -��λ����
     *
     * @param date1
     * @param date2
     * @return ����
     */
    public  static int subtractMinute(String date1, String date2) {
        int rs = 0;
        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long cha = (end.getTime() - start.getTime()) / 1000;
            rs = (int) cha / (60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ����
     *
     * @param date1
     * @param date2
     * @return ����
     */
    public  static int subtractMinute(Date date1, Date date2) {
        long cha = date2.getTime() - date1.getTime();
        return (int) cha / (1000 * 60);
    }

    /**
     * ʱ��date1��date2��ʱ���-��λСʱ
     *
     * @param date1
     * @param date2
     * @return Сʱ
     */
    public  static int subtractHour(Date date1, Date date2) {
        long cha = (date2.getTime() - date1.getTime()) / 1000;
        return (int) cha / (60 * 60);
    }

    /**
     * ʱ��date1��date2��ʱ���-��λСʱ
     *
     * @param date1
     * @param date2
     * @return Сʱ
     */
    public  static int subtractHour(String date1, String date2) {
        int rs = 0;
        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long cha = (end.getTime() - start.getTime()) / 1000;
            rs = (int) cha / (60 * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }


    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static int subtractDay(String date1, String date2) {
        int rs = 0;
        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long sss = (end.getTime() - start.getTime()) / 1000;
            rs = (int) sss / (60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static int subtractDay(Date date1, Date date2) {
        long cha = date2.getTime() - date1.getTime();
        return (int) cha / (1000 * 60 * 60 * 24);
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static int subtractMonth(String date1, String date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(DateInstance().parse(date1));
            c2.setTime(DateInstance().parse(date2));
            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH);
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH);
            if (year1 == year2) {
                result = month2 - month1;
            } else {
                result = 12 * (year2 - year1) + month2 - month1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static int subtractMonth(Date date1, Date date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        if (year1 == year2) {
            result = month2 - month1;
        } else {
            result = 12 * (year2 - year1) + month2 - month1;
        }
        return result;
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static int subtractYear(String date1, String date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(DateInstance().parse(date1));
            c2.setTime(DateInstance().parse(date2));
            int year1 = c1.get(Calendar.YEAR);
            int year2 = c2.get(Calendar.YEAR);
            result = year2 - year1;
        } catch (ParseException e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    /**
     * ʱ��date1��date2��ʱ���-��λ��
     *
     * @param date1
     * @param date2
     * @return ��
     */
    public  static int subtractYear(Date date1, Date date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        result = year2 - year1;
        return result;
    }

    /**
     * ��ȡ����ʱ��Ĳ�����ʱ���ʾ
     *
     * @param date1
     * @param date2
     * @return ��Сʱ:������:������
     * @Summary:�˴����Խ��������װ��һ���ṹ�巵�ر��ڸ�ʽ��
     */
    public  static String subtractTime(String date1, String date2) {
        String result = "";
        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long sss = (end.getTime() - start.getTime()) / 1000;
            int hh = (int) sss / (60 * 60);
            int mm = (int) (sss - hh * 60 * 60) / (60);
            int ss = (int) (sss - hh * 60 * 60 - mm * 60);
            result = hh + ":" + mm + ":" + ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ��ȡ����ʱ��Ĳ�����ʱ���ʾ
     *
     * @param date1
     * @param date2
     * @return ����-��Сʱ:������:������
     * @Summary:�˴����Խ��������װ��һ���ṹ�巵�ر��ڸ�ʽ��
     */
    public  static String subtractDate(String date1, String date2) {
        String result = "";
        try {
            Date start = DateTimeInstance().parse(date1);
            Date end = DateTimeInstance().parse(date2);
            long sss = (end.getTime() - start.getTime()) / 1000;
            int dd = (int) sss / (60 * 60 * 24);
            int hh = (int) (sss - dd * 60 * 60 * 24) / (60 * 60);
            int mm = (int) (sss - dd * 60 * 60 * 24 - hh * 60 * 60) / (60);
            int ss = (int) (sss - dd * 60 * 60 * 24 - hh * 60 * 60 - mm * 60);
            result = dd + "-" + hh + ":" + mm + ":" + ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ��ȡ����ʱ��֮ǰ�����������
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public  static int subDay(Date startTime, Date endTime) {
        int days = 0;
        Calendar can1 = Calendar.getInstance();
        can1.setTime(startTime);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(endTime);
        int year1 = can1.get(Calendar.YEAR);
        int year2 = can2.get(Calendar.YEAR);

        Calendar can = null;
        if (can1.before(can2)) {
            days -= can1.get(Calendar.DAY_OF_YEAR);
            days += can2.get(Calendar.DAY_OF_YEAR);
            can = can1;
        } else {
            days -= can2.get(Calendar.DAY_OF_YEAR);
            days += can1.get(Calendar.DAY_OF_YEAR);
            can = can2;
        }
        for (int i = 0; i < Math.abs(year2 - year1); i++) {
            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
            can.add(Calendar.YEAR, 1);
        }

        return days;
    }

    /**
     * ��ȡ����ʱ��֮ǰ�����������
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public  static int subDay(String startTime, String endTime) {
        int days = 0;
        try {
            Date date1 = DateInstance().parse(DateInstance().format(DateTimeInstance().parse(startTime)));
            Date date2 = DateInstance().parse(DateInstance().format(DateTimeInstance().parse(endTime)));
            Calendar can1 = Calendar.getInstance();
            can1.setTime(date1);
            Calendar can2 = Calendar.getInstance();
            can2.setTime(date2);
            int year1 = can1.get(Calendar.YEAR);
            int year2 = can2.get(Calendar.YEAR);

            Calendar can = null;
            if (can1.before(can2)) {
                days -= can1.get(Calendar.DAY_OF_YEAR);
                days += can2.get(Calendar.DAY_OF_YEAR);
                can = can1;
            } else {
                days -= can2.get(Calendar.DAY_OF_YEAR);
                days += can1.get(Calendar.DAY_OF_YEAR);
                can = can2;
            }
            for (int i = 0; i < Math.abs(year2 - year1); i++) {
                days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
                can.add(Calendar.YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * ��������ʱ����ʱ���(����ÿ���08:00-18:00)��ʱ��-��λ��
     *
     * @param startDate
     * @param endDate
     * @param timeBurst ֻ�Ͱ���ʱ����ڵ�08:00-18:00ʱ��
     * @return ����������
     * @throws ParseException
     * @summary ��ʽ���󷵻�0
     */
    public  static long subtimeBurst(String startDate, String endDate, String timeBurst)
            throws ParseException {
        Date start = DateTimeInstance().parse(startDate);
        Date end = DateTimeInstance().parse(endDate);
        return subtimeBurst(start, end, timeBurst);
    }

    /**
     * ��������ʱ����ʱ���(����ÿ���08:00-18:00)��ʱ��-��λ��
     *
     * @param startDate
     * @param endDate
     * @param timeBurst ֻ�Ͱ���ʱ����ڵ�08:00-18:00ʱ��
     * @return ����������
     * @throws ParseException
     */
    public  static long subtimeBurst(Date startDate, Date endDate, String timeBurst)
            throws ParseException {
        long second = 0;
        Pattern p = Pattern.compile("^\\d{2}:\\d{2}-\\d{2}:\\d{2}");
        Matcher m = p.matcher(timeBurst);
        boolean falg = false;
        if (startDate.after(endDate)) {
            Date temp = startDate;
            startDate = endDate;
            endDate = temp;
            falg = true;
        }
        if (m.matches()) {
            String[] a = timeBurst.split("-");
            int day = subDay(startDate, endDate);
            if (day > 0) {
                long firstMintues = 0;
                long lastMintues = 0;
                long daySecond = 0;
                String strDayStart = DateInstance().format(startDate) + " " + a[0] + ":00";
                String strDayEnd = DateInstance().format(startDate) + " " + a[1] + ":00";
                Date dayStart = DateTimeInstance().parse(strDayStart);
                Date dayEnd = DateTimeInstance().parse(strDayEnd);
                daySecond = subtract(dayStart, dayEnd);
                if ((startDate.after(dayStart) || startDate.equals(dayStart))
                        && startDate.before(dayEnd)) {
                    firstMintues = (dayEnd.getTime() - startDate.getTime()) / 1000;
                } else if (startDate.before(dayStart)) {
                    firstMintues = (dayEnd.getTime() - dayStart.getTime()) / 1000;
                }
                dayStart = DateTimeInstance().parse(DateInstance().format(endDate) + " " + a[0] + ":00");
                dayEnd = DateTimeInstance().parse(DateInstance().format(endDate) + " " + a[1] + ":00");
                if (endDate.after(dayStart) && (endDate.before(dayEnd) || endDate.equals(dayEnd))) {
                    lastMintues = (endDate.getTime() - dayStart.getTime()) / 1000;
                } else if (endDate.after(dayEnd)) {
                    lastMintues = (dayEnd.getTime() - dayStart.getTime()) / 1000;
                }
                //��һ������� + ���һ������� + ����*ȫ�������
                second = firstMintues + lastMintues;
                second += (day - 1) * daySecond;
            } else {
                String strDayStart = DateInstance().format(startDate) + " " + a[0] + ":00";
                String strDayEnd = DateInstance().format(startDate) + " " + a[1] + ":00";
                Date dayStart = DateTimeInstance().parse(strDayStart);
                Date dayEnd = DateTimeInstance().parse(strDayEnd);
                if ((startDate.after(dayStart) || startDate.equals(dayStart))
                        && startDate.before(dayEnd) && endDate.after(dayStart)
                        && (endDate.before(dayEnd) || endDate.equals(dayEnd))) {
                    second = (endDate.getTime() - startDate.getTime()) / 1000;
                } else {
                    if (startDate.before(dayStart)) {
                        if (endDate.before(dayEnd)) {
                            second = (endDate.getTime() - dayStart.getTime()) / 1000;
                        } else {
                            second = (dayEnd.getTime() - dayStart.getTime()) / 1000;
                        }
                    }
                    if (startDate.after(dayStart)) {
                        if (endDate.before(dayEnd)) {
                            second = (endDate.getTime() - startDate.getTime()) / 1000;
                        } else {
                            second = (dayEnd.getTime() - startDate.getTime()) / 1000;
                        }
                    }
                }
                if ((startDate.before(dayStart) && endDate.before(dayStart))
                        || startDate.after(dayEnd) && endDate.after(dayEnd)) {
                    second = 0;
                }
            }
        } else {
            second = (endDate.getTime() - startDate.getTime()) / 1000;
        }
        if (falg) {
            second = Long.parseLong("-" + second);
        }
        return second;
    }

    /**
     * ʱ��Date��ʱ���(����ÿ���08:00-18:00)�����ӻ��ȥsecond��
     *
     * @param date
     * @param second
     * @param timeBurst
     * @return ������ʱ��
     * @Suumary ָ���ĸ�ʽ����󷵻�ԭ���
     */
    public  static Date calculate(String date, int second, String timeBurst) {
        Date start = null;
        try {
            start = DateTimeInstance().parse(date);
            return calculate(start, second, timeBurst);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * ʱ��Date��ʱ���(����ÿ���08:00-18:00)�����ӻ��ȥsecond��
     *
     * @param date
     * @param second
     * @param timeBurst
     * @return ������ʱ��
     * @Suumary ָ���ĸ�ʽ����󷵻�ԭ���
     */
    public  static Date calculate(Date date, int second, String timeBurst) {
        Pattern p = Pattern.compile("^\\d{2}:\\d{2}-\\d{2}:\\d{2}");
        Matcher m = p.matcher(timeBurst);
        java.util.Calendar cal = java.util.Calendar.getInstance();
        if (m.matches()) {
            String[] a = timeBurst.split("-");
            try {
                Date dayStart = DateTimeInstance().parse(DateInstance().format(date) + " " + a[0] + ":00");
                Date dayEnd = DateTimeInstance().parse(DateInstance().format(date) + " " + a[1] + ":00");
                int DaySecond = (int) subtract(dayStart, dayEnd);
                int toDaySecond = (int) subtract(dayStart, dayEnd);
                if (second >= 0) {
                    if ((date.after(dayStart) || date.equals(dayStart))
                            && (date.before(dayEnd) || date.equals(dayEnd))) {
                        cal.setTime(date);
                        toDaySecond = (int) subtract(date, dayEnd);
                    }
                    if (date.before(dayStart)) {
                        cal.setTime(dayStart);
                        toDaySecond = (int) subtract(dayStart, dayEnd);
                    }
                    if (date.after(dayEnd)) {
                        cal.setTime(day(dayStart, 1));
                        toDaySecond = 0;
                    }

                    if (second > toDaySecond) {
                        int day = (second - toDaySecond) / DaySecond;
                        int remainder = (second - toDaySecond) % DaySecond;
                        cal.setTime(day(dayStart, 1));
                        cal.add(Calendar.DAY_OF_YEAR, day);
                        cal.add(Calendar.SECOND, remainder);
                    } else {
                        cal.add(Calendar.SECOND, second);
                    }

                } else {
                    if ((date.after(dayStart) || date.equals(dayStart))
                            && (date.before(dayEnd) || date.equals(dayEnd))) {
                        cal.setTime(date);
                        toDaySecond = (int) subtract(date, dayStart);
                    }
                    if (date.before(dayStart)) {
                        cal.setTime(day(dayEnd, -1));
                        toDaySecond = 0;
                    }
                    if (date.after(dayEnd)) {
                        cal.setTime(dayEnd);
                        toDaySecond = (int) subtract(dayStart, dayEnd);
                    }
                    if (Math.abs(second) > Math.abs(toDaySecond)) {
                        int day = (Math.abs(second) - Math.abs(toDaySecond)) / DaySecond;
                        int remainder = (Math.abs(second) - Math.abs(toDaySecond)) % DaySecond;
                        cal.setTime(day(dayEnd, -1));
                        cal.add(Calendar.DAY_OF_YEAR, Integer.valueOf("-" + day));
                        cal.add(Calendar.SECOND, Integer.valueOf("-" + remainder));
                    } else {
                        cal.add(Calendar.SECOND, second);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.setTime(date);
        }
        return cal.getTime();
    }

    /**
     * �ж��Ƿ���ĳ��ʱ�����
     * @param startTime
     * @param endTime
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean between(String startTime,String endTime,Date date)
            throws ParseException {
        return between(dateTime(startTime),dateTime(endTime),date);
    }

    /**
     * �ж���ĳ��ʱ����
     * @param startTime
     * @param endTime
     * @param date
     * @return
     */
    public static boolean between(Date startTime,Date endTime,Date date){
        return date.after(startTime) && date.before(endTime);
    }
}
