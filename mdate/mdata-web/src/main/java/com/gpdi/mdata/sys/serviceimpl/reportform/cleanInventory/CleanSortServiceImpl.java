package com.gpdi.mdata.sys.serviceimpl.reportform.cleanInventory;

import com.gpdi.mdata.sys.dao.report.CleanRelDao;
import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.sys.entity.report.CleanResult;
import com.gpdi.mdata.sys.entity.report.CleanTemps;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanSortService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.utils.ReadTextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.TypeUtils;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;

import static com.gpdi.mdata.web.utils.FileUtils.createDir;
import static com.gpdi.mdata.web.utils.FileUtils.createFile;
import static pub.functions.StrFuncs.trim;
import static sun.misc.PostVMInitHook.run;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/9/11 20:13
 * @modifier:
 */
@Service
@Transactional(readOnly = false)
public class CleanSortServiceImpl implements CleanSortService {
    @Autowired
    private GeneralDao generalDao;

    public String getCompanyName(String code){
        String sql = "select supplier_name from t_clean_list_immediately_rel where contract_code = ?";
        return generalDao.queryValue(String.class, sql, code);
    }

    public HashMap<String, Integer> doSortComb(QueryData queryData, QuerySettings settings) {
        HashMap<String, Integer> compMap = null;
        List li = new ArrayList();
        List<Integer> listCompanyId = new ArrayList<>();
       String compam = queryData.getName();//公司名称
        String depart = queryData.getHid();//暂代部门名称
        String compam2 = null;
        String sql = "select id from t_company where company_name=?";
        if(queryData.getName() != null && queryData.getName() != ""){
            listCompanyId = generalDao.queryList(Integer.class, sql, queryData.getName());
        } else if(queryData.getCode() != null && queryData.getCode() != ""){
            compam2 = getCompanyName(queryData.getCode());
            listCompanyId = generalDao.queryList(Integer.class, sql,compam2);
        }
        if (compam2 !=null && (compam==null || compam=="")){
            compam=compam2;
        }
//=============================查询该公司参与的所有合同的比对情况===========================
        String sql2 = "select replace( GROUP_CONCAT(distinct bb.id), ',', ' ') as cid \n" +
                "  from t_clean_candidater a, t_company bb \n" +
                " where a.contract_code in(select t.contract_code \n" +
                "from t_clean_candidater t, t_company b \n" +
                "where b.id = ? and b.company_name=t.company_name) \n" +
                "and a.company_name=bb.company_name and bb.id <> ?\n" +
                "group by a.contract_code";
 //=============================查询该公司参与的所有合同的比对情况,部门不为空的情况===========================
        String sql4 = "select replace( GROUP_CONCAT(distinct bb.id), ',', ' ') as cid \n" +
                "  from t_clean_candidater a, t_company bb \n" +
                " where a.contract_code in(select t.contract_code \n" +
                "from t_clean_candidater t,t_clean_list_immediately_rel r,t_company b \n" +
                "where b.id = ? and r.undertake_dept='"+depart+"' and r.contract_code=t.contract_code and b.company_name=t.company_name) \n" +
                "and a.company_name=bb.company_name and bb.id <> ?\n" +
                "group by a.contract_code";

//=============================查询该公司中标的所有合同的比对情况===========================
       /* String sql2 = "select replace( GROUP_CONCAT(distinct bb.id), ',', ' ') as cid \n" +
                "  from t_clean_candidater a, t_company bb \n" +
                " where a.contract_code in(select t.contract_code \n" +
                "from t_clean_list_immediately_rel t, t_company b \n" +
                "where b.id = ? and b.company_name=t.supplier_name) \n" +
                "and a.company_name=bb.company_name and bb.id <> ?\n" +
                "group by a.contract_code";*/

       String where = null;
       String where2 =null;
       if (compam !=null && compam !=""){
           where = "supplier_name='"+compam+"'";
           where2 = "d.company_name = '"+compam+"'";
       }
       if (depart !=null && depart !="" && compam !=null && compam !=""){
           where += "and undertake_dept ='"+depart+"'";
           where2 += "and d.contract_code  in (\n" +
                   "         select r.contract_code from t_clean_list_immediately_rel r, (SELECT b.contract_code,a.company_name FROM t_clean_candidater a RIGHT OUTER JOIN t_clean_list_immediately_rel b ON a.contract_code = b.contract_code) t\n" +
                   "          where r.contract_code = t.contract_code\n" +
                   "            and r.undertake_dept='"+depart+"'\n" +
                   "  )";
           sql2 = sql4;
       }
        Integer numbe=null;
        Integer contrcatCount = null;
       if (where !=null) {
           String sql3 = "SELECT COUNT(contract_name) num FROM t_clean_list_immediately_rel WHERE "+where;
            numbe = generalDao.queryValue(Integer.class, sql3);
           /*String sql5 = "{call p_clean()}";
           generalDao.execute(sql5);*/
           String sql6 = "select COUNT(*) from  (SELECT b.contract_code,a.company_name FROM t_clean_candidater a RIGHT OUTER JOIN t_clean_list_immediately_rel b ON a.contract_code = b.contract_code) d where " +where2;
           contrcatCount = generalDao.queryValue(Integer.class,sql6);
       }

        File f2 = new File("C:\\datas\\comp.txt");
        if (f2.exists()) {
            System.out.println("=============输出文件路径:"+f2);
            f2.delete();
        }

        //在填写文件路径时，一定要写上具体的文件名称（xx.txt），否则会出现拒绝访问。
        File f = new File("C:\\datas\\comp.txt");
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try{
                f.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        /*if (!f.exists()) {
            //赋予该文件夹写入的权限。因为该Path是Tomcat目录的webapp下。创建者不一定赋予了写入权限
            f.setWritable(true);
            //mkdir 和 mkdirs 区别。后者可以递归创建多级目录
            f.mkdirs();
        }*/
        Writer fw = null;
        try {
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if (fw != null) {
            try {
                for (int i : listCompanyId) {
                    List<String> cids = generalDao.queryList(String.class, sql2, i, i);
                    try {
                        for (String s : cids) {
                            fw.write(s + "\n");
                           // contrcatCount++;
                        }
                        fw.flush();
                        fw.close();
                        compMap = callExe("C:\\datas\\comp.txt");
                        li.add(compMap);
                        li.add(contrcatCount);
                        compMap.put("count",contrcatCount);
                        compMap.put("cid",i);
                        compMap.put("numbe",numbe);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            } finally {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //return li;
        return compMap;
    }

    @Override
    public List<CleanTemps> getAllByDept(String dept) {
        /*String sql="select CONCAT(contract_name,'##',contract_code) from t_clean_list_immediately_rel where undertake_dept = ?";
        List<String> result = generalDao.queryList(String.class, sql, dept);
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(String str: result){
            hashMap.put(str, 0);
        }*/
       /* Query query =new PagedQuery<>();
        query.select("supplier_name,\n" +
                "\tCOUNT(supplier_name) AS nam,\n" +
                "\tCOUNT(supplier_name) / b.sumer AS Percent,\n" +
                "\tb.sumer as sum")
                .from("t_clean_list_immediately_rel,\n" +
                        "(\n" +
                        "\t\tSELECT\n" +
                        "\t\t\tSUM(sss) sumer\n" +
                        "\t\tFROM\n" +
                        "\t\t\t(\n" +
                        "\t\t\t\tSELECT\n" +
                        "\t\t\t\t\tCOUNT(supplier_name) AS sss\n" +
                        "\t\t\t\tFROM\n" +
                        "\t\t\t\t\tt_clean_list_immediately_rel\n" +
                        "\t\t\t\tWHERE\n" +
                        "\t\t\t\t\tundertake_dept = '"+dept+"'\n" +
                        "\t\t\t\tGROUP BY\n" +
                        "\t\t\t\t\tsupplier_name\n" +
                        "\t\t\t\tORDER BY\n" +
                        "\t\t\t\t\tsss DESC\n" +
                        "\t\t\t) a\n" +
                        "\t) AS b")
                .where("undertake_dept = '"+dept+"'")
                .groupBy("supplier_name")
                .orderBy("nam DESC");*/
       // generalDao.execute(query);
        String sql = "SELECT \n" +
        "supplier_name,\n" +
        "\tCOUNT(supplier_name) AS nam,\n" +
        "\tCOUNT(supplier_name) / b.sumer AS percent,\n" +
        "\tb.sumer as sum\n" +
        "FROM\n" +
        "t_clean_list_immediately_rel,\n" +
        "(\n" +
        "\t\tSELECT\n" +
        "\t\t\tSUM(sss) sumer\n" +
        "\t\tFROM\n" +
        "\t\t\t(\n" +
        "\t\t\t\tSELECT\n" +
        "\t\t\t\t\tCOUNT(supplier_name) AS sss\n" +
        "\t\t\t\tFROM\n" +
        "\t\t\t\t\tt_clean_list_immediately_rel\n" +
        "\t\t\t\tWHERE\n" +
        "\t\t\t\t\tundertake_dept = '"+dept+"'\n" +
        "\t\t\t\tGROUP BY\n" +
        "\t\t\t\t\tsupplier_name\n" +
        "\t\t\t\tORDER BY\n" +
        "\t\t\t\t\tsss DESC\n" +
        "\t\t\t) a\n" +
        "\t) AS b\n" +
        "WHERE\n" +
        "undertake_dept = '"+dept+"'\n" +
        "GROUP BY\n" +
        "supplier_name\n" +
        "ORDER BY\n" +
        "nam DESC";
        List<CleanTemps> resa= generalDao.queryList(CleanTemps.class,sql);
        return resa;
    }

    @Override
    public QueryResult queryContract(QueryData queryData, PageSettings settings) {
//        String sql = "SELECT * FROM t_clean_list_immediately_rel r where r.contract_code in (SELECT c.contract_code FROM t_clean_candidater c";
        String[] strs = queryData.getSupperName().split(";");
        String name = queryData.getName();
        Query query =new PagedQuery<>();
        String where = "r.contract_code in (SELECT c.contract_code FROM t_clean_candidater c ";
        if(strs.length == 1){
            where += "JOIN t_clean_candidater c1 ON c.contract_code = c1.contract_code\n" +
                    "WHERE c.company_name = '"+name+"' AND c1.company_name = '"+strs[0]+"')";

        }else if (strs.length == 2){
            where += "JOIN t_clean_candidater c1 ON c.contract_code = c1.contract_code\n" +
                    "JOIN t_clean_candidater c2 ON c.contract_code = c2.contract_code\n" +
                    "WHERE c.company_name = '"+name+"' AND c1.company_name = '"+strs[0]+"' AND c2.company_name = '"+strs[1]+"')";
        }else if (strs.length == 3){
            where += "JOIN t_clean_candidater c1 ON c.contract_code = c1.contract_code\n" +
                    "JOIN t_clean_candidater c2 ON c.contract_code = c2.contract_code\n" +
                    "JOIN t_clean_candidater c3 ON c.contract_code = c3.contract_code\n" +
                    "WHERE c.company_name = '"+name+"' AND c1.company_name = '"+strs[0]+"' AND c2.company_name = '"+strs[1]+"' AND c3.company_name = '"+strs[2]+"')";
        }else{
            return null;
        }
        query.select("*").from("t_clean_list_immediately_rel r")
                .where(where);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();

       /* if(strs.length == 1){
            sql += "JOIN t_clean_candidater c1 ON c.contract_code = c1.contract_code\n" +
                    "WHERE c.company_name = ? AND c1.company_name = ?)";
            return generalDao.queryList(CleanInventoryRel.class, sql, queryData.getName(), strs[0]);
        } else if(strs.length == 2){
            sql += "JOIN t_clean_candidater c1 ON c.contract_code = c1.contract_code\n" +
                    "JOIN t_clean_candidater c2 ON c.contract_code = c2.contract_code\n" +
                    "WHERE c.company_name = ? AND c1.company_name = ? AND c2.company_name = ?)";
            return generalDao.queryList(CleanInventoryRel.class, sql, queryData.getName(), strs[0], strs[1]);
        } else {
            return null;
        }*/
       return queryResult;
    }

    @Override
    public QueryResult queryContractByDept(QueryData queryData, PageSettings settings) {
       String dept = queryData.getDept();
       String name = queryData.getName();
       Query query = new PagedQuery<>(settings);
       query.select("*").from("t_clean_list_immediately_rel").where("supplier_name ='"+name+"' AND undertake_dept='"+dept+"'");
       if (dept !=null && dept !="" && name !=null && name !=""){
           generalDao.execute(query);
           QueryResult queryResult = query.getResult();
           return queryResult;
       }
        return null;
    }

    private HashMap<String, Integer> callExe(String dataFile) {
        HashMap<String, Integer> names = null;
        Process p = null;
        String[] cmd = {"C:\\datas\\SortComb.exe", dataFile};
        try {
            StringBuilder sbrOK = new StringBuilder();
            List<String> list = new ArrayList<>();
            boolean b1 = false;
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader r1 = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            String line = r1.readLine();
            while (line != null) {
                sbrOK.append(line + "\n");
                if (!b1 && ("*统计这些公司与被查公司共同投标合同数（由大到小排序）".equalsIgnoreCase(line))) {
                    b1 = true;
                    line = r1.readLine();
                    continue;
                }
                if (b1 && StringUtils.isEmpty(line)) {
                    b1 = false;
                }
                if (b1) {
                    list.add(line);
                }
                line = r1.readLine();
            }
           /* System.out.println("+++++++++++++++++开始读取文件p:"+p);
            System.out.println("+++++++++++++++++开始读取文件r1:"+r1);
            System.out.println("+++++++++++++++++开始读取文件line:"+line);*/
            p.waitFor();
           // System.out.println("====================读到的数据:sbrOK: ");
            //System.out.println(sbrOK);
            r1.close();
            System.out.println("======== 过滤后的数据 ============");
            names = dealCompanySort(list);
            /*for (String k : names.keySet()) {
                System.out.println(k + " : " + String.valueOf(names.get(k)));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }

        return names;
    }

    private HashMap<String, Integer> dealCompanySort(List<String> list) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        List<String> listre = list;
        String[] ss;
        //=======================拆分字符,将参与公司相同,顺序不同的情况排除======
       if (listre !=null && listre.size()>0){
           for (int i=0;i<listre.size();i++){
               for (int k=i+1;k<listre.size();k++){
                   String str1 = listre.get(i);
                   String stt1 = listre.get(k);
                   String[] str2 = str1.split(":");
                   String[] stt2 = stt1.split(":");
                   if (2 != str1.length()) {
                       String[] qe = str2[0].split(" ");
                       String[] qw = stt2[0].split(" ");
                       if (qe.length==2 && qw.length ==2){
                           if (qe[0].equals(qw[1]) && qe[1].equals(qw[0])){
                               String aa = str2[1].trim();
                               String bb = stt2[1].trim();
                               int a = Integer.parseInt(aa);
                               int b = Integer.parseInt(bb);
                               int nu = a+b;
                               str1 =str2[0]+":"+nu;
                               listre.set(i,str1);
                               listre.remove(k);
                               break;
                           }
                       }else if (qe.length==3 && qw.length ==3){
                           Arrays.sort(qe);
                           Arrays.sort(qw);
                           if (Arrays.equals(qe,qw)){
                               String aa = str2[1].trim();
                               String bb = stt2[1].trim();
                               int a = Integer.parseInt(aa);
                               int b = Integer.parseInt(bb);
                               int nu = a+b;
                               str1 =str2[0]+":"+nu;
                               listre.set(i,str1);
                               listre.remove(k);
                               continue;
                           }
                       }

                   }

               }
           }

       }
       //=============循环取出公司id,获取公司名称及数量加入map===========
        for (String s : listre) {
          //  System.out.println(s);
            ss = s.split(":");
            if (2 != s.length()) {
                String k = transCompName(ss[0]);
                Integer v = Integer.parseInt(ss[1].trim());
                hashMap.put(k, v);
            }
        }
        return hashMap;
    }

    private String transCompName(String ids) {
        StringBuilder sb = new StringBuilder();
        String[] ss = ids.split(" ");
        String sql = "select company_name from t_company where id=?";
        for (String s : ss) {
            if (StringUtils.isEmpty(s)) {
                continue;
            }
            //todo: query name from db
            String name = generalDao.queryValue(String.class, sql, s.trim());
            sb.append(name + ";");
        }
        String ret = sb.toString();
        if (!StringUtils.isEmpty(ret)) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

   /* public static void main(String arr[]) {
        String f = "D:/comp.txt";
        String s = callExe(f);
        //   String s =ReadTextUtil.readtext(f);
        System.out.println(s);
        System.exit(0);
    }*/

}
