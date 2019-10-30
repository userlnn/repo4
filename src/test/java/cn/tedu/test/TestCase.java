package cn.tedu.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import cn.tedu.entity.User;

public class TestCase {

	SessionFactory factory;
	@Before
	public void init() {
		Configuration cfg=new Configuration();
		cfg.configure("hibernate.cfg.xml");
		factory=cfg.buildSessionFactory();
	}
	
	@Test
	public void testFactory() {
		Session session=factory.openSession();
		System.out.println(session);
		session.close();
	}
	
	@Test
	public void testSession() {
//		//读取配置文件
//		Configuration cfg=new Configuration();
//		cfg.configure("hibernate.cfg.xml");
//		//创建Session工厂
//		SessionFactory factory=
//				cfg.buildSessionFactory();
		//可以创建Session对象
		//session的底层就是JDBC Connection
		//如果没有异常，就说明数据库连接成功！
		Session session=factory.openSession();
		System.out.println(session);
		session.close();
	}
	@Test
	public void testSaveObject() {
		User user=new User("200","Tom","123","ok","Cat");
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();
		session.save(user);//user对象转换为持久状态
		user.setNick("Andy");
		// 将影响数据库，不用调用session.update也会
		//更改数据库中的数据
		tx.commit();
		session.close();
	}
	@Test
	public void testUpdateObject() {
		Session session=null;
		Transaction tx=null;
		try {
			session =factory.openSession();
			tx=session.beginTransaction();
			//测试更新功能
			//找到现有的用户信息
			User user=(User)session.get(User.class, "100");
			System.out.println(user);
			//修改用户信息
			user.setName("Jerry");
			//将修改结果更新到数据库
			session.update(user);
			tx.commit(); //提交事务 //执行update
		} catch (Exception e) {
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback(); //回滚事务
			}
		}finally {
			if(session!=null) session.close(); //释放资源
		}
	}
	@Test
	public void testDeleteObject() {
		
		Session session=null;
		Transaction tx=null;
		try {
			session=factory.openSession();
			tx=session.beginTransaction();
			User user=(User)session.get(User.class, "100");
			System.out.println(user);
			session.delete(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}
	@Test
	public void testUpdate() {
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();
		//get方法返回的对象时“持久状态”对象
		User user=(User)session.get(User.class, "200");
		//修改“持久状态”对象的属性，将影响数据库中的数据
		user.setNick("老王");
		tx.commit();
		session.close();
	}
	
	@Test
	public void testEvict() {
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();
		
		User user=(User)session.get(User.class, "200");
		//将对象user踢出session缓存，使其成为游离状态
		session.evict(user);//踢出一个对象
//		session.clear(); //踢出所有对象
		//游离状态的user对象，修改其属性不影响数据库
		user.setNick("老宋");
		session.update(user);//由游离状态返回持久状态
		tx.commit();
		session.close();
	}
}
