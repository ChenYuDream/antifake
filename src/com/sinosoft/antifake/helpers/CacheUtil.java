/**
 * 
 */
package com.sinosoft.antifake.helpers;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓存工具类
 * 
 * @PackageName:com.upgradeJspt.bank.util
 * @ClassName: 中文名称
 * @Description: 相关描述
 * @Author: mamin
 * @CreateTime:2012-12-4下午06:24:10
 * @Version: 1.0
 * @History: 修改历史(修改内容、修改人、修改时间)
 * 
 */

public final class CacheUtil<K, V> {

	private final Lock lock = new ReentrantLock();
	private final int maxCapacity;
	private final Map<K, V> eden;
	private final Map<K, V> perm;

	public CacheUtil(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		this.eden = new ConcurrentHashMap<K, V>(maxCapacity);
		this.perm = new WeakHashMap<K, V>(maxCapacity);
	}

	public V get(K k) {
		V v = this.eden.get(k);
		if (v == null) {
			lock.lock();
			try {
				v = this.perm.get(k);
			} finally {
				lock.unlock();
			}
			if (v != null) {
				this.eden.put(k, v);
			}
		}
		return v;
	}

	public void put(K k, V v) {
		if (this.eden.size() >= maxCapacity) {
			lock.lock();
			try {
				this.perm.putAll(this.eden);
			} finally {
				lock.unlock();
			}
			this.eden.clear();
		}
		this.eden.put(k, v);
	}
	
//	public void remove(K k)
//	{
//		this.eden.clear();
//		this.perm.clear();
//	}
}
