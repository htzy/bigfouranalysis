package com.huangshihe.analysisweb.controller;

import com.huangshihe.analysisweb.model.Simple;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by root on 3/21/17.
 */
public class MainController extends Controller {
    public void index() {
        setAttr("username", "huangshihe");
        render("index.jsp");
    }

    public void analysisUserAction() {
        String username = "han";
        try{
            Record simples = Db.findFirst("select count, sum(count) as sumCount from simpleUser where username=?", username);
            setAttr("username", username);
            setAttr("opt", simples.get("count"));
            setAttr("total", simples.get("sumCount"));
        }catch (Exception e){
            e.printStackTrace();
        }

        render("analysisUserAction.jsp");
    }
}
