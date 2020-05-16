package com.hl.common.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量类
 */
public class Constants {

    //分隔符
    public static final String BAR = "-";
    public static final String PROJECT_TEAM_NAME_SPLIT = "-->";
    public static final String PROJECT_TEAM_IDS_SPLIT=",";
    //登陆者信息的Key
    public static final String LOGINERKEY = "loginer";
    //登陆者的JWT的Key
    public static final String AUTHORIZATION = "Authorization";
    //通用是否字符串
    public static final String YESSTR = "1";
    public static final String NOSTR = "0";
    //通用是否整形
    public static final int YES = 1;
    public static final int NO = 0;
    //默认页大小
    public static final int PAGESIZE = 20;

    //特殊日期类型
    public static final String DIC_NAME_SPE_DATE_TYPE="spe_date_type";

    public static final String TASK_FLOW="task_flow";
    public static final String EDIT_NODE="edit_node";
    public static final String MONTH="MONTH";
    public static final String DAY="DAY";
    public static final String TIME_UINT="time_unit";
    public static final String YES_STR="1";
    public static final String NO_STR="0";
    public static final String DEFAULT_USERPASS = "888888";

    public static final String PATH = "path";
    public static final String BACKUP_PATH = "/WEB-INF/buckup";

    public static final String corp_dictionary_name = "公司名称";
    public static final String worksys_dictionary_name = "工时制度";
    public static final String worksys_dictionary_name1 = "标准工时";
    public static final String worksys_dictionary_name2 = "不定时制";
    public static final String work_date_spe_dictionary_name = "特殊日期";

    public static final String DINGDING_CHECKTYPE_OffDuty = "OffDuty";
    public static final String DINGDING_CHECKTYPE_OnDuty = "OnDuty";

    public static final String DATE_PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN2 = "yyyyMMdd";
    public static final String DATE_PATTERN3 = "yyyy-MM-dd";
    public static final String DATE_PATTERN4 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final Map<String, String> timeResultMap = new HashMap<String, String>();
    public static final Map<String, String> locationResultMap = new HashMap<String, String>();

    public static final String CORP1_NAME = "广州市原象广告有限公司";
    public static final String CORP2_NAME = "广州市原象电子商务有限公司";

    public static final String leave_type1 = "病假";
    public static final String leave_type2 = "事假";
    public static final String leave_type3 = "调休";
    public static final String leave_type4 = "年假";
    public static final String leave_type5 = "产假";
    public static final String leave_type6 = "陪产假";
    public static final String leave_type7 = "婚假";
    public static final String leave_type8 = "福利假（限不定时员工）";
    public static final String leave_type9 = "其他";

    public static final String onduty_location_normal = "Normal";
    public static final String onduty_location_outside = "Outside";
    public static final String onduty_location_notsigned = "NotSigned";

    public static final String offduty_location_normal = "Normal";
    public static final String offduty_location_outside = "Outside";
    public static final String offduty_location_notsigned = "NotSigned";

    public static final String onduty_time_result_normal = "Normal";
    public static final String onduty_time_result_late = "Late";
    public static final String onduty_time_result_seriouslate = "SeriousLate";
    public static final String onduty_time_result_notsigned = "NotSigned";

    public static final String USER_TABLE_SHEET_NAME1 = "在职";
    public static final String USER_TABLE_SHEET_NAME2 = "离职";

    public static final String DEPARTMENT_FULLNAME_INTERVAL = "-->";
    public static final String DEPARTMENT_PIDS_INTERVAL = ",";


    public static final String employe_type = "员工类型";
    public static final String employe_type_en = "employe_type";
    public static final String employee_identity = "员工身份";
    public static final String employee_identity_en = "employee_identity";
    public static final String marital_status = "婚姻状况";
    public static final String marital_status_en = "marital_status";
    public static final String politics_status = "政治面貌";
    public static final String politics_status_en = "politics_status";
    public static final String id_type = "证件类型";
    public static final String id_type_en = "id_type";
    public static final String id_type_idCard = "身份证";
    public static final String sex = "性别";
    public static final String sex_en = "sex";
    public static final String household_register = "户口性质";
    public static final String household_register_en = "household_register";
    public static final String state_employees = "员工状态";
    public static final String state_employees_en = "state_employees";
    public static final String type = "员工类型";
    public static final String type_en = "type";
    public static final String level_position = "职称等级";
    public static final String level_position_en = "level_position";
    public static final String work_schedules = "工时制度";
    public static final String work_schedules_en = "work_schedules";
    public static final String high_record = "最高学历";
    public static final String high_record_en = "high_record";
    public static final String dingdingDynchronization = "钉钉同步";
    public static final String dingdingDynchronization_en = "dingdingDynchronization";
    public static final String resource_dic_name = "资源类型";
    public static final String resource_dic_name_en = "resource_type";

    public static final String attachment = "附件";
    public static final String attachment_en = "attachment";
    public static final String attachment_idCard = "身份证";
    public static final String attachment_xueLi = "学历证书";
    public static final String attachment_resume = "简历";
    public static final String attachment_bankCard = "银行卡";
    public static final String attachment_otherInfo = "其他资料";

    public static final String contract_type = "合同类型";

    public static final String userCenterAppName = "userCenter";

    public static final String operlog_target_user = "员工";
    public static final String operlog_target_department = "部门";
    public static final String operlog_target_projectteam = "项目组";
    public static final String operlog_target_position = "职位";

    public static final String operlog_type_add = "新增";
    public static final String operlog_type_update = "修改";
    public static final String operlog_type_delete = "删除";
    public static final String operlog_type_cancelManager = "取消负责人";
    public static final String operlog_type_setManager = "设置负责人";
    public static final String operlog_type_addGroup = "加入项目组";
    public static final String operlog_type_leaveGroup = "离开项目组";

    public static final Map<String, String> userFieldMap = new HashMap<String, String>();
    public static final Map<String, String> deptFieldMap = new HashMap<String, String>();
    public static final Map<String, String> projecTeamFieldMap = new HashMap<String, String>();

    public static final List<String> beisen_position_columns = new ArrayList<String>();
    public static final List<String> beisen_organization_columns = new ArrayList<String>();
    public static final List<String> beisen_userinfo_columns = new ArrayList<String>();

    public static final String DIC_TYPE_SYNC_BEISEN = "sync_beisen";
    public static final String DIC_KEY_PROCESS_REPORT_RELATION = "process_report_relation";


    public static final String dingtalk_notice_type = "dingtalk";

    public static final String PROJECT_TEAM="PROJECT_TEAM";
    public static final String DEPARTMENT="DEPARTMENT";
    public static final String TRANSFER_TYPE_EXIT="EXIT";
    public static final String TRANSFER_TYPE_JOIN="JOIN";
    public static final String TRANSFER_TYPE_CHANGE="CHANGE";


    //全角空格
    public static final String FULL_ANGLE_SPACE="　";
    public static final String HALF_ANGLE_SPACE=" ";

    //日程安排
    public static final String SPE_DATE_WORK="上班";

    //文件导入后缀
    public static final List<String> EXCEL_SUFFIX = new ArrayList<>();


    static{
        beisen_userinfo_columns.add("Name");//姓名
        beisen_userinfo_columns.add("EngName");//英文名
        beisen_userinfo_columns.add("Email");//电子邮件
        beisen_userinfo_columns.add("BackupMail");//个人邮箱
        beisen_userinfo_columns.add("IDNumber");//身份证号
        beisen_userinfo_columns.add("PassportNumber");//护照号码
        beisen_userinfo_columns.add("private Date Birthday");//出生日期
        beisen_userinfo_columns.add("Agedouble") ;//年龄
        beisen_userinfo_columns.add("Gender");//性别
        beisen_userinfo_columns.add("Nation");//民族
        beisen_userinfo_columns.add("PoliticalStatus");//政治面貌
        beisen_userinfo_columns.add("MarryCategory");//婚姻状况
        beisen_userinfo_columns.add("LastSchool");//毕业学校
        beisen_userinfo_columns.add("Major");//专业
        beisen_userinfo_columns.add("Nationality");//国籍
        beisen_userinfo_columns.add("Birthplace");//户籍所在地
        beisen_userinfo_columns.add("RegistAddress");//籍贯(参照)
        beisen_userinfo_columns.add("PostalCode");//邮政编码
//		beisen_userinfo_columns.add("HomeAddress");//联系地址,暂不同步
        beisen_userinfo_columns.add("EducationLevel");//最高学历
        beisen_userinfo_columns.add("GraduateDatedatetime") ;//毕业时间
        beisen_userinfo_columns.add("BusinessAddress");//办公地址
        beisen_userinfo_columns.add("MobilePhone");//手机号码
        beisen_userinfo_columns.add("DomicileType");//户口性质
        beisen_userinfo_columns.add("WorkEmail");//电子邮件（工作）
        beisen_userinfo_columns.add("ResidenceAddress");//户口地址
        beisen_userinfo_columns.add("Region");//区域
        beisen_userinfo_columns.add("IsDeleted");//是否删除
        beisen_userinfo_columns.add("IDType");//证件类型
        beisen_userinfo_columns.add("extdef1_107390_356821570");//外籍证件号码
        beisen_userinfo_columns.add("extdef2_107390_611624203");//银行卡号
        beisen_userinfo_columns.add("extdef4_107390_1314224049");//社保号
        beisen_userinfo_columns.add("extdef10_107390_581331150");//籍贯
        beisen_userinfo_columns.add("extzwmc_107390_1008718094");//中文名称
        beisen_userinfo_columns.add("UserID");//人员
        beisen_userinfo_columns.add("OIdDepartment");// 部门
        beisen_userinfo_columns.add("OIdJobPosition");//职位
        beisen_userinfo_columns.add("OidJobGrade") ;//职等
        beisen_userinfo_columns.add("Place") ;// 工作地点
        beisen_userinfo_columns.add("EntryDate") ;//  入职日期
        beisen_userinfo_columns.add("StopDate") ;// 失效日期
        beisen_userinfo_columns.add("EmployeeStatus") ;//人员状态
        beisen_userinfo_columns.add("RegularizationDate") ;// 转正日期
        beisen_userinfo_columns.add("IsCharge") ;// 是否部门负责人
        beisen_userinfo_columns.add("ChangeTypeOID");// 变动类型
        beisen_userinfo_columns.add("ChangeReason") ;//变动原因
        beisen_userinfo_columns.add("LastWorkDate");//最后工作日
        beisen_userinfo_columns.add("UserID");//用户id
        beisen_userinfo_columns.add("extgszd_107390_949005602");//工时制度
        beisen_userinfo_columns.add("POIdEmpAdmin");//直线经理
        beisen_userinfo_columns.add("POIdEmpReserve2");//虚线经理
        beisen_userinfo_columns.add("EmploymentForm");//用工形式
        beisen_userinfo_columns.add("extcwzz_107390_200800709");//员工身份
        beisen_userinfo_columns.add("JobNumber");//员工工号

        timeResultMap.put("Normal", "正常");
        timeResultMap.put("Early", "早退");
        timeResultMap.put("Late", "迟到");
        timeResultMap.put("SeriousLate", "严重迟到");
        timeResultMap.put("NotSigned", "未打卡");

        locationResultMap.put("Normal", "范围内");
        locationResultMap.put("Outside", "范围外");
        locationResultMap.put("NotSigned", "未打卡");

        userFieldMap.put("user_id", "用户id");
        userFieldMap.put("user_cname", "中文姓名");
        userFieldMap.put("user_ename", "英文名字");
        userFieldMap.put("employee_number", "员工编号");
        userFieldMap.put("native_place", "籍贯");
        userFieldMap.put("nationality", "国籍");
        userFieldMap.put("wages_card", "工资卡");
        userFieldMap.put("birth_date", "出生日期");
        userFieldMap.put("marital_status", "婚姻状况");
        userFieldMap.put("politics_status", "政治面貌");
        userFieldMap.put("id_type", "证件类型");
        userFieldMap.put("id_number", "证件号码");
        userFieldMap.put("social_security_no", "社保号");
        userFieldMap.put("sex", "性别");
        userFieldMap.put("work_place", "工作地");
        userFieldMap.put("national", "民族");
        userFieldMap.put("household_register", "户口性质");
        userFieldMap.put("employee_identity", "员工身份");
        userFieldMap.put("high_record", "最高学历");
        userFieldMap.put("professional", "专业");
        userFieldMap.put("graduate_chool", "毕业院校");
        userFieldMap.put("work_schedules", "工时制度");
        userFieldMap.put("work_schedules_change_date", "转工时日期");
        userFieldMap.put("state_employees", "员工状态");
        userFieldMap.put("type", "员工类型");
        userFieldMap.put("level_position", "职称等级");
        userFieldMap.put("hiredate", "入职时间");
        userFieldMap.put("conversion_date", "转正日期");
        userFieldMap.put("dimission_date", "离职日期");
        userFieldMap.put("service_length", "工龄");
        userFieldMap.put("age", "年龄");
        userFieldMap.put("department", "部门");
        userFieldMap.put("department_role", "部门角色（1代表部门负责人，0代表部门成员）");
        userFieldMap.put("position", "职位");
        userFieldMap.put("registered_permanent_residence", "户口");
        userFieldMap.put("address", "现住址");
        userFieldMap.put("email", "电子邮件");
        userFieldMap.put("phone", "手机号码");

        deptFieldMap.put("department_id", "部门id");
        deptFieldMap.put("Name", "部门名称");
        deptFieldMap.put("fullname", "部门全名");
        deptFieldMap.put("phone", "部门电话");
        deptFieldMap.put("fax_number", "部门传真");
        deptFieldMap.put("parent_id", "部门上一级id");
        deptFieldMap.put("parent_ids", "部门所有上级id");

        projecTeamFieldMap.put("id", "项目组id");
        projecTeamFieldMap.put("team_name", "项目组名称");
        projecTeamFieldMap.put("team_full_name", "项目组全名");
        projecTeamFieldMap.put("parent_id", "项目组上一级id");
        projecTeamFieldMap.put("parent_ids", "项目组所有上级id");

        beisen_position_columns.add("Code");
        beisen_position_columns.add("Name");
        beisen_position_columns.add("OId");

        beisen_organization_columns.add("OId");
        beisen_organization_columns.add("Name");
        beisen_organization_columns.add("POIdOrgAdmin");
        beisen_organization_columns.add("POIdOrgReserve2");
        beisen_organization_columns.add("IsVirtualOrg");
        beisen_organization_columns.add("PersonInCharge");
        beisen_organization_columns.add("Status");
        beisen_organization_columns.add("IsDeleted");



        EXCEL_SUFFIX.add("xlsx");
        EXCEL_SUFFIX.add("xls");
    }
    //系统默认角色admin
    public static final String ADMIN = "admin";

    /**
     * 载入设置
     */
    public static void loadSetting() {
    }

}
