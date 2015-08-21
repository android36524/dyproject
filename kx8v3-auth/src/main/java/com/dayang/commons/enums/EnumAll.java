package com.dayang.commons.enums;

public class EnumAll {
	
	/**
	 * 是否当前学期
	 * @author wjj
	 *
	 */
	public static enum IsYesOrNot {
		IsYes("是",1),IsNot("否",2);
	    private String name;
	    private int value;
	    private IsYesOrNot(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 学期
	 * @author wjj
	 *
	 */
	public static enum SemesterFlag {
	    FIRSTFLAG("上学期",0),SECONDFLAG("下学期",1);
	    private String name;
	    private int value;
	    private SemesterFlag(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 变动标记
	 * @author wjj
	 *
	 */
	public static enum AlterFlag {
	    STUDENTFLAG("学生",2),SCHOOLFLAG("学校",1);
	    private String name;
	    private int value;
	    private AlterFlag(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 变动状态
	 * @author wjj
	 *
	 */
	public static enum AlterStatus {
	    ALTERFINISHSTATUS("变动完成",9),ALTERTINGSTATUS("变动中",1);
	    private String name;
	    private int value;
	    private AlterStatus(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 类描述：学期标识枚举
	 * <pre>
	 * -------------History------------------
	 *   DATE                 AUTHOR         VERSION        DESCRIPTION
	 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
	 * </pre>
	 *
	 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
	 */
	public enum SubjectFlag {
	    COMMONFLAG("通用科目",1),SCHOOLFLAG("学校科目",2);
	    private String name;
	    private int value;
	    private SubjectFlag(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 类描述：学期标识枚举
	 * <pre>
	 * -------------History------------------
	 *   DATE                 AUTHOR         VERSION        DESCRIPTION
	 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
	 * </pre>
	 *
	 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
	 */
	public enum GradeFlag {
	    COMMONFLAG("通用年级",1),SCHOOLFLAG("学校年级",2);
	    private String name;
	    private int value;
	    private GradeFlag(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 申请单标记
	 * @author wjj
	 *
	 */
	public static enum ApproveFlowFlag {
		Teacher("老师",1),Student("学生",2);
	    private String name;
	    private int value;
	    private ApproveFlowFlag(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 申请单条目状态
	 * @author wjj
	 *
	 */  
	public static enum ApproveItemStatus {
		create("已创建",1),pass("通过",2),nopass("不通过",3);
	    private String name;
	    private int value;
	    private ApproveItemStatus(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	  /**
		 * 申请单条目标记
		 * @author wjj
		 *
		*/  
	public static enum ApproveItemFlag {
		apply("申请",1),approve("审批",2),cancel("撤销",4);
	    private String name;
	    private int value;
	    private ApproveItemFlag(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	    	return value;
	    }
		public String getValueStr(){
			return this.value + "";
		}
	}	
	/**
	 * 就读方式
	 * @author wjd
	 *
	 */
	public static enum StudyWays {
		GoRead("走读",1),Residence("住校",2);
	    private String name;
	    private int value;
	    private StudyWays(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 时间节点
	 * @author wjd
	 *
	 */
	public static enum TimeNode {
		Morning("上午",1),Afternoon("下午",2),Night("晚上",3);
	    private String name;
	    private int value;
	    private TimeNode(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 隐藏或者显示
	 * @author wjj
	 *
	 */
	public static enum IsShowOrHidden {
		IsShow("是",1),IsHidden("否",2);
	    private String name;
	    private int value;
	    private IsShowOrHidden(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
	
	/**
	 * 学校办别
	 * */
	public static enum SchProperty{
		SchPublic("公办",21),SchPrivate("民办",22);
		private String name;
	    private int value;
	    private SchProperty(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	         return this.name;
	    }

	    public int getValue() {
	        return value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
	}
}
