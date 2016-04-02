/* Author : Kirtika thyagarajan 
   Version : 1.0 */
package firsthibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UserProfile user = new UserProfile(1,"kiki21","password","40 jersey city","kiki","tyagi","765897213");
		 Configuration configuration = new Configuration();
		 configuration.configure("hibernate.cfg.xml");
		 
		 SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(user);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
		
	}

}
