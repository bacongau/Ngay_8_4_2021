package com.example.ngay_8_4_2021.model.response;

import com.example.ngay_8_4_2021.model.Clothes;

import java.util.ArrayList;

public class ResponseDanhSachClothes {
    private Integer code;
    private String msg;
    private ResultGetAllClothes data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultGetAllClothes getData() {
        return data;
    }

    public void setData(ResultGetAllClothes data) {
        this.data = data;
    }

    public ResponseDanhSachClothes(Integer code, String msg, ResultGetAllClothes data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseDanhSachClothes() {
    }


    public class ResultGetAllClothes {
        private ArrayList<Clothes> results;

        public ArrayList<Clothes> getResults() {
            return results;
        }

        public void setResults(ArrayList<Clothes> results) {
            this.results = results;
        }

        public ResultGetAllClothes() {
        }

        public ResultGetAllClothes(ArrayList<Clothes> results) {
            this.results = results;
        }
    }
}


