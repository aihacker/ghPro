package wxgh.param.pcadmin.party.exam;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ExportExamPartyParam {

    public static final Integer TYPE_EXAM = 1;//按考试导出
    public static final Integer TYPE_SING = 2;//单个考试导出
    public static final Integer TYPE_PARTY = 3;//党委导出

    private Integer exportType; //导出类型
    private String id; //party ID或考试ID

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
