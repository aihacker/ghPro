package com.libs.common.excel;


import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-01  14:43
 * --------------------------------------------------------- *
 */
public interface ExcelReadSheet extends ExcelReadRow{

    void sheet(List<List> list, Integer sheet);

}
