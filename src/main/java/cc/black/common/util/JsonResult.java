package cc.black.common.util;

public class JsonResult {

    private int status = 200;
    private String errMsg = "";
    private Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setErrorCode(ErrorCode errorCode)
    {
        this.status = errorCode.getStatus();
        this.errMsg = errorCode.getErrMsg();
    }

    public JsonResult error(){
        this.status = 500;
        this.errMsg = "系统异常";
        this.result = "";
        return this;
    }

    /**
     * 参数异常
     * @return
     */
    public JsonResult paramsErr(){
        this.status = 417;
        this.errMsg = "参数异常";
        return this;
    }
}
