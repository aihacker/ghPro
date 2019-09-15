package wxgh.app.sys.excel.four;


import java.io.Serializable;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-13  10:06
 * --------------------------------------------------------- *
 */
public class ReadResult implements Serializable {

    private Boolean status;
    private String message;

    private ReadResult(){}

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static ReadResult create(){
        return new ReadResult();
    }

    public ReadResult setStatus(Boolean flag){
        status = flag;
        return this;
    }

    public ReadResult setMessage(String message){
        this.message = message;
        return this;
    }

    public static ReadResult ok(){
        return create().setStatus(true);
    }

    public static ReadResult ok(String message){
        return ok().setMessage(message);
    }

    public static ReadResult error(){
        return create().setStatus(false);
    }

    public static ReadResult error(String message){
        return error().setMessage(message);
    }

}
