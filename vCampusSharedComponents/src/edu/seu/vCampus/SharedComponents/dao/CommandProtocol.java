/**
 * ��������뺬���Ӧ��ϵ����
 * @author Wenyu, Shangfu Duan, Yi Yang
 * @version 3.0
 */

package edu.seu.vCampus.SharedComponents.dao;

public class CommandProtocol {
    // ״̬����
    public static final String ERROR = "error",
            SUCCESS = "success",
            UNKNOWN_CMD = "unknown_command",
            NO_CMD = "no_command",
            CRED_ERROR = "cred_error",
            LOGGED_IN = "logged_in",
            FAIL_CONN ="fail_connect", // ����ʧ�ܱ�־
            EMPTY_INS="empty_insert", // δ�����ʺ������־
            NO_PREV = "no_prev", // Ȩ�޲���
            FORMAT_ERROR = "format_error";
    
    // ָ�����
    public static final String CMD_LOGIN = "login",
            CMD_REG = "register",
            CMD_LOGOUT = "logout",
            CMD_CHANGE_PWD = "change_pwd",
            CMD_GET_UID = "get_uid",
            CMD_GET_NAME = "get_name";
    public static final String CMD_SCANUSERINFO = "scan_userinfo",
    		CMD_ADDUSERINFO = "add_userinfo";
    
    // ��������
    public static final int ROLE_SYS_ADMIN = 0,
            ROLE_FACAULTY_ADMIN = 1,
            ROLE_TEACHER = 2,
            ROLE_STUDENT = 3,
            ROLE_FINANCIAL_ADMIN = 4,
            ROLE_LIBRARIAN = 5,
            ROLE_SHOPOWNER = 6;
    
    //�̵�ָ�����
    public static final String CMD_SCANGOODS = "scan_goods",
            CMD_SCANGOODSWITHUID = "scan_goodswithuid",
            CMD_SCANGOODSWITHGID = "scan_goodswithgid",
            CMD_SCANGOODSWITHNAME = "scan_goodswithname",
            CMD_SCANGOODSWITHTYPE = "scan_goodswithtype",
            CMD_ADDGOOD = "add_good",
            CMD_DELETEGOOD = "delete_good",
            CMD_UPDATEGOOD = "update_good",
            CMD_SCANORDERBWITHOUT = "scan_orderwithout",
            CMD_SCANORDERMWITHUIDB = "scan_order_b",
            CMD_SCANORDERMWITHUIDM = "scan_order_m",
            CMD_ADDORDER = "add_order",
            CMD_DELETEORDER = "delete_order",
            CMD_UPDATEORDER = "update_order",
            CMD_CREATEORDER = "create_order",
            BALANCE_NOT_ENOUGH = "balance_not_enough",
            STOCK_NOT_ENOUGH = "stock_not_enough";
    
    //ѧ��ѧ��ģ��ָ�����
    public static final String CMD_SCANSTUINFO = "scan_stuinfo",
    		CMD_SCANSTUINFOWITHUID = "scan_stuinfowithuid",
    		CMD_ADDSTUINFO = "add_stuinfo",
    		CMD_DELETESTUINFO = "delete_stuinfo",
    		CMD_UPDATESTUINFO = "update_stuinfo",
    		CMD_SCANGRADE = "scan_grade",
    		CMD_SCANGRADEWITHUID = "scan_gradewithuid",
    		CMD_SCANGRADEWITHCID = "scan_gradewithcid",
    		CMD_ADDGRADE = "add_grade",
    		CMD_DELETEGRADE = "delete_grade",
    		CMD_UPDATEGRADE = "update_grade";
    
    // �û�ģ��ָ�����
    public static final String REG_BEFOR = "reg_before",
            NONEXIST_STUNUM = "nonexist_stunum";
    
    // �γ̹���ָ�����
    public static final String C_ADD = "course_add",
            C_MODIFY = "course_modify",
            C_DELETE = "course_delete",
            C_LOOKUP = "course_lookup",
            C_LOOKUPTID = "course_lookup_tId",
            C_LOOKUP_MY = "course_lookup_forme";
    
    // ѡ��ָ�����
    public static final String CS_ADD = "csel_add",
            CS_DELETE = "csel_delete",
            CS_VIEWALL = "csel_viewall",
            CS_UNAVAI = "csel_unavailable",
            CS_EXCEED = "csel_exceeded",
            CS_MANDOTARY = "csel_mandatory";
    public static final String CSADMIN_ADD = "csel_admin_add",
            CSADMIN_DELETE = "csel_admin_delete",
            CSADMIN_VIEWALL = "csel_admin_viewall",
            CSADMIN_VIEWALL_BY_CLASS = "csel_admin_viewall_bycls";
    
    // ͼ�����ָ�����
    public static final String B_ADD = "book_add",
            B_MODIFY = "book_modify",
            B_DELETE = "book_delete",
            B_LOOKUP = "book_lookup",
            B_LOOKUP_MY = "book_lookup_forme";
    
    // ͼ�����ָ�����
    public static final String BB_ADD = "bb_add",
            BB_DELETE = "bb_delete",
            BB_VIEW = "bb_view",
            BB_VIEW_UID = "bb_view_uid",
            BB_VIEW_BID = "bb_view_bid",
            BB_UNAVAI = "bb_unavailable",
            BB_EXCEED = "bb_exceeded";
    
    // �̵�ָ�����
    public static final String NONEXIST_TRANID = "noneexist_transactionID", // �޴�����ID(�ڽ�����������ӵ�ID)
            NONEXIST_OUTID = "noneexist_outID", // �޴�֧��ID
            NO_REC = "no_record", // �޽��׼�¼
            NODATA = "no_data", // ������
            ARITH_ERROR = "arithematic_error", // ת��������(���С��0)
            CMD_TRAN = "transfer", // ת��
            CMD_QUERY = "query", // ��ѯ
            CMD_VIEW = "view",
            CMD_CHARGE = "charge";
}
