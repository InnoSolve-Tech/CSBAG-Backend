package com.cosek.edms.helper;

public class Constants {
    public static final String GENERAL_ROUTE = "/api/v1";

    // User permissions
    public static final String READ_USER = "READ_USER";
    public static final String CREATE_USER = "CREATE_USER";
    public static final String DELETE_USER = "DELETE_USER";
    public static final String UPDATE_USER = "UPDATE_USER";

    // Role permissions
    public static final String READ_ROLE = "READ_ROLE";
    public static final String CREATE_ROLE = "CREATE_ROLE";
    public static final String DELETE_ROLE = "DELETE_ROLE";
    public static final String UPDATE_ROLE = "UPDATE_ROLE";

    // Permission management permissions
    public static final String READ_PERMISSION = "READ_PERMISSION";
    public static final String CREATE_PERMISSION = "CREATE_PERMISSION";
    public static final String DELETE_PERMISSION = "DELETE_PERMISSION";
    public static final String UPDATE_PERMISSION = "UPDATE_PERMISSION";

    // New Dashboard permissions
    public static final String READ_REQUISITION = "READ_REQUISITION";
    public static final String CREATE_REQUISITION = "CREATE_REQUISITION";
    public static final String UPDATE_REQUISITION = "UPDATE_REQUISITION";
    public static final String DELETE_REQUISITION = "DELETE_REQUISITION";

    // New permissions for Bids
    public static final String READ_BIDS = "READ_BIDS";
    public static final String CREATE_BIDS = "CREATE_BIDS";
    public static final String UPDATE_BIDS = "UPDATE_BIDS";
    public static final String DELETE_BIDS = "DELETE_BIDS";

    // New permissions for Conflicts
    public static final String READ_CONFLICTS = "READ_CONFLICTS";
    public static final String CREATE_CONFLICTS = "CREATE_CONFLICTS";
    public static final String UPDATE_CONFLICTS = "UPDATE_CONFLICTS";
    public static final String DELETE_CONFLICTS = "DELETE_CONFLICTS";

    // New permissions for Memos
    public static final String READ_MEMO = "READ_MEMO";
    public static final String CREATE_MEMO = "CREATE_MEMO";
    public static final String UPDATE_MEMO = "UPDATE_MEMO";
    public static final String DELETE_MEMO = "DELETE_MEMO";

    // New permissions for Funds
    public static final String READ_FUNDS = "READ_FUNDS";
    public static final String CREATE_FUNDS = "CREATE_FUNDS";
    public static final String UPDATE_FUNDS = "UPDATE_FUNDS";
    public static final String DELETE_FUNDS = "DELETE_FUNDS";
    // Other constants...
    public static final String USER_ROUTE = GENERAL_ROUTE + "/**";
    public static final String ROLE_ROUTE = GENERAL_ROUTE + "/roles/**";
    public static final String PERMISSION_ROUTE = GENERAL_ROUTE + "/permissions/**";
    public static final String AUTH_ROUTE = GENERAL_ROUTE + "/auth/**";

    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ADMIN_EMAIL = "admin@example.com";
    public static final String ADMIN_PASSWORD = "password123";
    public static final String ADMIN_PHONE = "+256777338787";
    public static final String ADMIN_FIRST_NAME = "Admin";
    public static final String ADMIN_LAST_NAME = "Super";
    public static final String ADMIN_COUNTRY = "Uganda";
    public static final String ADMIN = "ADMIN";
    public static final String OFFICER = "OFFICER";
    public static final String MANAGER = "MANAGER";
    public static final String DEPUTY = "DEPUTY";

    public static final String SUCCESSFUL_DELETION = "Delete Successful";
    public static final String FAILED_DELETION = "Delete Failed";
}
