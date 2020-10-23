package com.ruoyi.common.utils.redis;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

	private static final Logger log = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value, String db) {
		key = db + key;
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Set<Object> keys(String key) {
		Set<Object> set = redisTemplate.keys(key);
		return set;
	}

	/**
	 * 指定时间类型---秒
	 *
	 * @param key
	 * @return
	 */
	public long getExpireTime(String key, String db) {
		long time = redisTemplate.getExpire(db + key, TimeUnit.SECONDS);
		return time;
	}

	/**
	 * 写入缓存设置时效时间
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 *            秒
	 * @return
	 */
	public boolean set(String key, Object value, Long expireTime, String db) {
		key = db + key;
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			log.error("set error,key:{}, {}, {}", key, e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public void removePattern(String pattern, String db) {
		pattern = db + pattern;
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	public void remove(String key, String db) {
		if (exists(key, db)) {
			log.info("delete key =====>>>>>" + db + key);
			redisTemplate.delete(db + key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(String key, String db) {
		return redisTemplate.hasKey(db + key);
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key, String db) {
		key = db + key;

		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		return operations.get(key);
	}

	/**
	 * 哈希 添加
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	public void hmSet(String key, Object hashKey, Object value, Long expireTime, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}
	/**
	 * 哈希获取数据
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	/**
	 * 根据key获取所有hash值
	 *
	 * @param key
	 * @return
	 */
	public List<Object> hmGetAll(String key, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		return hashOperations.values(key);
	}

	/**
	 * 列表左侧添加
	 *
	 * @param key
	 * @param value
	 * return: the length of the list after the push operations.
	 */
	public Long lPush(String key, Object value, String db) {
		key = db + key;
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.leftPush(key, value);
	}

	/**
	 * 列表右侧添加
	 *
	 * @param key
	 * @param value
	 */
	public Long rPush(String key, Object value, String db) {
		key = db + key;
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.rightPush(key, value);
	}

	/**
	 * 有序列表左侧弹出元素
	 *
	 * @param key
	 * @param db
	 * @return
	 */
	public Object lPop(String key, String db) {
		key = db + key;
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.leftPop(key);
	}

	/**
	 * 有序列表右侧弹出元素
	 *
	 * @param key
	 * @param db
	 * @return
	 */
	public Object rPop(String key, String db) {
		key = db + key;
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.rightPop(key);
	}

	/**
	 * 列表获取
	 *
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String k, long l, long l1, String db) {
		k = db + k;
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(k, l, l1);
	}

	public List<Object> getList(String key, String db) {
		key = db + key;
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key, 0, listOperations.size(key));
	}

	/**
	 * 根据下标获取列表数据
	 *
	 *
	 * @param key
	 * @param index
	 * @param db
	 * @return
	 */

	public Object lIndex(String key, long index, String db) {
		key = db + key;
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.index(key, index);
	}

	/**
	 * 获取列表长度
	 *
	 * @param key
	 * @param db
	 * @return
	 */
	public Long lLength(String key, String db) {
		key = db + key;
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.size(key);
	}

	/**
	 * 根据下标获取有序列表
	 *
	 * @param key
	 * @param db
	 * @return
	 */
	public List<Object> lRange(String key, String db, int start, int end) {
		key = db + key;
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key, start, end);
	}

	/**
	 * 无序集合添加
	 *
	 * @param key
	 * @param value
	 *
	 * return:the number of elements that were added to the set,
	 * not includingall the elements already present into the set.
	 */
	public Long setAdd(String key, Object value, String db) {
		key = db + key;
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.add(key, value);
	}


	/**
	 * 检查无须集合中是否存在
	 *
	 * @param key
	 * @param value
	 */
	public boolean setIsMember(String key, Object value, String db) {
		key = db + key;
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.isMember(key, value).booleanValue();
	}

	/**
	 * 无序集合获取
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key, String db) {
		key = db + key;
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	public Set<Object> setMembers(String key, Long expireTime, String db) {
		key = db + key;
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		Set<Object> retSet = set.members(key);
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		return retSet;
	}

	public void setAdd(String key, Object value, Long expireTime, String db) {
		key = db + key;
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}
	/**
	 * 无序列表移除一个元素
	 *
	 * @param key
	 * @param value
	 */
	public void setRemove(String key, Object value, String db) {
		key = db + key;
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		setOperations.remove(key, value);
	}

	/**
	 * 有序集合添加
	 *
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void rangeAdd(String key, Object value, double scoure, String db) {
		key = db + key;
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	/**
	 * 有序集合删除
	 *
	 * @param key
	 * @param value
	 * @param db
	 * @return
	 */
	public long rangeRemove(String key, Object value, String db) {
		key = db + key;
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.remove(key, value);
	}

	/**
	 * 获取有序集合中某个值的积分
	 *
	 * @param key
	 * @param value
	 * @param db
	 * @return
	 */
	public Double rangeGet(String key, Object value, String db) {
		key = db + key;

		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();

		return zset.score(key, value);
	}

	/**
	 * 有序集合获取
	 *
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1, String db) {
		key = db + key;
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}

	public void hmSetAll(String key, Map map, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		hashOperations.putAll(key, map);
	}

	public void hmDeleteAll(String key, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		Set<Object> keys = hashOperations.keys(key);
		Iterator<Object> iterator = keys.iterator();
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			hashOperations.delete(key, object);
		}
	}

	public void hmDelete(String key, Object key1, String db) {
		key = db + key;
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		hashOperations.delete(key, key1);
	}

	/**
	 * 计数器，返回自增后的结果
	 * @param key
	 * @param expireTime
	 * @param timeUnit
	 * @return
	 */
	public Long incr(String key, long expireTime, TimeUnit timeUnit) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		Long increment = entityIdCounter.getAndIncrement();
		if ((null == increment || increment.longValue() == 0) && expireTime > 0) {
			//初始设置过期时间
			entityIdCounter.expire(expireTime, timeUnit);
		}
		return ++increment;
	}

	public void flushdb() {
		redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * 刷新缓存过期时间
	 *
	 * @param key
	 * @param expire
	 * @param db
	 */
	public boolean refreshLiveTime(String key, long expire, String db) {
		return redisTemplate.expire(db + key, expire, TimeUnit.SECONDS);
	}

	/**
	 * 消息发布
	 *
	 * @param channel
	 * @param message
	 */
	public void publish(String channel, Object message) {

		redisTemplate.convertAndSend(channel, message);

		log.info("消息发布到渠道{}，消息{}，成功", channel, JSONObject.toJSONString(message));
	}

}
