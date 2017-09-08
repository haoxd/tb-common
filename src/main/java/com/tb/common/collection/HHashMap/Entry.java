package com.tb.common.collection.HHashMap;


public class Entry<K,V> {
	
	final K key;
	V value;
	Entry<K, V> next;//下一个节点
	
	
	/**
	 * 有参数构造函数
	 * @param k
	 * @param v
	 * @param n
	 */
	public Entry(K k, V v, Entry<K, V> n){
		key=k;
		value=v;
		next = n;
	}


	public final V getValue() {
		return value;
	}


	public final V setValue(V newValue) {
		
		V oldValue = value;
		value = newValue;
		return oldValue;
	}


	public final K getKey() {
		return key;
	}
	
	/*
	 *  重写equals
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object o){
		//类型判断
		if(!(o instanceof Entry)){
			return false;
		}
		Entry entry = (Entry)o;
		Object k1 =getKey();
		Object k2 = entry.getKey();
		//key 判断
		if (k1 == k2 || (k1 != null && k1.equals(k2))) {
			Object v1 = getValue();
			Object v2 = entry.getValue();
			//value 判断
			if (v1 == v2 || (v1 != null && v1.equals(v2))) {
				return true;
			}
		}
		return false;
	}
	/* 重写hashcode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
        return (key==null   ? 0 : key.hashCode()) ^ (value==null ? 0 : value.hashCode());
    }

	/* 重写tostring方法
	 * @see java.lang.Object#toString()
	 */
	@Override
    public final String toString() {
        return getKey() + "=" + getValue();
    }

}
