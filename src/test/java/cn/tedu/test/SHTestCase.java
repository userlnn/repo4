package cn.tedu.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.tedu.dao.UserDao;
import cn.tedu.entity.User;
import cn.tedu.service.UserService;


public class SHTestCase {

	ClassPathXmlApplicationContext ctx;
	SessionFactory factory;
	@Before
	public void init() {
		String[] cfg= {"conf/spring-orm.xml"};
		ctx=new ClassPathXmlApplicationContext(cfg);
		factory=ctx.getBean("sessionFactory",SessionFactory.class);
	}
	
	@Test
	public void testSession() {
		Session session=factory.openSession();
		System.out.println(session);
		session.close();
	}
	
	@Test
	public void testHibernateTemplate() {
		//相对于Session对象HibernateTemplate对象使用
		//更加简便，HibernateTemplate 由spring提供的
		HibernateTemplate template=
				ctx.getBean("hibernateTemplate",HibernateTemplate.class);
		User user=new User("400","John","123","ok","J");
		template.save(user);
	}
	
	@Test
	public void testUserDao() {
		UserDao dao=ctx.getBean("userDao",UserDao.class);
		User user=new User("500","Wang","123","ok","P");
		dao.addUser(user);
	}
	
	@Test
	public void testDeleteUsers() {
		String[] ids= {"400","500"};
		UserService service=
				ctx.getBean("userService",UserService.class);
		service.deleteUsers(ids);
	}
}
