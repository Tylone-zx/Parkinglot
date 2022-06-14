package world.tylone.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int err; // err:0表示没有错误, err:1表示错误
    private String msg;
    private Object data;

    public static Result succ(Object data) {
        return succ(0, "操作成功", data);
    }

    public static Result succ(int err, String msg, Object data) {
        Result r = new Result();
        r.setErr(err);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg) {
        return fail(1, msg, null);
    }

    public static Result fail(int err, String msg, Object data) {
        Result r = new Result();
        r.setErr(err);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
