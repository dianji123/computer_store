package com.hzh.computer_store.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

//响应的结果集实体类
//因为所有的响应的结果都采用Json格式的数据进行响应（通过网络发送对象）,所以需要实现Serializable接口 持久化
public class JsonResult<E> implements Serializable {

    //状态码
    private Integer state;
    //描述信息
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    //数据类型不确定,用E表示任何的数据类型,一个类里如果声明的有泛型的数据类型,类也要声明为泛型
    //当 data 属性非 null 时，它才会被序列化到 JSON 响应体中
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private E data;

    public JsonResult() {

    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public JsonResult(Integer state, String message, E data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
