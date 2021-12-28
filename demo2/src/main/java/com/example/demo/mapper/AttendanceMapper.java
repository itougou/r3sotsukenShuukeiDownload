package com.example.demo.mapper;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
/**
 * 出席状況 Mapper
 */
@Mapper
public interface AttendanceMapper {

	//科目名取り出し
	//	[科目名、科目名、科目名、・・・・・、科目名]
	List<String> getAttendanceKamoku();

	// 学生・科目毎出席数集計SQL （ HashMap(1行分のﾃﾞｰﾀ)をListにして取得 ）
	//	[ キー：値, キー：値, ・・・・・, キー：値　]
	//	[ キー：値, キー：値, ・・・・・, キー：値　]
	// 			：
	//	[ キー：値, キー：値, ・・・・・, キー：値　]
	List< HashMap<String,Object> >  getAttendanceTotal( List<String> kamokuItem );
}