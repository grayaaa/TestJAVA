package spring.test1;

import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJDBCTest {

	public static void main(String[] args) {
		ApplicationContext act = new ClassPathXmlApplicationContext("spring/test1/applicationContext-pro.xml");

		PersonService personService = (PersonService) act.getBean("personService");

		Person person = new Person();

		Random random = new Random();
		person.setId(10);
		person.setName("sudongpo");
		person.setAge(random.nextInt(100));
		person.setSex("man");

		// 保存一条记录
		personService.save(person);

		List<Person> person1 = personService.getPerson();
		System.out.println("++++++++得到所有Person");
		for (Person person2 : person1) {
			System.out.println(person2.getId() + "  " + person2.getName() + "   " + person2.getAge() + "  "
					+ person2.getSex());
		}
		// Person updatePerson = new Person();
		// updatePerson.setName("Divide");
		// updatePerson.setAge(20);
		// updatePerson.setSex("男");
		// updatePerson.setId(5);
		// // 更新一条记录
		// personService.update(updatePerson);
		// System.out.println("******************");
		//
		// // 获取一条记录
		// Person onePerson = personService.getPerson(2);
		// System.out.println(onePerson.getId() + "  " + onePerson.getName() +
		// "  " + onePerson.getAge() + "  "
		// + onePerson.getSex());
		// // 删除一条记录
		// personService.delete(1);
	}
}