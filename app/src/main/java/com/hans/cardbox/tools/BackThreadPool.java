package com.hans.cardbox.tools;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BackThreadPool {
	private static ThreadPoolExecutor backThreadPool;
	private static Map<Object, List<WeakReference<Future<?>>>> requestMap;
	private static Map<Object, WeakReference<Future<?>>> simpleRequestMap;

	public static void init() {
		requestMap = new WeakHashMap<Object, List<WeakReference<Future<?>>>>();
		simpleRequestMap = new WeakHashMap<Object, WeakReference<Future<?>>>();
		getBackThreadPool();
	}

	/** 关闭后台线程池 */
	public static void shutDown() {
		if (backThreadPool != null && !backThreadPool.isShutdown()) {
			backThreadPool.shutdown();
		}
	}

	/** 后台数据线程池 */
	private static ThreadPoolExecutor getBackThreadPool() {
		if (backThreadPool == null || backThreadPool.isShutdown()) {
			backThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		}
		return backThreadPool;
	}

	public static void cancelRequests(Object key, boolean mayInterruptIfRunning) {
		List<WeakReference<Future<?>>> requestList = requestMap.get(key);
		if (requestList != null) {
			for (WeakReference<Future<?>> requestRef : requestList) {
				Future<?> request = requestRef.get();
				if (request != null) {
					request.cancel(mayInterruptIfRunning);
				}
			}
		}
		requestMap.remove(key);
	}

	public static void post(Runnable backWork, Object key) {

		Future<?> request = getBackThreadPool().submit(backWork);
		if (key != null) {
			List<WeakReference<Future<?>>> requestList = requestMap.get(key);
			if (requestList == null) {
				requestList = new LinkedList<WeakReference<Future<?>>>();
				requestMap.put(key, requestList);
			}
			requestList.add(new WeakReference<Future<?>>(request));
		}
	}
	
	public static void postKill(Runnable backWork, Object key){
		if (key != null) {
			WeakReference<Future<?>> old = simpleRequestMap.get(key);
			if(old!=null){
				Future<?> request = old.get();
				if(request!=null){
					request.cancel(true);
				}
			}
		}
		Future<?> newRequest = getBackThreadPool().submit(backWork);
		simpleRequestMap.put(key, new WeakReference<Future<?>>(newRequest));
	}
}
