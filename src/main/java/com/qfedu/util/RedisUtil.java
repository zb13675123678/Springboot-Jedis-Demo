package com.qfedu.util;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @auther Zhangbo
 * @date 2020/5/2 2:05
 */
@Component
public class RedisUtil {

    /**
     * 注入模板对象
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //////////////基本操作//////////////////
    /**
     * 设置数据缓存的失效时间
     * @param key  redis中的键
     * @param time  失效时间（秒）
     * @return   该key对应的值是否失效
     */
    public Boolean expire(String key,long time){
        try {
            if(time > 0){
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取数据缓存的失效时间
     * @param key redis中的键
     * @return  失效的时间（秒）
     */
    public  long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否在缓存中存在
     * @param key
     * @return
     */
    public  boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存中所有keys的值
     * @param keys  可变长度参数，可以是0个，1个，多个key进行删除
     */
    public void delete(String ... keys){
        if(keys != null && keys.length >0){
            if(keys.length == 1){
                redisTemplate.delete(keys[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }

    /////////////////////////String类型///////////////////////
    /**
     * 获取string类型的key对应的值
     * @param key string类型redis数据的key
     * @return  该key对应的string的value值
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置String类型的值
     * @param key String类型的key
     * @param value 值
     * @return     true添加成功，否则false
     */
    public boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置String类型的值，并设置失效时间
     * @param key String类型的key
     * @param value 值
     * @param time 失效时间（秒）
     * @return 设置是否成功
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time > 0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else{
                set(key,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //////////////////String类型//////////////////////////////////

    //////////////////hash类型//////////////////////////////////

    /**
     * 设置hash类型的单个数据（指定key下field的值）
     * @param key    hash的key
     * @param field  hash中的field域（属性）
     * @param value  field设置的值
     * @return   返回是否设置成功
     */
    public boolean hset(String key,String field,Object value){
        try {
            redisTemplate.opsForHash().put(key,field,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash类型的单个数据，并指定失效时间
     * @param key  hash的key
     * @param field hash中的field域（属性）
     * @param value field设置的值
     * @param time   key的失效时间
     * @return  返回是否设置成功
     */
    public boolean hset(String key,String field,Object value,Long time){
        try {
            redisTemplate.opsForHash().put(key,field,value);
            if(time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *获取hash类型数据的获取，field的值
     * @param key hash的键
     * @param field hash类型的field
     * @return      返回该key对应filed的值
     */
    public Object hget(String key,String field){
        return redisTemplate.opsForHash().get(key,field);
    }


    /**
     * 设置hash类型的多个数据
     * @param key  hash的key
     * @param map  键值对
     * @return  设置是否成功
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash类型的多个数据，并指定失效时间
     * @param key  hash的key
     * @param map  键值对
     * @param time 失效时间(秒)
     * @return      设置是否成功
     */
    public  boolean hmset(String key,Map<String,Object> map,long time){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            if(time > 0){
                expire(key,time);
            }
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hash类型key对应整个map对象
     * @param key
     * @return
     */
    public Object hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }
    //////////////////hash类型//////////////////////////////////

    //////////////////set类型/////////////////////////////

    /**
     * 设置set类型的数据
     * @param key  set的key
     * @param values 可变参数，key的值
     * @return
     */
    public long sset(String key,Object ... values){
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 :count;
    }

    /**
     * 删除set的数据
     * @param key   set的key
     * @param values  可变参数，key的多个值
     * @return      删除的set值的个数
     */
    public long sdel(String key,Object ... values){
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }
    //////////////////set类型/////////////////////////////

    //////////////////list类型/////////////////////////////

    /**
     * 设置list类型的单个数据
     * @param key  list数据的key
     * @param value 值
     * @return  设置是否成功
     */
    public boolean lset(String key,Object value){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置list类型的多个数据
     * @param key list数据的key
     * @param values    可变参数，值
     * @return  返回存入的数据数
     */
    public long lsets(String key,Object ... values){
        Long count = redisTemplate.opsForList().rightPush(key, values);
        return  count == null ? 0 : count;
    }

    /**
     * 设置list类型的多个数据
     * @param key list数据的key
     * @param values    可变参数，值
     * @return  返回存入的数据数
     */
    public long lsets(String key, Collection<Object> values){
        Long count = redisTemplate.opsForList().rightPush(key, values);
        return  count == null ? 0 : count;
    }

    /**
     * 获取list中start到end的数据
     * @param key list数据的键
     * @param start 开始位置
     * @param end   结束位置（start=0,end =-1表示获取全部元素）
     * @return  list数据key的全部对象
     */
    public List<Object> lgets(String key,int start,int end){
        return redisTemplate.opsForList().range(key,start,end);
    }
    //////////////////list类型/////////////////////////////

}