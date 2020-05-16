package com.hl.common.constants;

import com.hl.common.util.Formatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * pagesize一定要有值，目前有默认值，而且一定要和baseserviceimp里的值保持一致，并且优先于pageIndex设置，方便offset取到正确值
 *
 * @author ivan.huang
 * @date 2016-8-2
 * @date date 封版本 改condition之后
 */

@Component
@Scope("prototype")
@Slf4j
public class QueryParam {

    private String queryCondition;
    private String orderField;
    private boolean like = true;   //默认模糊查询
    private boolean isAnd = true;   //默认各条件  and 否则 or
    private Integer limitOffSet;
    private Integer pageIndex = 1; //start with 1;

    @Value("${pageSize}")
    private Integer pageSize;//默认页大小 //配置值优先级高于这个=10

    private String orderDirection;
    private String orderFieldListStr;

    //for senior jquery
    private String searchAnds;
    private String searchColumnNames;
    private String searchConditions;
    private String searchVals;

    //for shoppingcart
    private String temp;

    /**
     *
     */
    public QueryParam() {
    }

    /**
     * @param queryCondition
     */
    public QueryParam(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    /**
     * @param searchAnds
     * @param searchColumnNames
     * @param searchConditions
     * @param searchVals
     */
    public QueryParam(String searchAnds, String searchColumnNames, String searchConditions, String searchVals) {
        this.searchAnds = "[" + searchAnds + "]";
        this.searchColumnNames = "[" + searchColumnNames + "]";
        this.searchConditions = "[" + searchConditions + "]";
        this.searchVals = "[" + searchVals + "]";
    }

    /**
     * @param orderFiled
     * @param orderDirection
     * @param searchAnds
     * @param searchColumnNames
     * @param searchConditions
     * @param searchVals
     */
    public QueryParam(String orderFiled, String orderDirection, String searchAnds, String searchColumnNames, String searchConditions, String searchVals) {
        this.orderField = orderFiled;
        this.orderDirection = orderDirection;
        this.searchAnds = "[" + searchAnds + "]";
        this.searchColumnNames = "[" + searchColumnNames + "]";
        this.searchConditions = "[" + searchConditions + "]";
        this.searchVals = "[" + searchVals + "]";
    }

    public static void main(String[] args) {
//	    String ssString=" or yxxx";
//	    System.out.println(ssString.trim().substring(2));
//	    System.out.println("[xaxaaaa]xx[rr]eee[xxx]rr".replace("[", " ").replace("]", " "));
    }

    /**
     * @return the orderFieldListStr
     */
    public String getOrderFieldListStr() {
        return orderFieldListStr;
    }

    /**
     * @param orderFieldListStr the orderFieldListStr to set
     */
    public void setOrderFieldListStr(String orderFieldListStr) {
        this.orderFieldListStr = orderFieldListStr;
    }

    /**
     * @return the isAnd
     */
    public boolean isAnd() {
        return isAnd;
    }

    /**
     * @param isAnd the isAnd to set
     */
    public void setAnd(boolean isAnd) {
        this.isAnd = isAnd;
    }

    /**
     * @return the like
     */
    public boolean isLike() {
        return like;
    }

    /**
     * @param like the like to set
     */
    public void setLike(boolean like) {
        this.like = like;
    }

    /**
     * @return the limitOffSet
     */
    public Integer getLimitOffSet() {
        return limitOffSet;
    }

    /**
     * @param limitOffSet the limitOffSet to set
     */
    public void setLimitOffSet(Integer limitOffSet) {
        this.limitOffSet = limitOffSet;
    }

    /**
     * @return the pageIndex
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * @param pageIndex the pageIndex to set
     */
    public void setPageIndex(Integer pageIndex) {
        if (pageIndex < 1) {
            this.pageIndex = 1;
        } else {
            this.pageIndex = pageIndex;
        }
        if (pageSize == null) {
            //防止绑定异常。10可以读数据库配置或者
            limitOffSet = (pageIndex - 1) * 10;
        } else {
            limitOffSet = (pageIndex - 1) * pageSize;
            // limit 0,10,第一页 的10个record//pagesize一定要有值，并且先设置
        }
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getOrderFiled() {
        return orderField;
    }

    public void setOrderFiled(String orderFiled) {
        this.orderField = orderFiled;
    }

    public String getOrderDirection() {
        if (orderDirection == null && orderField != null) {
            this.orderDirection = "asc";
        }
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getSearchAnds() {
        return searchAnds;
    }

    public void setSearchAnds(String searchAnds) {
        this.searchAnds = searchAnds;
    }

    public String getSearchColumnNames() {
        return searchColumnNames;
    }

    public void setSearchColumnNames(String searchColumnNames) {
        this.searchColumnNames = searchColumnNames;
    }

    public String getSearchConditions() {
        return searchConditions;
    }

    public void setSearchConditions(String searchConditions) {
        this.searchConditions = searchConditions;
    }

    public String getSearchVals() {
        return searchVals;
    }

    public void setSearchVals(String searchVals) {
        this.searchVals = searchVals;
    }

    /**
     * @param and_or   and 、or
     * @param column   # or non '#'
     * @param eq_lt_gt =、> 、<、<>、like
     * @param value
     */
    public void setMyCondition(String and_or, String column, String eq_lt_gt, String value) {
        if (getSearchAnds() != null) {
            setSearchAnds(getSearchAnds() + ",[" + and_or + "]");
        } else {
            setSearchAnds("[" + and_or + "]");
        }
        if (getSearchColumnNames() != null) {
            setSearchColumnNames(getSearchColumnNames() + "," + column);
        } else {
            setSearchColumnNames(column);
        }
        if (getSearchConditions() != null) {
            setSearchConditions(getSearchConditions() + ",[" + eq_lt_gt + "]");
        } else {
            setSearchConditions("[" + eq_lt_gt + "]");
        }
        if (getSearchVals() != null) {
            setSearchVals(getSearchVals() + "," + value);
        } else {
            setSearchVals(value);
        }
    }

    /**
     * @param t 反射来构建sql
     */
    public void rebuildCondition(Object t) {
        if (t == null) {
            return;
        }
        StringBuffer sbf = new StringBuffer();
        // 得道给定实例的类型。
        Class<?> theClass = t.getClass();
        // 使用类名替换Object字符串。
//        sbf.replace(5, 11, theClass.getSimpleName());
        Field[] fields = t.getClass().getDeclaredFields();

        String[] ignoreFields = {"logger", "foundTimeCostStr", "positioningTimeCostStr", "resolvingTimeCostStr", "affectingTimeCostStr", "solutionMakingTimeCostStr", "solutionExecTimeCostStr"};
        // 遍历所有属性
        for (int i = 0; i < fields.length; i++) {
            PropertyDescriptor pd = null;
            Field field = fields[i];
            try {
                boolean isIgnoreField = false;
                for (int j = 0; j < ignoreFields.length; j++) {
                    if (field.getName().equals(ignoreFields[j])) {
                        isIgnoreField = true;
                        break;
                    }
                }

                if (isIgnoreField) {
                    continue;
                }

                pd = new PropertyDescriptor(field.getName(), theClass);
                // 获得所有属性的读取方法
                Method getMethod = pd.getReadMethod();
                // 执行读取方法，获得属性值
                Object objTemp = getMethod.invoke(t);
                // 如果属性值为null，就略过
                if (Formatter.isEmpty(objTemp)) {
                    continue;
                } else if (objTemp instanceof String) {
                    if (StringUtils.isBlank((String) objTemp)) continue;
                }

                //test:
                log.info("----->" + i + field.getType().getName() + "::::::::" + field.getName() + ":::::值:" + objTemp);

                String or_and = isAnd() ? " and " : " or ";

                //先处理时间格式
                String dateTypeStr = "java.lang.DateTime,java.lang.Date,java.util.Date";
                if (dateTypeStr.contains(field.getType().getName())) {
                    objTemp = Formatter.parseDate2Str((Date) objTemp);
                }
                // 如果不为空。
                // 判断是否开启模糊查询，添加查询条件,并且加上%%符号。
                if (isLike()) {
                    sbf.append(or_and + Formatter.fieldToCulumn(field.getName()) + " like '%" + objTemp + "%'");
                }//同理添加查询条件，不添加%%符号。
                else {
                    String fieldType = "java.lang.String,java.lang.DateTime,java.lang.Date";

                    if (fieldType.contains(field.getType().getName())) {
                        sbf.append(or_and + field.getName() + "='" + objTemp + "'");
                    } else {
                        sbf.append(or_and + field.getName() + "=" + objTemp + "");
                    }

                }
            } catch (IllegalAccessException e) {
//                log.error("",e);
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
//                log.error("",e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
//                log.error("",e);
            } catch (IntrospectionException e) {
                e.printStackTrace();
//                log.error("",e);
            }
        }
        if (this.queryCondition == null) {
            this.queryCondition = "";
        }
        this.queryCondition += sbf.toString();
    }

    /**
     * 根据数组来build
     */
    public void rebuildCondition() {
        String spliter = ",";
        if (!StringUtils.isBlank(searchAnds)) {
            String[] searchAndsArr = searchAnds.split(spliter);
            String[] searchColumnNamesArr = searchColumnNames.split(spliter);
            String[] searchConditionsArr = searchConditions.split(spliter);
            String[] searchValsArr = searchVals.split(spliter);
            if (queryCondition == null) {
                queryCondition = "";
            }
            for (int i = 0; i < searchValsArr.length; i++) {
                //先处理like 的 value值
                if ("[like]".equals(searchConditionsArr[i])) {
                    searchValsArr[i] = "'%" + searchValsArr[i] + "%'";
                }
                //再处理in exists
                else if ("[in]".equals(searchConditionsArr[i]) || "[not in]".equals(searchConditionsArr[i]) || "[exists]".equals(searchConditionsArr[i]) || "[not exists]".equals(searchConditionsArr[i])) {
                    searchValsArr[i] = "(" + searchValsArr[i] + ")";
                }
                //最后处理字符型和数字型
                else {//= ' ' >' '
                    //字符
                    if (searchColumnNamesArr[i].startsWith("#")) {
                        searchValsArr[i] = "'" + searchValsArr[i] + "'";
                    }
                }
                //字段名字的#去掉
                searchColumnNamesArr[i] = searchColumnNamesArr[i].replace("#", "");
                queryCondition += searchAndsArr[i] + searchColumnNamesArr[i] + searchConditionsArr[i] + searchValsArr[i];
            }

            this.queryCondition = queryCondition.replace("[", " ").replace("]", " ").replace("|", ",");
            System.out.println("queryCondition:[" + queryCondition + "]");
        }
    }

    public void rebuildConditionReflectByConditionArr(Object t) {
        String dateTypeStr = "java.lang.DateTime,java.lang.Date,java.util.Date";
        String stringTypeStr = "java.lang.String,java.lang.DateTime,java.lang.Date,java.util.Date";
        if (t == null) {
            return;
        }
        StringBuffer sbf = new StringBuffer();
        // 得道给定实例的类型。
        Class<?> theClass = t.getClass();
        // 使用类名替换Object字符串。
//      sbf.replace(5, 11, theClass.getSimpleName());
        Field[] fields = t.getClass().getDeclaredFields();

        String spliter = ",";
        if (!StringUtils.isBlank(searchAnds)) {
            String[] searchAndsArr = searchAnds.split(spliter);
            String[] searchColumnNamesArr = searchColumnNames.split(spliter);
            String[] searchConditionsArr = searchConditions.split(spliter);
            String[] searchValsArr = searchVals.split(spliter);
            if (queryCondition == null) {
                queryCondition = "";
            }

            //这里处理：
            // 遍历所有属性
            for (int i = 0; i < fields.length; i++) {
                PropertyDescriptor pd = null;
                Field field = fields[i];

                for (int j = 0; j < searchColumnNamesArr.length; j++) {
                    if (field.getType().getName().equals(searchColumnNamesArr[j])) {
                        //找到字段
                        String objTemp = searchValsArr[j];
//                        if(dateTypeStr.contains(field.getType().getName()))
//                        {
//                            if(searchAndsArr[j].toLowerCase().trim().equals("like"))
//                            {
//                                objTemp="'%" + objTemp + "%'";
//                            }
//                            else
//                            {
//                                objTemp="'" + objTemp + "'";
//                            }
//                        }
//                        else
                        if (stringTypeStr.contains(field.getType().getName())) {
                            if (searchAndsArr[j].toLowerCase().trim().equals("like")) {
                                objTemp = "'%" + objTemp + "%'";
                            } else {
                                objTemp = "'" + objTemp + "'";
                            }
                        } else {
                            //数字
                            if (searchAndsArr[j].toLowerCase().trim().equals("like")) {
                                objTemp = "'%" + objTemp + "%'";
                            }
                        }

                        sbf.append(" " + searchAndsArr[j] + Formatter.fieldToCulumn(field.getName()) + searchConditionsArr[j] + objTemp);
                        break;
                    }
                }


            }

            if (this.queryCondition == null) {
                this.queryCondition = "";
            }
            this.queryCondition += sbf.toString();

            System.out.println("queryCondition:[" + queryCondition + "]");
        }
    }

    public String toString() {
        // MULTI_LINE_STYLE
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true) + "\n";
    }

}
