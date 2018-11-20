package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.io.StreamTokenizer;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 15:23
 * @description:佛山本部粤通卡号
 */
@Entity
@Table(name = "t_card_location")
public class Ascription implements Serializable {

    private static final long serialVersionUID = 4598407848894466330L;

    private Integer id;

    private String company; //所属公司

    private String  plateNumber; //汽车牌照

    private String  cardNumber;//油卡卡号

    @Id
    @Column(name = "id")//映射数据表中的对应列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的自增主键
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    @Column(name = "plate_number")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    @Column(name = "card_number")
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Ascription{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
