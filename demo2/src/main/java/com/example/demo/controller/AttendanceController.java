package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.AttendanceTotal;
import com.example.demo.mapper.AttendanceMapper;
import com.example.demo.service.AttendanceService;
/**
 * 出席状況集計 Controller
 */
@Controller
//@RestController
public class AttendanceController {

  @Autowired
  AttendanceMapper attendanceMapper;
  
  @Autowired
  AttendanceService attendanceService;
  /*
   * Get へのマッピング
   */
  @GetMapping(value = "/AttendanceTotalShow")
  public String attendanceTotalShow(Model model) {
	
	AttendanceTotal at = attendanceService.execute();	//出席状況サービスのexecuteメソッドを実行し集計データを取得
	model.addAttribute( "label", at.getAttendanceLabel() );	//見出しのListをスコープにセット
	model.addAttribute( "data", at.getAttendanceData() );	//全データ行Listをスコープにセット
	
	return "attendance/attendanceTotal";	//出席数集計画面へ遷移
  }

}