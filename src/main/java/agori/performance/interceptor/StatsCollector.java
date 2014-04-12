package agori.performance.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatsCollector {
	
	static List<ThreadStatsCollector> globalData = Collections.synchronizedList(
			new ArrayList<ThreadStatsCollector>());
	
	static ThreadLocal<ThreadStatsCollector> tc = new ThreadLocal<StatsCollector.ThreadStatsCollector>();
	
	public static class ThreadStatsCollector {
		long begin, end;
		
		private long now() {
			return System.currentTimeMillis();
		}
		
		public void start() {
			begin = now();
		}
		
		public void end() {
			end = now();
			try {
				globalData.add(this);
			} finally {
				tc.set(null);
			}
		}
		
		public long getElapsedTime() {
			return end - begin;
		}
	}
	
	public static ThreadStatsCollector threadInstance() {
		if (tc.get() == null) {
			tc.set(new ThreadStatsCollector());
		}
		return tc.get();
	}
	
	public static String getCurrentStats() {
		long max = 0, min = Long.MAX_VALUE, sum = 0, size;
		synchronized (globalData) {
			size = globalData.size();
			for (ThreadStatsCollector t : globalData) {
				if (max < t.getElapsedTime()) {
					max = t.getElapsedTime();
				}
				if (min > t.getElapsedTime()) {
					min = t.getElapsedTime();
				}
				
				sum += t.getElapsedTime();
					
			}	
		}
		
		return "min=" + min + ",max=" + max + ",average=" + (sum / size);

	}
	

}
