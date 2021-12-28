package com.example.demo.entity;

import java.util.List;

import lombok.Data;

/*
 * 出席集計エンティティクラス
 */
@Data
public class AttendanceTotal {
	List<String> AttendanceLabel;	//出席集計表見出し行
	List< List<Object> > AttendanceData;	//出席集計表データ行
}
