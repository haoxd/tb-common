package com.tb.common.collection.HHashMap;

/**
 * @author acer11
 *  作者：haoxd
 * @param <K>
 * @param <V>
* 创建时间：2017年9月8日 下午5:15:52  
* 项目名称：tb-common  
* 文件名称：HHashMap.java  
* 类说明：瞎几把自己实现的hashmap
* 参考：http://www.cnblogs.com/ITtangtang/p/3948406.html
 */
public class HHashMap<K,V> {
	
	private Entry[] table;//数组表
	static final int DEFAULT_TABLE_LENGTH=16;//默认数组表长度
	private int size;
	
	/**
	 *无参构造函数 
	 */
	public HHashMap(){
		table = new Entry[DEFAULT_TABLE_LENGTH];
		size=DEFAULT_TABLE_LENGTH;
	}
	
	/**
	 * 获取大小
	 * @return
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 *  求index
	 * @param h
	 * @param length
	 * @return
	 */
	static int indexFor(int h,int length){
		return h%(length-1);
	}
	
	/**
	 * 更具K获取Value
	 * @param Key
	 * @return
	 */
	public V get(Object Key){
		if(null == Key){
			return null;
		}
		int hash = Key.hashCode();// key的哈希值
		int index= indexFor(hash, table.length);//求key在数组中的下标
		for (Entry<K,V> entry =table[index]; entry!=null;entry = entry.next) {
			  Object k = entry.key;
			  if (entry.key.hashCode() == hash && (k == Key || Key.equals(k))){
				  return entry.value; 
			  }
	               
		}
		return null;
	}
	
    /**
     * 存放数据
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        if (key == null)
            return null;
        int hash = key.hashCode();
        int index = indexFor(hash, table.length);

        // 如果添加的key已经存在，那么只需要修改value值即可
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            Object k = e.key;
            if (e.key.hashCode() == hash && (k == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;// 原来的value值
            }
        }
        // 如果key值不存在，那么需要添加
        Entry<K, V> e = table[index];// 获取当前数组中的e
        table[index] = new Entry<K, V>(key, value, e);// 新建一个Entry，并将其指向原先的e
        return null;
    }

}
