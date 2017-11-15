package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Question;

public class QuestionDTO extends BaseDTO {
    private ArrayList<Question> Data;

    public ArrayList<Question> getData() {
        return Data;
    }

    public void setData(ArrayList<Question> data) {
        this.Data = data;
    }
}
