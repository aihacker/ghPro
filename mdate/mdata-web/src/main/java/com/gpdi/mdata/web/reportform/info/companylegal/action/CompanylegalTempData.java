package com.gpdi.mdata.web.reportform.info.companylegal.action;

import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/15 10:08
 * @modifier:
 */
public class CompanylegalTempData {

    private String id;
    private String company_name;
    private String company_historical_name;
    private String legal_representative;
    private String tax_code;
    private String credit_code;
    private String shareholder_one;
    private String shareholder_two;
    private String shareholder_three;
    private String shareholder_four;
    private String senior_admin_one;
    private String senior_admin_two;
    private String senior_admin_three;
    private String controller;
    private String is_country;
    private String input_time;
    private LinkedCaseInsensitiveMap map;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_historical_name() {
        return company_historical_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getLegal_representative() {
        return legal_representative;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public String getTax_code() {
        return tax_code;
    }

    public String getShareholder_one() {
        return shareholder_one;
    }

    public String getController() {
        return controller;
    }

    public String getShareholder_three() {
        return shareholder_three;
    }

    public String getShareholder_four() {
        return shareholder_four;
    }

    public String getShareholder_two() {
        return shareholder_two;
    }

    public String getInput_time() {
        return input_time;
    }

    public String getIs_country() {
        return is_country;
    }

    public String getSenior_admin_one() {
        return senior_admin_one;
    }

    public String getSenior_admin_three() {
        return senior_admin_three;
    }

    public String getSenior_admin_two() {
        return senior_admin_two;
    }

    public void setCompany_historical_name(String company_historical_name) {
        this.company_historical_name = company_historical_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code;
    }

    public void setLegal_representative(String legal_representative) {
        this.legal_representative = legal_representative;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public void setSenior_admin_one(String senior_admin_one) {
        this.senior_admin_one = senior_admin_one;
    }

    public void setShareholder_one(String shareholder_one) {
        this.shareholder_one = shareholder_one;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public void setShareholder_four(String shareholder_four) {
        this.shareholder_four = shareholder_four;
    }

    public void setShareholder_three(String shareholder_three) {
        this.shareholder_three = shareholder_three;
    }

    public void setShareholder_two(String shareholder_two) {
        this.shareholder_two = shareholder_two;
    }

    public void setInput_time(String input_time) {
        this.input_time = input_time;
    }

    public void setIs_country(String is_country) {
        this.is_country = is_country;
    }

    public void setSenior_admin_three(String senior_admin_three) {
        this.senior_admin_three = senior_admin_three;
    }

    public void setSenior_admin_two(String senior_admin_two) {
        this.senior_admin_two = senior_admin_two;
    }

    public LinkedCaseInsensitiveMap getMap() {
        return map;
    }

    public void setMap(LinkedCaseInsensitiveMap map) {
        this.map = map;
    }
}
