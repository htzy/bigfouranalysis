package com.huangshihe.analysisweb.controller;

import com.huangshihe.analysisweb.model.Simple;
import com.huangshihe.analysisweb.service.MainService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by root on 3/21/17.
 */
public class MainController extends Controller {

    private MainService mainService = new MainService();

    public void index() {
        setAttr("username", "huangshihe");
        try{
            System.out.printf("mainService.getTotalAvgPer():" + mainService.getTotalAvgPer());
        }catch (Exception e){
            e.printStackTrace();
        }

        render("index.jsp");
    }

    public void analysisUserAction() {
        String username = getPara("username");

        setAttr("record", mainService.getTotalAvgPer());
        render("analysisUserAction.jsp");
    }
}
