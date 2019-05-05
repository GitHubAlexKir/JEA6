package domain.response;

import java.io.Serializable;
/**
 * @author Alex
 * Simpele Object voor json resonses, Idee orgineel van een tuutorial maar aangepast naar wat ik nodig heb.
 **/
public class JsonResponse implements Serializable {

    private String status;
    private String errorMsg;
    private Object data;

    public JsonResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
