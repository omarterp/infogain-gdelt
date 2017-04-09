package io.infogains.gdelt.file;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by omart_000 on 3/3/2017.
 *
 * PoJo - GDELT Version One
 */
public class VersionOne {

    private String geid;
    private String eDt;
    private Integer eDayYr;
    private Integer eDayWk;
    private Integer eDayMth;
    private Integer eMth;
    private Integer eYr;
    private Integer eMthYr;
    private Double fractionDate;
    private String a1Code;
    private String a1Name;
    private String a1Country;
    private String a1Group;
    private String a1Ethnicity;
    private String a1Religion1;
    private String a1Religion2;
    private String a1Type1;
    private String a1Type2;
    private String a1Type3;
    private String a2Code;
    private String a2Name;
    private String a2Country;
    private String a2Group;
    private String a2Ethnicity;
    private String a2Religion1;
    private String a2Religion2;
    private String a2Type1;
    private String a2Type2;
    private String a2Type3;
    private Integer isRootEvent;
    private String eventCode;
    private String eventBaseCode;
    private String eventRootCode;
    private Integer quadClass;
    private Double goldsteinScale;
    private Double numMentions;
    private Double numSources;
    private Double numArticles;
    private Double avgTone;
    private Integer a1GeoType;
    private String a1GeoName;
    private String a1GeoCountry;
    private String a1GeoAdm;
    private Double a1Lat;
    private Double a1Long;
    private String a1GeoFeature;
    private Integer a2GeoType;
    private String a2GeoName;
    private String a2GeoCountry;
    private String a2GeoAdm;
    private Double a2Lat;
    private Double a2Long;
    private String a2GeoFeature;
    private Integer actionGeoType;
    private String actionGeoName;
    private String actionCountry;
    private String actionGeoAdm;
    private Double actionLat;
    private Double actionLong;
    private String actionGeoFeature;
    private Date dateAdded;
    private String sourceUrl;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Geo-Point Fields - Set only if pushed to ES
    private List<Double> a1Location;
    private List<Double> a2Location;
    private List<Double> actionLocation;

    public List<Double> getA1Location() {
        return a1Location;
    }

    public void setA1Location(Double a1Lat, Double a1Long) {
        if(a1Lat != null && a1Long != null) {
            a1Location = new ArrayList<>();
            a1Location.add(a1Long);
            a1Location.add(a1Lat);
        }
        this.a1Location = a1Location;
    }

    public List<Double> getA2Location() {
        return a2Location;
    }

    public void setA2Location(Double a2Lat, Double a2Long) {
        if(a2Lat != null && a2Long != null) {
            a2Location = new ArrayList<>();
            a2Location.add(a2Long);
            a2Location.add(a2Lat);
        }
        this.a2Location = a2Location;
    }

    public List<Double> getActionLocation() {
        return actionLocation;
    }

    public void setActionLocation(Double actionLat, Double actionLong) {
        if(actionLat != null && actionLong != null) {
            actionLocation = new ArrayList<>();
            actionLocation.add(actionLong);
            actionLocation.add(actionLat);
        }
        this.actionLocation = actionLocation;
    }

    private static final String DATE_FORMAT = "";

    public String getGeid() {
        return geid;
    }

    public void setGeid(String geid) {
        this.geid = geid;
    }

    public String getEDt() {
        return eDt;
    }

    public String geteDt() {
        return eDt;
    }

    public void seteDt(String eDt) {
        this.eDt = eDt;
    }

    public Integer geteDayYr() {
        return eDayYr;
    }

    public void seteDayYr(Integer eDayYr) {
        this.eDayYr = eDayYr;
    }

    public Integer geteDayWk() {
        return eDayWk;
    }

    public void seteDayWk(Integer eDayWk) {
        this.eDayWk = eDayWk;
    }

    public Integer geteDayMth() {
        return eDayMth;
    }

    public void seteDayMth(Integer eDayMth) {
        this.eDayMth = eDayMth;
    }

    public Integer geteMth() {
        return eMth;
    }

    public void seteMth(Integer eMth) {
        this.eMth = eMth;
    }

    public Integer geteYr() {
        return eYr;
    }

    public void seteYr(Integer eYr) {
        this.eYr = eYr;
    }

    public Integer geteMthYr() {
        return eMthYr;
    }

    public void seteMthYr(Integer eMthYr) {
        this.eMthYr = eMthYr;
    }

    public static String getDateFormat() {
        return DATE_FORMAT;
    }

    // Set event date and related date attributes - eDayYr, eDayWk, eDayMth, eYr
    public void setEDt(Date eDate) {
        LocalDate localEventDate = eDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.eDt = localEventDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.eDayYr = localEventDate.getDayOfYear();
        this.eDayMth = localEventDate.getDayOfMonth();
        this.eDayWk = localEventDate.getDayOfWeek().getValue();
        this.eYr = localEventDate.getYear();
        this.eMth = localEventDate.getMonth().getValue();
    }

    public Integer getEMthYr() {
        return eMthYr;
    }

    public void setEMthYr(Integer monthYear) {
        this.eMthYr = monthYear;
    }

//    public Integer getYear() {
//        return year;
//    }
//
//    public void setYear(Integer year) {
//        this.year = year;
//    }
//
    public Double getFractionDate() {
        return fractionDate;
    }

    public void setFractionDate(Double fractionDate) {
        this.fractionDate = fractionDate;
    }

    public String getA1Code() {
        return a1Code;
    }

    public void setA1Code(String a1Code) {
        this.a1Code = a1Code;
    }

    public String getA1Name() {
        return a1Name;
    }

    public void setA1Name(String a1Name) {
        this.a1Name = a1Name;
    }

    public String getA1Country() {
        return a1Country;
    }

    public void setA1Country(String a1Country) {
        this.a1Country = a1Country;
    }

    public String getA1Group() {
        return a1Group;
    }

    public void setA1Group(String a1Group) {
        this.a1Group = a1Group;
    }

    public String getA1Ethnicity() {
        return a1Ethnicity;
    }

    public void setA1Ethnicity(String a1Ethnicity) {
        this.a1Ethnicity = a1Ethnicity;
    }

    public String getA1Religion1() {
        return a1Religion1;
    }

    public void setA1Religion1(String a1Religion1) {
        this.a1Religion1 = a1Religion1;
    }

    public String getA1Religion2() {
        return a1Religion2;
    }

    public void setA1Religion2(String a1Religion2) {
        this.a1Religion2 = a1Religion2;
    }

    public String getA1Type1() {
        return a1Type1;
    }

    public void setA1Type1(String a1Type1) {
        this.a1Type1 = a1Type1;
    }

    public String getA1Type2() {
        return a1Type2;
    }

    public void setA1Type2(String a1Type2) {
        this.a1Type2 = a1Type2;
    }

    public String getA1Type3() {
        return a1Type3;
    }

    public void setA1Type3(String a1Type3) {
        this.a1Type3 = a1Type3;
    }

    public String getA2Code() {
        return a2Code;
    }

    public void setA2Code(String a2Code) {
        this.a2Code = a2Code;
    }

    public String getA2Name() {
        return a2Name;
    }

    public void setA2Name(String a2Name) {
        this.a2Name = a2Name;
    }

    public String getA2Country() {
        return a2Country;
    }

    public void setA2Country(String a2Country) {
        this.a2Country = a2Country;
    }

    public String getA2Group() {
        return a2Group;
    }

    public void setA2Group(String a2Group) {
        this.a2Group = a2Group;
    }

    public String getA2Ethnicity() {
        return a2Ethnicity;
    }

    public void setA2Ethnicity(String a2Ethnicity) {
        this.a2Ethnicity = a2Ethnicity;
    }

    public String getA2Religion1() {
        return a2Religion1;
    }

    public void setA2Religion1(String a2Religion1) {
        this.a2Religion1 = a2Religion1;
    }

    public String getA2Religion2() {
        return a2Religion2;
    }

    public void setA2Religion2(String a2Religion2) {
        this.a2Religion2 = a2Religion2;
    }

    public String getA2Type1() {
        return a2Type1;
    }

    public void setA2Type1(String a2Type1) {
        this.a2Type1 = a2Type1;
    }

    public String getA2Type2() {
        return a2Type2;
    }

    public void setA2Type2(String a2Type2) {
        this.a2Type2 = a2Type2;
    }

    public String getA2Type3() {
        return a2Type3;
    }

    public void setA2Type3(String a2Type3) {
        this.a2Type3 = a2Type3;
    }

    public Integer getIsRootEvent() {
        return isRootEvent;
    }

    public void setIsRootEvent(Integer isRootEvent) {
        this.isRootEvent = isRootEvent;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventBaseCode() {
        return eventBaseCode;
    }

    public void setEventBaseCode(String eventBaseCode) {
        this.eventBaseCode = eventBaseCode;
    }

    public String getEventRootCode() {
        return eventRootCode;
    }

    public void setEventRootCode(String eventRootCode) {
        this.eventRootCode = eventRootCode;
    }

    public Integer getQuadClass() {
        return quadClass;
    }

    public void setQuadClass(Integer quadClass) {
        this.quadClass = quadClass;
    }

    public Double getGoldsteinScale() {
        return goldsteinScale;
    }

    public void setGoldsteinScale(Double goldsteinScale) {
        this.goldsteinScale = goldsteinScale;
    }

    public Double getNumMentions() {
        return numMentions;
    }

    public void setNumMentions(Double numMentions) {
        this.numMentions = numMentions;
    }

    public Double getNumSources() {
        return numSources;
    }

    public void setNumSources(Double numSources) {
        this.numSources = numSources;
    }

    public Double getNumArticles() {
        return numArticles;
    }

    public void setNumArticles(Double numArticles) {
        this.numArticles = numArticles;
    }

    public Double getAvgTone() {
        return avgTone;
    }

    public void setAvgTone(Double avgTone) {
        this.avgTone = avgTone;
    }

    public Integer getA1GeoType() {
        return a1GeoType;
    }

    public void setA1GeoType(Integer a1GeoType) {
        this.a1GeoType = a1GeoType;
    }

    public String getA1GeoName() {
        return a1GeoName;
    }

    public void setA1GeoName(String a1GeoName) {
        this.a1GeoName = a1GeoName;
    }

    public String getA1GeoCountry() {
        return a1GeoCountry;
    }

    public void setA1GeoCountry(String a1GeoCountry) {
        this.a1GeoCountry = a1GeoCountry;
    }

    public String getA1GeoAdm() {
        return a1GeoAdm;
    }

    public void setA1GeoAdm(String a1GeoAdm) {
        this.a1GeoAdm = a1GeoAdm;
    }

    public Double getA1Lat() {
        return a1Lat;
    }

    public void setA1Lat(Double a1Lat) {
        this.a1Lat = a1Lat;
    }

    public Double getA1Long() {
        return a1Long;
    }

    public void setA1Long(Double a1Long) {
        this.a1Long = a1Long;
    }

    public String getA1GeoFeature() {
        return a1GeoFeature;
    }

    public void setA1GeoFeature(String a1GeoFeature) {
        this.a1GeoFeature = a1GeoFeature;
    }

    public Integer getA2GeoType() {
        return a2GeoType;
    }

    public void setA2GeoType(Integer a2GeoType) {
        this.a2GeoType = a2GeoType;
    }

    public String getA2GeoName() {
        return a2GeoName;
    }

    public void setA2GeoName(String a2GeoName) {
        this.a2GeoName = a2GeoName;
    }

    public String getA2GeoCountry() {
        return a2GeoCountry;
    }

    public void setA2GeoCountry(String a2GeoCountry) {
        this.a2GeoCountry = a2GeoCountry;
    }

    public String getA2GeoAdm() {
        return a2GeoAdm;
    }

    public void setA2GeoAdm(String a2GeoAdm) {
        this.a2GeoAdm = a2GeoAdm;
    }

    public Double getA2Lat() {
        return a2Lat;
    }

    public void setA2Lat(Double a2Lat) {
        this.a2Lat = a2Lat;
    }

    public Double getA2Long() {
        return a2Long;
    }

    public void setA2Long(Double a2Long) {
        this.a2Long = a2Long;
    }

    public String getA2GeoFeature() {
        return a2GeoFeature;
    }

    public void setA2GeoFeature(String a2GeoFeature) {
        this.a2GeoFeature = a2GeoFeature;
    }

    public Integer getActionGeoType() {
        return actionGeoType;
    }

    public void setActionGeoType(Integer actionGeoType) {
        this.actionGeoType = actionGeoType;
    }

    public String getActionGeoName() {
        return actionGeoName;
    }

    public void setActionGeoName(String actionGeoName) {
        this.actionGeoName = actionGeoName;
    }

    public String getActionCountry() {
        return actionCountry;
    }

    public void setActionCountry(String actionCountry) {
        this.actionCountry = actionCountry;
    }

    public String getActionGeoAdm() {
        return actionGeoAdm;
    }

    public void setActionGeoAdm(String actionGeoAdm) {
        this.actionGeoAdm = actionGeoAdm;
    }

    public Double getActionLat() {
        return actionLat;
    }

    public void setActionLat(Double actionLat) {
        this.actionLat = actionLat;
    }

    public Double getActionLong() {
        return actionLong;
    }

    public void setActionLong(Double actionLong) {
        this.actionLong = actionLong;
    }

    public String getActionGeoFeature() {
        return actionGeoFeature;
    }

    public void setActionGeoFeature(String actionGeoFeature) {
        this.actionGeoFeature = actionGeoFeature;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
