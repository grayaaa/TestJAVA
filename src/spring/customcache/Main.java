package spring.customcache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.cache.Account;
import spring.springcache.AccountService;

public class Main {
	public static void main(String[] args) {
		// 加载spring 配置文件
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/customcache/spring-cache-anno.xml");

		AccountService s = (AccountService) context.getBean("accountServiceBean");

		Account account = s.getAccountByName("someone");
		System.out.println("passwd=" + account.getPassword());
		account = s.getAccountByName("someone");
		System.out.println("passwd=" + account.getPassword());
	}
}
