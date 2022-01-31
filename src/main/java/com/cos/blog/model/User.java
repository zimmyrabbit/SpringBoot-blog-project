package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> Java(언어) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor //빈 생성자
@AllArgsConstructor //전체 생성자
@Builder //빌더 패턴

//ORM -> Java(언어) Object -> 테이블로 매핑해주는 기술
@Entity // User Class가 Mysql에 테이블이 생성된다.

//@DynamicInsert // insert할때 null값인 컬럼을 제외시켜준다.
public class User {
	
	@Id //Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. ex) 오라클 - SEQ / Mysql - AutoIncrement
	private int id; //Seq, auto-increment
	
	@Column(nullable = false, length = 30)
	private String username; //아이디
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// @ColumnDefault(" 'user' ")
	//DB는 RoleType이 없음
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋다. //ADMIN, USER
	
	@CreationTimestamp //시간이 자동 입력   ㅇ
	private Timestamp createDate;

	
}
