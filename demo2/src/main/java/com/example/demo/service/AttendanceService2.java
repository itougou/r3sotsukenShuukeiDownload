package com.example.demo.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceTotal;
import com.example.demo.repository.AttendaneRepo;
/**
 * 出席集計Service２　（MyBatis使わず、jdbcTemplateを使ったパターン）
 */
@Service
public class AttendanceService2 {
    /**
     * 出席状況 Mapper
     */
    
    @Autowired
    AttendaneRepo attendanceRepo;

    public AttendanceTotal execute() {

    	/*
    	 * 戻り値用の入れ物
    	 */
    	AttendanceTotal  attendanceTotal = new AttendanceTotal();	//出席状況エンティティ（戻り値）
     	List<String> AttendanceLabel = new ArrayList<String>();	//見出し部分
     	List< List<Object> > AttendanceData = new ArrayList< List<Object> >();	//データ行部分
     	
    	//科目名をListで取得
    	List<Map<String,Object>> kamokuMapList  = attendanceRepo.getKamokuMapList();

    	System.out.println("★tendanceRepo.getKamokuMapList() 科目List ="+kamokuMapList );

    	//全行のMapデータを取り出し、Listへ格納しなおす処理
		List<String> kamokuList = new ArrayList<>();	//1行分のListを生成
    	for ( int i = 0; i < kamokuMapList .size(); i++ ) {	//行数分繰り返し
    		Map<String,Object> hm = kamokuMapList.get(i);	//1行分のMapを取り出し
    		for ( String key :hm.keySet() ) {	//マップの件数分(1行の列数分)繰り返し
    			System.out.println(key + " => " + hm.get(key));
    			kamokuList.add( (String)hm.get(key) );	//ハッシュのデータをList追加
    		}
    	}    	

    	//科目Listを渡して動的に集計用SQLを実行、結果（1行分のMap（キー・値の組み合わせ）がListになったデータ））を戻り値で取得
    	List<Map<String,Object>> attendanceTotalList = attendanceRepo.getTotal( kamokuList );	
    	//List< HashMap<String,Object> > attendanceTotalList = attendanceMapper.getAttendanceTotal( kamokuList );

    	//集計結果を取り出し表示
    	System.out.println("attendanceMapper.getAttendanceTotal="+attendanceTotalList );
    	
    	//1行目のデータを取り出し、見出しの文字列Listを生成する処理
    	Map<String,Object> hm_1 = attendanceTotalList .get(0);	//1行目取り出し
    	
    	for ( String key :hm_1.keySet() ) {	//キーの数分（見出しの列数）繰り返し
    		AttendanceLabel.add(key);	//キーの文字列（見出しにする文字列）を取り出し、Listへ格納
    	}
    	
    	//全行のデータを取り出し、1行分のデータが入ったListをListへ格納する処理
    	for ( int i = 0; i < attendanceTotalList .size(); i++ ) {	//行数分繰り返し
    		Map<String,Object> hm = attendanceTotalList .get(i);	//1行分のHashMapを取り出し
    		List<Object> AttendanceRow = new ArrayList<>();	//1行分のListを生成
    		for ( String key :hm.keySet() ) {	//ハッシュマップの件数分(1行の列数分)繰り返し
    			System.out.println(key + " => " + hm.get(key));
    			AttendanceRow.add( hm.get(key) );	//ハッシュのデータをList追加
    		}
    		AttendanceData.add( AttendanceRow );	//1行分のListを全データ行のLisへ追加
    	}
    	attendanceTotal.setAttendanceLabel(AttendanceLabel);	//見出し行データを格納
    	attendanceTotal.setAttendanceData(AttendanceData);	//データ行部分を格納
    	
    	return attendanceTotal;
    }

}