package com.cos.blog.test;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html 파일이 아니라 data를 리턴 해주는 controller = RestController
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { 
		
		//@RequestBody
		//json 데이터를 요청 => java Object(MessageConverter의 JackSon라이브러리가)로 변환해서 받는다.
		
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//save 함수는 id를 전달하지 않으면 insert를 해주고
		//save 함수는 id를 전당하면 해당 id에 대한 데이터가 있으면 update를 해주고
		//save 함수는 id를 전달하면 해당 id에 대한 데이터가 업으면 insert를 한다
//		userRepository.save(requestUser);
		
		return null;
	}
	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8088/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		// user/4 을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가  null이 된다.
		// 그럼 return null 이 리턴된다. 그럼 프로그램에 문제가 생김
		// Optional 로 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해라
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
			// 람다식
		//	User user = userRepository.findById(id).orElseThrow(()-> {
		//		return new IllegalArgumentException("해당 사용자는 없습니다.");
		//	});

		
		//요청 : 웹브라우저
		//user 객체 = 자바 object
		// 변환 (웹 브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		// SpringBoot = MessageConverter 라는 애가 응답시에 자동 작동
		// 만약 자바 object를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user object를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}

	
	//http://localhost:8080/blog/dummy/join (요청_
	//http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { //key-value (약속된규칙)
		
		System.out.println("id : " + user.getId() );
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
