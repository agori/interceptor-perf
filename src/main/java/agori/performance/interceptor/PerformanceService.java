package agori.performance.interceptor;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import agori.performance.interceptor.StatsCollector.ThreadStatsCollector;

@Path("/perf")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class PerformanceService {
	
	@Inject WidgetService service;

	@GET
	@Path("execute")
	public String execute() {
		service.test();
		ThreadStatsCollector s = null;
		try {
			s = StatsCollector.threadInstance();
		} finally {
			if (s != null) {
				s.end();
			}
		}
		return "time elapsed: " + s.getElapsedTime();
	}
	
	@GET
	@Path("stats")
	public String getStats() {
		return StatsCollector.getCurrentStats();
	}
}
