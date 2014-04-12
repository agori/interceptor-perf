package agori.performance.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Catched @Interceptor
public class CatchedInterceptor {
	
	@PersistenceContext 
	EntityManager em;
	
	@AroundInvoke
	public Object catchAll(InvocationContext invocationContext) {
		try {
			System.out.println("executing interceptor!");
			Object result = invocationContext.proceed();
			StatsCollector.threadInstance().start();
			em.flush();
			em.clear();
			return result;
		} catch (Exception e) {
			return "ERROR";
		}
	}

}
