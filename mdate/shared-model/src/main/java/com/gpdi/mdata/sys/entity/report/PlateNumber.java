package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:车牌号和卡号对应关系表
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 17:24
 * @modifier:
 */
@Entity
@Table(name = "t_card_relation")
public class PlateNumber implements Serializable{
    private static final long serialVersionUID = 3074946119791042142L;

    private Integer id;
    private String  licensePlateNumber;
    private String  cardNumber;
    @Id
    @Column(name = "id")//映射数据表中的对应列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的自增主键
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "license_plate_number")
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
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
        return "PlateNumber{" +
                "id=" + id +
                ", licensePlateNumber='" + licensePlateNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
