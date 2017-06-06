package geolaxia.geolaxia.Model.Dto;

/**
 * Created by uriel on 15/4/2017.
 */

/**
 * DTO Base del cual extienden todos los de la aplicación. Contiene atributos comunes como status (OK, ERROR) y code (código de error en caso de resultado erróneo)
 */
public class BaseDTO {

    private StatusDTO Status;
    private int Code;

    public StatusDTO getStatus() {
        return Status;
    }

    public void setStatus(StatusDTO status) {
        this.Status = status;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }
}
