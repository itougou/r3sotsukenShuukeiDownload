package com.example.demo.controller;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.AttendanceTotal;
import com.example.demo.mapper.AttendanceMapper;
import com.example.demo.service.AttendanceService;
/**
 * 出席状況集計結果ダウンロード Controller
 */
//@Controller
@RestController
public class AttendanceDownloadController {

  @Autowired
  AttendanceMapper attendanceMapper;
  
  @Autowired
  AttendanceService attendanceService;

  /*
   * Get へのマッピング
   */
  @GetMapping(value = "/AttendanceTotalDownload")
  public void attendanceTotalDownloal(Model model,HttpServletRequest request,HttpServletResponse response) {
	  
	response.setHeader("Content-Disposition", "attachment; filename=\"AttendanecTotalData.txt\"");
	response.setContentType("text/plain; charset=shift_jis");
      
	try( ServletOutputStream out = response.getOutputStream() ){
		
		AttendanceTotal at = attendanceService.execute();	//出席状況サービスのexecuteメソッドを実行し集計データを取得
		List<String> labelRow = at.getAttendanceLabel();	//見出しのListを取り出し
		
		String tsv = "";	//ダウンロードさせる文字列
		
		for( int i=0 ; i<labelRow.size() ; i++) {	//見出しの列数分繰り返す
			if( i+1 < labelRow.size() ) {	//最後の列でない場合
				tsv += labelRow.get(i) +"\t";	//タブで区切る
			}else {	//最後の列の場合
				tsv += labelRow.get(i) +"\r\n";	//改行
			}
		}

		List< List<Object> > dataRow = at.getAttendanceData();//全データ行Listを取り出し
		
		for( int i=0 ; i<dataRow.size() ; i++ ) {	//データ行分繰り返す
			List<Object> oneRow = dataRow.get(i);	//データ行から1行取り出す
			
			for( int j=0 ; j<oneRow.size() ; j++ ) {	//各行の列数分繰り返す
				if( j+1 < oneRow.size()) {	//最後の列でない場合
					tsv += oneRow.get(j)+"\t"; 	//タブで区切る
				}else {//最後の列の場合
					tsv += oneRow.get(j)+"\r\n"; 	//改行
				}
			}
		}
		out.write( tsv.getBytes("shift_jis") );	//StringをシフトJISのバイトに変換
        out.flush();	//バイトのデータを書き出す
		System.out.println("★tsv=\n"+tsv);
		
	} catch ( Exception e ) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}

  }
}