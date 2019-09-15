package wxgh.param.pcadmin.party.di;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ExportExamParam {

    public static final Integer TYPE_GROUP = 1; //按群体导出
    public static final Integer TYPE_EXAM = 2;//按考试导出
    public static final Integer TYPE_SING = 3;//单个考试导出

    private Integer exportType; //导出类型
    private String id; //群体ID或考试ID

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
