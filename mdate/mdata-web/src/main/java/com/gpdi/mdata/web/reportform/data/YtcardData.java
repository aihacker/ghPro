package com.gpdi.mdata.web.reportform.data;

/**
 * @description:粤通卡传参数
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 16:34
 * @modifier:
 */
public class YtcardData {
    private String code;
    private String name;
    private Integer number;
    private int pageMonth;
    private String pageYear;
    private int pageCarNumber;
    private String workinDayType;
    private String week;
    private String holidayTypes;
    private String workinDayTypecq;
    private String weekcq;
    private String holidayTypescq;
    private String workinDayTypeyk;
    private String weekyk;
    private String holidayTypesyk;
    private String hour;
    private Integer isExport;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getPageMonth() {
        return pageMonth;
    }

    public void setPageMonth(int pageMonth) {
        this.pageMonth = pageMonth;
    }

    public String getPageYear() {
        return pageYear;
    }

    public void setPageYear(String pageYear) {
        this.pageYear = pageYear;
    }

    public int getPageCarNumber() {
        return pageCarNumber;
    }

    public void setPageCarNumber(int pageCarNumber) {
        this.pageCarNumber = pageCarNumber;
    }

    public String getWorkinDayType() {
        return workinDayType;
    }

    public void setWorkinDayType(String workinDayType) {
        this.workinDayType = workinDayType;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getHolidayTypes() {
        return holidayTypes;
    }

    public void setHolidayTypes(String holidayTypes) {
        this.holidayTypes = holidayTypes;
    }

    public String getWorkinDayTypecq() {
        return workinDayTypecq;
    }

    public void setWorkinDayTypecq(String workinDayTypecq) {
        this.workinDayTypecq = workinDayTypecq;
    }

    public String getWeekcq() {
        return weekcq;
    }

    public void setWeekcq(String weekcq) {
        this.weekcq = weekcq;
    }

    public String getHolidayTypescq() {
        return holidayTypescq;
    }

    public void setHolidayTypescq(String holidayTypescq) {
        this.holidayTypescq = holidayTypescq;
    }

    public String getWorkinDayTypeyk() {
        return workinDayTypeyk;
    }

    public void setWorkinDayTypeyk(String workinDayTypeyk) {
        this.workinDayTypeyk = workinDayTypeyk;
    }

    public String getWeekyk() {
        return weekyk;
    }

    public void setWeekyk(String weekyk) {
        this.weekyk = weekyk;
    }

    public String getHolidayTypesyk() {
        return holidayTypesyk;
    }

    public void setHolidayTypesyk(String holidayTypesyk) {
        this.holidayTypesyk = holidayTypesyk;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getIsExport() {
        return isExport;
    }

    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    @Override
    public String toString() {
        return "YtcardData{" + "code='" + code + '\'' + ", name='" + name + '\'' + ", number=" + number + ", pageMonth=" + pageMonth + ", pageYear='" + pageYear + '\'' + ", pageCarNumber=" + pageCarNumber + ", workinDayType='" + workinDayType + '\'' + ", week='" + week + '\'' + ", holidayTypes='" + holidayTypes + '\'' + ", workinDayTypecq='" + workinDayTypecq + '\'' + ", weekcq='" + weekcq + '\'' + ", holidayTypescq='" + holidayTypescq + '\'' + ", workinDayTypeyk='" + workinDayTypeyk + '\'' + ", weekyk='" + weekyk + '\'' + ", holidayTypesyk='" + holidayTypesyk + '\'' + ", hour='" + hour + '\''+", isExport='"+isExport+'\''+ '}';
    }
}
