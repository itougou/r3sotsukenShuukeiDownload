package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AttendaneRepo {

@Autowired
JdbcTemplate jdbcTemplate;	//科目名取得で使う（標準のjdbcTemplate）

    private NamedParameterJdbcTemplate NpjdbcTemplate;	//集計でつかう（動的にプレースホルダ―使用できる）
    
    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate){
        this.NpjdbcTemplate = jdbcTemplate;
    }

    /*
     * 科目名をMapのListで取得するメソッド　{subject、値}
     */
	public List<Map<String,Object>> getKamokuMapList( ) {

		String sql = "select distinct subject from attendance ";
		List< Map<String,Object> >  kamokuMapList= jdbcTemplate.queryForList(sql);

		return kamokuMapList;
	}
	
	/*
	 * 集計結果取得メソッド
	 */
	public List< Map<String, Object > > getTotal( List<String> kamoku ) {

		String sql = "select user_id as 氏名,";
		for(int i=0; i<kamoku.size() ; i++) {
			sql += "count( case when subject=:k"+(i+1)+" then 1 else null end ) as :k"+(i+1);
			if( i+1 < kamoku.size() ) sql += ",";
			System.out.println("：k"+(i+1)+"="+kamoku.get(i)); 
		}
		sql += " from  attendance group by 氏名 order by 氏名";
		System.out.println(sql); 
		
		// Mapにパラメータ（科目名）を設定する処理
		Map<String, Object> parameters = new HashMap<String, Object>();
		for(int i=0; i<kamoku.size() ; i++) {
			parameters.put( "k"+(i+1), kamoku.get(i) );
		}

		List<Map<String, Object>>  ret = NpjdbcTemplate.queryForList(sql, parameters);

		return ret;
	}
}
