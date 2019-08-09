package cc.black.common.util;

public class ErrorCode {
    public static ErrorCode SYS_ERR = new ErrorCode(500, "系统内部错误");
    public static ErrorCode PARAMS_ERR = new ErrorCode(400, "参数异常");
    private int status;
    private String errMsg = "操作成功";

    public ErrorCode(int status, String errMsg)
    {
        this.status = status;
        this.errMsg = errMsg;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getErrMsg()
    {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }
}
