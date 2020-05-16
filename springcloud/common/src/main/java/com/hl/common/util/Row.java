package com.hl.common.util;

import java.util.*;

/**
 * <字段,值> 重新修改了一下，可以完全取代HashMap
 * Created by ivan.huang on 2016/7/28.
 * desc :
 */
public class Row extends HashMap {
	
    private static final long serialVersionUID = 1L;

    protected List<Object> ordering = new ArrayList<Object>(); // <字段>

    protected Map<String, String> functionMap = null; // <字段,函数>

    /**
     *
     */
    public Row() {
    }

    /**
     * 直接拿一个Map过来当作一个Row使用
     */
    @SuppressWarnings("unchecked")
    public Row(Map map) {
        super(map);
        for (Object obj : map.keySet()) {
            ordering.add(obj);
        }
    }


    /**
     * 根据字段名返回字符串值
     *
     * @param name
     * @return
     */
    public String gets(Object name) {
        try {
            if (get(name) != null)
                return get(name).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 根据字段名返回字符串值,若为null侧返回默认值;
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public String gets(Object name, String defaultValue) {
        try {
            if (get(name) != null)
                return get(name).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 根据字段名返回字符串值,若为null或者""侧返回默认值;
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public String get(Object name, String defaultValue) {
        try {
            if (get(name) != null && !"".equals(get(name)))
                return get(name).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 获取整型值（可以为负数），出错时返回默认值-1
     */
    public int getInteger(Object key) {
        int i = -1;
        try {
            i = Integer.parseInt(get(key).toString());
        } catch (Exception e) {
        }
        return i;
    }

    /**
     * @param name
     * @return
     */
    public int getInt(Object name) {
        return getInt(name, -1);
    }

    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public int getInt(Object name, int defaultValue) {
        Object o = get(name);
        if (o != null) {
            try {
                return StringUtil.getInt(o.toString(), defaultValue);
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

    /**
     * @param which
     * @param defaultValue
     * @return
     */
    public int getInt(int which, int defaultValue) {
        Object key = ordering.get(which);
        return getInt(key, defaultValue);
    }

    /**
     * @param name
     * @return
     */
    public float getFloat(Object name) {
        return Float.valueOf(get(name).toString()).floatValue();
    }

    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public float getFloat(Object name, float defaultValue) {
        Object o = get(name);
        if (o != null)
            try {
                return Float.valueOf(o.toString()).floatValue();
            } catch (Exception e) {
            }
        return defaultValue;
    }

    /**
     * @param which
     * @param defaultValue
     * @return
     */
    public float getFloat(int which, float defaultValue) {
        Object key = ordering.get(which);
        return getFloat(key, defaultValue);
    }

    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public long getLong(Object name, long defaultValue) {
        Object o = get(name);
        if (o != null)
            try {
                return Long.valueOf(o.toString()).longValue();
            } catch (Exception e) {
            }
        return defaultValue;
    }

    /**
     * @param which
     * @return
     */
    public Object get(int which) {
        Object key = ordering.get(which);
        return get(key);
    }

    /**
     * @param which
     * @return
     */
    public Object getKey(int which) {
        Object key = ordering.get(which);
        return key;
    }

    public String[] getKeys() {
        Set keys = this.keySet();
        Iterator iter = keys.iterator();
        String[] strs = new String[keys.size()];
        int i = 0;
        while (iter.hasNext()) {
            strs[i] = iter.next().toString();
            i++;
        }
        return strs;
    }

    /**
     *
     */
    public void dump() {
        for (Iterator e = keySet().iterator(); e.hasNext(); ) {
            String name = (String) e.next();
            Object value = get(name);
            System.out.println(name + "=" + value + ", ");
        }
        System.out.println("");
    }

    public String dumpToString() {
        StringBuffer sb = new StringBuffer();
        for (Iterator e = keySet().iterator(); e.hasNext(); ) {
            String name = (String) e.next();
            Object value = get(name);
            sb.append(value).append(",");
        }
        return sb.toString();
    }

    /*
     * (non-Javadoc) 覆盖了父类的put方法
     *
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object put(Object name, Object value) {
        if ("null".equals(value)) value = "";
        if (!containsKey(name)) {
            ordering.add(name); // 将键保存起来
        }
        super.put(name, value);
        if (functionMap != null && functionMap.containsKey(name))
            functionMap.remove(name);
        return value;
    }

    /**
     * @param name
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public int putInt(Object name, int value) {
        super.put(name, Integer.valueOf(value));
        return value;
    }

    /**
     * @param name
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public float putFloat(Object name, float value) {
        super.put(name, new Float(value));
        return value;
    }

    /**
     * @param name
     * @param value
     * @return
     */
    public String putFunction(String name, String value) {
        if (functionMap == null)
            functionMap = new HashMap<String, String>();
        if (this != null && this.containsKey(name)) {
            // ordering.remove(name);
            remove(name);
        }
        functionMap.put(name, value);
        return value;
    }

    /**
     * @param name
     * @return
     */
    public String getFunction(String name) {
        return functionMap.get(name);
    }

    public Map getFunctionMap() {
        return this.functionMap;
    }

    /**
     * @param fmap
     */
    public void setFunctionMap(HashMap<String, String> fmap) {
        if (fmap != null && fmap.size() > 0) {
            if (functionMap == null)
                functionMap = new HashMap<String, String>();
            this.functionMap.putAll(fmap);
        }
    }

    /*
     * (non-Javadoc) 覆盖了父类的putAll方法
     *
     * @see java.util.Map#putAll(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public void putAll(Map otherMap) {
        Set keySet = otherMap.keySet();
        for (Object name : keySet)
            ordering.add(name);
        super.putAll(otherMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#remove(java.lang.Object)
     */
    public Object remove(Object name) {
        if (ordering.remove(name))
            return super.remove(name);
        return null;
    }

    /*
     * (non-Javadoc) 覆盖了父类的clear方法
     *
     * @see java.util.Map#clear()
     */
    public void clear() {
        super.clear();
        ordering = null;
    }

    /**
     * 得到行对象中字段的个数
     *
     * @return
     */
    public int length() {
        return size();
    }

}