package agori.performance.interceptor;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless @Catched
public class WidgetService {
	
	@PersistenceContext EntityManager em;
	
	public String test() {
		@SuppressWarnings("unchecked")
		List<Widget> list = (List<Widget>) em.createQuery("select w from Widget w").getResultList();
		return "ok: " + list.size();
	}

}
