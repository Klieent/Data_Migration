package org.example;

import org.example.DataMigration.ConvertToSqlQuery.mst_bank.service.BankService;
import org.example.DataMigration.Tables.Bank;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        BankService bankService = context.getBean(BankService.class);

        String url = "jdbc:mysql://localhost:3306/aeon"; // Replace with your DB URL
        String user = "root"; // Replace with your DB username
        String password = "#Klient123"; // Replace with your DB password

        accountMasterSql(url, user, password);
        bankMasterSql(url, user, password);
        creditCardMasterSql(url, user, password);
        cashMasterSql(url, user, password);
        priceMasterSql(url, user, password);
        priceLevelSql(url, user, password);
        tenderTypeSql(url, user, password);
        companyMasterSql(url, user, password);
        userMaster(url, user, password);
        companyLoginSql(url, user, password);
        companyLoginWarehouseSelectionSql(url, user, password);
        companyLoginBranchSelectionSql(url, user, password);
        accessLevelMasterSql(url, user, password);
        uomSql(url, user, password);
        uomConversionSql(url, user, password);
        regionMasterSql(url, user, password);
        branchMasterSql(url, user, password);
        warehouseMasterSql(url, user, password);
        giftCertTypeMasterSql(url, user, password);
        giftCertificateMasterSql(url, user, password);
        cancelReasonMasterSql(url, user, password);
        groupTypeMasterSql(url, user, password);
        nationalityMasterSql(url, user, password);
        productHierarchySql(url, user, password);
        productCategorySql(url, user, password);
        productMasterSql(url, user, password);
        priceListProductSql(url, user, password);
        stockLedgerSql(url, user, password);

        terminalMasterSQL(url, user, password);
        tmHardwareSql(url, user, password);
        tmBasicSettingSql(url, user, password);
        TmHardwareReceiptSql(url, user, password);
        TmHardwareTransferSql(url, user, password);
        TmHardwareStockCountSql(url, user, password);
        TmHardwareCountSheetSql(url, user, password);
        TmHardwareAdjustment(url, user, password);
        TmReceipt(url, user, password);
        TmReadingSql(url, user, password);
        TmTenantSql(url, user, password);
        TmComputationSql(url, user, password);
        updateTmHardwareSql(url,user,password);
        updateTerminalMasterSQL(url,user,password);
        licenseSQL(url,user,password);
//        readingSQL(url,user,password);
        securityAccessSQL(url,user,password);

        auditTrailSql(url,user,password);
    }

    @Deprecated
    private static void fieldNullChecker(String fieldValue, String fieldName, StringBuilder sqlBuilder, boolean isLast) {
        if (fieldValue != null) {
            sqlBuilder.append(fieldName);
            if (!isLast) {
                sqlBuilder.append(", ");
            }
        }
    }

    @Deprecated
    private static void migrateMstBank(BankService bankService) {
        List<Bank> banks = bankService.getBankById();

        // Specify the file path where you want to save the SQL statements
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\BankMaster.sql";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Bank bank : banks) {
                String sql = String.format("INSERT INTO `BankMaster` (`id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, " + "`bankId`, `isActive`, `isDeleted`, `memo`, `name`, `companyId`) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%d', '%d', '%s', '%s', '%s');", bank.getCreatedBy(), convertDate(bank.getCreatedDate()), bank.getUpdatedBy(), convertDate(bank.getUpdatedDate()), bank.getBankId(), bank.getActiveFlag() ? 1 : 0, 0, bank.getMemo(), bank.getName(), bank.getCompanyId());

                // Write the SQL statement to the file
                writer.write(sql);
                writer.newLine(); // Add a new line for the next statement
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions
        }
    }


    private static void companyMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CompanyMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_company"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String companyId = resultSet.getString("fcompanyid");
                    String name = resultSet.getString("fname");
                    boolean isActive = resultSet.getBoolean("factive_flag");
                    String address1 = resultSet.getString("faddress1");
                    String address2 = resultSet.getString("faddress2");
                    String address3 = resultSet.getString("faddress3");
                    String telNo = resultSet.getString("ftelno");
                    String memo = resultSet.getString("fmemo");
                    String manageBy = resultSet.getString("fuserid");
                    String tinNo = resultSet.getString("ftinno");
                    BigDecimal salesTax = resultSet.getBigDecimal("fvat");
                    String taxType = resultSet.getString("ftax_type");
                    String productTypeId = resultSet.getString("fproduct_type");
                    String registeredIpAddress = resultSet.getString("fregistered_ip_address");
                    BigDecimal markUp = resultSet.getBigDecimal("fmarkup");
                    boolean hsFlag = resultSet.getBoolean("fhs_flag");
                    char fsFlag = resultSet.getString("ffsp_flag").charAt(0);
                    BigDecimal couponExpiry = resultSet.getBigDecimal("fcoupon_expiry");
                    String franchiseId = resultSet.getString("ffranchiseid");
                    boolean franchiseStatus = resultSet.getBoolean("ffranchise_status");
                    boolean franchiseFlag = resultSet.getBoolean("ffranchise_flag");
                    boolean ecFlag = resultSet.getBoolean("fec_flag");
                    boolean allowFranchise = resultSet.getBoolean("fallow_franchise");
                    String ecTermId = resultSet.getString("fec_termid");
                    boolean allowDcs = resultSet.getBoolean("fallow_dcs");
                    BigDecimal maxUnbilledTerminal = resultSet.getBigDecimal("fmax_unbilled_terminal");
                    String oic = resultSet.getString("foic");
                    boolean timeCardFlag = resultSet.getBoolean("ftime_card_flag");
                    String invMethod = resultSet.getString("finv_method");
                    boolean checkInventoryFlag = resultSet.getBoolean("fcheck_inventory_flag");
                    String packageType = resultSet.getString("fpackage");
                    boolean advertiseFlag = resultSet.getBoolean("fadvertise_flag");
                    boolean feedbackFlag = resultSet.getBoolean("ffeedback_flag");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    boolean productSorting = resultSet.getBoolean("fsort_product");
                    boolean checkInventoryLotFlag = resultSet.getBoolean("fcheck_inventory_lot_flag");
                    BigDecimal uid = resultSet.getBigDecimal("fuid");
                    boolean standaloneFlag = resultSet.getBoolean("fstandalone_flag");
                    boolean stampsFlag = resultSet.getBoolean("fstamps_flag");
                    Blob receiptEmailTemplate = resultSet.getBlob("fereceipt_email_template");
                    String subjectOfEmail = resultSet.getString("fereceipt_email_subject");
                    String replyToEmail = resultSet.getString("fereceipt_replyto_email");
                    String replyToName = resultSet.getString("fereceipt_replyto_name");
                    String logoVersion = resultSet.getString("fpower_logo_version");
                    boolean inventoryPendingProcessFlag = resultSet.getBoolean("finventory_pending_process_flag");
                    boolean inventoryAllocateFromPriorDateFlag = resultSet.getBoolean("finventory_allocate_from_prior_date_flag");
                    boolean inventoryMigrationFlag = resultSet.getBoolean("finventory_migration_flag");
                    boolean disableFlag = resultSet.getBoolean("fdisable_flag");
                    boolean widgetFlag = resultSet.getBoolean("fwidget_flag");
                    boolean activateF3pFlag = resultSet.getBoolean("factivate_f3p_flag");
                    boolean allowAcc = resultSet.getBoolean("fallow_acc");
                    String menuType = resultSet.getString("fmenu_type");
                    boolean postUnpostedInventoryFlag = resultSet.getBoolean("fpost_unposted_inventory_flag");
                    BigDecimal idNo = resultSet.getBigDecimal("fid_no");
                    BigDecimal userCount = resultSet.getBigDecimal("fuser_count");
                    String currencySymbol = resultSet.getString("fcurrency_symbol");

                    // SQL Insert Statement
                    String formattedSql = String.format("INSERT INTO CompanyMaster " + "(companyId, createdBy, createdDate, updatedBy, updatedDate, " + "activateLoyaltyProgram, address1, address2, address3, bodyOfEmail, " + "checkQtyGen, checkQtyLot, companyLogoImg, gcExpiry, genMerchProductId, " + "initialCashierId, isActive, isDeleted, logoVersion, mainBranchName, " + "markUp, memo, name, productSorting, replyToEmail, replyToName, " + "salesTax, standaloneTerminal, subjectOfEmail, taxType, telNo, " + "tin, loyaltyProgram, productTypeId, managedBy) VALUES " + "(%s, %s, %s, %s, %s, " + // 1-5: companyId, createdBy, createdDate, updatedBy, updatedDate
                                    "%d, %s, %s, %s, %s, " + // 6: activateLoyaltyProgram, address1, address2, address3, bodyOfEmail
                                    "%d, %d, %s, %s, %s, " + // 11-15: Check quantities and companyLogoImg
                                    "%d, %d, %s, %s, " + // 16-19: markUp, isActive, logoVersion
                                    "%s, %s, %s, %s, %s, " + // 20-24: memo, name, productSorting, replyToEmail, replyToName
                                    "%s, %s, %s, %s, %s, " + // 25-29: salesTax, standaloneTerminal, subjectOfEmail, taxType, telNo
                                    "%s, %s, %s, %s, %s, %s);", // 30-33: tin, loyaltyProgram, productTypeId, managedBy
                            escapeSqlString(companyId),              // 1: companyId
                            escapeSqlString(createdBy),               // 2: createdBy
                            convertDate(createdDate),                 // 3: createdDate
                            escapeSqlString(updatedBy),               // 4: updatedBy
                            convertDate(updatedDate),                 // 5: updatedDate
                            isActive ? 1 : 0,                         // 6: activateLoyaltyProgram
                            escapeSqlString(address1),                // 7: address1
                            escapeSqlString(address2),                // 8: address2
                            escapeSqlString(address3),                // 9: address3
                            null,                                      // 10: bodyOfEmail (assuming NULL)
                            0,                                         // 11: checkQtyGen (assuming 0)
                            0,                                         // 12: checkQtyLot (assuming 0)
                            null,                                      // 13: companyLogoImg (assuming NULL)
                            null,                                      // 14: gcExpiry (assuming NULL)
                            null,                                      // 15: genMerchProductId (assuming NULL)
                            0,                                         // 16: initialCashierId (assuming 0)
                            isActive ? 1 : 0,                         // 17: isActive
                            0,                                         // 18: isDeleted (assuming 0)
                            escapeSqlString(logoVersion),             // 19: logoVersion
                            null,                                      // 20: mainBranchName (assuming NULL)
                            markUp != null ? markUp : BigDecimal.ZERO, // 21: markUp
                            escapeSqlString(memo),                    // 22: memo
                            escapeSqlString(name),                    // 23: name
                            productSorting ? 1 : 0,                   // 24: productSorting
                            escapeSqlString(replyToEmail),            // 25: replyToEmail
                            escapeSqlString(replyToName),             // 26: replyToName
                            salesTax != null ? salesTax : BigDecimal.ZERO, // 27: salesTax
                            0,                                         // 28: standaloneTerminal (assuming 0)
                            escapeSqlString(subjectOfEmail),          // 29: subjectOfEmail
                            chooseForTaxType(taxType),                 // 30: taxType
                            escapeSqlString(telNo),                   // 31: telNo
                            escapeSqlString(tinNo),                   // 32: tin
                            null,                                      // 33: loyaltyProgram (assuming NULL)
                            chooseForProductType(productTypeId),           // 34: productTypeId
                            escapeSqlString(manageBy)                 // 35: managedBy
                    );


                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Bank Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void accessLevelMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\AccessLevelMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_access_level"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String accessLevelId = resultSet.getString("faclvlid");
                    String name = resultSet.getString("fname");
                    String memo = resultSet.getString("fmemo");
                    BigDecimal clearanceLevel = resultSet.getBigDecimal("fclearance_level");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");

                    // Write the SQL statement with values to the file
                    String formattedSql = String.format("INSERT INTO `AccessLevelMaster` " + "(`id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `accessLevelId`, `clearanceLevel`, `isDeleted`, `memo`, `name`) " + "VALUES (NULL, %s, %s, %s, %s, %s, %s, %d, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), escapeSqlString(accessLevelId), escapeSqlString(clearanceLevel.toString()), 0, // isDeleted
                            escapeSqlString(memo), escapeSqlString(name));

                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Access Level Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void uomSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\Uom.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_uom"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String companyId = resultSet.getString("fcompanyid");
                    String uomId = resultSet.getString("fuomid");
                    String name = resultSet.getString("fname");
                    String base = resultSet.getString("fbase_uom");
                    BigDecimal decimalPlaces = resultSet.getBigDecimal("fdecimal_qty");
                    String accessMode = resultSet.getString("faccess_mode");
                    String groupAsUnit = resultSet.getString("fgroup_flag");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String promptQty = resultSet.getString("fprompt_qty_flag");

                    // Write the SQL statement with values to the file
                    String formattedSql = String.format("INSERT INTO Uom (createdBy, createdDate, updatedBy, updatedDate, base, decimalPlaces, groupAsUnit, isDeleted, promptQty, uomId, uomName, companyId) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %d, %s, %s, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), escapeSqlString(base), escapeSqlString(decimalPlaces.toString()), // Convert BigDecimal to String
                            checkBooleanValueIsTrue(groupAsUnit), 0, // Assuming 0 for isDeleted
                            escapeSqlString(promptQty), escapeSqlString(uomId), escapeSqlString(name), escapeSqlString(companyId));

                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("UOM Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void uomConversionSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\UomConversion.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_uom_detail"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String uomId = resultSet.getString("fuomid"); // UOM ID
                    String uom = resultSet.getString("fuom"); // UOM
                    BigDecimal quantity = resultSet.getBigDecimal("fquantity"); // Quantity
                    String name = resultSet.getString("fname"); // Name
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date


                    // Write the SQL statement with values to the file
                    String sqlInsert = String.format("INSERT INTO `UomConversion` " + "(`baseMultiplier`, `description`, `isDeleted`, `unitConvId`, `companyId`, `uomId`) " + "VALUES (%s, %s, %s, %s, %s, %s);", quantity,                           // baseMultiplier (Quantity)
                            escapeSqlString(name),               // description (Name)
                            0,                                  // isDeleted (assumed 0)
                            escapeSqlString(uomId),              // unitConvId (UOM ID)
                            escapeSqlString(companyId),          // companyId
                            chooseUomMaster(uom)                 // uomId (UOM)
                    );

                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("UOM Conversion Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void regionMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\RegionMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_region"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String companyId = resultSet.getString("fcompanyid");
                    String regionId = resultSet.getString("fregionid");
                    String name = resultSet.getString("fname");
                    String activeFlag = resultSet.getString("factive_flag");
                    String memo = resultSet.getString("fmemo");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");

                    // Convert activeFlag to boolean for isActive
                    boolean isActive = activeFlag != null && activeFlag.equals("1"); // Adjust according to your logic
                    int isDeleted = 0; // Assuming default value for isDeleted is 0

                    // Write the SQL statement with values to the file
                    String formattedSql = String.format("INSERT INTO `RegionMaster` (createdBy, createdDate, updatedBy, updatedDate, isActive, isDeleted, memo, name, regionId, companyId) " + "VALUES (%s, %s, %s, %s, %d, %d, %s, %s, %s, %s);", escapeSqlString(createdBy), // Assumes this will never be null, handle it if necessary
                            convertDate(createdDate), escapeSqlString(updatedBy), // Assumes this will never be null, handle it if necessary
                            convertDate(updatedDate), isActive ? 1 : 0, // Convert boolean to int
                            isDeleted, // Assuming isDeleted is 0
                            escapeSqlString(memo), escapeSqlString(name), escapeSqlString(regionId), escapeSqlString(companyId));

                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Region Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void branchMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\BranchMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_salesoffice"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Retrieve data from ResultSet
                    String companyId = resultSet.getString("fcompanyid");
                    String branchId = resultSet.getString("fofficeid");
                    String name = resultSet.getString("fname");
                    String isActive = resultSet.getString("factive_flag");
                    String warehouseId = resultSet.getString("fsiteid");
                    String regionMasterId = resultSet.getString("fregionid");
                    String address1 = resultSet.getString("faddress1");
                    String address2 = resultSet.getString("faddress2");
                    String address3 = resultSet.getString("faddress3");
                    String phone = resultSet.getString("fphone");
                    String fax = resultSet.getString("ffax");
                    String email = resultSet.getString("femail");
                    String memo = resultSet.getString("fmemo");
                    String tin = resultSet.getString("ftinno");
                    String startReport = resultSet.getString("fstart_report");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String biFlag = resultSet.getString("fbi_flag");
                    String thirdPartyId = resultSet.getString("fthirdpartyid");

                    // Format the SQL insert statement
                    String formattedSql = String.format("INSERT INTO `BranchMaster` (createdBy, createdDate, updatedBy, updatedDate, address1, address2, address3, branchId, email, fax, isActive, isDeleted, memo, name, phone, tin, companyId, regionMasterId, warehouseMasterId) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %d, %d, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), escapeSqlString(address1), escapeSqlString(address2), escapeSqlString(address3), escapeSqlString(branchId), // Using officeId as branchId
                            escapeSqlString(email), escapeSqlString(fax), isActive != null && isActive.equals("1") ? 1 : 0, // Convert to int
                            0, // Assuming default value for isDeleted
                            escapeSqlString(memo), escapeSqlString(name), escapeSqlString(phone), escapeSqlString(tin), escapeSqlString(companyId), chooseForRegionMasterId(regionMasterId), chooseForWarehouseMasterId(warehouseId)); // Assuming default for warehouseMasterId
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Branch Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void warehouseMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\WarehouseMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_warehouse"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String companyId = resultSet.getString("fcompanyid");
                    String warehouseId = resultSet.getString("fsiteid");
                    String name = resultSet.getString("fname");
                    String isActive = resultSet.getString("factive_flag");
                    String address1 = resultSet.getString("faddress1");
                    String address2 = resultSet.getString("faddress2");
                    String address3 = resultSet.getString("faddress3");
                    String phoneNo = resultSet.getString("fphone");
                    String fax = resultSet.getString("ffax");
                    String email = resultSet.getString("femail");
                    String memo = resultSet.getString("fmemo");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String replenishmentSiteId = resultSet.getString("freplenishment_siteid");
                    String replenishmentCompanyId = resultSet.getString("freplenishment_companyid");
                    String searchableFlag = resultSet.getString("fsearchable_flag");
                    String thirdPartyId = resultSet.getString("fthirdpartyid");

                    // SQL Insert Statement
                    String formattedSql = String.format("INSERT INTO `WarehouseMaster` " + "(`id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `address1`, `address2`, `address3`, `email`, `fax`, `isActive`, `isDeleted`, `label`, `memo`, `name`, `phoneNo`, `value`, `warehouseId`, `companyId`) " + "VALUES (NULL, %s, %s, %s, %s, %s, %s, %s, %s, %s, %d, %d, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),                // 1
                            convertDate(createdDate),                  // 2
                            escapeSqlString(updatedBy),                 // 3
                            convertDate(updatedDate),                   // 4
                            escapeSqlString(address1),                  // 5
                            escapeSqlString(address2),                  // 6
                            escapeSqlString(address3),                  // 7
                            escapeSqlString(email),                     // 8
                            escapeSqlString(fax),                       // 9
                            isActive != null && isActive.equals("1") ? 1 : 0, // 10
                            0,                                          // 11
                            null,                                       // 12
                            escapeSqlString(memo),                      // 13
                            escapeSqlString(name),                      // 14
                            escapeSqlString(phoneNo),                   // 15
                            null,                                       // 16
                            escapeSqlString(warehouseId),               // 17
                            escapeSqlString(companyId));                 // 18


                    // Write to file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement

                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Warehouse Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void bankMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\BankMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_bank"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String bankId = resultSet.getString("fbankid");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String memo = resultSet.getString("fmemo");
                    String name = resultSet.getString("fname");
                    String companyId = resultSet.getString("fcompanyid");
                    boolean isActive = resultSet.getBoolean("factive_flag");
                    // Retrieve other fields as needed

                    String sql1 = String.format("INSERT INTO `BankMaster` (`id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, " + "`bankId`, `isActive`, `isDeleted`, `memo`, `name`, `companyId`) VALUES (NULL, %s, %s, %s, %s, %s, %d, %d, %s, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), escapeSqlString(bankId), isActive ? 1 : 0, // Convert boolean to int
                            0, // Assuming default value for isDeleted
                            escapeSqlString(memo), escapeSqlString(name), escapeSqlString(companyId));

                    // Write the SQL statement to the file
                    writer.write(sql1);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Bank Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void priceLevelSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\PriceLevelMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_price_level"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    boolean isActive = resultSet.getBoolean("factive_flag");
                    String name = resultSet.getString("fname");
                    String plevelId = resultSet.getString("fplevelid");
                    String companyId = resultSet.getString("fcompanyid");
                    String memo = resultSet.getString("fmemo");

//                    BigDecimal discp = resultSet.getBigDecimal("fdiscp");
//                    fieldNullChecker(companyId, "companyId", sqlBuilder, sqlBuilder);
//
//                    String code = resultSet.getString("fcode");
//                    fieldNullChecker(companyId, "companyId", sqlBuilder, sqlBuilder);

                    // Retrieve other fields as needed

                    String sql1 = String.format("INSERT INTO `PriceLevelMaster` (`id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `isActive`, `isDeleted`, `name`, `priceLevelId`, `companyId`, `memo`)" + "VALUES (NULL, %s, %s, %s, %s, %d, %d, %s, %s, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), isActive ? 1 : 0, 0, escapeSqlString(name), escapeSqlString(plevelId), escapeSqlString(companyId), escapeSqlString(memo));

                    // Write the SQL statement to the file
                    writer.write(sql1);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Price Level Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void priceListProductSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\PriceListProduct.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_price_product"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String priceId = resultSet.getString("fpriceid"); // Price ID
                    BigDecimal seqNo = resultSet.getBigDecimal("fseqno"); // Sequence Number
                    BigDecimal hierarchyId = resultSet.getBigDecimal("fhierarchy"); // Hierarchy
                    String productMasterId = resultSet.getString("fcategory"); // Category
                    BigDecimal qty1 = resultSet.getBigDecimal("fqty1"); // Quantity 1
                    BigDecimal qty2 = resultSet.getBigDecimal("fqty2"); // Quantity 2
                    String uom = resultSet.getString("fuom"); // UOM (Unit of Measure)
                    BigDecimal uomQty = resultSet.getBigDecimal("fuomqty"); // UOM Quantity
                    BigDecimal listPrice = resultSet.getBigDecimal("flist_price"); // List Price
                    BigDecimal discountPercent = resultSet.getBigDecimal("fdiscp"); // Discount Percentage
                    BigDecimal discount = resultSet.getBigDecimal("fdiscount"); // Discount
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String fspEarnType = resultSet.getString("ffsp_earn_type"); // FSP Earn Type
                    BigDecimal fspEarnPoint = resultSet.getBigDecimal("ffsp_earn_point"); // FSP Earn Point

                    String sqlInsert = String.format("INSERT INTO `PriceListProduct` (`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `discPercentage`, `discount`, " + "`isDeleted`, `loyaltyCompId`, `loyaltyPoints`, `margin`, `netPrice`, `qty`, `stndCost`, `toQty`, `unitConvId`, `unitPrice`, `uomId`, " + "`priceMasterId`, `productMasterId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", null,        // Created By
                            null,      // Created Date
                            null,        // Updated By
                            escapeSqlString(updatedDate),      // Updated Date
                            discountPercent,                   // Discount Percentage
                            discount,                          // Discount
                            0,                                 // isDeleted (default 0 for active)
                            null,                              // loyaltyCompId (null)
                            fspEarnPoint,                      // Loyalty Points (mapped from FSP Earn Points)
                            null,                              // margin (null)
                            null,                              // netPrice (null)
                            qty1,                              // Quantity 1 (qty)
                            null,                              // stndCost (null)
                            qty2,                              // To Quantity (qty2)
                            null,                              // Unit Conversion ID (null)
                            listPrice,                         // Unit Price (List Price)
                            chooseUomMaster(uom),                               // UOM ID (mapped from fuom)
                            choosePriceMaster(priceId),                           // Price Master ID (mapped from fpriceid)
                            chooseProductMasterId(productMasterId)                         // Product Master ID (mapped from fcategoryid)
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Price Level Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void stockStatusSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\StockStatus.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM inv_product_status"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String productId = resultSet.getString("fproductid"); // Product ID
                    String siteId = resultSet.getString("fsiteid"); // Site ID
                    String lastSold = resultSet.getString("flast_sold"); // Last Sold
                    BigDecimal qty = resultSet.getBigDecimal("fqty"); // Quantity
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    BigDecimal onOrderQty = resultSet.getBigDecimal("fonorder_qty"); // On Order Quantity
                    BigDecimal onReplenishQty = resultSet.getBigDecimal("fonreplenish_qty"); // On Replenish Quantity
                    String updatedDateIndex = resultSet.getString("fupdated_date_index"); // Updated Date Index


//                    String sqlInsert = String.format(
//                            "INSERT INTO `StockStatus` (`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `expiryDate`, `postedDate`, `productLotId`, `qty`, `unitConvId`, `uomId`, `companyId`, `productMasterId`, `warehouseMasterId`) " +
//                                    "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);",
//                            null,      // Created By
//                            null,        // Created Date
//                            null,      // Updated By
//                            convertDate(updatedDate),        // Updated Date
//                            convertDate(expiryDate),         // Expiry Date
//                            convertDate(postedDate),         // Posted Date
//                            escapeSqlString(productLotId),   // Product Lot ID
//                            qty,                             // Quantity
//                            unitConvId,                      // Unit Conversion ID
//                            uomId,                           // UOM ID
//                            escapeSqlString(companyId),      // Company ID
//                            escapeSqlString(productId),      // Product Master ID
//                            escapeSqlString(siteId)          // Warehouse Master ID
//                    );


                    // Write the SQL statement to the file
//                    writer.write(sqlInsert);
//                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Price Level Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void stockLedgerSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\StockLedger.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM inv_ledger"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String pubId = resultSet.getString("fpubid"); // Publication ID
                    BigDecimal recNo = resultSet.getBigDecimal("frecno"); // Record Number
                    BigDecimal seqNo = resultSet.getBigDecimal("fseqno"); // Sequence Number
                    String trxLink = resultSet.getString("ftrxlink"); // Transaction Link
                    BigDecimal docType = resultSet.getBigDecimal("fdoctype"); // Document Type
                    String referenceCode = resultSet.getString("freference_code"); // Reference Code
                    String memo = resultSet.getString("fmemo"); // Memo
                    String trxDate = resultSet.getString("ftrxdate"); // Transaction Date
                    String postedDate = resultSet.getString("fposted_date"); // Posted Date
                    String invDate = resultSet.getString("finv_date"); // Invoice Date
                    String costDate = resultSet.getString("fcost_date"); // Cost Date
                    String productId = resultSet.getString("fproductid"); // Product ID
                    String siteId = resultSet.getString("fsiteid"); // Site ID
                    String lotNo = resultSet.getString("flotno"); // Lot Number
                    String expiry = resultSet.getString("fexpiry"); // Expiry
                    BigDecimal qty = resultSet.getBigDecimal("fqty"); // Quantity
                    BigDecimal unitPrice = resultSet.getBigDecimal("funitprice"); // Unit Price
                    String costFlag = resultSet.getString("fcost_flag"); // Cost Flag
                    BigDecimal listPrice = resultSet.getBigDecimal("flist_price"); // List Price
                    BigDecimal balance = resultSet.getBigDecimal("fbalance"); // Balance
                    String specificLotFlag = resultSet.getString("fspecific_lot_flag"); // Specific Lot Flag
                    BigDecimal transferRecNo = resultSet.getBigDecimal("ftransfer_recno"); // Transfer Record Number
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date

                    String sqlInsert = String.format("INSERT INTO `StockLedger` (`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `details`, `docId`, `lotId`, `qty`, `trxDate`, `companyId`, `productMasterId`, `warehouseMasterId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),          // Created By
                            convertDate(createdDate),            // Created Date
                            escapeSqlString(updatedBy),          // Updated By
                            convertDate(updatedDate),            // Updated Date
                            escapeSqlString(memo),               // Details (Memo)
                            recNo,                                // Document ID (RecNo as docId)
                            escapeSqlString(lotNo),              // Lot ID
                            qty,                                  // Quantity
                            convertDate(trxDate, true),                // Transaction Date
                            escapeSqlString(companyId),          // Company ID
                            chooseProductMasterId(productId),          // Product Master ID
                            chooseForWarehouseMasterId(siteId)               // Warehouse Master ID (Site ID)
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Stock Ledger: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void creditCardMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CreditCardMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_credit"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid");
                    String creditId = resultSet.getString("fcreditid");
                    String name = resultSet.getString("fname");
                    String memo = resultSet.getString("fmemo");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String thirdPartyId = resultSet.getString("fthirdpartyid");
                    String bankId = resultSet.getString("fbankid");
                    String apiCode = resultSet.getString("fapi_code");

                    // Prepare the SQL Insert Statement
                    String formattedSql = String.format("INSERT INTO `CreditCardMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `apiCode`, `creditId`, `isDeleted`, `memo`, `name`, `bankMasterId`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %d, %s, %s, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), escapeSqlString(apiCode), escapeSqlString(creditId), 0, // Assuming default value for isDeleted
                            escapeSqlString(memo), escapeSqlString(name), chooseForBankMasterId(bankId), // Assuming this corresponds to bankMasterId
                            escapeSqlString(companyId));

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Credit Card Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void cashMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CashMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_cash"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid");
                    String cashId = resultSet.getString("fcashid");
                    String name = resultSet.getString("fname");
                    BigDecimal amount = resultSet.getBigDecimal("famount");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");

                    String formattedSql = String.format("INSERT INTO `CashMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `amount`, `cashId`, `isDeleted`, `name`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %d, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), amount != null ? amount.toString() : "NULL", // Use amount as string or NULL
                            escapeSqlString(cashId), 0, // Assuming default value for isDeleted
                            escapeSqlString(name), escapeSqlString(companyId));

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Cash Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void companyLoginSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CompanyLogin.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_user_company"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String emailAddress = resultSet.getString("fuserid"); // User ID
                    BigDecimal seqNo = resultSet.getBigDecimal("fseqno"); // Sequence Number
                    String accessLevelId = resultSet.getString("faclvlid"); // Access Level ID
                    String branchId = resultSet.getString("fofficeid"); // Office ID
                    String limitedToBranch = resultSet.getString("foffice_flag"); // Office Flag
                    String limitedToWarehouse = resultSet.getString("fsite_flag"); // Site Flag
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String warehouseId = resultSet.getString("fsiteid"); // Site ID


                    String sqlInsert = String.format("INSERT INTO `CompanyLogin` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `isActive`, `isDeleted`, `limitedToBranch`, `limitedToWarehouse`, `name`, `accessLevelId`, `defaultBranchId`, `companyId`, `emailAddress`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, '%s', %s, %s, %s, %s);", escapeSqlString(createdBy),        // createdBy
                            convertDate(createdDate),          // createdDate
                            escapeSqlString(updatedBy),        // updatedBy
                            convertDate(updatedDate),          // updatedDate
                            1,                                  // isActive (from factive_flag)
                            0,                                  // isDeleted (assuming not deleted)
                            escapeSqlString(limitedToBranch),         // limitedToBranch (if you have it)
                            escapeSqlString(limitedToWarehouse),      // limitedToWarehouse (if you have it)
                            escapeSqlString(chooseNameForCompanyLogin(emailAddress), true),             // name (from fname)
                            chooseForAccessLevel(accessLevelId),                           // accessLevelId (from faclvlid)
                            chooseForBranchMasterId(branchId),                          // defaultBranchId (from fofficeid)
                            escapeSqlString(companyId),                         // companyId (from fcompanyid)
                            escapeSqlString(emailAddress)      // emailAddress (add the variable for email address)
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Company Login: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void companyLoginWarehouseSelectionSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CompanyLoginWarehouseSelection.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_user_company_site"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String userId = resultSet.getString("fuserid"); // User ID
                    String siteCompanyId = resultSet.getString("fsite_companyid"); // Site Company ID
                    String siteId = resultSet.getString("fsiteid"); // Site ID


                    String sqlInsert = String.format("INSERT INTO `CompanyLoginWarehouseSelection` " + "(`warehouseSelectionId`, `isDeleted`, `companyLoginId`, `warehouseMasterId`) " + "VALUES (%s, %s, %s, %s);", null,              // Assuming you have this variable defined
                            0,                               // isDeleted (assuming not deleted)
                            chooseCompanyLoginIdFrom(escapeSqlString(userId, true)),                            // companyLoginId (mapped from fuserid)
                            chooseForWarehouseMasterId(siteId)                             // warehouseMasterId (mapped from fsiteid),
                    );


                    // Write the SQL statement to the file
                    if (chooseCompanyLoginIdFrom(userId) != null && chooseForWarehouseMasterId(siteId) != null) {
                        writer.write(sqlInsert);
                        writer.newLine(); // Add a new line for the next statement
                    }

                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Company Login Warehouse Selection: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void companyLoginBranchSelectionSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CompanyLoginBranchSelection.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_user_company_office"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String userId = resultSet.getString("fuserid"); // User ID
                    String companyLoginId = resultSet.getString("foffice_companyid"); // Site Company ID
                    String branchMasterId = resultSet.getString("fofficeid"); // Site ID


                    String sqlInsert = String.format("INSERT INTO `CompanyLoginBranchSelection` " + "(`branchSelectionId`, `isDeleted`, `companyLoginId`, `branchMasterId`) " + "VALUES (%s, %s, %s, %s);", null,              // Assuming you have this variable defined
                            0,                               // isDeleted (assuming not deleted)
                            chooseCompanyLoginIdFrom(escapeSqlString(userId, true)),                            // companyLoginId (mapped from fuserid)
                            chooseForBranchMasterId(branchMasterId)                                                         // branchMasterId (mapped from fofficeid)
                    );


                    // Write the SQL statement to the file
                    if (chooseCompanyLoginIdFrom(userId) != null && chooseForBranchMasterId(branchMasterId) != null) {
                        writer.write(sqlInsert);
                        writer.newLine(); // Add a new line for the next statement
                    }

                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Company Login Warehouse Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void priceMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\PriceMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_price"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Retrieving values from the ResultSet
                    String companyId = resultSet.getString("fcompanyid");
                    String priceId = resultSet.getString("fpriceid");
                    String name = resultSet.getString("fname");
                    String activeFlag = resultSet.getString("factive_flag");
                    String memo = resultSet.getString("fmemo");
                    String schemeType = resultSet.getString("fscheme_type");
                    String periodFlag = resultSet.getString("fperiod_flag");
                    String startDate = resultSet.getString("fsdate");
                    String endDate = resultSet.getString("fedate");
                    String timeFlag = resultSet.getString("ftime_flag");
                    String startTime = resultSet.getString("fstime");
                    String endTime = resultSet.getString("fetime");
                    String priceLevelFlag = resultSet.getString("fprice_level_flag");
                    String limitToBranch = resultSet.getString("foffice_flag");
                    String linkPriceId = resultSet.getString("flink_priceid");
                    String limitDayFlag = resultSet.getString("flimit_day_flag");
                    String day0Flag = resultSet.getString("fday0_flag");
                    String day1Flag = resultSet.getString("fday1_flag");
                    String day2Flag = resultSet.getString("fday2_flag");
                    String day3Flag = resultSet.getString("fday3_flag");
                    String day4Flag = resultSet.getString("fday4_flag");
                    String day5Flag = resultSet.getString("fday5_flag");
                    String day6Flag = resultSet.getString("fday6_flag");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String thirdPartyId = resultSet.getString("fthirdpartyid");

                    // SQL Insert Statement
                    String sqlInsert = String.format("INSERT INTO `PriceMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `endDate`, `friday`, `fromTime`, `isActive`, `isDeleted`, " + "`limitToBranch`, `limitToPriceLevel`, `limitToRegion`, `memo`, `monday`, `name`, `periodConstraint`, `priceId`, " + "`reuseProdDefPriceId`, `saturday`, `startDate`, `sunday`, `thursday`, `timeConstraint`, `toTime`, `tuesday`, " + "`wednesday`, `weekdayConstraint`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %b, %b, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), escapeSqlString(endDate), escapeSqlString(day5Flag), // Friday
                            escapeSqlString(startTime), activeFlag.equals("1"), // isActive as boolean
                            false, // isDeleted
                            limitToBranch.equals("1"), // limitToBranch
                            escapeSqlString(priceLevelFlag), // limitToPriceLevel
                            escapeSqlString(schemeType), // limitToRegion
                            escapeSqlString(memo), escapeSqlString(day1Flag), // Monday
                            escapeSqlString(name), escapeSqlString(periodFlag), // periodConstraint
                            escapeSqlString(priceId), null, // reuseProdDefPriceId
                            escapeSqlString(day6Flag), // Saturday
                            escapeSqlString(startDate), escapeSqlString(day0Flag), // Sunday
                            escapeSqlString(day3Flag), // Thursday
                            escapeSqlString(timeFlag), escapeSqlString(endTime), escapeSqlString(day2Flag), // Tuesday
                            escapeSqlString(day4Flag), // Wednesday
                            escapeSqlString(limitDayFlag), // weekdayConstraint
                            escapeSqlString(companyId)); // companyId

                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Price Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void tenderTypeSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TenderType.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_tender_type"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming you have retrieved the values from the ResultSet
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String tenderId = resultSet.getString("ftenderid"); // Tender ID
                    String name = resultSet.getString("fname"); // Name of the tender
                    String fieldLabel1 = resultSet.getString("ffield1"); // Field 1
                    String fieldLabel2 = resultSet.getString("ffield2"); // Field 2
                    String fieldLabel3 = resultSet.getString("ffield3"); // Field 3
                    String fieldLabel4 = resultSet.getString("ffield4"); // Field 4
                    String field1RequiredFlag = resultSet.getString("ffield1_required_flag"); // Required flag for field 1
                    String field2RequiredFlag = resultSet.getString("ffield2_required_flag"); // Required flag for field 2
                    String field3RequiredFlag = resultSet.getString("ffield3_required_flag"); // Required flag for field 3
                    String field4RequiredFlag = resultSet.getString("ffield4_required_flag"); // Required flag for field 4
                    BigDecimal additionalCopy = resultSet.getBigDecimal("fadditional_copy"); // Additional copies
                    String equalSaleFlag = resultSet.getString("fequal_sale_flag"); // Equal sale flag
                    String changeableFlag = resultSet.getString("fchangeable_flag"); // Changeable flag
                    String accessLevelId = resultSet.getString("faclvlid"); // Access level ID
                    String sysOption = resultSet.getString("fsysoption"); // System option
                    String memo = resultSet.getString("fmemo"); // Memo
                    String createdBy = resultSet.getString("fcreated_by"); // Creator of the record
                    String createdDate = resultSet.getString("fcreated_date"); // Creation date
                    String updatedBy = resultSet.getString("fupdated_by"); // Last updater
                    String updatedDate = resultSet.getString("fupdated_date"); // Last update date
                    String ecFlag = resultSet.getString("fec_flag"); // EC flag
                    Blob ecInstruction1 = resultSet.getBlob("fec_instruction1"); // EC instruction 1
                    Blob ecInstruction2 = resultSet.getBlob("fec_instruction2"); // EC instruction 2
                    BigDecimal ecExpireMins = resultSet.getBigDecimal("fec_expire_mins"); // Expiration time in minutes
                    String apiCode = resultSet.getString("fapi_code"); // API code


                    // Construct the SQL INSERT statement
                    String sqlInsert = String.format("INSERT INTO `TenderType` (`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `addReceipts`, " + "`allowChange`, `apiCode`, `fieldLabel1`, `fieldLabel2`, `fieldLabel3`, `fieldLabel4`, `isDefault`, `isDeleted`, " + "`isRequiredLabel1`, `isRequiredLabel2`, `isRequiredLabel3`, `isRequiredLabel4`, `memo`, `mustEqualGrossSales`, `name`," + " `tenderId`, `accessLevelId`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy), // Creator
                            convertDate(createdDate), // Creation date
                            escapeSqlString(updatedBy), // Last updated by
                            convertDate(updatedDate), // Last updated date
                            null, // Add receipts flag (boolean)
                            0, // Allow change flag (boolean)
                            escapeSqlString(apiCode), // API code
                            escapeSqlString(fieldLabel1), // Field label 1
                            escapeSqlString(fieldLabel2), // Field label 2
                            escapeSqlString(fieldLabel3), // Field label 3
                            escapeSqlString(fieldLabel4), // Field label 4
                            0, // Default flag (boolean)
                            0, // Deleted flag (boolean)
                            field1RequiredFlag, // Required flag for field 1
                            field2RequiredFlag, // Required flag for field 2
                            field3RequiredFlag, // Required flag for field 3
                            field4RequiredFlag, // Required flag for field 4
                            escapeSqlString(memo), // Memo
                            0, // Must equal gross sales flag (boolean)
                            escapeSqlString(name), // Tender name
                            escapeSqlString(tenderId), // Tender ID
                            chooseForAccessLevel(accessLevelId), // Access level ID
                            escapeSqlString(companyId)); // Company ID


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Tender Type: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void giftCertTypeMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\GiftCertTypeMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_gc_type"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String gcTypeId = resultSet.getString("fgctypeid"); // GC Type ID
                    String name = resultSet.getString("fname"); // Name
                    String supplierId = resultSet.getString("fsupplierid"); // Supplier ID
                    String activeFlag = resultSet.getString("factive_flag"); // Active Flag
                    String memo = resultSet.getString("fmemo"); // Memo
                    BigDecimal amount = resultSet.getBigDecimal("famount"); // Amount
                    String expiry = resultSet.getString("fexpiry"); // Expiry
                    String usageFlag = resultSet.getString("fusage_flag"); // Usage Flag
                    BigDecimal couponExpiry = resultSet.getBigDecimal("fcoupon_expiry"); // Coupon Expiry
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String periodFlag = resultSet.getString("fperiod_flag"); // Period Flag
                    String startDate = resultSet.getString("fsdate"); // Start Date
                    String endDate = resultSet.getString("fedate"); // End Date
                    String recurFlag = resultSet.getString("frecur_flag"); // Recur Flag
                    String recurStartDate = resultSet.getString("frecur_sdate"); // Recur Start Date
                    String recurEndDate = resultSet.getString("frecur_edate"); // Recur End Date
                    String disableTenderFlag = resultSet.getString("fdisable_tender_flag"); // Disable Tender Flag


                    // Construct the SQL INSERT statement
                    String sqlInsert = String.format("INSERT INTO `GiftCertTypeMaster` (`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `amount`, `couponExpiryId`, `expiry`, `gcTypeId`, " + "`isActive`, `isDeleted`, `isPeriod`, `isRecur`, `isTender`, `memo`, `periodFrom`, `periodTo`, `recurFrom`, `recurTo`, `supplierId`, `typeName`, `useInGc`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, 0, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),         // createdBy
                            convertDate(createdDate),           // createdDate
                            escapeSqlString(updatedBy),         // updatedBy
                            convertDate(updatedDate),           // updatedDate
                            escapeSqlString(amount.toString()),                  // amount
                            escapeSqlString(couponExpiry.toString()),            // couponExpiryId (mapped from fcoupon_expiry)
                            convertDate(expiry),                             // expiry
                            escapeSqlString(gcTypeId),                           // gcTypeId
                            checkBooleanValueIsTrue(activeFlag),                         // isActive (mapped from factive_flag)
                            checkBooleanValueIsTrue(periodFlag),                         // isPeriod (mapped from fperiod_flag)
                            checkBooleanValueIsTrue(recurFlag),                          // isRecur (mapped from frecur_flag)
                            checkBooleanValueIsTrue(disableTenderFlag),                  // isTender (mapped from fdisable_tender_flag)
                            escapeSqlString(memo),              // memo
                            convertDate(startDate),                          // periodFrom (mapped from fsdate)
                            convertDate(endDate),                            // periodTo (mapped from fedate)
                            convertDate(recurStartDate),                     // recurFrom (mapped from frecur_sdate)
                            convertDate(recurEndDate),                       // recurTo (mapped from frecur_edate)
                            escapeSqlString(supplierId),                         // supplierId
                            escapeSqlString(name),              // typeName (mapped from fname)
                            usageFlag,                          // useInGc (mapped from fusage_flag)
                            escapeSqlString(companyId)                           // companyId
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Gift Cert Type Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void giftCertificateMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\GiftCertificateMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_coupon"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String couponId = resultSet.getString("fcouponid"); // Coupon ID
                    String useDate = resultSet.getString("fuse_date"); // Use Date
                    String expiry = resultSet.getString("fexpiry"); // Expiry
                    String statusFlag = resultSet.getString("fstatus_flag"); // Status Flag
                    String source = resultSet.getString("fdoctype"); // Document Type
                    BigDecimal amount = resultSet.getBigDecimal("famount"); // Amount
                    String trxLink = resultSet.getString("ftrxlink"); // Transaction Link
                    String gcTypeId = resultSet.getString("fgctypeid"); // GC Type ID
                    String memo = resultSet.getString("fmemo"); // Memo
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String batchId = resultSet.getString("fbatchid"); // Batch ID


                    // Construct the SQL INSERT statement
                    String sqlInsert = String.format("INSERT INTO `GiftCertificateMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `amount`, `expiryDate`, `giftCertificateId`, `isDeleted`, `source`, `status`, `usedDate`, `companyId`, `giftCertTypeId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),         // createdBy
                            convertDate(createdDate),           // createdDate
                            escapeSqlString(updatedBy),         // updatedBy
                            convertDate(updatedDate),           // updatedDate
                            escapeSqlString(amount.toString()),                  // amount
                            convertDate(expiry),                             // expiryDate (from fexpiry)
                            escapeSqlString(couponId),                           // giftCertificateId (from fcouponid)
                            0, chooseSourceForGiftCertificate(source),                            // source (from ftrxlink)
                            checkBooleanValueIsTrue(statusFlag),                         // status (from fstatus_flag)
                            convertDate(useDate),                            // usedDate (from fuse_date)
                            escapeSqlString(companyId),                          // companyId
                            chooseForGiftCertTypeMasterId(gcTypeId)                            // giftCertTypeId (from fgctypeid)
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Gift Certificate Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void cancelReasonMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\CancelReasonMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_cancel"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String cancelId = resultSet.getString("fcancelid"); // Cancel ID
                    String name = resultSet.getString("fname"); // Name
                    String activeFlag = resultSet.getString("factive_flag"); // Active Flag
                    String memoFlag = resultSet.getString("fmemo_flag"); // Memo Flag
                    String memo = resultSet.getString("fmemo"); // Memo
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date


                    // Construct the SQL INSERT statement
                    String sqlInsert = String.format("INSERT INTO `CancelReasonMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `allowToEnterMemo`, `cancelId`, `isActive`, `isDeleted`, `memo`, `name`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),        // createdBy
                            convertDate(createdDate),          // createdDate
                            escapeSqlString(updatedBy),        // updatedBy
                            convertDate(updatedDate),          // updatedDate
                            memoFlag,                          // allowToEnterMemo (from fmemo_flag)
                            escapeSqlString(cancelId),                          // cancelId (from fcancelid)
                            activeFlag,                        // isActive (from factive_flag)
                            0, escapeSqlString(memo),             // memo (from fmemo)
                            escapeSqlString(name),             // name (from fname)
                            escapeSqlString(companyId)                          // companyId (from fcompanyid)
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Cancel Reason Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void groupTypeMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\GroupTypeMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_customer_type"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String customerTypeId = resultSet.getString("fcustomertypeid"); // Customer Type ID
                    String name = resultSet.getString("fname"); // Name
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String biFlag = resultSet.getString("fbi_flag"); // BI Flag

                    String sqlInsert = String.format("INSERT INTO `GroupTypeMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `groupTypeId`, `isDeleted`, `name`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),         // Created By
                            escapeSqlString(createdDate),       // Created Date
                            escapeSqlString(updatedBy),         // Updated By
                            escapeSqlString(updatedDate),       // Updated Date
                            escapeSqlString(customerTypeId),    // Group Type ID (mapped from customerTypeId)
                            0,                              // isDeleted (assuming 'N' for not deleted)
                            escapeSqlString(name),              // Name
                            escapeSqlString(companyId)          // Company ID
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Group Type Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void nationalityMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\NationalityMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_nationality"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String nationalityId = resultSet.getString("fnationalityid"); // Nationality ID
                    String name = resultSet.getString("fname"); // Name
                    String localName = resultSet.getString("flocal_name"); // Local Name
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date


                    String sqlInsert = String.format("INSERT INTO `NationalityMaster` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `isDefault`, `isDeleted`, `localName`, `nationality`, `nationalityId`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),         // Created By
                            escapeSqlString(createdDate),       // Created Date
                            escapeSqlString(updatedBy),         // Updated By
                            escapeSqlString(updatedDate),       // Updated Date
                            0,                              // isDefault (assuming 'N' for not default)
                            0,                              // isDeleted (assuming 'N' for not deleted)
                            escapeSqlString(localName),         // Local Name
                            escapeSqlString(name),              // Nationality
                            escapeSqlString(nationalityId),     // Nationality ID
                            escapeSqlString(companyId)          // Company ID
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Nationality Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void productHierarchySql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\ProductHierarchy.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_product_category"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String categoryLevel = resultSet.getString("fcategory_level"); // Category Level
                    String categoryName = resultSet.getString("fcategory_name"); // Category Name
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String ecProductFlag = resultSet.getString("fec_product_flag"); // EC Product Flag

                    String sqlInsert = String.format("INSERT INTO `ProductHierarchy` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `isDeleted`, `name`, `companyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),         // Created By
                            escapeSqlString(createdDate),       // Created Date
                            escapeSqlString(updatedBy),         // Updated By
                            escapeSqlString(updatedDate),       // Updated Date
                            0,                              // isDeleted (assuming 'N' for not deleted)
                            escapeSqlString(categoryName),      // Category Name
                            escapeSqlString(companyId)          // Company ID
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Product Hierarchy: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void productCategorySql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\ProductCategory.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_product_category_detail"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String categoryLevel = resultSet.getString("fcategory_level"); // Category Level
                    String categoryId = resultSet.getString("fcategoryid"); // Category ID
                    String categoryValue = resultSet.getString("fcategory_value"); // Category Value
                    String accessMode = resultSet.getString("faccess_mode"); // Access Mode
                    String createdBy = resultSet.getString("fcreated_by"); // Created By
                    String createdDate = resultSet.getString("fcreated_date"); // Created Date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated By
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated Date
                    String salesType = resultSet.getString("fsales_type"); // Sales Type
                    String biFlag = resultSet.getString("fbi_flag"); // BI Flag
                    String ecProductFlag = resultSet.getString("fec_product_flag"); // EC Product Flag

                    String sqlInsert = String.format("INSERT INTO `ProductCategory` " + "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `categoryId`, `isDeleted`, `name`, `companyId`, `hierarchyId`) " + "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),        // Created By
                            convertDate(createdDate),          // Created Date
                            escapeSqlString(updatedBy),        // Updated By
                            convertDate(updatedDate),          // Updated Date
                            escapeSqlString(categoryId),       // Category ID
                            0,                                 // isDeleted flag (assumed 0)
                            escapeSqlString(categoryValue),    // Name (categoryValue used for the name)
                            escapeSqlString(companyId),        // Company ID
                            escapeSqlString(categoryLevel)     // Hierarchy ID (categoryLevel)
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Product Category: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void accountMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\AccountMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_account"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Retrieve values from the result set
                    String createdBy = resultSet.getString("fcreated_by"); // Created by
                    String createdDate = resultSet.getString("fcreated_date"); // Created date
                    String updatedBy = resultSet.getString("fupdated_by"); // Updated by
                    String updatedDate = resultSet.getString("fupdated_date"); // Updated date
                    String accountId = resultSet.getString("faccountid"); // Account ID
                    String accountName = resultSet.getString("fname"); // First name (assuming this represents account name)
                    String address1 = resultSet.getString("faddress1"); // Address line 1
                    String address2 = resultSet.getString("faddress2"); // Address line 2
                    String address3 = resultSet.getString("faddress3"); // Address line 3
                    String altKey = null; // Alternate key (user ID)
                    String birthDate = resultSet.getString("fbirthdate"); // Birth date
                    String civilStatus = resultSet.getString("fcivil_status"); // Civil status
                    String contactPerson = resultSet.getString("fcontact"); // Contact person
                    String customerTypeId = resultSet.getString("fcustomertypeid"); // Customer type ID
                    String email = resultSet.getString("femail"); // Email
                    boolean enrolledInLoyaltyProgram = resultSet.getString("fnewsletter_flag").equals("1"); // Loyalty program flag
                    String extension = resultSet.getString("fext"); // Extension
                    String fax = resultSet.getString("ffax"); // Fax
                    String firstName = resultSet.getString("ffname"); // First name
                    String image = null; // Image (assuming not available)
                    boolean isAccountLock = resultSet.getString("faccountid").equals("1"); // Account lock flag
                    boolean isActive = resultSet.getString("factive_flag").equals("1"); // Active flag
                    boolean isAllowLogin = resultSet.getString("flogon_flag").equals("1"); // Allow login
                    boolean isCashier = resultSet.getString("fcashier_flag").equals("1"); // Cashier flag
                    boolean isClerk = resultSet.getString("fclerk_flag").equals("1"); // Clerk flag
                    boolean isCustomer = resultSet.getString("fcustomer_flag").equals("1"); // Customer flag
                    boolean isDeleted = false; // Set to false if not mapped from old DB
                    boolean isEmployee = resultSet.getString("femployee_flag").equals("1"); // Employee flag
                    boolean isSupplier = resultSet.getString("fsupplier_flag").equals("1"); // Supplier flag
                    String lastName = resultSet.getString("flname"); // Last name
                    String memberExpiry = resultSet.getString("ffsp_expiry"); // Member expiry date
                    String memo = resultSet.getString("fmemo"); // Memo
                    String middleName = resultSet.getString("fmname"); // Middle name
                    String mobile = resultSet.getString("fmobile"); // Mobile
                    String password2 = resultSet.getString("fpassword"); // Password
                    String passwordUpdate = resultSet.getString("fpassword_update"); // Password update date
                    String phone = resultSet.getString("fphone"); // Phone
                    String restrictToBranch = resultSet.getString("frestrict_office_flag"); // Restrict to branch
                    String salesmanId = resultSet.getString("fclerkid"); // Salesman ID
                    String sex = resultSet.getString("fsex"); // Sex
                    String taxType = resultSet.getString("ftax_type"); // Tax type
                    String terminalAccountGuid = null; // Terminal account GUID (assuming not available)
                    String tin = resultSet.getString("ftinno"); // TIN
                    String title = resultSet.getString("ftitle"); // Title
                    boolean transmit = false; // Set to false if not mapped from old DB
                    String accessLevelMasterId = resultSet.getString("faclvlid"); // Access level ID
                    String branchMasterId = resultSet.getString("fofficeid"); // Branch ID
                    String companyId = resultSet.getString("fcompanyid"); // Company ID
                    String groupTypeMasterId = resultSet.getString("fcustomertypeid");
                    ; // Group type master ID (assuming not available)
                    String nationalityMasterId = resultSet.getString("fnationalityid"); // Nationality ID
                    String priceLevelMasterId = resultSet.getString("fplevelid"); // Price level ID

// Construct the SQL INSERT statement
                    String sqlInsert = String.format("INSERT INTO `AccountMaster` " + // 7+ 7 + 7+ 8 + 8 + 8 + 6
                                    "(`createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `accountId`, `accountName`, `address1`, " + // 7
                                    "`address2`, `address3`, `altKey`, `birthDate`, `civilStatus`, `contactPerson`, `customerTypeId`, " + // 7
                                    "`email`, `enrolledInLoyaltyProgram`, `extension`, `fax`, `firstName`, `image`, `isAccountLock`, " + // 7
                                    "`isActive`, `isAllowLogin`, `isCashier`, `isClerk`, `isCustomer`, `isDeleted`, `isEmployee`, `isSupplier`, " + // 8
                                    "`lastName`, `memberExpiry`, `memo`, `middleName`, `mobile`, `password`, `passwordUpdate`, `phone`, " + // 8
                                    "`restrictToBranch`, `salesmanId`, `sex`, `taxType`, `terminalAccountGuid`, `tin`, `title`, `transmit`," + // 8
                                    " `accessLevelMasterId`, `branchMasterId`, `companyId`, `groupTypeMasterId`, `nationalityMasterId`, `priceLevelMasterId`) " + // 6
                                    "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %b, " + //16
                                    "%s, %s, %s, %s, %b, %b, %b, %b, %b, %b, %b, %b, %b, %b, %s, %s, %s, %s, " + "%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy), // Created by
                            convertDate(createdDate), // Created date
                            escapeSqlString(updatedBy), // Updated by
                            convertDate(updatedDate), // Updated date
                            escapeSqlString(accountId), // Account ID
                            escapeSqlString(accountName), // Account name
                            escapeSqlString(address1), // Address line 1
                            escapeSqlString(address2), // Address line 2
                            escapeSqlString(address3), // Address line 3
                            escapeSqlString(altKey), // Alternate key
                            Objects.equals(birthDate, "") ? null : escapeSqlString(birthDate), // Birth date
                            escapeSqlString(civilStatus), // Civil status
                            escapeSqlString(contactPerson), // Contact person
                            escapeSqlString(customerTypeId), // Customer type ID
                            escapeSqlString(email), // Email
                            enrolledInLoyaltyProgram, // Enrolled in loyalty program
                            escapeSqlString(extension), // Extension
                            escapeSqlString(fax), // Fax
                            escapeSqlString(firstName), // First name
                            escapeSqlString(image), // Image (assuming it's null or default)
                            isAccountLock, // Is account lock
                            isActive, // Is active
                            isAllowLogin, // Is allow login
                            isCashier, // Is cashier
                            isClerk, // Is clerk
                            isCustomer, // Is customer
                            isDeleted, // Is deleted
                            isEmployee, // Is employee
                            isSupplier, // Is supplier
                            escapeSqlString(lastName), // Last name
                            Objects.equals(memberExpiry, "") ? null : escapeSqlString(memberExpiry), // Member expiry
                            escapeSqlString(memo), // Memo
                            escapeSqlString(middleName), // Middle name
                            escapeSqlString(mobile), // Mobile
                            escapeSqlString(password2), // Password
                            Objects.equals(passwordUpdate, "") ? null : escapeSqlString(passwordUpdate), // Password update
                            escapeSqlString(phone), // Phone
                            (restrictToBranch == null || restrictToBranch.isBlank() || restrictToBranch.isEmpty()) ? 0 : escapeSqlString(restrictToBranch), // Restrict to branch
                            escapeSqlString(salesmanId), // Salesman ID
                            escapeSqlString(sex), // Sex
                            (taxType == null || taxType.isBlank() || taxType.isEmpty()) ? 0 : chooseForTaxType(taxType), // Tax type
                            escapeSqlString(terminalAccountGuid), // Terminal account GUID
                            escapeSqlString(tin), // TIN
                            escapeSqlString(title), // Title
                            transmit, // Transmit
                            chooseForAccessLevel(escapeSqlString(accessLevelMasterId)), // Access level master ID
                            chooseForBranchMasterId(escapeSqlString(branchMasterId)), // Branch master ID
                            escapeSqlString(companyId), // Company ID
                            chooseGroupTypeMaster(groupTypeMasterId), // Group type master ID
                            chooseNationalityMaster(nationalityMasterId), // Nationality master ID
                            choosePriceLevelMaster(priceLevelMasterId) // Price level master ID
                    );


                    // Write the SQL statement to the file
                    writer.write(sqlInsert);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Account Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void productMasterSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\ProductMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_product"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    // Assuming resultSet contains the data from your query
                    String companyId = resultSet.getString("fcompanyid");
                    String productId = resultSet.getString("fproductid");
                    String name = resultSet.getString("fname");
                    String activeFlag = resultSet.getString("factive_flag");
                    String saleFlag = resultSet.getString("fsale_flag");
                    String purchaseFlag = resultSet.getString("fpurchase_flag");
                    String costingMethod = resultSet.getString("fcosting_method");
                    String memo = resultSet.getString("fmemo");
                    BigDecimal stdCost = resultSet.getBigDecimal("fstnd_cost");
                    BigDecimal prevCost = resultSet.getBigDecimal("fprev_cost");
                    BigDecimal minPrice = resultSet.getBigDecimal("fmin_price");
                    BigDecimal markup = resultSet.getBigDecimal("fmarkup");
                    BigDecimal markCost = resultSet.getBigDecimal("fmarkcost");
                    BigDecimal listPrice = resultSet.getBigDecimal("flist_price");
                    String taxType = resultSet.getString("ftax_type");
                    String productType = resultSet.getString("fproduct_type");
                    String classId = resultSet.getString("fclassid");
                    String genericName = resultSet.getString("fgeneric_name");
                    String alternate1 = resultSet.getString("falternate1");
                    String alternate2 = resultSet.getString("falternate2");
                    String barcode1 = resultSet.getString("fbarcode1");
                    String barcode2 = resultSet.getString("fbarcode2");
                    String barcode3 = resultSet.getString("fbarcode3");
                    String partnerId = resultSet.getString("fpartnerid");
                    String uomId = resultSet.getString("fuomid");
                    String sellingUom = resultSet.getString("fselling_uom");
                    String purchaseUom = resultSet.getString("fpurchase_uom");
                    BigDecimal decimalQty = resultSet.getBigDecimal("fdecimal_qty");
                    BigDecimal alertQty = resultSet.getBigDecimal("falert_qty");
                    BigDecimal reorderQty = resultSet.getBigDecimal("freorder_qty");
                    BigDecimal maxQty = resultSet.getBigDecimal("fmax_qty");
                    String expiryAlert = resultSet.getString("fexpiry_alert");
                    String launchDate = resultSet.getString("flaunch_date");
                    String categoryValue1 = resultSet.getString("fcategory_value1");
                    String categoryValue2 = resultSet.getString("fcategory_value2");
                    String categoryValue3 = resultSet.getString("fcategory_value3");
                    String categoryValue4 = resultSet.getString("fcategory_value4");
                    String categoryValue5 = resultSet.getString("fcategory_value5");
                    String categoryValue6 = resultSet.getString("fcategory_value6");
                    String computeWarrantyFlag = resultSet.getString("fcompute_warranty_flag");
                    BigDecimal warrantyDuration = resultSet.getBigDecimal("fwarranty_duration");
                    String warrantyDurationType = resultSet.getString("fwarranty_duration_type");
                    String parentId = resultSet.getString("fparentid");
                    String subProductFlag = resultSet.getString("fsub_product_flag");
                    String attributeId1 = resultSet.getString("fattributeid1");
                    String attributeId2 = resultSet.getString("fattributeid2");
                    String attributeId3 = resultSet.getString("fattributeid3");
                    String attributeId4 = resultSet.getString("fattributeid4");
                    String attributeId5 = resultSet.getString("fattributeid5");
                    String attributeId6 = resultSet.getString("fattributeid6");
                    String accessMode = resultSet.getString("faccess_mode");
                    String ecFlag = resultSet.getString("fec_flag");
                    String modelNo = resultSet.getString("fmodel_no");
                    String variationId = resultSet.getString("fvariationid");
                    BigDecimal highlight = resultSet.getBigDecimal("fhighlight");
                    String tradeCode = resultSet.getString("ftrade_code");
                    String scTaxId = resultSet.getString("fsctaxid");
                    String amusementFlag = resultSet.getString("famusement_flag");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String shortName = resultSet.getString("fshort_name");
                    String fspEarnType = resultSet.getString("ffsp_earn_type");
                    BigDecimal fspEarnPoint = resultSet.getBigDecimal("ffsp_earn_point");
                    String singleLotFlag = resultSet.getString("fsingle_lot_flag");
                    String updatedDateIndex = resultSet.getString("fupdated_date_index");
                    String pwdTaxId = resultSet.getString("fpwd_taxid");
                    String thirdPartyId = resultSet.getString("fthirdpartyid");
                    String includeF3pLoyalty = resultSet.getString("finclude_f3p_loyalty");
                    String acpFlag = resultSet.getString("facp_flag");
                    String acpId = resultSet.getString("facpid");
                    String sellAcc = resultSet.getString("fsell_acc");
                    String promptProductMemoFlag = resultSet.getString("fprompt_product_memo_flag");
                    BigDecimal acquisitionCost = resultSet.getBigDecimal("facquisition_cost");
                    BigDecimal holdingRate = resultSet.getBigDecimal("fholding_rate");
                    BigDecimal leadTime = resultSet.getBigDecimal("flead_time");
                    BigDecimal moq = resultSet.getBigDecimal("fmoq");
                    byte[] description = resultSet.getBytes("fdescription");
                    String ecSdate = resultSet.getString("fec_sdate");
                    String ecEdate = resultSet.getString("fec_edate");

// SQL Insert Statement
                    String formattedSql = String.format("INSERT INTO ProductMaster " + "(createdBy, createdDate, updatedBy, updatedDate, altCode, amusementTax, barcode, durationType, ean13, " + "genericName, highlight, iSellOnWeb, iSellThisItem, image, isActive, isDeleted, isUniqueSerial, " + "isbn, listPrice, loyaltyComputation, loyaltyPoints, markUp, maxQty, memo, modelNo, name, " + "postToProductId, productId, reorderQty, shortName, standardCost, taxTypeId, trackExpiry, " + "trackWarranty, tradeCode, warrantyDuration, primarySupplierId, companyId, productClassId, " + "productTypeId, pwdTaxId, seniorTaxId, uomId, purchasingUnitId) " + "VALUES ( %s,%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %d, %d, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(createdBy),                    // 1
                            convertDate(createdDate),                      // 2
                            escapeSqlString(updatedBy),                     // 3
                            convertDate(updatedDate),                       // 4
                            null,                                           // 5: altCode
                            amusementFlag,                                  // 6: amusementTax
                            escapeSqlString(barcode1),                     // 7: barcode
                            warrantyDurationType,                           // 8: durationType
                            null,                                           // 9: ean13
                            escapeSqlString(genericName),                  // 10
                            highlight != null ? highlight : BigDecimal.ZERO,// 11
                            saleFlag != null && saleFlag.equals("1") ? 1 : 0, // 12: iSellOnWeb
                            purchaseFlag != null && purchaseFlag.equals("1") ? 1 : 0, // 13: iSellThisItem
                            null,                                           // 14: image
                            activeFlag != null && activeFlag.equals("1") ? 1 : 0, // 15: isActive
                            0,                                             // 16: isDeleted
                            0,                                             // 17: isUniqueSerial
                            null,                                           // 18: isbn
                            listPrice,                                     // 19
                            fspEarnPoint,                                  // 20: loyaltyComputation
                            fspEarnPoint,                                  // 21: loyaltyPoints
                            markup,                                        // 22
                            maxQty,                                        // 23
                            escapeSqlString(memo),                         // 24
                            escapeSqlString(modelNo),                      // 25
                            escapeSqlString(name),                         // 26
                            null,                                          // 27: postToProductId
                            escapeSqlString(productId),                    // 28
                            reorderQty,                                    // 29
                            escapeSqlString(shortName),                    // 30
                            stdCost,                                       // 31
                            chooseForTaxType(taxType),                      // 32
                            checkBooleanValueIsTrue(expiryAlert),                                             // 33: trackExpiry
                            0,                                             // 34: trackWarranty
                            escapeSqlString(tradeCode),                    // 35
                            warrantyDuration,                              // 36
                            null,                    // 37: primarySupplierId escapeSqlString(partnerId)
                            escapeSqlString(companyId),                    // 38
                            null,                      // 39: productClassId escapeSqlString(classId)
                            chooseForProductType(productType),                  // 40: productTypeId
                            null,                     // 41 escapeSqlString(pwdTaxId)
                            null,                                          // 42: seniorTaxId
                            chooseUomMaster(uomId),                        // 43: uomId
                            chooseUomConversion(purchaseUom)                   // 44: purchasingUnitId
                    );


                    if (chooseUomMaster(uomId) != null && chooseUomMaster(purchaseUom) != null) {
                        // Write the SQL statement to the file
                        writer.write(formattedSql);
                        writer.newLine(); // Add a new line for the next statement
                    }

                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Account Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void terminalMasterSQL(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TerminalMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String companyId = resultSet.getString("fcompanyid");
                    String broadCast = resultSet.getString("fbroadcast_ip");
                    String cashierId = resultSet.getString("fcashierid");
                    String diskSpace = resultSet.getString("fdiskspace");
                    Boolean http = resultSet.getBoolean("fhttp_server_flag");
                    String hw = resultSet.getString("fhwcon_version");
                    Boolean isActive = resultSet.getBoolean("factive_flag");
                    String licenseNo = resultSet.getString("flicense_no");
                    Integer limitedReleaseLvl = resultSet.getInt("flimited_release");
                    Integer logoVersion = resultSet.getInt("fpower_logo_version");
                    String memo = resultSet.getString("fmemo");
                    Double nacDiscountPercent = resultSet.getDouble("fnac_discount_percent");
                    String overrideLocalIP = resultSet.getString("foverride_local_ip");
                    String primaryIP = resultSet.getString("fip_address");
                    String secondaryIP = resultSet.getString("fsecondary_ip");
//                    Integer station = resultSet.getInt("fstationid");
                    String status = resultSet.getString("fstatus_flag");
                    Boolean stndAloneTm = resultSet.getBoolean("fstandalone_flag");
                    String tmId = resultSet.getString("ftermId");
                    String version = resultSet.getString("fversion");
                    String branchMasterId = resultSet.getString("fofficeid");
                    String warehouseMasterId = resultSet.getString("fsiteid");
                    String expiryDate = null;
                    String hostTerminal = null;
                    Boolean isDeleted = resultSet.getBoolean("factive_flag"); //temp
                    String lastSale = resultSet.getString("flast_post");
                    Boolean latest = false;

                    String operation = null;
                    String registrationKey = companyId + "-" + tmId + resultSet.getString("fsecret_key");
                    String terminalType = resultSet.getString("fterminal_flag");
                    String type = null;
                    String udp = null;

                    Integer versionDetail = resultSet.getInt("fpower_version");
                    Integer kitchenStationId = null;
                    Integer tmBasicId = null;
                    Integer tmCompId = null;
                    Integer tmHardwareId = null;
                    Integer tmReadingId = null;
                    Integer tmReceiptId = null;
                    Integer tmTenantId = null;


                    String query = """
                            INSERT INTO `TerminalMaster` (
                            `id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `companyId`,
                            `broadCast`, `cashierId`, `diskSpace`, `http`, `hw`, `isActive`,
                            `licenseNo`, `limitedReleaseLvl`, `logoVersion`, `memo`, `nacDiscountPercent`,
                            `overrideLocalIP`, `primaryIP`, `secondaryIP`, `status`,
                            `stndAloneTm`, `tmId`, `version`, `branchMasterId`, `warehouseMasterId`,
                            `expiryDate`, `hostTerminal`, `isDeleted`, `lastSale`, `latest`, `operation`,
                            `registrationKey`, `terminalType`, `type`, `udp`, `versionDetail`, `kitchenStationId`,
                            `tmBasicId`, `tmCompId`, `tmHardwareId`, `tmReadingId`, `tmReceiptId`, `tmTenantId`    
                            )
                            VALUES (%d, %s, %s, %s, %s, %s,
                            %s, %s, %s, %b, %s,%b,
                            %s, %d, %d, %s, %f,
                            %s, %s, %s,%s,
                            %b, %s, %s, %s, %s,
                            %s, %s, %b, %s, %b,
                            %s, %s, %s, %s, %s,
                            %d, %d, %d, %d, %d, %d, %d, %d
                            );
                            """;

                    String formattedSql = String.format(
                            query, id, escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy),
                            convertDate(updatedDate), escapeSqlString(companyId), escapeSqlString(broadCast), escapeSqlString(cashierId),
                            escapeSqlString(diskSpace), http, escapeSqlString(hw), isActive, escapeSqlString(licenseNo),
                            limitedReleaseLvl, logoVersion, escapeSqlString(memo), nacDiscountPercent, escapeSqlString(overrideLocalIP),
                            escapeSqlString(primaryIP), escapeSqlString(secondaryIP), escapeSqlString(status), stndAloneTm, escapeSqlString(tmId),
                            escapeSqlString(version), chooseForBranchMasterId(branchMasterId), chooseForWarehouseMasterId(warehouseMasterId),
                            expiryDate, hostTerminal, isDeleted, lastSale, latest,
                            operation, escapeSqlString(registrationKey), escapeSqlString(chooseTerminalType(terminalType)), type, udp, versionDetail,
                            kitchenStationId, tmBasicId, tmCompId, tmHardwareId, tmReadingId, tmReceiptId,tmTenantId
                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Terminal Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void tmHardwareSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmHardware.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    Boolean allowCreditEFTPOS = resultSet.getBoolean("fallow_eft_settle");
                    Boolean allowSetterBeepTm = resultSet.getBoolean("fallow_beep");
//                    Integer customerRotary = resultSet.getInt("frotary_type");
                    Integer customerRotary = null;
                    Boolean deletePrinterStatus = false; //temp
                    Boolean detectDrawerStats = resultSet.getBoolean("fdrawer_status_avail");
                    Boolean detectDrawerStats2 = resultSet.getBoolean("fdrawer2_status_avail");
                    String drawer = resultSet.getString("fdrawer_type");
                    String drawer2 = resultSet.getString("fdrawer2_type");
                    String drawer2CtrlDevice = resultSet.getString("fdrawer2_device");
                    String drawerCtrlDevice = resultSet.getString("fdrawer_device");
                    Integer eftposDevice = resultSet.getInt("fallow_eft_settle");;
                    Boolean enableBlackRedPrinting = resultSet.getBoolean("fepson_print_red_flag");
                    Boolean enableDualCashDrawer = resultSet.getBoolean("fenable_dual_cash_drawer");
                    Boolean enableDualCashierDrawer2 = false; //temp
                    String printer = resultSet.getString("fprinter_type");
                    String printerCtrlDevice = resultSet.getString("fprinter_device");
                    Integer printerQueue = resultSet.getInt("fprint_queue");
                    String rotaryCtrlDevice = resultSet.getString("frotary_device");;

                    String standByMsg1 = resultSet.getString("fstandby1");
                    String standByMsg2 = resultSet.getString("fstandby2");
                    String welcomeMsg1 = resultSet.getString("fwelcome1");
                    String welcomeMsg2 = resultSet.getString("fwelcome2");

                    Integer terminalMasterId = resultSet.getInt("ftermId");
                    Integer tmHardwareAdjustmentId = resultSet.getInt("ftermId");
                    Integer tmHardwareCountSheetId = resultSet.getInt("ftermId");
                    Integer tmHardwareReceiptId = resultSet.getInt("ftermId");
                    Integer tmHardwareStockCountId = resultSet.getInt("ftermId");
                    Integer tmHardwareTransferId = resultSet.getInt("ftermId");

                    String query = """
                                INSERT INTO `TmHardware` (
                                    `id`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`,
                                    `allowCreditEFTPOS`, `allowSetterBeepTm`, `customerRotary`, `deletePrinterStatus`,
                                    `detectDrawerStats`, `detectDrawerStats2`, `drawer`, `drawer2`,
                                    `drawer2CtrlDevice`, `drawerCtrlDevice`, `eftposDevice`, `enableBlackRedPrinting`,
                                    `enableDualCashDrawer`, `enableDualCashierDrawer2`, `printer`, `printerCtrlDevice`,
                                    `printerQueue`, `rotaryCtrlDevice`, `standByMsg1`, `standByMsg2`,
                                    `welcomeMsg1`, `welcomeMsg2`, `terminalMasterId`, `tmHardwareAdjustmentId`, 
                                    `tmHardwareCountSheetId`, `tmHardwareReceiptId`, `tmHardwareStockCountId`, 
                                    `tmHardwareTransferId`
                                )
                                VALUES (
                                    %d, %s, %s, %s, %s,
                                    %b, %b, %d, %b,
                                    %b, %b, %d, %d,
                                    %s, %s, %d, %b,
                                    %b, %b, %d, %s,
                                    %d, %s, %s, %s,
                                    %s, %s, %d, %d,
                                    %d, %d, %d, %d
                                );
                            """;


                    String formattedSql = String.format(query,
                            id, escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate),
                            allowCreditEFTPOS, allowSetterBeepTm, customerRotary, deletePrinterStatus,
                            detectDrawerStats, detectDrawerStats2, chooseDrawer(drawer), chooseDrawer(drawer2),
                            escapeSqlString(drawer2CtrlDevice), escapeSqlString(drawerCtrlDevice), eftposDevice, enableBlackRedPrinting,
                            enableDualCashDrawer, enableDualCashierDrawer2, choosePrinter(printer), escapeSqlString(printerCtrlDevice),
                            printerQueue, escapeSqlString(rotaryCtrlDevice), escapeSqlString(standByMsg1), escapeSqlString(standByMsg2),
                            escapeSqlString(welcomeMsg1), escapeSqlString(welcomeMsg2), terminalMasterId, null,
                            null, null, null, null);

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmHardware: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void tmBasicSettingSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmBasicSetting.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    Boolean accumulateSimilarProdDuringScan = resultSet.getBoolean("faccumulate_product");
                    Boolean activateFeedbackRate = resultSet.getBoolean("ffeedback_flag");
                    Integer allowCash = resultSet.getInt("fallow_cash");
                    Boolean allowChangeCustIfSet = resultSet.getBoolean("fstrict_customer_change");
                    Boolean allowChangeForCheck = resultSet.getBoolean("fchangeable_check");
                    Boolean allowChangeForCredit = resultSet.getBoolean("fchangeable_credit");
                    Boolean allowChangeForDebit = resultSet.getBoolean("fchangeable_debit");
                    Boolean allowChangeNonCash = resultSet.getBoolean("fchangeable_coupon");
                    Integer allowCharge = resultSet.getInt("fallow_charge");
                    Boolean allowChargeToCust = resultSet.getBoolean("fstrict_customer_charge");
                    Integer allowCheck = resultSet.getInt("fallow_check");
                    Integer allowCredit = resultSet.getInt("fallow_credit");
                    Integer allowDebit = resultSet.getInt("fallow_debit");
                    Integer allowGCCoupon = resultSet.getInt("fallow_coupon");
                    Boolean allowInvTrans = resultSet.getBoolean("finventory_transaction_flag");
                    Boolean allowNonCashPayment = resultSet.getBoolean("fallow_overcharge");
                    Boolean allowSalesOtherTm = resultSet.getBoolean("fallow_open_record");
                    Integer allowSalesReturnWithin = resultSet.getInt("fsales_return_max_days");
                    Integer allowTerminalSettle = resultSet.getInt("fallow_settle");
                    Boolean autoSearchProdSKU = resultSet.getBoolean("fauto_search_product");
                    Integer daysToRetainSalesTm = resultSet.getInt("fretain_sale_days");
                    Integer debugModeMins = resultSet.getInt("fdebug_interval");
                    Boolean detectQtyInSKU = resultSet.getBoolean("fauto_qty");
                    Boolean disableCancelBtn = false; //temp
                    Boolean disableEndOfDay = false; //temp
                    Boolean disableF11LotSn = false; //temp
                    Boolean disableF12Memo = false; //temp
                    Boolean disableF2Qty = false; //temp
                    Boolean disableF3UOM = false; //temp
                    Boolean disableF4Clerk = false; //temp
                    Boolean disableF5Discount = false; //temp
                    Boolean disableF6SeniorPwd = false; //temp
                    Boolean disableF7PriceLevel = false; //temp
                    Boolean disableF8Frank = false; //temp
                    Boolean disableF9SellPrice = false; //temp
                    Integer disableKeyboardForCredentials = resultSet.getInt("fdisable_login_keyboard");
                    Boolean disableLoyaltyCust = false; //temp
                    Boolean disableOkBtn = false; //temp
                    Boolean disableSplitBill = resultSet.getBoolean("fdisable_split_flag");
                    Boolean disableTransferMergeTable = resultSet.getBoolean("fdisable_table_movement_flag");
                    Boolean disableWarehouse = false; //temp
                    Boolean displayCashierReport = false; //temp
                    Boolean displayExportSales = false; //temp
                    Boolean displayF1OptionBtn = false; //temp
                    Boolean displayResetConfig = false; //temp
                    Integer downloadDataEveryMin = resultSet.getInt("fdownload_interval_minutes");
                    Boolean enableDebugMode = resultSet.getBoolean("fenable_debugging_flag");
                    Boolean enableOpenSalesBook = false; //temp
                    Boolean enableScreenKeyboard = resultSet.getBoolean("ftouch_flag");
                    Boolean enableSound = resultSet.getBoolean("fsound_flag");
                    Boolean genInvPostingOL = resultSet.getBoolean("fcheck_inventory_flag");
                    Boolean hideOptionBtnLogin = false; //temp
                    Boolean hideToolbarInMainScreen = false; //temp
                    Integer indicateOccupantDur1 = resultSet.getInt("fhg_wait1");
                    Integer indicateOccupantDur2 = resultSet.getInt("fhg_wait2");
                    Integer indicateOccupantDur3 = resultSet.getInt("fhg_wait3");
                    Boolean lotInvPostingOL = resultSet.getBoolean("fcheck_inventory_lot_flag");
                    Boolean lotProdSoldOnceOL = resultSet.getBoolean("fsingle_lot_flag");
                    Boolean openDrawerForNonCash = resultSet.getBoolean("fnon_drawer_cash_flag");
                    Boolean overrideCashierLogo = resultSet.getBoolean("ffront_logo_flag");
                    String overrideCashierLogoImg = null;
                    Boolean overrideCustBottomLogo = resultSet.getBoolean("fback_bottom_logo_flag");
                    String overrideCustBottomLogoImg = null;
                    Boolean overrideCustLogo = resultSet.getBoolean("fback_logo_flag");
                    String overrideCustLogoImg = null;
                    Boolean performUpsell = resultSet.getBoolean("fupsell_flag");
                    Integer printProdStation = resultSet.getInt("fprint_kitchen_settle_flag");
                    Integer productButtonSize = resultSet.getInt("fproduct_button_size");
                    Integer promptCancelReasonProd = resultSet.getInt("fprompt_cancel_product_flag");
                    Integer promptCancelReasonTrans = resultSet.getInt("fprompt_cancel_flag");
                    Boolean promptClerkDuringOrder = resultSet.getBoolean("fprompt_clerk_order_flag");
                    Boolean promptClerkWhenAddProd = resultSet.getBoolean("fprompt_clerk_line");
                    Integer promptLotNo = resultSet.getInt("fprompt_lot");
                    Integer promptProdMemo = resultSet.getInt("fprompt_product_memo_flag");
                    Integer promptProdWarehouse = resultSet.getInt("fprompt_site_flag");
                    Boolean reqClerkDuringSettlement = resultSet.getBoolean("frequired_clerk");
                    Boolean reqCreditApprovalCode = resultSet.getBoolean("fcc_approval_code_required_flag");
                    Boolean reqCustDuringSettlement = resultSet.getBoolean("frequired_customer");
                    Boolean reqDetailsGovDisc = resultSet.getBoolean("frequire_govt_detail");
                    Integer resetTicketNo = resultSet.getInt("fticket_reset_frequency");
                    Boolean searchOnlyProdName = resultSet.getBoolean("fticket_reset_frequency");
                    Boolean searchProdServerOL = resultSet.getBoolean("fsearch_product_online_flag");
                    Boolean setCustCount = resultSet.getBoolean("fverify_pax_on_order");
                    Boolean setSchedSync = resultSet.getBoolean("fschedule_online_flag");
                    Boolean showPriceInProduct = resultSet.getBoolean("fshow_product_price_flag");
                    Boolean specifyIndividualItem = resultSet.getBoolean("fmultiple_choices_flag");
                    Integer tmAutolockInactiveMin = resultSet.getInt("fauto_lock_time");
                    Boolean trackInventoryOffline = resultSet.getBoolean("foffline_stock_status_flag");
                    Integer uploadDataEveryMin = resultSet.getInt("fupload_interval_minutes");
                    Boolean verifyCustCount = resultSet.getBoolean("fverify_customer_count_flag");
                    Integer verifyLotNo = resultSet.getInt("fcheck_lot_flag");
                    Integer priceLevelMasterId = resultSet.getInt("fplevelid");
                    Integer terminalMasterId = resultSet.getInt("ftermId");
                    Integer tmF1OptionsId = null;


                    String query = """
                            INSERT INTO `TmBasicSetting` (
                                        accumulateSimilarProdDuringScan,
                                        activateFeedbackRate,
                                        allowChangeCustIfSet,
                                        allowChangeForCheck,
                                        allowChangeForCredit,
                                        allowChangeForDebit,
                                        allowChangeNonCash,
                                        allowChargeToCust,
                                        allowInvTrans,
                                        allowNonCashPayment,
                                        allowSalesOtherTm,
                                        autoSearchProdSKU,
                                        detectQtyInSKU,
                                        disableCancelBtn,
                                        disableEndOfDay,
                                        disableF11LotSn,
                                        disableF12Memo,
                                        disableF2Qty,
                                        disableF3UOM,
                                        disableF4Clerk,
                                        disableF5Discount,
                                        disableF6SeniorPwd,
                                        disableF7PriceLevel,
                                        disableF8Frank,
                                        disableF9SellPrice,
                                        disableLoyaltyCust,
                                        disableOkBtn,
                                        disableSplitBill,
                                        disableTransferMergeTable,
                                        disableWarehouse,
                                        displayCashierReport,
                                        displayExportSales,
                                        displayF1OptionBtn,
                                        displayResetConfig,
                                        enableDebugMode,
                                        enableOpenSalesBook,
                                        enableScreenKeyboard,
                                        enableSound,
                                        genInvPostingOL,
                                        hideOptionBtnLogin,
                                        hideToolbarInMainScreen,
                                        lotInvPostingOL,
                                        lotProdSoldOnceOL,
                                        openDrawerForNonCash,
                                        overrideCashierLogo,
                                        overrideCustBottomLogo,
                                        overrideCustLogo,
                                        performUpsell,
                                        promptClerkDuringOrder,
                                        promptClerkWhenAddProd,
                                        reqClerkDuringSettlement,
                                        reqCreditApprovalCode,
                                        reqCustDuringSettlement,
                                        reqDetailsGovDisc,
                                        searchOnlyProdName,
                                        searchProdServerOL,
                                        setCustCount,
                                        setSchedSync,
                                        showPriceInProduct,
                                        specifyIndividualItem,
                                        trackInventoryOffline,
                                        verifyCustCount,
                                        allowCash,
                                        allowCharge,
                                        allowCheck,
                                        allowCredit,
                                        allowDebit,
                                        allowGCCoupon,
                                        allowSalesReturnWithin,
                                        allowTerminalSettle,
                                        daysToRetainSalesTm,
                                        debugModeMins,
                                        disableKeyboardForCredentials,
                                        downloadDataEveryMin,
                                        id,
                                        indicateOccupantDur1,
                                        indicateOccupantDur2,
                                        indicateOccupantDur3,
                                        priceLevelMasterId,
                                        printProdStation,
                                        productButtonSize,
                                        promptCancelReasonProd,
                                        promptCancelReasonTrans,
                                        promptLotNo,
                                        promptProdMemo,
                                        promptProdWarehouse,
                                        resetTicketNo,
                                        terminalMasterId,
                                        tmAutolockInactiveMin,
                                        tmF1OptionsId,
                                        uploadDataEveryMin,
                                        verifyLotNo,
                                        createdBy,
                                        createdDate,
                                        overrideCashierLogoImg,
                                        overrideCustBottomLogoImg,
                                        overrideCustLogoImg,
                                        updatedBy,
                                        updatedDate
                                )
                                VALUES (
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %b,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %d,
                                    %s,
                                    %s,
                                    %s,
                                    %s,
                                    %s,
                                    %s,
                                    %s
                                );
                            """;



                    String formattedSql = String.format(query,

                            accumulateSimilarProdDuringScan,
                            activateFeedbackRate,
                            allowChangeCustIfSet,
                            allowChangeForCheck,
                            allowChangeForCredit,
                            allowChangeForDebit,
                            allowChangeNonCash,
                            allowChargeToCust,
                            allowInvTrans,
                            allowNonCashPayment,
                            allowSalesOtherTm,
                            autoSearchProdSKU,
                            detectQtyInSKU,
                            disableCancelBtn,
                            disableEndOfDay,
                            disableF11LotSn,
                            disableF12Memo,
                            disableF2Qty,
                            disableF3UOM,
                            disableF4Clerk,
                            disableF5Discount,
                            disableF6SeniorPwd,
                            disableF7PriceLevel,
                            disableF8Frank,
                            disableF9SellPrice,
                            disableLoyaltyCust,
                            disableOkBtn,
                            disableSplitBill,
                            disableTransferMergeTable,
                            disableWarehouse,
                            displayCashierReport,
                            displayExportSales,
                            displayF1OptionBtn,
                            displayResetConfig,
                            enableDebugMode,
                            enableOpenSalesBook,
                            enableScreenKeyboard,
                            enableSound,
                            genInvPostingOL,
                            hideOptionBtnLogin,
                            hideToolbarInMainScreen,
                            lotInvPostingOL,
                            lotProdSoldOnceOL,
                            openDrawerForNonCash,
                            overrideCashierLogo,
                            overrideCustBottomLogo,
                            overrideCustLogo,
                            performUpsell,
                            promptClerkDuringOrder,
                            promptClerkWhenAddProd,
                            reqClerkDuringSettlement,
                            reqCreditApprovalCode,
                            reqCustDuringSettlement,
                            reqDetailsGovDisc,
                            searchOnlyProdName,
                            searchProdServerOL,
                            setCustCount,
                            setSchedSync,
                            showPriceInProduct,
                            specifyIndividualItem,
                            trackInventoryOffline,
                            verifyCustCount,
                            allowCash,
                            allowCharge,
                            allowCheck,
                            allowCredit,
                            allowDebit,
                            allowGCCoupon,
                            allowSalesReturnWithin,
                            allowTerminalSettle,
                            daysToRetainSalesTm,
                            debugModeMins,
                            disableKeyboardForCredentials,
                            downloadDataEveryMin,
                            id,
                            indicateOccupantDur1,
                            indicateOccupantDur2,
                            indicateOccupantDur3,
                            priceLevelMasterId == 0 ? null : priceLevelMasterId,
                            printProdStation,
                            productButtonSize,
                            promptCancelReasonProd,
                            promptCancelReasonTrans,
                            promptLotNo,
                            promptProdMemo,
                            promptProdWarehouse,
                            resetTicketNo,
                            terminalMasterId,
                            tmAutolockInactiveMin,
                            tmF1OptionsId,
                            uploadDataEveryMin,
                            verifyLotNo,
                            escapeSqlString(createdBy),
                            convertDate(createdDate),
                            overrideCashierLogoImg,
                            overrideCustBottomLogoImg,
                            overrideCustLogoImg,
                            escapeSqlString(updatedBy),
                            convertDate(updatedDate)

                            );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmBasicSetting: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmHardwareReceiptSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmHardwareReceipt.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    Boolean receiptDisplayDiscount = resultSet.getBoolean("ftemplate_single_discount_flag");
                    Boolean receiptHideProductPrice= resultSet.getBoolean("ftemplate_hide_price_display");
                    Boolean receiptHideTaxInfo= resultSet.getBoolean("ftemplate_hide_tax_display");
                    Integer receiptNoOfDetailLines = resultSet.getInt("ftemplate_product_line_count");
                    Boolean receiptOverrideTemplate= resultSet.getBoolean("ftemplate_override_flag");
                    String receiptPrintFormat= null;
                    Integer receiptPrintLotNo= resultSet.getInt("ftemplate_print_lot_flag");
                    Boolean receiptPrintPaymentInfo= resultSet.getBoolean("ftemplate_show_payment");
                    Integer tmHardwareId = resultSet.getInt("ftermId");;

                    String query = """
                            INSERT INTO `TmHardwareReceipt` (
                                  id, receiptDisplayDiscount, receiptHideProductPrice,
                                  receiptHideTaxInfo, receiptNoOfDetailLines, receiptOverrideTemplate,
                                  receiptPrintFormat, receiptPrintLotNo, receiptPrintPaymentInfo,
                                  tmHardwareId
                                )
                                VALUES (
                                   %d, %b, %b, %b, %d, %b, %s, %d ,%b ,%d
                                );
                            """;



                    String formattedSql = String.format(query,
                            id, receiptDisplayDiscount, receiptHideProductPrice,
                            receiptHideTaxInfo, receiptNoOfDetailLines, receiptOverrideTemplate,
                            receiptPrintFormat, receiptPrintLotNo, receiptPrintPaymentInfo,
                            tmHardwareId
                    );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmHardwareReceipt: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmHardwareTransferSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmHardwareTransfer.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    Integer transferNoOfDetailLines = resultSet.getInt("finv_transfer_template_product_line_count");
                    Boolean transferOverrideTemplate= resultSet.getBoolean("finv_transfer_template_override_flag");
                    String transferPrintFormat= null;
                    Integer transferPrintLotNo= resultSet.getInt("finv_transfer_template_print_lot_flag");
                    Integer tmHardwareId = resultSet.getInt("ftermId");;

                    String query = """
                            INSERT INTO `TmHardwareTransfer` (
                                  id, transferNoOfDetailLines, transferOverrideTemplate,
                                  transferPrintFormat, transferPrintLotNo, tmHardwareId
                                )
                                VALUES (
                                   %d, %d, %b, %s, %d, %d
                                );
                            """;

                    String formattedSql = String.format(query,
                            id, transferNoOfDetailLines, transferOverrideTemplate,
                            transferPrintFormat, transferPrintLotNo, tmHardwareId
                    );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmHardwareTransfer: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmHardwareStockCountSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmHardwareStockCount.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    Integer stockCountNoOfDetailLines = resultSet.getInt("finv_count_template_product_line_count");
                    Boolean stockCountOverrideTemplate= resultSet.getBoolean("finv_count_template_override_flag");
                    String stockCountPrintFormat= null;
                    Integer stockCountPrintLotNo= resultSet.getInt("finv_count_template_print_lot_flag");
                    Integer tmHardwareId = resultSet.getInt("ftermId");;

                    String query = """
                            INSERT INTO `TmHardwareStockCount` (
                                  id, stockCountNoOfDetailLines, stockCountOverrideTemplate,
                                  stockCountPrintFormat, stockCountPrintLotNo, tmHardwareId
                                )
                                VALUES (
                                   %d, %d, %b, %s, %d, %d
                                );
                            """;

                    String formattedSql = String.format(query,
                            id, stockCountNoOfDetailLines, stockCountOverrideTemplate,
                            stockCountPrintFormat, stockCountPrintLotNo, tmHardwareId
                    );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmHardwareStockCount: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmHardwareCountSheetSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmHardwareCountSheet.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    Integer countSheetNoOfDetailLines = resultSet.getInt("finv_count_sheet_template_product_line_count");
                    Boolean countSheetOverrideTemplate= resultSet.getBoolean("finv_count_sheet_template_override_flag");
                    String countSheetPrintFormat= null;
                    Integer countSheetPrintLotNo= resultSet.getInt("finv_count_sheet_template_print_lot_flag");
                    Integer tmHardwareId = resultSet.getInt("ftermId");;

                    String query = """
                            INSERT INTO `TmHardwareCountSheet` (
                                  id, countSheetNoOfDetailLines, countSheetOverrideTemplate,
                                  countSheetPrintFormat, countSheetPrintLotNo, tmHardwareId
                                )
                                VALUES (
                                   %d, %d, %b, %s, %d, %d
                                );
                            """;

                    String formattedSql = String.format(query,
                            id, countSheetNoOfDetailLines, countSheetOverrideTemplate,
                            countSheetPrintFormat, countSheetPrintLotNo, tmHardwareId
                    );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmHardwareCountSheet: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmHardwareAdjustment(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmHardwareAdjustment.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    Integer adjustmentNoOfDetailLines = resultSet.getInt("finv_adjust_template_product_line_count");
                    Boolean adjustmentOverrideTemplate= resultSet.getBoolean("finv_adjust_template_override_flag");
                    String adjustmentPrintFormat= null;
                    Integer adjustmentPrintLotNo= resultSet.getInt("finv_adjust_template_print_lot_flag");
                    Integer tmHardwareId = resultSet.getInt("ftermId");;

                    String query = """
                            INSERT INTO `TmHardwareAdjustment` (
                                  id, adjustmentNoOfDetailLines, adjustmentOverrideTemplate,
                                  adjustmentPrintFormat, adjustmentPrintLotNo, tmHardwareId
                                )
                                VALUES (
                                   %d, %d, %b, %s, %d, %d
                                );
                            """;

                    String formattedSql = String.format(query,
                            id, adjustmentNoOfDetailLines, adjustmentOverrideTemplate,
                            adjustmentPrintFormat, adjustmentPrintLotNo, tmHardwareId
                    );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmHardwareAdjustment: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmReceipt(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmReceipt.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    Boolean activatePrintingAttr = resultSet.getBoolean("fattribute");
                    String bodyOfEmail = "Default"; //temp
                    Boolean duplicateIfCashTender = resultSet.getBoolean("faddl_cash_receipt");
                    Boolean duplicateIfChargeTender = resultSet.getBoolean("faddl_charge_receipt");
                    Boolean duplicateIfCheckTender = resultSet.getBoolean("faddl_check_receipt");
                    Boolean duplicateIfCouponTender = resultSet.getBoolean("faddl_coupon_receipt");
                    Boolean duplicateIfCreditTender = resultSet.getBoolean("faddl_credit_receipt");
                    Boolean duplicateIfDebitTender = resultSet.getBoolean("faddl_debit_receipt");
                    String footer1 = resultSet.getString("ffooter1");
                    String footer2 = resultSet.getString("ffooter2");
                    String footer3 = resultSet.getString("ffooter3");
                    String footer4 = resultSet.getString("ffooter4");
                    String footer5 = resultSet.getString("ffooter5");
                    String header1 = resultSet.getString("fheader1");
                    String header2 = resultSet.getString("fheader2");
                    String header3 = resultSet.getString("fheader3");
                    String header4 = resultSet.getString("fheader4");
                    String header5 = resultSet.getString("fheader5");
                    Boolean hideNonTaxableSales = resultSet.getBoolean("fhide_notax_sale");
                    Boolean hideTaxInfo = resultSet.getBoolean("fhide_tax_display");
                    Boolean hideTaxSuffix = resultSet.getBoolean("fhide_tax_suffix");
                    Integer maxCharPerLineNorm = resultSet.getInt("fmaxcol");
                    Integer maxCharPerLineWide = resultSet.getInt("fmaxcolwide");
                    Integer maxLinePerPage = resultSet.getInt("fmaxlines");
                    String nonReceiptFooter1 = resultSet.getString("fnon_receipt_footer1");
                    String nonReceiptFooter2 = resultSet.getString("fnon_receipt_footer2");
                    String nonReceiptFooter3 = resultSet.getString("fnon_receipt_footer3");
                    String nonReceiptFooter4 = resultSet.getString("fnon_receipt_footer4");
                    String nonReceiptFooter5 = resultSet.getString("fnon_receipt_footer5");
                    String nonReceiptHeader1 = resultSet.getString("fnon_receipt_header1");
                    String nonReceiptHeader2 = resultSet.getString("fnon_receipt_header2");
                    String nonReceiptHeader3 = resultSet.getString("fnon_receipt_header3");
                    String nonReceiptHeader4 = resultSet.getString("fnon_receipt_header4");
                    String nonReceiptHeader5 = resultSet.getString("fnon_receipt_header5");
                    Integer numOfLinesAfterFooter = resultSet.getInt("ftrailing_space");
                    Integer numOfLinesBeforeHeader = resultSet.getInt("fleading_space");
                    Boolean overrideEmailReceiptTemplate = resultSet.getBoolean("foverride_ereceipt_template");
                    Boolean overridePrintOutsForNonReceipt = resultSet.getBoolean("foverride_footer");
                    Boolean overridePrintOutsForReadings = resultSet.getBoolean("foverride_reading");
                    Boolean printHeader = resultSet.getBoolean("fpreprint");
                    Boolean printLotAndExpiryDate = resultSet.getBoolean("fprint_lot_flag");
                    Boolean printMemo = resultSet.getBoolean("fprint_product_memo_flag");
                    Boolean printProductDesc = resultSet.getBoolean("fprint_product_receipt_flag");
                    Boolean printSavedTransaction = resultSet.getBoolean("fprint_saved_flag");
                    Boolean printServiceBy = resultSet.getBoolean("fprint_product_service_flag");
                    Boolean printSnrListBillOut = resultSet.getBoolean("fprint_senior_bill_flag");
                    Boolean printSnrListReceipt = resultSet.getBoolean("fprint_senior_flag");
                    Boolean printStoreCopy = resultSet.getBoolean("fprint_acctg_copy");
                    Boolean printTableStatus = resultSet.getBoolean("fprint_table_trail_flag");
                    Boolean printVoidTrans = resultSet.getBoolean("fprint_void_transaction");
                    String readingsFooter1 = resultSet.getString("freading_footer1");
                    String readingsFooter2 = resultSet.getString("freading_footer2");
                    String readingsFooter3 = resultSet.getString("freading_footer3");
                    String readingsFooter4 = resultSet.getString("freading_footer4");
                    String readingsFooter5 = resultSet.getString("freading_footer5");
                    String readingsHeader1 = resultSet.getString("freading_header1");
                    String readingsHeader2 = resultSet.getString("freading_header2");
                    String readingsHeader3 = resultSet.getString("freading_header3");
                    String readingsHeader4 = resultSet.getString("freading_header4");
                    String readingsHeader5 = resultSet.getString("freading_header5");
                    String receiptLabel = resultSet.getString("freceipt_prefix");
                    String replyToEmail = resultSet.getString("fereceipt_replyto_email");
                    String replyToName = resultSet.getString("fereceipt_replyto_name");
                    String subjectOfEmail = resultSet.getString("fereceipt_email_subject");
                    Long terminalMasterId = resultSet.getLong("ftermId");


                    String query = """
                            INSERT INTO `TmReceipt` (
                                  activatePrintingAttr, duplicateIfCashTender, duplicateIfChargeTender, duplicateIfCheckTender, duplicateIfCouponTender,
                                  duplicateIfCreditTender, duplicateIfDebitTender, hideNonTaxableSales, hideTaxInfo, hideTaxSuffix,
                                  overrideEmailReceiptTemplate, overridePrintOutsForNonReceipt, overridePrintOutsForReadings, printHeader, printLotAndExpiryDate,
                                  printMemo, printProductDesc, printSavedTransaction, printServiceBy, printSnrListBillOut,
                                  printSnrListReceipt, printStoreCopy, printTableStatus, printVoidTrans, id,
                                  maxCharPerLineNorm, maxCharPerLineWide, maxLinePerPage, numOfLinesAfterFooter, numOfLinesBeforeHeader,
                                  terminalMasterId, bodyOfEmail, footer1, footer2, footer3,
                                  footer4, footer5, header1, header2, header3,
                                  header4, header5, nonReceiptFooter1, nonReceiptFooter2, nonReceiptFooter3,
                                  nonReceiptFooter4, nonReceiptFooter5, nonReceiptHeader1, nonReceiptHeader2, nonReceiptHeader3,
                                  nonReceiptHeader4, nonReceiptHeader5, readingsFooter1, readingsFooter2, readingsFooter3,
                                  readingsFooter4, readingsFooter5, readingsHeader1, readingsHeader2,
                                  readingsHeader3, readingsHeader4, readingsHeader5, receiptLabel,
                                  replyToEmail, replyToName, subjectOfEmail
                                )
                                VALUES (
                                   %b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%d,%d,
                                   %d,%d,%d,%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,
                                   %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s
                                );
                            """;

                    String formattedSql = String.format(query,
                            activatePrintingAttr, duplicateIfCashTender, duplicateIfChargeTender, duplicateIfCheckTender, duplicateIfCouponTender,
                            duplicateIfCreditTender, duplicateIfDebitTender, hideNonTaxableSales, hideTaxInfo, hideTaxSuffix,
                            overrideEmailReceiptTemplate, overridePrintOutsForNonReceipt, overridePrintOutsForReadings, printHeader, printLotAndExpiryDate,
                            printMemo, printProductDesc, printSavedTransaction, printServiceBy, printSnrListBillOut,
                            printSnrListReceipt, printStoreCopy, printTableStatus, printVoidTrans, id,
                            maxCharPerLineNorm, maxCharPerLineWide, maxLinePerPage, numOfLinesAfterFooter, numOfLinesBeforeHeader,
                            terminalMasterId,
                            escapeSqlString(bodyOfEmail), escapeSqlString(footer1), escapeSqlString(footer2), escapeSqlString(footer3),
                            escapeSqlString(footer4), escapeSqlString(footer5), escapeSqlString(header1),
                            escapeSqlString(header2), escapeSqlString(header3), escapeSqlString(header4),
                            escapeSqlString(header5), escapeSqlString(nonReceiptFooter1), escapeSqlString(nonReceiptFooter2),
                            escapeSqlString(nonReceiptFooter3), escapeSqlString(nonReceiptFooter4),
                            escapeSqlString(nonReceiptFooter5), escapeSqlString(nonReceiptHeader1),
                            escapeSqlString(nonReceiptHeader2), escapeSqlString(nonReceiptHeader3),
                            escapeSqlString(nonReceiptHeader4), escapeSqlString(nonReceiptHeader5),
                            escapeSqlString(readingsFooter1), escapeSqlString(readingsFooter2),
                            escapeSqlString(readingsFooter3), escapeSqlString(readingsFooter4),
                            escapeSqlString(readingsFooter5), escapeSqlString(readingsHeader1),
                            escapeSqlString(readingsHeader2), escapeSqlString(readingsHeader3),
                            escapeSqlString(readingsHeader4), escapeSqlString(readingsHeader5),
                            escapeSqlString(receiptLabel), escapeSqlString(replyToEmail),
                            escapeSqlString(replyToName), escapeSqlString(subjectOfEmail)
                    );
                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmReceipt: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmReadingSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmReading.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    Boolean allowMultipleReading = resultSet.getBoolean("fmultiple_sod");
                    String dailyCutoff = resultSet.getString("fauto_reading_cutoff");
                    Boolean disableCashierReport = resultSet.getBoolean("fdisable_cashier_zreading_flag");
                    Boolean disableZreading = resultSet.getBoolean("fdisable_zreading_flag");
                    Boolean dontPrintZeroStats = resultSet.getBoolean("fread_omit_zero");
                    Boolean hideRunningTotal = resultSet.getBoolean("fhide_running_total_flag");
                    String journalDir = resultSet.getString("fjournal_directory");
                    String nvramDir = resultSet.getString("fnvram_dir");
                    Boolean performAutoReading = resultSet.getBoolean("fauto_reading_flag");
                    Boolean printMediaSummary = resultSet.getBoolean("fmedia_flag");
                    String printProductSummary = resultSet.getString("fread_product");
                    Integer rqCasherDeclarationBeforeCashRep = resultSet.getInt("frequire_cashier_report_flag");
                    Boolean rqCashierDecBeforeZreading = resultSet.getBoolean("frequire_cash_declare_flag");
                    Boolean rqChangeFundBeforeCashDec = resultSet.getBoolean("frequire_change_fund_on_cash_declare_flag");
                    Boolean rqPickAmtBeforeCashDec = resultSet.getBoolean("frequire_pickup_on_cash_declare_flag");
                    Boolean saveJournal = resultSet.getBoolean("fjournal_flag");
                    Boolean saveNVRAM = resultSet.getBoolean("fnvram_flag");
                    Boolean saveTrace = resultSet.getBoolean("ftrace_flag");
                    Boolean showRawTotalProducts = resultSet.getBoolean("fread_product_compute_flag");
                    String traceDir = resultSet.getString("ftrace_dir");
                    Boolean zeroOut = resultSet.getBoolean("fzero_out_flag");

                    Integer terminalMasterId = resultSet.getInt("ftermId");


                    String query = """
                                INSERT INTO `TmReading` (
                                    allowMultipleReading, disableCashierReport, disableZreading, dontPrintZeroStats, hideRunningTotal,
                                    performAutoReading, printMediaSummary, rqCashierDecBeforeZreading, rqChangeFundBeforeCashDec, rqPickAmtBeforeCashDec,
                                    saveJournal, saveNVRAM, saveTrace, showRawTotalProducts, zeroOut,
                                    id, rqCasherDeclarationBeforeCashRep, terminalMasterId, createdBy, createdDate,
                                    dailyCutoff, journalDir, nvramDir, printProductSummary, traceDir,
                                    updatedBy, updatedDate 
                                )
                                VALUES (
                                   %b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%b,%d,%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s
                                );
                            """;


                    String formattedSql = String.format(query,
                            allowMultipleReading, disableCashierReport, disableZreading, dontPrintZeroStats, hideRunningTotal,
                            performAutoReading, printMediaSummary, rqCashierDecBeforeZreading, rqChangeFundBeforeCashDec, rqPickAmtBeforeCashDec,
                            saveJournal, saveNVRAM, saveTrace, showRawTotalProducts, zeroOut,
                            id, rqCasherDeclarationBeforeCashRep, terminalMasterId,
                             escapeSqlString(createdBy), convertDate(createdDate),
                            escapeSqlString(dailyCutoff), escapeSqlString(journalDir), escapeSqlString(nvramDir),
                            escapeSqlString(printProductSummary), escapeSqlString(traceDir),
                            escapeSqlString(updatedBy), convertDate(updatedDate)

                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmReading: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmTenantSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmTenant.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String backupDir = resultSet.getString("ftenant_backup");
                    String ftpSettings = resultSet.getString("ftenant_ftp_url");
                    Boolean sendViaFtp = resultSet.getBoolean("ftenant_ftp_flag");
                    String server = resultSet.getString("ftenant_server");
                    String serverConnIntro = resultSet.getString("ftenant_connection");
                    String tenantCode1 = resultSet.getString("ftenant_code1");
                    String tenantCode2 = resultSet.getString("ftenant_code2");
                    String tenantCode3 = resultSet.getString("ftenant_code3");
                    String tenantCode4 = resultSet.getString("ftenant_code4");
                    String tenantCode5 = resultSet.getString("ftenant_code5");
                    String tenantConnection = resultSet.getString("ftenant_type");
                    String tenantId = resultSet.getString("ftenantid");

                    Integer terminalMasterId = resultSet.getInt("ftermId");

                    String query = """
                                INSERT INTO `TmTenant` (
                                    sendViaFtp, id, terminalMasterId, backupDir, createdBy,
                                    createdDate, ftpSettings, server, serverConnIntro, tenantCode1,
                                    tenantCode2, tenantCode3, tenantCode4, tenantCode5,
                                    tenantId, tenantConnection, updatedBy, updatedDate
                                )
                                VALUES (
                                   %b,%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s
                                );
                            """;


                    String formattedSql = String.format(query,
                            sendViaFtp, id, terminalMasterId,
                            escapeSqlString(backupDir), escapeSqlString(createdBy),
                            convertDate(createdDate), escapeSqlString(ftpSettings), escapeSqlString(server),escapeSqlString(serverConnIntro) ,
                            escapeSqlString(tenantCode1), escapeSqlString(tenantCode2),escapeSqlString(tenantCode3), escapeSqlString(tenantCode4),
                            escapeSqlString(tenantCode5), escapeSqlString(tenantId), escapeSqlString(tenantConnection),
                            escapeSqlString(updatedBy), convertDate(updatedDate)

                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmTenant: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void TmComputationSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\TmComputation.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    Float amusementTax = resultSet.getFloat("famusement_tax");
                    Float dedctDiscBeforeCompSC = resultSet.getFloat("fschg_less_discount");
                    Float dedctLocalTaxBeforeComp = resultSet.getFloat("flocal_tax_less_discount");
                    Boolean dedctServChargeFromNGT = resultSet.getBoolean("fdeduct_scharge");
                    Boolean dedctTaxBeforeCompSC = resultSet.getBoolean("fschg_less_tax");
                    Boolean deductTaxFromNGT = resultSet.getBoolean("fdeduct_tax");
                    Float diplomatDisc = resultSet.getFloat("fdiscount_percent2");
                    Float ewtTax = resultSet.getFloat("fewt");
                    Boolean exclusiveGovDiscount = resultSet.getBoolean("fexclusive_government_discount_flag");
                    String govMandatedComputation = resultSet.getString("fgovt_comp_version");
                    Float localTax = resultSet.getFloat("flocal_tax");
                    Float movDisc = null; // This is set to null
                    Float nacDisc = resultSet.getFloat("fnac_discount_percent");
                    Float pwdDisc = resultSet.getFloat("fpwd_discount_percent");
                    Float salesTax = resultSet.getFloat("fvat");
                    Float seniorDisc = resultSet.getFloat("fsc_discp");
                    Float seniorExpMealCap = resultSet.getFloat("fmax_sc_discount");
                    Float servChargeReg = resultSet.getFloat("fschg_rate");
                    Float servChargeTakeout = resultSet.getFloat("fschg_rate_takeout");
                    Boolean serviceChargeIsTaxable =    resultSet.getBoolean("ftax_schg");
                    Float spDisc = null; // This is set to null
                    Long terminalMasterId = resultSet.getLong("ftermId");

                    String query = """
                                INSERT INTO `TmComputation` (
                                    dedctServChargeFromNGT, dedctTaxBeforeCompSC, deductTaxFromNGT, exclusiveGovDiscount, serviceChargeIsTaxable,
                                    amusementTax, dedctDiscBeforeCompSC, dedctLocalTaxBeforeComp, diplomatDisc, ewtTax,
                                    localTax, movDisc, nacDisc, pwdDisc, salesTax,
                                    seniorDisc, seniorExpMealCap, servChargeReg, servChargeTakeout, spDisc,
                                    id, terminalMasterId, createdBy, createdDate, govMandatedComputation,
                                    updatedBy, updatedDate
                                )
                                VALUES (
                                   %b,%b,%b,%b,%b,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%d,%d,%s,%s,%s,%s,%s
                                );
                            """;


                    String formattedSql = String.format(query,
                            dedctServChargeFromNGT, dedctTaxBeforeCompSC, deductTaxFromNGT, exclusiveGovDiscount, serviceChargeIsTaxable,
                            amusementTax, dedctDiscBeforeCompSC, dedctLocalTaxBeforeComp, diplomatDisc, ewtTax,
                            localTax, movDisc, nacDisc, pwdDisc, salesTax,
                            seniorDisc, seniorExpMealCap, servChargeReg, servChargeTakeout, spDisc,
                            id, terminalMasterId, escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(govMandatedComputation),
                            escapeSqlString(updatedBy), convertDate(updatedDate)

                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("TmComputation: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void updateTmHardwareSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\UpdateTmHardware.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");

                    Integer tmHardwareAdjustmentId = resultSet.getInt("ftermId");
                    Integer tmHardwareCountSheetId = resultSet.getInt("ftermId");
                    Integer tmHardwareReceiptId = resultSet.getInt("ftermId");
                    Integer tmHardwareStockCountId = resultSet.getInt("ftermId");
                    Integer tmHardwareTransferId = resultSet.getInt("ftermId");

                    String query = """
                                UPDATE
                                    `TmHardware`
                                SET
                                    tmHardwareAdjustmentId = %d,
                                    tmHardwareCountSheetId = %d,
                                    tmHardwareReceiptId = %d,
                                    tmHardwareStockCountId = %d,
                                    tmHardwareTransferId = %d
                                WHERE
                                    id = %d;
                            """;


                    String formattedSql = String.format(query,
                            tmHardwareAdjustmentId, tmHardwareCountSheetId, tmHardwareReceiptId, tmHardwareTransferId,tmHardwareStockCountId, id);

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Update TmHardware: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void updateTerminalMasterSQL(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\UpdateTerminalMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_terminal"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("ftermId");

                    Integer kitchenStationId = null;
                    Integer tmBasicId = id;
                    Integer tmCompId = id;
                    Integer tmHardwareId = id;
                    Integer tmReadingId = id;
                    Integer tmReceiptId = id;
                    Integer tmTenantId = id;


                    String query = """
                            UPDATE
                                `TerminalMaster` 
                            SET
                                tmBasicId = %d,
                                tmCompId = %d,
                                tmHardwareId = %d,
                                tmReadingId = %d,
                                tmReceiptId = %d,
                                tmTenantId = %d
                            WHERE
                                id = %d;
                            """;

                    String formattedSql = String.format(
                            query,
                            tmBasicId, tmCompId, tmHardwareId, tmReadingId, tmReceiptId,tmTenantId, id
                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Update Terminal Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void licenseSQL(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\License.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_license"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {


                    Long id = null;
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String applicationDate = resultSet.getString("fcreated_date");
                    Boolean billable = resultSet.getBoolean("fbillable");
                    String expiryDate = resultSet.getString("fedate");
                    String licenseKey = resultSet.getString("flicense_key");
                    String memo = resultSet.getString("fmemo");
                    Integer monthsToAdd = resultSet.getInt("fmonths");
                    String requestedBy = resultSet.getString("fcreated_by");
                    String salesRep = null;
                    String startDate = resultSet.getString("fsdate");
                    Integer status = resultSet.getInt("fstatus_flag");
//                    Integer type = resultSet.getInt("flicense_type");
                    Integer type = null;
                    Integer unit = resultSet.getInt("funitprice");
                    String companyId = resultSet.getString("fcompanyid");
                    Long terminalMasterId = resultSet.getLong("ftermid");

                    String query = """
                            INSERT INTO `License` (
                                    billable, monthsToAdd, status, type, unit,
                                    id, terminalMasterId, applicationDate, companyId, createdBy, 
                                    createdDate, expiryDate, licenseKey, memo, requestedBy, 
                                    salesRep, startDate, updatedBy, updatedDate
                                )
                                VALUES (
                                   %b,%d,%d,%d,%d,%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s
                                );
                            """;
                    String formattedSql = String.format(
                            query,
                            billable, monthsToAdd, status, type, unit,
                            id, terminalMasterId,
                            convertDate(applicationDate),
                            escapeSqlString(companyId),
                            escapeSqlString(createdBy),
                            convertDate(createdDate),
                            convertDate(expiryDate, true),
                            escapeSqlString(licenseKey),
                            escapeSqlString(memo),
                            escapeSqlString(requestedBy),
                            escapeSqlString(salesRep),
                            convertDate(startDate, true),
                            escapeSqlString(updatedBy),
                            convertDate(updatedDate)

                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("License: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void readingSQL(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\Reading.txt";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM pos_reading"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {


                    Long id = null;
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String closeTime = resultSet.getString("fclose_time");
                    BigDecimal discount5 = null;
                    BigDecimal discount5Num = null;
                    BigDecimal discount6 = null;
                    BigDecimal discount6Num = null;
                    Float ewt = resultSet.getFloat("fewt");
                    Float ewt2 = resultSet.getFloat("fewt2");
                    Integer fDocumentNo = resultSet.getInt("ffdocument_no");
                    Float gross = resultSet.getFloat("fgross");
                    Float gross2 = resultSet.getFloat("fgross2");
                    Integer hspct = resultSet.getInt("fhspct");
                    Integer isSent = resultSet.getInt("fsent_flag");
                    Integer lineCounter = resultSet.getInt("fline_counter");
                    Float noTaxSale = resultSet.getFloat("fnotax_sale");
                    Float noTaxSale2 = resultSet.getFloat("fnotax_sale2");
                    String openTime = resultSet.getString("fopen_time");
                    Float prevEwt = resultSet.getFloat("fpewt");
                    Float prevEwt2 = resultSet.getFloat("fpewt2");
                    Float prevGross = resultSet.getFloat("fpgross");
                    Float prevGross2 = resultSet.getFloat("fpgross2");
                    Float prevNoTaxSale = resultSet.getFloat("fpnotax_sale");
                    Float prevNoTaxSale2 = resultSet.getFloat("fpnotax_sale2");
                    Integer prevResetCounter = resultSet.getInt("fpzcounter");
                    Integer prevResetCounter2 = resultSet.getInt("fpzcounter2");
                    Float prevTax = resultSet.getFloat("fptax");
                    Float prevTax2 = resultSet.getFloat("fptax2");
                    Float prevTaxSale = resultSet.getFloat("fptax_sale");
                    Float prevTaxSale2 = resultSet.getFloat("fptax_sale2");
                    Integer resetCounter = resultSet.getInt("fzcounter");
                    Integer resetCounter2 = resultSet.getInt("fpzcounter2");
                    String saleDate = resultSet.getString("fsale_date");
                    Integer sentCount = resultSet.getInt("fsent_count");
                    Integer tDocumentNo = resultSet.getInt("ftdocument_no");
                    Float tax = resultSet.getFloat("ftax");
                    Float tax2 = resultSet.getFloat("ftax2");
                    Float taxSale = resultSet.getFloat("ftax_sale");
                    Float taxSale2 = resultSet.getFloat("ftax_sale2");
                    String terminalReadingGuid = generateUUID();
                    String transmit = "0002"; // static temp
                    String companyId = resultSet.getString("fcompanyid");
                    Long terminalMasterId = resultSet.getLong("ftermid");

                    String query = """
                            INSERT INTO `Reading` (
                                    discount5, discount5Num, discount6, discount6Num, ewt, 
                                    ewt2, gross, gross2, noTaxSale, noTaxSale2, 
                                    prevEwt, prevEwt2, prevGross, prevGross2, prevNoTaxSale, 
                                    prevNoTaxSale2, prevTax, prevTax2, prevTaxSale, prevTaxSale2, 
                                    tax, tax2, taxSale, taxSale2, fDocumentNo, 
                                    hspct, isSent, lineCounter, prevResetCounter, prevResetCounter2, 
                                    resetCounter, resetCounter2, sentCount, tDocumentNo, id, 
                                    terminalMasterId, closeTime, companyId, createdBy, createdDate, 
                                    openTime, saleDate, terminalReadingGuid, transmit, updatedBy, 
                                    updatedDate
                                )
                                VALUES (
                                   %f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,
                                   %f,%f,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s
                                );
                            """;
                    String formattedSql = String.format(
                            query,
                            discount5, discount5Num, discount6, discount6Num, ewt,
                            ewt2, gross, gross2, noTaxSale, noTaxSale2,
                            prevEwt, prevEwt2, prevGross, prevGross2, prevNoTaxSale,
                            prevNoTaxSale2, prevTax, prevTax2, prevTaxSale, prevTaxSale2,
                            tax, tax2, taxSale, taxSale2, fDocumentNo,
                            hspct, isSent, lineCounter, prevResetCounter, prevResetCounter2,
                            resetCounter, resetCounter2, sentCount, tDocumentNo, id,
                            terminalMasterId, escapeSqlString(closeTime), escapeSqlString(companyId), escapeSqlString(createdBy), convertDate(createdDate),
                            escapeSqlString(openTime), convertDate(saleDate,true), escapeSqlString(terminalReadingGuid),
                            escapeSqlString(transmit), escapeSqlString(updatedBy),
                            convertDate(updatedDate)


                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Reading: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void securityAccessSQL(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\SecurityAccess.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_access_level_list"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {


                    Long id = null;
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String app = resultSet.getString("fappid");
                    String resourceId = resultSet.getString("fresourceid");
                    String resourceName = null;
                    String accessLevelMasterId = resultSet.getString("faclvlid");
                    String companyId = resultSet.getString("fcompanyid");

                    String query = """
                            INSERT INTO `SecurityAccess` (
                                   id, createdBy, createdDate,
                                   updatedBy, updatedDate,
                                   app, resourceId, resourceName,
                                   accessLevelMasterId, companyId
                                )
                                VALUES (
                                   %d,%s,%s,%s,%s,%s,%s,%s,%d,%s
                                );
                            """;
                    String formattedSql = String.format(
                            query,
                            id, escapeSqlString(createdBy), convertDate(createdDate),
                            escapeSqlString(updatedBy), convertDate(updatedDate),
                            escapeSqlString(app), escapeSqlString(resourceId), escapeSqlString(resourceName),
                            getRoleId(accessLevelMasterId), escapeSqlString(companyId)


                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Security Access: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }


    private static void auditTrailSql(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\AuditTrail.txt";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_audit_trail"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {


                    Long id = null;
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fcreated_by");
                    String updatedDate = resultSet.getString("fcreated_date");
                    String resource = resultSet.getString("fresourceid");
                    String companyId = resultSet.getString("fcompanyid");
                    String mode = resultSet.getString("fcode");
                    String memo = resultSet.getString("fmemo");
                    String timestamp = resultSet.getString("fcreated_date");

                    String query = """
                            INSERT INTO `SecurityAccess` (
                                   id, createdBy, createdDate,
                                   updatedBy, updatedDate,
                                   resource, mode, memo,
                                   timestamp, companyId
                                )
                                VALUES (
                                   %d,%s,%s,%s,%s,%s,%s,%s,%s,%s
                                );
                            """;
                    String formattedSql = String.format(
                            query,
                            id, escapeSqlString(createdBy), convertDate(createdDate),
                            escapeSqlString(updatedBy), convertDate(updatedDate),
                            escapeSqlString(resource), escapeSqlString(mode), escapeSqlString(memo),
                            convertDate(timestamp), escapeSqlString(companyId)


                    );

                    // Write the SQL statement to the file
                    writer.write(formattedSql);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Audit Trail: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }
    @Deprecated
    public static String formatNullField(String field) {
        return (field == null) ? "NULL" : "'" + field.replace("'", "''") + "'"; // Return actual value or NULL for SQL
    }

    @Deprecated
    private static void priceLevelSqlDeprecated(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\PriceLevelMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM mst_price_level"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    StringBuilder sqlBuilder = new StringBuilder("INSERT INTO `").append("PriceLevelMaster").append("` (");
                    // Always include NULL for the `id` column if it's auto-incremented
                    sqlBuilder.append("id, isDeleted, ");

                    String createdBy = resultSet.getString("fcreated_by");
                    fieldNullChecker(createdBy, "createdBy", sqlBuilder, false);

                    String createdDate = resultSet.getString("fcreated_date");
                    fieldNullChecker(createdDate, "createdDate", sqlBuilder, false);

                    String updatedBy = resultSet.getString("fupdated_by");
                    fieldNullChecker(updatedBy, "updatedBy", sqlBuilder, false);

                    String updatedDate = resultSet.getString("fupdated_date");
                    fieldNullChecker(updatedDate, "updatedDate", sqlBuilder, false);

                    boolean isActive = resultSet.getBoolean("factive_flag");

                    String name = resultSet.getString("fname");
                    fieldNullChecker(name, "name", sqlBuilder, false);

                    String plevelId = resultSet.getString("fplevelid");
                    fieldNullChecker(plevelId, "priceLevelId", sqlBuilder, false);

                    String companyId = resultSet.getString("fcompanyid");
                    fieldNullChecker(companyId, "companyId", sqlBuilder, false);

                    String memo = resultSet.getString("fmemo");
                    fieldNullChecker(memo, "memo", sqlBuilder, true);

//                    BigDecimal discp = resultSet.getBigDecimal("fdiscp");
//                    fieldNullChecker(companyId, "companyId", sqlBuilder, sqlBuilder);
//
//                    String code = resultSet.getString("fcode");
//                    fieldNullChecker(companyId, "companyId", sqlBuilder, sqlBuilder);


                    // Retrieve other fields as needed

                    String sql1 = String.format(sqlBuilder + ") " + "VALUES (NULL,'%b', '%s', '%s', '%s', '%s', '%b', '%s', '%s', '%s', '%s');", false, createdBy, createdDate == null ? null : convertDate(createdDate), updatedBy, updatedDate == null ? null : convertDate(updatedDate), isActive, name, plevelId, companyId, memo);

                    // Write the SQL statement to the file
                    writer.write(sql1);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("Price Level Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static void userMaster(String url, String user, String password) {
        String filePath = "C:\\Users\\Owner\\Documents\\Data Migration\\UserMaster.sql";


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Execute a SQL query
            String sql = "SELECT * FROM sm_user"; // Replace with your SQL query
            resultSet = statement.executeQuery(sql);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while (resultSet.next()) {
                    String emailAddress = resultSet.getString("fuserid");
                    String displayName = resultSet.getString("fname");
                    String firstName = resultSet.getString("ffirst_name");
                    String lastName = resultSet.getString("flast_name");
                    String phoneNo = resultSet.getString("ftelno");
                    String securityQuestionType = resultSet.getString("fsecurity_question_type"); // status
                    String answer = resultSet.getString("fsecurity_answer");
                    String registeredIpAddress = resultSet.getString("fregistered_ip_address");
                    boolean isActive = resultSet.getBoolean("factive_flag");
                    String password1 = resultSet.getString("fpassword");
                    String passwordUpdate = resultSet.getString("fpassword_update");
                    String accessLevelId = resultSet.getString("faclvlid");
                    boolean adminAccess = resultSet.getBoolean("fadmin_logon_flag");
                    String memo = resultSet.getString("fmemo");
                    String lastLogon = resultSet.getString("flast_logon");
                    String address = resultSet.getString("faddress");
                    String expiry = resultSet.getString("fexpiry");
                    String createdBy = resultSet.getString("fcreated_by");
                    String createdDate = resultSet.getString("fcreated_date");
                    String updatedBy = resultSet.getString("fupdated_by");
                    String updatedDate = resultSet.getString("fupdated_date");
                    String hostCompanyId = resultSet.getString("fhost_companyid");
                    String sessionId = resultSet.getString("fsessionid");
                    BigDecimal companyAllotment = resultSet.getBigDecimal("fcompany_allotment");
                    boolean leftHandFlag = resultSet.getBoolean("fleft_hand_flag");

                    // Retrieve other fields as needed

//                    String sql1 = String.format("INSERT INTO `UserMaster` (`emailAddress`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `adminAccess`, `answer`, " +
//                                    "`displayName`, `firstName`, `forcePasswordChange`, `isActive`, `isDeleted`, `isVerified`, `lastName`, `password`, `passwordExpiry`, `phoneNo`," +
//                                    " `status`, `accessLevelId`, `securityQuestion`) " +
//                                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %b, '%s', '%s', '%s', '%s', '%s', '%s', %s, %b, %s, %s);",
//                            emailAddress, createdBy, convertDate(createdDate), updatedBy, convertDate(updatedDate), adminAccess, answer,
//                            displayName, firstName, forcePasswordChange, isActive, false, isActive, lastName, password1, passwordExpiry,
//                            phoneNo, securityQuestionType, accessLevelId, securityQuestion, expiry);

//                    String sql1 = String.format("INSERT INTO `UserMaster` (`emailAddress`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `adminAccess`, `answer`, " +
//                                    "`displayName`, `firstName`, `forcePasswordChange`, `isActive`, `isDeleted`, `isVerified`, `lastName`, `password`, `passwordExpiry`, `phoneNo`," +
//                                    " `status`, `accessLevelId`, `securityQuestion`) " +
//                                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %b, '%s', '%s', '%s', '%s', '%s', '%s', %s, %b, %s, %s);",
//                            emailAddress, createdBy, convertDate(createdDate), updatedBy, convertDate(updatedDate), adminAccess, answer,
//                            displayName, firstName, 0, isActive, false, isActive, lastName, password1, "someNullValue",
//                            phoneNo, securityQuestionType, accessLevelId, securityQuestion, expiry);

//                    String sql1 = String.format("INSERT INTO `UserMaster` (`emailAddress`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `adminAccess`, `answer`, " +
//                                    "`displayName`, `firstName`, `forcePasswordChange`, `isActive`, `isDeleted`, `isVerified`, `lastName`, `password`, `passwordExpiry`, `phoneNo`, " +
//                                    "`status`, `accessLevelId`, `securityQuestion`) " +
//                                    "VALUES (%s, %s, %s, %s, %s, %d, %s, %s, %s, %d, %d, %d, %s, %s, %s, %s, %s, %s, %s, %s);",
//                            escapeSqlString(emailAddress),
//                            escapeSqlString(createdBy),
//                            convertDate(createdDate),
//                            escapeSqlString(updatedBy),
//                            convertDate(updatedDate),
//                            adminAccess ? 1 : 0, // Convert boolean to int
//                            answer != null ? "'" + answer + "'" : "NULL", // Handle answer
//                            escapeSqlString(displayName),
//                            escapeSqlString(firstName),
//                            0, // forcePasswordChange as integer (assuming it’s 0)
//                            isActive ? 1 : 0, // Convert boolean to int
//                            0, // Assuming isDeleted as 0
//                            1, // Convert boolean to int
//                            escapeSqlString(lastName),
//                            escapeSqlString(password1),
//                            "NULL", // Assuming passwordExpiry as NULL
//                            escapeSqlString(phoneNo),
//                            escapeSqlString(securityQuestionType),
//                            "NULL",// escapeSqlString(accessLevelId),
//                            "NULL");//escapeSqlString(securityQuestion));
//

                    String sql1 = String.format("INSERT INTO `UserMaster` (`emailAddress`, `createdBy`, `createdDate`, `updatedBy`, `updatedDate`, `adminAccess`, `answer`, " + "`displayName`, `firstName`, `isActive`, `isDeleted`, `isVerified`, `lastName`, `password`, `passwordExpiry`, `phoneNo`, " + "`status`, `accessLevelId`, `securityQuestion`) " + "VALUES (%s, %s, %s, %s, %s, %d, %s, %s, %s, %d, %d, %d, %s, %s, %s, %s, %s, %s, %s);", escapeSqlString(emailAddress), escapeSqlString(createdBy), convertDate(createdDate), escapeSqlString(updatedBy), convertDate(updatedDate), adminAccess ? 1 : 0, // Convert boolean to int
                            answer != null ? "'" + answer + "'" : null, // Handle answer
                            escapeSqlString(displayName), escapeSqlString(firstName), isActive ? 1 : 0, // Convert boolean to int
                            0, // Assuming isDeleted as 0
                            1, // Convert boolean to int for isVerified
                            escapeSqlString(lastName), escapeSqlString(password1), null, // Assuming passwordExpiry as NULL
                            escapeSqlString(phoneNo), 0, // escapeSqlString(accessLevelId)
                            chooseForAccessLevel(accessLevelId),  // escapeSqlString(securityQuestion)
                            chooseForSecurityQuestion(securityQuestionType));


//                    System.out.println(sql1);
//                    // Write the SQL statement to the file
                    writer.write(sql1);
                    writer.newLine(); // Add a new line for the next statement
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IO exceptions
            }
            // Process the ResultSet

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // Close resources in reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

                System.out.println("User Master: DONE");
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }
    }

    private static String convertDate(String inputDateStr) {

        if (inputDateStr == null || inputDateStr.isBlank() || inputDateStr.isEmpty()) {
            return null;
        }

        // Define the input format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Parse the input string to LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(inputDateStr, inputFormatter);

        // Define the output format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the LocalDateTime to the desired output format

        return "'" + dateTime.format(outputFormatter) + "'";
    }

    private static String convertDate(String inputDateStr, boolean noTime) {
        if (inputDateStr == null || inputDateStr.isBlank() || inputDateStr.isEmpty()) {
            return null;
        }

        // Define the input format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Parse the input string to LocalDateTime
        LocalDate date = LocalDate.parse(inputDateStr, inputFormatter);

        // Define the output format to include only the date
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the LocalDateTime to the desired output format
        return "'" + date.format(outputFormatter) + "'";
    }


    private static int chooseForSecurityQuestion(String value) {
        // Use Integer.decode to handle string representation of integers.
        Integer secType = null;

        try {
            secType = Integer.decode(value);
        } catch (NumberFormatException e) {
            // Handle the case where value is not a valid integer
            // You can log the error or set secType to a default value if needed
        }

        // If secType is null or invalid, return 1; otherwise return secType + 1
        return (secType != null ? secType + 1 : 1);
    }

    private static Integer chooseForAccessLevel(String value) {
        if (value.equals("ADMIN")) {
            return 1;
        } else if (value.equals("0")) {
            return 8;
        } else {
            return 7;
        }
    }

    public static Integer getRoleId(String role) {
        switch (role) {
            case "ADMIN":
                return 1;
            case "CASHIER":
                return 2;
            case "ENCODER":
                return 3;
            case "MANAGER":
                return 4;
            case "OWNER":
                return 5;
            case "SUPERVISOR":
                return 6;
            case "1":
                return 7;
            case "0":
                return 8;
            case "PRICEMANAGER":
                return 9;
            case "SALESMANAGER":
                return 10;
            case "ADJTYPEMANAGER":
                return 11;
            case "INVMANAGER":
                return 12;
            case "INVSUPERVISOR":
                return 13;
            case "ASSETACCTANT":
                return 14;
            case "CCODEMANAGER":
                return 15;
            case "DIVMANAGER":
                return 16;
            case "EMPTYPEMANAGER":
                return 17;
            case "ACCTMANAGER":
                return 18;
            case "ACCTENCODER":
                return 19;
            case "ACCTSUPERVISOR":
                return 20;
            case "PRICELVLMANAGER":
                return 21;
            case "PRODMANAGER":
                return 22;
            case "CANCELREASONMANAGER":
                return 23;
            case "CUTOFFMANAGER":
                return 24;
            case "REGIONMANAGER":
                return 25;
            case "PPOINTMANAGER":
                return 26;
            case "WAREHOUSEMANAGER":
                return 27;
            case "TENDERTYPEMANAGER":
                return 28;
            case "AUDITOR":
                return 29;
            case "BUSINESSMANAGER":
                return 30;
            case "BUSINESSSUPERVISOR":
                return 31;
            case "BUSINESSASSISTANT":
                return 32;
            case "HRUBFADMIN":
                return 33;
            case "SALESSUPERVISOR":
                return 34;
            case "SALES":
                return 35;
            case "ACCTINVBACKUP":
                return 36;
            case "ENROLLEDEMPMGR":
                return 37;
            default:
                return  null;
        }
    }


    private static Integer chooseForProductType(String value) {
        return switch (value.charAt(0)) {
            case '0' -> 1;
            case '1' -> 2;
            case 'S' -> 3;
            case 'D' -> 4; // Example value, change as needed
            case 'K' -> 5; // Example value, change as needed
            case 'A' -> 6; // Example value, change as needed
            case 'C' -> 7; // Example value, change as needed
            case 'V' -> 8; // Example value, change as needed
            case 'P' -> 9; // Example value, change as needed
            case 'R' -> 10; // Example value, change as needed
            case 'N' -> 11;
            default -> 0; // Default case
        };
    }

    private static Integer chooseForWarehouseMasterId(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "AFP062DASMA" -> 1;
            case "AFP103SMMASINAG" -> 2;
            case "AFP104SMROSALES" -> 3;
            case "AFP105SMANGONO" -> 4;
            case "AFP106SMLUCENA" -> 5;
            case "AFP107MALOLOS" -> 6;
            case "AFP108NUVALI" -> 7;
            case "AFP109FAIRVIEW" -> 8;
            case "AFP110STARMILLS" -> 9;
            case "AFP111STAMESA" -> 10;
            case "AFP112CHINATOWN" -> 11;
            case "AFP113CEBUGALLER" -> 12;
            case "AFP114CABANATUAN" -> 13;
            case "AFP115CEBUCONSOL" -> 14;
            case "AFP116CEBUSEASID" -> 15;
            case "AFP117SHANGRILA" -> 16;
            case "AFP118TAGUM" -> 17;
            case "AFP119SANLAZARO" -> 18;
            case "AFP121UPTOWN" -> 19;
            case "AFP122SMCAUAYAN" -> 20;
            case "AFP123SMBATANGAS" -> 21;
            case "AFP124SMDAVAO" -> 22;
            case "AFP125CENTURY" -> 23;
            case "AFP126SOUTHPARK" -> 24;
            case "AFP127SMMARILAO" -> 25;
            case "AFP128VISTAMALL" -> 26;
            case "AFP129MARQUEE" -> 27;
            case "AFP130EASTWOOD" -> 28;
            case "AFP131DASMA" -> 29;
            case "AFP132NAGA" -> 30;
            case "AFP133SOUTHWOODS" -> 31;
            case "AFP134SANPABLO" -> 32;
            case "AFP135CLOVERLEAF" -> 33;
            case "AFP136SUBIC" -> 34;
            case "AFP137BACOLOD" -> 35;
            case "AFP138ILOILO" -> 36;
            case "AFP139UPTOWN" -> 37;
            case "AFP140VALENZUELA" -> 38;
            case "AFP141WMALL" -> 39;
            case "AFP142VENICE" -> 40;
            case "AFP143FESTIVE" -> 41;
            case "AFP144TUGUEGARAO" -> 42;
            case "AFP145BATAAN" -> 43;
            case "AFP146LEGAZPI" -> 44;
            case "AFP147BACOOR" -> 45;
            case "AFP148GENSAN" -> 46;
            case "AFP149CDO" -> 47;
            case "AFP150FESTIVAL" -> 48;
            case "AFP151TRECE" -> 49;
            case "AFP152NORTHEDSA" -> 50;
            case "AFP153SOUTHMALL" -> 51;
            case "AFP154SANPEDRO" -> 52;
            case "AFP155SMFAIRVIEW" -> 53;
            case "AFP156SMSJDM" -> 54;
            case "AFP157SUCAT" -> 55;
            case "AFP158SMLANANG" -> 56;
            case "AFP160SMBUTUAN" -> 57;
            case "AFP161SMMINDPRO" -> 58;
            case "AFP162ANTIPOLO" -> 59;
            case "AFP163STAROSA" -> 60;
            case "AFP164SMNORTH" -> 61;
            case "AFP165ROBLAUNION" -> 62;
            case "AFP166STOTOMAS" -> 63;
            case "AFP167SMPUERTO" -> 64;
            case "AFP168SMDAET" -> 65;
            case "AFP170ROBILO" -> 66;
            case "AFP171SMBALIWAG" -> 67;
            case "AFP171VALENCIA" -> 68;
            case "AFP172CANDELARIA" -> 69;
            case "AFP173FELIZ" -> 70;
            case "AFP174TACLOBAN" -> 71;
            case "AFP175ROXAS" -> 72;
            case "AFP176CALOOCAN" -> 73;
            case "AFP177CASHCARRY" -> 74;
            case "AFP178SOUTHMALL" -> 75;
            case "GALERIA" -> 76;
            case "GENTRI" -> 77;
            case "MAIN" -> 78;
            default -> null; // Default case
        };
    }

    private static Integer chooseForGiftCertTypeMasterId(String value) {
        if (value == null || value.isEmpty()) {
            return 0; // Handle null or empty strings
        }

        return switch (value) {
            case "KIDZOOONAGC200" -> 1;
            default -> null; // Default case
        };
    }

    private static Integer chooseForBankMasterId(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "01" -> 1;
            case "02" -> 2;
            case "03" -> 3;
            case "04" -> 4;
            case "05" -> 5;
            case "06" -> 6;
            case "07" -> 7;
            case "08" -> 8;
            case "09" -> 9;
            case "99" -> 10;
            default -> null; // Default case
        };
    }

    private static Integer chooseForBranchMasterId(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "AFP103SMMASINAG" -> 1;
            case "AFP104SMROSALES" -> 2;
            case "AFP105SMANGONO" -> 3;
            case "AFP106SMLUCENA" -> 4;
            case "AFP107MALOLOS" -> 5;
            case "AFP108NUVALI" -> 6;
            case "AFP109FAIRVIEW" -> 7;
            case "AFP110STARMILLS" -> 8;
            case "AFP111STAMESA" -> 9;
            case "AFP112CHINATOWN" -> 10;
            case "AFP113CEBUGALLER" -> 11;
            case "AFP114CABANATUAN" -> 12;
            case "AFP115CEBUCONSOL" -> 13;
            case "AFP116CEBUSEASID" -> 14;
            case "AFP117SHANGRILA" -> 15;
            case "AFP118TAGUM" -> 16;
            case "AFP119-SANLAZARO" -> 17;
            case "AFP121UPTOWN" -> 18;
            case "AFP122SMCAUAYAN" -> 19;
            case "AFP123SMBATANGAS" -> 20;
            case "AFP124SMDAVAO" -> 21;
            case "AFP125CENTURY" -> 22;
            case "AFP126SOUTHPARK" -> 23;
            case "AFP127SMMARILAO" -> 24;
            case "AFP128VISTAMALL" -> 25;
            case "AFP129MARQUEE" -> 26;
            case "AFP130EASTWOOD" -> 27;
            case "AFP131DASMA" -> 28;
            case "AFP132NAGA" -> 29;
            case "AFP133SOUTHWOODS" -> 30;
            case "AFP134SANPABLO" -> 31;
            case "AFP135CLOVERLEAF" -> 32;
            case "AFP136SUBIC" -> 33;
            case "AFP137BACOLOD" -> 34;
            case "AFP138ILOILO" -> 35;
            case "AFP139UPTOWN" -> 36;
            case "AFP140VALENZUELA" -> 37;
            case "AFP141WMALL" -> 38;
            case "AFP142VENICE" -> 39;
            case "AFP143FESTIVE" -> 40;
            case "AFP144TUGUEGARAO" -> 41;
            case "AFP145BATAAN" -> 42;
            case "AFP146LEGAZPI" -> 43;
            case "AFP147BACOOR" -> 44;
            case "AFP148GENSAN" -> 45;
            case "AFP149CDO" -> 46;
            case "AFP150FESTIVAL" -> 47;
            case "AFP151TRECE" -> 48;
            case "AFP152NORTHEDSA" -> 49;
            case "AFP153SOUTHMALL" -> 50;
            case "AFP154SANPEDRO" -> 51;
            case "AFP155SMFAIRVIEW" -> 52;
            case "AFP156SMSJDM" -> 53;
            case "AFP157SUCAT" -> 54;
            case "AFP158SMLANANG" -> 55;
            case "AFP160SMBUTUAN" -> 56;
            case "AFP161SMMINDPRO" -> 57;
            case "AFP162ANTIPOLO" -> 58;
            case "AFP163STAROSA" -> 59;
            case "AFP164SMNORTH" -> 60;
            case "AFP165ROBLAUNION" -> 61;
            case "AFP166STOTOMAS" -> 62;
            case "AFP167SMPUERTO" -> 63;
            case "AFP168SMDAET" -> 64;
            case "AFP170ROBILO" -> 65;
            case "AFP171SMBALIWAG" -> 66;
            case "AFP171VALENCIA" -> 67;
            case "AFP172CANDELARIA" -> 68;
            case "AFP173FELIZ" -> 69;
            case "AFP174TACLOCBAN" -> 70;
            case "AFP175ROXAS" -> 71;
            case "AFP176CALOOCAN" -> 72;
            case "AFP177CASHCARRY" -> 73;
            case "AFP178SOUTHMALL" -> 74;
            case "GALERIA" -> 75;
            case "GENTRI" -> 76;
            case "MAIN" -> 77;
            default -> null; // Default case
        };
    }

    private static Integer chooseForRegionMasterId(String value) {
        if (value == null || value.isEmpty()) {
            return 0; // Handle null or empty strings
        }

        return switch (value) {
            case "A00" -> 1;
            case "X00" -> 2;
            case "X01" -> 3;
            case "X02" -> 4;
            case "X03" -> 5;
            case "Z01" -> 6;
            case "Z02" -> 7;
            case "Z03" -> 8;
            case "Z04" -> 9;
            case "Z05" -> 10;
            case "Z06" -> 11;
            case "Z07" -> 12;
            case "Z08" -> 13;
            case "Z09" -> 14;
            case "Z10" -> 15;
            case "Z11" -> 16;
            case "Z12" -> 17;
            case "Z13" -> 18;
            case "Z14" -> 19;
            case "Z15" -> 20;
            case "Z99" -> 21;
            default -> 0; // Default case
        };
    }

    private static String chooseSourceForGiftCertificate(String value) {
        if (value == null) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "0" -> "0";
            case "1" -> "1";
            case "2" -> "2";
            default -> null; // Default case
        };
    }

    // Function to escape single quotes and handle newlines in a string for SQL
    private static String escapeSqlString(String value) {
        if (value == null) {
            return null;
        }

        String replace = value.replace("'", "''").replace("\n", " ").replace("\r", " ");
        // Replace single quotes with two single quotes, and remove newlines
        return "'" + replace + "'";

    }

    private static String escapeSqlString(String value, boolean noSingleQuotes) {
        if (value == null) {
            return null;
        }

        String replace = value.replace("'", "''").replace("\n", " ").replace("\r", " ");
        // Replace single quotes with two single quotes, and remove newlines
        return replace;

    }

    private static boolean checkBooleanValueIsTrue(String value) {
        if (value == null || value.isEmpty() || value.isBlank()) {
            return false;
        }

        return value.equals("1");
    }

    private static String chooseNameForCompanyLogin(String value) {
        return switch (value) {
            case "9055587099" -> "zyralyn gamboa";
            case "22ESCOTOLUMEN@GMAIL.COM" -> "Lumen Escoto";
            case "4LONZOMARLON@GMAIL.COM" -> "Marlon";
            case "AARONDAVE2319@GMAIL.COM" -> "Aaron Dave Trinidad";
            case "AARONDAVE2319@YAHOO.COM" -> "Aaron Dave Trinidad";
            case "AAUSTRIA@ASI-DEV3.COM" -> "Aldrinn Glenn Austria";
            case "ABANILLA@AEONFANTASY.COM.PH" -> "Lheo Abanilla";
            case "ABAPOMADILYN80@GMAIL.COM" -> "Madilyn Abapo";
            case "ABARAQUIO28@GMAIL.COM" -> "ALYSSA MENDOZA";
            case "ABAYA@AEONFANTASY.COM.PH" -> "Glenda Abaya";
            case "ABBY.BELTRAN@YAHOO.COM" -> "abigail beltran";
            case "ABECURRAY2@GMAIL.COM" -> "Abraham";
            case "ABECURRAY@GMAIL.COM" -> "abraham curray";
            case "ABUANANALIZA11@GMAIL.COM" -> "Analiza Abuan";
            case "ACEDERARANNY21@GMAIL.COM" -> "Ranny Acedera";
            case "ACEFERCHAEL28@YAHOO.COM" -> "aceferchael santos";
            case "ACOSTAFLORENCE302720@GMAIL.COM" -> "Florence Acosta";
            case "ADAMAOLAO@CSYOUTSOURCING.COM" -> "Adamaolao";
            case "ADAMCHRISMARCERA@GMAIL.COM" -> "Adam Chris Marcera";
            case "ADELELI1212@GMAIL.COM" -> "Elizabeth Adel";
            case "ADJHETEARLTHERESE@GMAIL.COM" -> "ma theresa ramilo";
            case "ADMIN" -> "Administrator";
            case "ADS.BETIS38@GMAIL.COM" -> "adalyn betis";
            case "AEONFANTASYLEA14@GMAIL.COM" -> "Leonora Bernardo";
            case "AESJAMELA49@GMAIL.COM" -> "ailene alelin";
            case "AFP.KODI@GMAIL.COM" -> "Kriska Odi";
            case "AFP112KIDZOOONA@GMAIL.COM" -> "Arturo Decena";
            case "AFP118@AEONFANTASY.COM.PH" -> "Kidzooona Tagum";
            case "AFP118TAGUM@GMAIL.COM" -> "Kidzooona Tagum";
            case "AFP118TAGUM@GMAIL.COOM" -> "Kidzooona Tagum";
            case "AFP139JEAN@GMAIL.COM" -> "Regine Ngalot";
            case "AGUILAVERLYN0414@GMAIL.COM" -> "Marie Verlyn Aguila";
            case "AINNA.CREUS@YAHOO.COM" -> "Anna Lissa Creus";
            case "AIRKNEE_DS14@YAHOO.COM" -> "Ernie Sanga";
            case "AIVEEYHOJ@GMAIL.COM" -> "Ivy Joy Casaul";
            case "AKOCLYN24@GMAIL.COM" -> "Arlyn De Guzman";
            case "ALA.JENNIFER88@GMAIL.COM" -> "Jennifer Ala";
            case "ALBERT.TRUILEN@GMAIL.COM" -> "albert truilen";
            case "ALDRINESIASAT2@GMAIL.COM" -> "Aldrine";
            case "ALDRINLUANG@GMAIL.COM" -> "aldrin luang";
            case "ALFAROJHAJHA@GMAIL.COM" -> "JANICE IAN ALFARO";
            case "ALLAINEB03@GMAIL.COM" -> "allaine borja";
            case "ALLENDINGLE0006@GMAIL.COM" -> "allen dingle";
            case "ALLIANCEHELPDESK@ASI-EES.COM" -> "Alliance Manila Helpdesk";
            case "ALLIYAHCAMBAL595@GMAIL.COM" -> "Janice Rago";
            case "ALNIKSLYN@GMAIL.COM" -> "Alman Valderama";
            case "ALTANIBARICHARD@GMAIL.COM" -> "Richard Altaniba";
            case "AMADORJHEN0114@GMAIL.COM" -> "Jennifer amador";
            case "AMBRAYCHRISTAL@GMAIL.COM" -> "Christal Ambray";
            case "AMIR19DRIZZLE@GMAIL.COM" -> "Irma San Andres";
            case "AMMARIELAQUIAN.KIDZOOONA@GMAIL.COM" -> "Ammarie Jane Laquian";
            case "AMRIH2003@YAHOO.COM" -> "agnes ma rica moyano";
            case "AMURAODOLF92@GMAIL.COM" -> "Dolf Amurao";
            case "AMURAODOLFJR@GMAIL.COM" -> "Rodolfo Jr Amurao";
            case "ANAGALABERNABE@GMAIL.COM" -> "BERNABE JR. ANGALA";
            case "ANAMAEZING1029@GMAIL.COM" -> "Ana Mae Martirizar";
            case "ANDALLO.AEON@GMAIL.COM" -> "Marie Sheila Andallo";
            case "ANDREAMARIERAMONES@GMAIL.COM" -> "andrea marie penaflor";
            case "ANDRIEJELLE2408@GMAIL.COM" -> "Nikkie Ceriola";
            case "ANGELA.ETANG@GMAIL.COM" -> "Mari Angela Etang";
            case "ANGELAMARIE27@GMAIL.COM" -> "erovelyn zafra";
            case "ANGELAZAFRA27@GMAIL.COM" -> "Erovelyn Zafra";
            case "ANGELESANALYN990@GMAIL.COM" -> "Analyn Angeles";
            case "ANGELGELACIO@GMAIL.COM" -> "Angelica Gelacio";
            case "ANGELICAPLAZOS243@GMAIL.COM" -> "Angelica Plazos";
            case "ANGELICATEJADA0821@GMAIL.COM" -> "angelica tejada";
            case "ANGELIEDELACRUZ925@GMAIL.COM" -> "angelie dela cruz";
            case "ANGELRUTHPILI@GMAIL.COM" -> "Ruth Pili";
            case "ANNABRUGADA1007@GMAIL.COM" -> "Anna Rose Brugada";
            case "ANNALYN2022@GMAIL.COM" -> "Annalyn Patenga";
            case "ANNAMARIEVALENTIN19@GMAIL.COM" -> "Anna Marie Garcia";
            case "ANNANARCISO.AEON@GMAIL.COM" -> "Annabel Narciso";
            case "ANNBADILLA012" -> "ruby ann badilla";
            case "ANNBADILLA012@GMAIL.COM" -> "Ann badilla";
            case "ANNEESPINAASAY@GMAIL.COM" -> "annalyn asay";
            case "ANNEGZG23@GMAIL.COM" -> "merian gonzaga";
            case "ANNEKIMBERLY22@GMAIL.COM" -> "ANNE KIMBERLY SANTOS";
            case "ANNERELLONS@GMAIL.COM" -> "Mary Ann Rellon";
            case "ANNERESENTES08@GMAIL.COM" -> "Annie Rele";
            case "ANTHEAADVINCULAMUGOT@GMAIL.COM" -> "anthea mugot";
            case "ANTHEANOBLEZA03@GMAIL.COM" -> "Anthea Marie Nobleza";
            case "ANTHEANOBLEZA@GMAIL.COM" -> "Anthea Marie Nobleza";
            case "ANTHEATAM02@GMAIL.COM" -> "Anthea Marie Tambunting";
            case "ANTHEA_NOBLEZA@YAHOO.COM" -> "Anthea Marie Tambunting";
            case "ANTONEITTEFUERTE@GMAIL.COM" -> "ANTONEITTE FUERTE";
            case "APATSTEPHANIE@GMAIL.COM" -> "STEPHANIE APAT";
            case "APPLETUPAS@GMAIL.COM" -> "Apple Tupas";
            case "APREALMALDITA@GMAIL.COM" -> "April Dayrit";
            case "APRIL.SITOY23@GMAIL.COM" -> "April Dawn Sitoy";
            case "APRILGCABRERA01@GMAIL.COM" -> "April Cabrera";
            case "APRILPLATON01@GMAIL.COM" -> "APRIL PLATON";
            case "ARAHGARCIA0908@GMAIL.COM" -> "Arah Gracia Mari Rosquites";
            case "ARANASDANALEE@GMAIL.COM" -> "Human Resource";
            case "ARCELYNJARALBE21@GMAIL.COM" -> "arcelyn jaralbe";
            case "ARGIEMEN@YAHOO.COM" -> "Jasmin Velasco";
            case "ARIAJAMILLE05@GMAIL.COM" -> "Cris Camille Sarandi";
            case "ARIANNBALUYOT55@GMAIL.COM" -> "Ariann Baluyot";
            case "ARJANE_ATANACIO@ICLOUD.COM" -> "arjane atanacio";
            case "ARJELYN_AJ@YAHOO.COM" -> "Arjelyn Poblete";
            case "ARLENECSABADO40@GMAIL.COM" -> "Arlene Sabado";
            case "ARLENELAYSON@GMAIL.COM" -> "arlene layson";
            case "ARNALDOAILYN9@GMAIL.COM" -> "ARNALDO AILYN";
            case "ARNALDOAILYN9GMAIL.COM" -> "ailyn arnaldo";
            case "ARVHIN04@GMAIL.COM" -> "Arvin Granito";
            case "ASHARKY15@GMAIL.COM" -> "Anna Deth Opeda";
            case "ASHLEY0819SAMSON@GMAIL.COM" -> "Annalyn Samson";
            case "ASHLEYANDAJ96@GMAIL.COM" -> "annalyn sotelo";
            case "ASHLEYLEN@GMAIL.COM" -> "Annalyn Samson";
            case "ASPIRASROEL07@GMAIL.COM" -> "Roel Aspiras";
            case "ATAREZKAREN09@GMAIL.COM" -> "jesza paran";
            case "AVILLAGRACIA@ASI-DEV3.COM" -> "Arnel Villagracia";
            case "AVMARKVILLES@YAHOO.COM" -> "stephin patero";
            case "AZLEAH001@GMAIL.COM" -> "ROSALIA ANSOTIQUE";
            case "AZZKAL24@YAHOO.COM" -> "Caroline Malinao";
            case "A_IYODA@AEONFANTASY.JP" -> "ATSUSHI IYODA";
            case "BACULAOLOYO1428@GMAIL.COM" -> "leo Baculaoloyo";
            case "BACUSMEDMELYN@GMAIL.COM" -> "edmelyn bacusmo";
            case "BACUSMOEDMELYN@GMAIL.COM" -> "edmelyn bacusmo";
            case "BAJITJEANETTE8@GMAIL.COM" -> "Jeanette Bajit";
            case "BALANAMARIACORAZON@GMAIL.COM" -> "Maria Corazon Balana";
            case "BALDOZA12CHONA@GMAIL.COM" -> "chona baldoza";
            case "BALINGASA20@GMAIL.COM" -> "Annie mae Sanchez";
            case "BALMES.MARJA" -> "Marja Balmes";
            case "BALMES.MARJA@GMAI.COM" -> "Marja Balmes";
            case "BALMES.MARJA@GMAIL.COM" -> "Marja Leena Balmes";
            case "BALOLOR" -> "Bazel Armand R. Alolor";
            case "BALTAZARMARILOU5@GMAIL.COM" -> "Marilou Baltazar";
            case "BAMBA_2427@YAHOO.COM" -> "Kristina Erika Bustillos";
            case "BARAQUIEL.YOJYRAM@GMAIL.COM" -> "Mary Joy Caratao";
            case "BARRIENTOS@AEONFANTASY.COM.PH" -> "Drei Barrientos";
            case "BARRIENTOSKRISANGELI23@GMAIL.COM" -> "kris angeli barrientos";
            case "BARRIENTOSKRISANGELI@GMAIL.COM" -> "Kris Angeli Barrientos";
            case "BASHA29CASTRO@GMAIL.COM" -> "Bonaflor Castro";
            case "BAUTISTA@AEONFANTASY.COM.PH" -> "kHRISTIANNE BAUTISTA";
            case "BAYLONMARIALOLITA@GMAIL.COM" -> "MARIA LOLITA BAYLON";
            case "BDO0289@GMAIL.COM" -> "Beverly De La Cruz";
            case "BERMUDMELVIN7@GMAIL.COM" -> "MELVIN BERMUDO";
            case "BERMUDOMELVIN7@GMAIL.COM" -> "Melvin Bermudo";
            case "BERNOVICE27@YAHOO.COM" -> "Bernie Banday";
            case "BESENIOCINDY4@GMAIL.COM" -> "Cynthia Besenio";
            case "BEVSBEVS21@GMAIL.COM" -> "beverly batac";
            case "BHEBIEJEANP@GMAI.COM" -> "Bhebie Jean ParreÃ±o";
            case "BHEBIEJEANP@GMAIL.COM" -> "Bhebie Jean ParreÃ±o";
            case "BHERNA_0506@YAHOO.COM" -> "bernadette tomas";
            case "BINAMANLICLIC1231@GMAIL.COM" -> "Bina Manliclic";
            case "BINAMANLICLIC31@GMAIL.COM" -> "Bina Manliclic";
            case "BMANGONDATO@GMAIL.COM" -> "bimbong mangondato";
            case "BORBE@AEONFANTASY.COM.PH" -> "Luisa Borbe";
            case "BRANDODEGUZMAN13@GMAIL.COM" -> "JAYSON DE GUZMAN";
            case "BRIONESRUFA2020@GMAIL.COM" -> "Rufa Briones";
            case "BRYANJAYENRIQUEZ03@GMAIL.COM" -> "Bryan Jay Enriquez";
            case "CABIGAO430@GMAIL.COM" -> "Amael Cabigao";
            case "CAISIP@AEONFANTASY.COM.PH" -> "Jannica May Caisip";
            case "CAITCAIT.BRUAN@GMAIL.COM" -> "Jacquelin Bruan";
            case "CALDWELLBAYLON@GMAIL.COM" -> "Caldwell Baylon";
            case "CAMILLEBUERANO02@GMAIL.COM" -> "CAMILLE BUERANO";
            case "CAMPOSRUDYLYN@GMAIL.COM" -> "Rudylyn Campos";
            case "CANILLO.VINCE@GMAIL.COM" -> "John";
            case "CAPACITE33@GMAIL.COM" -> "Frecilda Capacite";
            case "CAPACITEFRECILDA@GMAIL.COM" -> "FRECILDA CAPACITE";
            case "CARISSA15JOY@GMAIL.COM" -> "CARISSA JOY TUMALIUAN";
            case "CAROL102517@GMAIL.COM" -> "caroline malinao";
            case "CAROL102518@GMAIL.COM" -> "caroline malinao";
            case "CASELYNTABASAN@GMAIL.COM" -> "Caselyn Tabasan";
            case "CATHCATH.VASQUEZ@YAHOO.COM" -> "Catherine Vasquez";
            case "CATHERENENAVAREZ@YAHOO.COM" -> "catherene navarez";
            case "CATHERINECRUZ014@GMAIL.COM" -> "catherine cruz";
            case "CBAJADO@ASI-EES.COM" -> "cbajado";
            case "CCABATINGAN@ASI-EES.COM.PH" -> "Alliance Tech";
            case "CEBRERO@AEONFANTASY.COM.PH" -> "Ricky Cebrero";
            case "CELINE.NAVAREZ02@GMAIL.COM" -> "catherene navarez";
            case "CESZ26@YMAIL.COM" -> "Princess Ann Castro";
            case "CEZ_NFSI@YAHOO.COM" -> "Cecille Ramos";
            case "CGAYOTIN@ASI-EES.COM" -> "cgayotin";
            case "CHEBROSAS15@GMAIL.COM" -> "CHERRY BRETILLER";
            case "CHENIZ_29@YAHOO.COM" -> "Gretchen Setotong";
            case "CHINGPARDO27@GMAIL.COM" -> "ching pardo";
            case "CHONABALDOZA10@GMAIL.COM" -> "chona baldoza";
            case "CHONABALDOZA1279@GMAIL.COM" -> "chona baldoza";
            case "CHONABALDOZA@GMAIL.COM" -> "Chona Baldoza";
            case "CHONABALDOZAAEON@GMAIL.COM" -> "Chona Baldoza";
            case "CHONAMATAMOROSALEOSALA@GMAIL.COM" -> "CHONA LEOSALA";
            case "CHRISTALNOCOSAMBRAY@GMAIL.COM" -> "Christal Ambray";
            case "CHRISTINE6ONABIA@GMAIL.COM" -> "CHRISTINE ONABIA";
            case "CHRISTINEJAYNEPERALTA745@GMAIL.COM" -> "Christine Jayne Peralta";
            case "CHUA@AEONFANTASY.COM.PH" -> "Kristine Chua";
            case "CIEANELLA826@GMAIL.COM" -> "Cielito Santos";
            case "CINMENZO@ASI-DEV1.COM" -> "Charlie Inmenzo";
            case "CIVIRAMTUDLAS84@YAHOO.COM" -> "Marivic Tudlas";
            case "CJENYVIEVE@GMAIL.COM" -> "Jenyvieve Carig";
            case "CLAIREANNGAVINO9@GMAIL.COM" -> "Claire Ann Gavino";
            case "CLAIREANNGVINO9@GMAIL.COM" -> "claire gavino";
            case "CLARISARAMOS24@GMAIL.COM" -> "Ma Clarissa Ramos";
            case "CLAROSALYN2018@GMAIL.COM" -> "Rosalyn Claro";
            case "CLARSMOD102018@GMAIL.COM" -> "clarence cedon";
            case "CLEOSALA753@GMAIL.COM" -> "Chona Leosala";
            case "CMALINAO829@GMAIL.COM" -> "CAROLINE MALINAO";
            case "COLLADO@AEONFANTASY.COM.PH" -> "Jennifer Collado";
            case "COLLADOERICKAROSE@GMAIL.COM" -> "ERICKA ROSE COLLADO";
            case "COLYNGAMBOA8@GMAIL.COM" -> "zyralyn gamboa";
            case "CONILASJOHN123@GMAIL.COM" -> "John Kenneth Conilas";
            case "CONSUELOBIASON45@GMAIL.COM" -> "Consuelo Biaosn";
            case "CONSUELOBIASON@45GMAIL.COM" -> "CONSUELO BIASON";
            case "CONSUELODENNISISHI45@GMAIL.COM" -> "Consuelo Biason";
            case "CORPUZEMIREEN@GMAIL.COM" -> "Emireen Corpuz";
            case "CQUIMBO@ASI-EES.COM.PH" -> "chris quimbo";
            case "CRAIGJHOANNA@GMAIL.COM" -> "jhoanna Craig";
            case "CRIMENROSEANNE@YAHOO.COM" -> "Rose Anne Crimen";
            case "CRISANTAMARTIN.ZHYRAH@GMAIL.COM" -> "Crisanta Martin";
            case "CRISTINA.MATURAN28@GMAIL.COM" -> "cristina maturan";
            case "CRISTYLUCEROANGELKITA@GMAIL.COM" -> "Cristy Lucero";
            case "CRITINAMATURAN28@GMAIL.COM" -> "cristina maturan";
            case "CSOMBILLO@ASI-EES.COM" -> "Christopher John Sombillo";
            case "CURRAY@AEONFANTASY.COM.PH" -> "Abe Curray";
            case "CUTIEFIGHTER16@GMAIL.COM" -> "abegail madrid";
            case "CUTIEFIGHTER_16@YAHOO.COM" -> "abegail madrid";
            case "CYLERRY@GMAIL.COM" -> "cherry Fernandez";
            case "CZARLANDICHO14@GMAIL.COM" -> "Czar Landicho";
            case "C_CONTRIDAS@AEONFANTASY.COM.PH" -> "CLAUDETTE CONTRIDAS";
            case "D.CRYSTAL-ESTOLOGA@BAYFRONTHOTELCEBU.COM" -> "Doreen Angelie";
            case "DAISYTANGSOLANO@GMAIL.COM" -> "Daisy";
            case "DALRYMPLEJANE.LUMEN.BSES@GMAIL.COM" -> "DALRYMPLE JANE LUMEN";
            case "DANGBARDOS.22@GMAIL.COM" -> "Sandra Bardos";
            case "DANICASENIN.18@GMAIL.COM" -> "Danica Senin";
            case "DANIELLUMONGSOD84@GMAIL.COM" -> "daniel lumongsod";
            case "DANNAKAYESD@GMAIL.COM" -> "Danna Kaye Mandani";
            case "DAPHNEANNEDB.AEON@GMAIL.COM" -> "Daphne Anne Barquilla";
            case "DARCHIEVERGARA@GMAIL.COM" -> "Darchie Vergara";
            case "DARIARUSSELANNP@GMAIL.COM" -> "Russel Ann Daria";
            case "DARIARUSSELANNP@YAHOO.COM" -> "Russel Ann Daria";
            case "DARWINPALMESCHAVEZ@GMAIL.COM" -> "Darwin Palmes";
            case "DARWINPALNESCHAVEZ@GMAIL.COM" -> "Darwin Palmes";
            case "DAYEN.IGDALINO@GMAIL.COM" -> "janith igdalino";
            case "DCMORA75@GMAIL.COM" -> "Debbie Mora";
            case "DDEEBONA@GMAIL.COM" -> "deebona domingo";
            case "DECLAROTINE2@GMAIL.COM" -> "valintine de claro";
            case "DELCASTILLOKATRINA16@GMAIL.COM" -> "katrina del castillo";
            case "DELCASTILLO_KATH31@GMAIL.COM" -> "katrina del castillo";
            case "DELCASTILLO_KATRINA@YAHOO.COM" -> "katrina del castillo";
            case "DELING82@YAHOO.COM" -> "Josefina D Bartolabac";
            case "DELING82YAHOO.COM" -> "Josefina D Bartolabac";
            case "DELLOMASMICHELLE04@GMAIL.C0M" -> "michelle Dellomas";
            case "DELLOMASMICHELLE04@GMAIL.COM" -> "MICHELLE DELLOMAS";
            case "DEMOCURA08@GMAIL.COM" -> "Dennis Rapanot";
            case "DENNIS.RAPANOT@NEC.COM.PH" -> "Dennis Rapanot";
            case "DGFETILUNA@ASI-DEV6.COM" -> "Dan Gynon Fetiluna";
            case "DHALLYALIGADO@GMAIL.COM" -> "Dally Aligado";
            case "DIANAADRALES44@GMAIL.COM" -> "Diana Adrales";
            case "DIONEPADILLO3@GMAIL.COM" -> "Dione Padillo";
            case "DIOSAPRINCESSTOLENTINO@GMAIL.COM" -> "Princess Diosa Dotollo";
            case "DIVINEGADDI26@GMAIL.COM" -> "DIVINE GADDI";
            case "DIZONLYZA35@GMAIL.COM" -> "Lyza Amor Dizon";
            case "DIZONROSALENE@GMAIL.COM" -> "rosalene dizon";
            case "DIZON_REIAN@YAHOO.COM" -> "Reian Dizon";
            case "DJPITCHSIDON@GMAIL.COM" -> "Ma. Rhodora Palagam";
            case "DLLOUPERT@GMAIL.COM" -> "loupert de leon";
            case "DLOUPERT@GMAIL.COM" -> "loupert de leon";
            case "DMACARILAY@CSYOUTSOURCING.COM" -> "David Nicholas";
            case "DMANAOG@ASI-DEV6.COM" -> "David";
            case "DOMINGODEEBONA@GMAIL.COM" -> "DEEBONA DOMINGO";
            case "DONNJHAYR@GMAIL.COM" -> "LADY MADONNA CATO";
            case "D_SENIN@AEONFANTASY.COM" -> "Danica Senin";
            case "ECORAMOSJAVIER@GMAIL.COM" -> "John Erieco Javier";
            case "EDELFA.GARCIA13@GMAIL.COM" -> "edelfa garcia";
            case "EDELYNCURRIE@GMAIL.COM" -> "EDELYN CURRIE";
            case "EDELYN_DELMUNDO@YAHOO.COM" -> "EDELYN CURRIE";
            case "EDERLITARESURRECCION@GMAIL.COM" -> "EDERLITA SACRO";
            case "EDNAPEDERNAL01@GMAIL.COM" -> "edna pedernal";
            case "EDU.CAPAROSO@GMAIL.COM" -> "edu caparoso";
            case "EESPANOLA@ASI-DEV1.COM" -> "Elna Espanola";
            case "EFIBELQUEVADA@GMAIL.COM" -> "Maria Efibel Quevada";
            case "EFRELYN772@GMAIL.COM" -> "EFRELYN BONAGUA";
            case "EJAYTALAVERA@GMAIL.COM" -> "Ejay Talavera";
            case "ELEANORDETORRES@YAHOO.COM.PH" -> "eleanor de torres";
            case "ELISEDETORRES06@YAHOO.COM" -> "Eleanor De Torres";
            case "ELJHAEY0529@GMAIL.COM" -> "Lerry Joyce Mandac";
            case "ELMO_TWISTER22@YAHOO.COM" -> "Lorilyn Karla Guevarra";
            case "EM08IAN@YAHOO.COM" -> "EMALYN ALMENCION";
            case "EMI.SGD@HOTMAIL.COM" -> "Noemi Sagrado";
            case "EMILYFLORES022487@GMAIL.COM" -> "Emily Verdadero";
            case "EMMEVELLTANO@GMAIL.COM" -> "EMMEVELL TANO";
            case "EMTAGUDANDO@GMAIL.COM" -> "Eric Tagudando";
            case "EMZGULPERE@GMAIL.COM" -> "emmie gulpere";
            case "ENCISAEMMANUEL@GMAIL.COM" -> "emmanuel encisa";
            case "ERGACITA16@GMAIL.COM" -> "EDNA ROSE GACITA";
            case "ERICA12GUZMAN29@GMAIL.COM" -> "ERICA GUZMAN";
            case "ERICAABANA2000@GMAIL.COM" -> "Erica Abana";
            case "ERICAQGUZMAN@GMAIL.COM" -> "erica guzman";
            case "ERICAVENTURA0000@GMAIL.COM" -> "ma.erica ventura";
            case "ERIKAMORENOBUSTILLOS@GMAIL.COM" -> "Kristina Erika Bustillos";
            case "ERINBLAIRXYRISH@GMAIL.COM" -> "Vinessa Linda Almoneda";
            case "ERIS.GASPE@NEC.COM.PH" -> "Eris Paul Gaspe";
            case "ERMAJOY84@GMAIL.COM" -> "erma joy tayo";
            case "ERNALEZAKARINAOREO10@GMAIL.COM" -> "Ernaleza Karina Oreo";
            case "ESMABEIVYROSE26@GMAIL.COM" -> "Ivy Rose Esmabe";
            case "ESPADERO.REYNAN@GMAIL.COM" -> "Reynante Espadero";
            case "ESPINOJJ0109@GMAIL.COM" -> "Jay Jay Espino";
            case "ESPINOJJ_0109@YAHOO.COM" -> "Jay Jay Espino";
            case "ESTERALJULIENNE@GMAIL.COM" -> "Julienne Marie Esteral";
            case "EUNICEAGUSTIN2613@GMAIL.COM" -> "Eunice Millo";
            case "EYAH_AEHR@YAHOO.COM" -> "Rhea Portos";
            case "E_ANCHETA@AEONFANTASY.COM.PH" -> "Eduard Ancheta";
            case "E_JAVIER@AEONFANTASY.COM.PH" -> "John Erieco Javier";
            case "FABULARIJEPZEL@GMAIL.COM" -> "JEPZEL FABULARI";
            case "FEDERISJANINE@GMAIL.COM" -> "Franz Janine Federis";
            case "FEMME.SPILL@GMAIL.COM" -> "helga ladao";
            case "FLORESMHEAN@GMAIL.COM" -> "Mary Ann Flores";
            case "FPENNYLLANE@YAHOO.COM" -> "Pennyllane Fernandez";
            case "FRANCIALIEZL8@GMAIL.COM" -> "Liezl Creus";
            case "FRHEA987@GMAIL.COM" -> "Rhea Fernandez";
            case "GALAROZAARMIE08@GMAIL.COM" -> "Armie Plaza";
            case "GALIGAOABIGAIL@GMAIL.COM" -> "abigail coquia";
            case "GAYO.CRISANTA@GMAIL.COM" -> "Ma. Crisanta Valderama";
            case "GAYOPOLENTISIMA@GMAIL.COM" -> "Gay Opolentisima";
            case "GEANIETAYAG@GMAIL.COM" -> "Anne Eugene Tayag";
            case "GEMALYN_V@YAHOO.COM" -> "Gemalyn Villas";
            case "GERMAINEMORONG@GMAIL.COM" -> "Germaine Dae Morong";
            case "GERONAIBYANG@GMAIL.COM" -> "Ivy Gerona";
            case "GGUIN@ASI-DEV5.COM" -> "Gerome Guin";
            case "GHEAHERMOSA03@GMAIL.COM" -> "Beverly Jayson";
            case "GILBERTTEJADA31@YAHOO.COM" -> "Gilbert Tejada";
            case "GISUNGA0522@GMAIL.COM" -> "glenner ian sunga";
            case "GLENDAABAYA0629@GMAIL.COM" -> "Glenda Abaya";
            case "GLENNEVARDONE33@GMAIL.COM" -> "glenn evardone";
            case "GLESAKAYSINDAC@GMAIL.COM" -> "glesa kay sindac";
            case "GOTHEYES1130@GMAIL.COM" -> "Angelie nibay";
            case "GREGORYBEN29@GMAIL.COM" -> "gregory benesisto";
            case "GRENDYJOY@GMAIL.COM" -> "rendy joy gajeton";
            case "GRENDYJOY@YAHOO.COM" -> "rendy joy gajeton";
            case "GRISK_SHAYNE_21@YAHOO.COM" -> "Louie Shayne Papa";
            case "GTANCIOCO@ASI-EES.COM.PH" -> "Guiy Juergen Tancioco";
            case "GUERREROROGER92@GMAIL.COM" -> "Roger Guerrero Jr.";
            case "G_AFANTE@AEONFANTASY.COM.PH" -> "Gian Carlo Afante";
            case "HACIR_06@YAHOO.COM" -> "Ricah Joy Noche";
            case "HAGUIREKHANAME@GMAIL.COM" -> "christian leonor";
            case "HAIDEE.JUBAN@GMAIL.COM" -> "Haidee Juban";
            case "HANAH.CABALES10@GMAIL.COM" -> "hanah cabales";
            case "HANNAHLEEBAQUIL@YAHOO.COM" -> "Hannahlee Baquil";
            case "HAPPYAUBREY@GMAIL.COM" -> "Aubrey Anjila Villanueva";
            case "HAPPYAURBEY@GMAIL.COM" -> "Aubrey Anjila Villanueva";
            case "HAVEGAEL@GMAIL.COM" -> "avegael hara";
            case "HAYDEEYUMO19@GMAIL.COM" -> "Haydee Yumo";
            case "HAZEL1227@GMAIL.COM" -> "HAZEL DANGARAN";
            case "HAZELESPELITA030318@GMAIL.COM" -> "HAZEL DE GUZMAN";
            case "HAZEL_ARQUIZA@YAHOO.COM.PH" -> "hazel simon";
            case "HAZZELVIBAR@GMAIL.COM" -> "hazzel vibar";
            case "HC071219@GMAIL.COM" -> "HAZEL DANGARAN";
            case "HMPUNZALAN.ZEPER@GMAIL.COM" -> "hazel ann punzalan";
            case "HONEYAPRIL20@GMAIL.COM" -> "April Dimalanta";
            case "HUGO@AEONFANTASY.COM.PH" -> "Patrick Hugo";
            case "HZARA29@GMAIL.COM" -> "Arjane Atanacio";
            case "I.REMOROZO@YAHOO.COM" -> "Ivy Remorozo";
            case "IALMADOVAR@ASI-DEV5.COM" -> "Irelle Almadovar";
            case "IALMODOVAR@ASI-DEV5.COM" -> "Irelle Almodovar";
            case "IAMMINERVALAPUZ@YAHOO.COM" -> "MINERVA LAPUZ";
            case "ILOVEYOUCHARM23@GMAIL.COM" -> "Florence Acosta";
            case "ILYNTESORA14@GMAIL.COM" -> "Ilyn Tesora";
            case "IMELENDRES@ASI-EES.COM" -> "Ian Melendres";
            case "IMPERIALMAY.ACC@GMAIL.COM" -> "may imperial";
            case "INAMIAH79@GMAIL.COM" -> "beth policarpio";
            case "INOCENCIOMICHELLEGRACE8@GMAIL.COM" -> "MICHELLE GRACE INOCENCIO";
            case "INTERINONELVIEGRACE@GMAIL.COM" -> "NELVIE GRACE INTERINO";
            case "IREMOROZO@YAHOO.COM" -> "Ivy Remorozo";
            case "IRENEODECLARO79@GMAIL.COM" -> "VALINTINE DE CLARO";
            case "ISAACMANANSALA@GMAIL.COM" -> "ISAAC JR MANANSALA";
            case "IVANAIRAHSOLANO051294@GMAIL.COM" -> "Ivan Airah Solano";
            case "JACKSON.ANG23@YAHOO.COM" -> "Jackson Ang";
            case "JACQUELINELCLEOFAS@GMAIL.COM" -> "jacqueline cleofas";
            case "JAI23RA@GMAIL.COM" -> "Jaira Lumactod";
            case "JAIEVANGELISTA07@GMAIL.COM" -> "Janet Evangelista";
            case "JAIMEJEPSANI@GMAIL.COM" -> "jaime jepsani";
            case "JALCAYDE@ASI-DEV3.COM" -> "Jmark Alcayde";
            case "JALLER@CSYOUTSOURCING.COM" -> "Juaquin Fert Aller";
            case "JAMAICSAJELA@GMAIL.COM" -> "jamaica marie ajela";
            case "JAMEJEPSANI@GMAIL.COM" -> "James Jepsani";
            case "JANECANO0509@GMAIL.COM" -> "Jane Cano";
            case "JANEMARYBALANA@GMAIL.COM" -> "Maryjane Balana";
            case "JANESAGARIO1020@GMAIL.COM" -> "Jane Dablo";
            case "JANNENVALDEZ0929@GMAIL.COM" -> "Jannen Valdez";
            case "JANNSENDIONISIO04@GMAIL.COM" -> "Jannsen Dionisio";
            case "JAQPSTFU@YAHOO.COM" -> "Quennie Pamela Baltazar";
            case "JARABELOJEN@GMAIL.COM" -> "JENNIELYN JARABELO";
            case "JASMINEANDAYA78@GMAIL.COM" -> "jasmine andaya";
            case "JASMINEANDAYA89@GMAIL.COM" -> "jasmine andaya";
            case "JASMINEVELASCO1988@GMAIL.COM" -> "Jasmine Velasco";
            case "JASMINVELASCO1988@GMAIL.COM" -> "Jasmin Velasco";
            case "JAY.LEDRES@NEC.COM.PH" -> "Jay Ledres";
            case "JAYAEVELUZ@GMAIL.COM" -> "Jaya Veluz";
            case "JBOLOTAOLO@CSYOUTSOURCING.COM" -> "Welmer Bolotaolo";
            case "JCABALE@ASI-EES.COM" -> "jay r cabale";
            case "JCABALE@ASI-EES.COM.PH" -> "jayr cabale";
            case "JCANILLO@ASI-EES.COM.PH" -> "John V Canillo";
            case "JDESCARTIN@CSYOUTSOURCING.COM" -> "John Paul Descartin";
            case "JEANETTE.BAJIT08@GMAIL.COM" -> "Jeanette Bajit";
            case "JELICAKUDEMUS0806@GMAIL.COM" -> "Jelica Kudemus";
            case "JEMMARBAGAMAN@GMAIL.COM" -> "Jemmar Bagaman";
            case "JEN.DATOS123@GMAIL.COM" -> "Jennifer Datos";
            case "JENEYURI29@GMAIL.COM" -> "jene yuri";
            case "JENNIFERDATOS123@GMAIL.COM" -> "JENNIFER DATOS";
            case "JENNIFERURSUA93@GMAIL.COM" -> "Jennifer Ursua";
            case "JENNIVIEVECASIMIRO@GMAIL.COM" -> "Jennivieve Leano";
            case "JENNLASCUNA28@GMAIL.COM" -> "Jennalyn Lascuna";
            case "JENNYJASMIN1317@GMAIL.COM" -> "Jennifer Jasmin";
            case "JENNYRUIZ060586@GMAIL.COM" -> "Jenny Ruiz";
            case "JEPZHYIE0811@GMAIL.COM" -> "zyra ricohermoso";
            case "JEPZHYKIE0811@GMAIL.COM" -> "zyra ricohermoso";
            case "JEREZANNALIZA08@GMAIL.COM" -> "ANNALIZA JEREZ";
            case "JEREZANNE02" -> "Annaliza Jerez";
            case "JEREZANNE02@GMAIL.COM" -> "Annaliza Jerez";
            case "JERICOBAGTASOS11@GMAIL.COM" -> "Jerico Bagtasos";
            case "JERRAHGWAPA24@GMAIL.COM" -> "JERRYSON MABBORANG";
            case "JERRAHMABBORANG@GMAIL.COM" -> "JERRYSON MABBORANG";
            case "JERRYLYNALAURIN@GMAIL.COM" -> "JERRYLYN ALAURIN";
            case "JERRYLYNALAURIN@YAHOO.COM" -> "Jerrylyn Alaurin";
            case "JERSONSULTAN@GMAIL.COM" -> "jerson sultan";
            case "JESSAAV25@GMAIL.COM" -> "Jessa Mae Vargas";
            case "JESSAMAYPAULME10@GMAIL.COM" -> "JESSA MAY PAULME";
            case "JESSAMIELAMARCA" -> "JISSAMIE LAMARCA";
            case "JESSAMIELAMARCA@GMAIL.COM" -> "JISSAMIE LAMARCA";
            case "JESSAVARGAS1997@GMAIL.COM" -> "JESSA MAE VARGAS";
            case "JESSICABASQUINAS12@GMAIL.COM" -> "jessica basquinas";
            case "JESSICABASQUINAS7@GMAIL.COM" -> "JESSICA BASQUINAS";
            case "JESSICALOZANO00018@GMAIL.COM" -> "Jessica Lozano";
            case "JESSICASHINESANTOS2590@GMAIL.COM" -> "JESSICA SHINE SANTOS";
            case "JESZAPARAN1204@GMAIL.COM" -> "jesza paran";
            case "JESZAPARAN1204@YAHOO.COM" -> "JESZA PARAN";
            case "JEWELANNFABRICANTE@GMAIL.COM" -> "JEWEL ANN FABRICANTE";
            case "JEZZDARVIN@GMAIL.COM" -> "Ma. Jezette Darvin";
            case "JFRANCISCO@ASI-DEV1.COM" -> "jeffrey francisco";
            case "JFRANCISCO@ASO-DEV1.COM" -> "Jeffrey Francisco";
            case "JHAGONDRANEOS@GMAIL.COM" -> "Janine Liela Antazo";
            case "JHAMDUMALIG977@GMAIL.COM" -> "jamicah dumalig";
            case "JHAZANDAYA@GMAIL.COM" -> "JASMINE ANDAYA";
            case "JHENAMADOR14GMAIL.COM" -> "jennifer amador";
            case "JHENAMADOR2@GMAIL.COM" -> "jhen amador";
            case "JHENCASERES@YAHOO.COM" -> "Mary Jane Ebcas";
            case "JHENCORTEZ120@GMAIL.COM" -> "jenneth avillanoza";
            case "JHONGAYLORDCALANOGA21@GMAIL.COM" -> "Jhon Gaylord Calanoga";
            case "JHONGAYLORDCALANOGA@GMAIL.COM" -> "jhon gaylord calanoga";
            case "JHONZERFAYEPHEECALANOGA@GMAIL.COM" -> "jhon gaylord calanoga";
            case "JIMMYLYNSACLOTE013@GMAIL.COM" -> "Jimmylyn Saclote";
            case "JINGJAYVALENCIABOLIMA25@GMAIL.COM" -> "Jing Jay Bolima";
            case "JINJIM1992@GMAIL.COM" -> "Cristina Yvette Selpides";
            case "JINKZLAZARTE@GMAIL.COM" -> "Jinky Lazarte";
            case "JLU@ASI-DEV1.COM" -> "Jamie Lu";
            case "JMADEJA100@GMAIL.COM" -> "JESEBEL PALMA";
            case "JMANZANO@CSYOUTSOURCING.COM" -> "Jefferson Manzano";
            case "JMARYROSE25@YAHOO.COM" -> "MARY ROSE JOSE JOSE";
            case "JMONSANTO.ASIDEV@GMAIL.COM" -> "John Paul";
            case "JMONSANTO@CSYOUTSOURCING.COM" -> "John Paul Monsanto";
            case "JNARCA@ASI-DEV3.COM" -> "John Paolo Narca";
            case "JO.CALAMAYA24@GMAIL.COM" -> "JOSEFINA CALAMAYA";
            case "JOANAMARIEDUMASIG2019@GMAIL.COM" -> "JOANA MARIE DUMASIG";
            case "JOANAMARIEDUMASIG@GMAIL.COM" -> "Joana Marie Dumasig";
            case "JOANNENARAG33@GMAIL.COM" -> "Joanne Narag";
            case "JOANTAMAYO99@GMAIL.COM" -> "JOAN TAMAYO";
            case "JOCELAZADON19@GMAIL.COM" -> "Jorecel Guerrero";
            case "JOCELYN031327@GMAIL.COM" -> "jocelyn san juan";
            case "JOCELYNSANJUAN101325@GMAIL.COM" -> "JOCELYN SAN JUAN";
            case "JOCELYNSANJUAN984@GMAIL.COM" -> "jocelyn san juan";
            case "JOELJOHN.BRUAN0411@GMAIL.COM" -> "jacquelin bruan";
            case "JOHNEDWARDSOLANO@GMAIL.COM" -> "Jets Wade";
            case "JOHNMURPHYMIRANDE@GMAIL.COM" -> "john murphy mirande";
            case "JOLS_1978@YAHOO.COM.PH" -> "Joly Gelladuga";
            case "JOLYGELLADUGA3@GMAIL.COM" -> "JOLY GELLADUGA";
            case "JOLYGELLADUGA@YAHOO.COM" -> "joly gelladuga";
            case "JONALYNCUYONG28@GMAIL.COM" -> "jonalyn cuyong";
            case "JONALYNCUYONG@YAHOO.COM" -> "Jonalyn Cuyong";
            case "JORENZO060515@GMAIL.COM" -> "Jovic Mae Agujo";
            case "JORENZO06051@GMAIL.COM" -> "Jovic Mae Agujo";
            case "JORLANDA@CSYOUTSOURCING.COM" -> "Dev User";
            case "JOSEMARYROSE28@GMAIL.COM" -> "Mary Rose Jose";
            case "JOSHCAYABYAB07@GMAIL.COM" -> "Joshua Pagad";
            case "JOSHPHISIT@GMAIL.COM" -> "JOSHUA PHILIP MONISIT";
            case "JOSHUAPHILIPM@ROCKETMAIL.COM" -> "Joshua Philip Monisit";
            case "JOSHUAPHILIPMONISIT@ROCKETMAIL.COM" -> "JOSHUA PHILIP MONISIT";
            case "JOVINALNETTE@GMAIL.COM" -> "nette jovinal";
            case "JOYCEANNEPENTECOSTES@YAHOO.COM" -> "joyce anne pentecostes";
            case "JOYCEDP.358@GMAIL.COM" -> "MARIE JOYCE PUJANES";
            case "JOYESMABE3@GMAIL.COM" -> "Mery joy Esmabe";
            case "JOYFEVILLANUEVA20@GMAIL.COM" -> "Joyfe Ayudoc";
            case "JOZELLE_JOY17@YAHOO.COM" -> "Jozelle Joy Magadia";
            case "JPAPASIN@CSYOUTSOURCIN.COM" -> "Junrey Papasin";
            case "JPMASANGCAY@LIVE.COM.PH" -> "John Paul Masangcay";
            case "JPONCE150@YAHOO.COM" -> "joy joan ponce";
            case "JRDESOYO@CSYOUTSOURCING.COM" -> "joserico";
            case "JRMOISES2324@GMAIL.COM" -> "jennie rose moises";
            case "JRONATO@ASI-EES.COM" -> "jronato";
            case "JRONATO@ASI-EESC.COM" -> "jronato";
            case "JRUIZ.ASIDEV@GMAIL.COM" -> "John Ruiz";
            case "JRUIZ@CSYOUTSOURCING.COM" -> "John Paul Ruiz";
            case "JSARDA@ASI-EES.COM.PH" -> "Jesson (Alliance IT)";
            case "JSMNESNCHO@GMAIL.COM" -> "Jasmine Joy Sancho";
            case "JSOLANO@ASI-DEV3.COM" -> "John Edward Solano";
            case "JSOLANO@ASI-EES.COM.PH" -> "John Edward Solano";
            case "JSULTAN@ASI-DEV3.COM" -> "jers sultan";
            case "JSUMAYAO@ASI-EES.COM.PH" -> "Jhazteen";
            case "JTOLENTIN@ASI-EES.COM" -> "jtolentin";
            case "JUDYANNDIRIJE1@GMAIL.COM" -> "Judy Ann Dirije";
            case "JUDYDONGOAN2001@GMAIL.COM" -> "Judy";
            case "JULIE_GERALDINO25@YAHOO.COM" -> "Julie Geraldino";
            case "JUNELOU23@GMAIL.COM" -> "Junelou Epanto";
            case "JUNIOTEAROSE@GMAIL.COM" -> "Tea Rose Junio";
            case "JURBIZTONDO@ASI-EES.COM.PH" -> "Jerick Urbiztondo";
            case "JUTRINWORKEMAIL9@GMAIL.COM" -> "JUTRIN";
            case "JUVIEALAS@GMAIL.COM" -> "Juvie Rose Alas";
            case "JUVY.ALIANZA@GMAIL.COM" -> "Juvy Alianza";
            case "J_AMARILLE@AEONFANTASY.COM.PH" -> "Jonathan Amarille";
            case "J_BAJIT@AEONFANTASY.COM.PH" -> "Jeanette Bajit";
            case "J_CRUZ@AEONFANTASY.COM.PH" -> "JAMES JOSEPH CRUZ";
            case "J_DACLES@AEONFANTASY.COM.PH" -> "Jeminah Dacles";
            case "KABELA@CSYOUTSOURCING.COM" -> "kevin abela";
            case "KATHLENEJOYCEMARGELINO@GMAIL.COM" -> "Kathlene Margelino";
            case "KAZYANNS@GMAIL.COM" -> "kazy ann santiago";
            case "KCEEGIEH18@GMAIL.COM" -> "Aiza Rheygene Casbadillo";
            case "KCIRTAPHUGO09@GMAIL.COM" -> "John Patrick Hugo";
            case "KDELFINO@CSYOUTSOURCING.COM" -> "kenneth";
            case "KENETHJOY.AEON@GMAIL.COM" -> "KENETH JOY OLITRES";
            case "KHAISARDAN@GMAIL.COM" -> "Khai Buaya";
            case "KHALHAS.RRS@GMAIL.COM" -> "ryan rey santos";
            case "KHANJIE0811@GMAIL.COM" -> "Khanyao Haborul Perfinan";
            case "KHAYEVILLANUEVA11@GMAIL.COM" -> "Kath Villanueva";
            case "KHAYSINDAC@GMAIL.COM" -> "glesa sindac";
            case "KHYSZA21@GMAIL.COM" -> "crislyn arado";
            case "KIDZOOONAPH@GMAIL.COM" -> "Ariel June Jabagaton";
            case "KIMBERLY.GALABAN@YAHOO.COM" -> "Kimberly Galaban";
            case "KIMBERLYBASILIO02@YAHOO.COM" -> "KIMBERLY BASILIO";
            case "KIMJHADE09@YAHOO.COM" -> "Dianne Kimberly Lampos";
            case "KIMQUINQUINO.LAZADA@GMAIL.COM" -> "kimberly anne quinquino";
            case "KKBYANYAN08@YAHOO.COM" -> "April Dayrit";
            case "KOCARIZA93@GMAIL.COM" -> "sheryl diones";
            case "KOIZELWIN45@GMAIL.COM" -> "elwin alcaldeza";
            case "KQUEMUEL@ASI-EES.COM.PH" -> "Kim Quemuel";
            case "KRADOVAN022215@GMAIL.COM" -> "Kristina Radovan";
            case "KRISTELLECONCEPCION23@GMAIL.COM" -> "Kristelle Mae Concepcion";
            case "KRISTINAGACOSTA27@GMAIL.COM" -> "MA. KRISTINA GACOSTA";
            case "KRISTINEYTURIAGA28@GMAIL.COM" -> "kristine yturiaga";
            case "KRISTINEYTURIAGA@GMAIL.COM" -> "kristine yturiaga";
            case "KRIZALAINE7@GMAIL.COM" -> "kriza laine cofino";
            case "KYRA_BREZUELA@YAHOO.COM" -> "Kyra Ybasco";
            case "K_NOHAY@AEONFANTASY.COM.PH" -> "Kristine Nohay";
            case "LAARNITAROSANAN09@GMAIL.COM" -> "laarni tarosanan";
            case "LANYCUTAMORA99@GMAIL.COM" -> "Lany Sheen Cutamora";
            case "LANZB6@GMAIL.COM" -> "Diana Adrales";
            case "LARIZA25_MANLUTAC@YAHOO.COM" -> "lariza manlutac";
            case "LARRASERAFINO25@GMAIL.COM" -> "Larra Serafino";
            case "LBACULAOLOYO@GMAIL.COM" -> "Leo Baculaoloyo";
            case "LEAH.CORITANA@YAHOO.COM" -> "Leah Coritana";
            case "LEALOPEZ888@GMAIL.COM" -> "lea lopez";
            case "LEIJUNDOMINGO51586@GMAIL.COM" -> "leonardo domingo";
            case "LEOJEROMESARIEGO1@GMAIL.COM" -> "Leo Jerome Sariego";
            case "LEONORA_BERNARDO@AEONFANTASY.COM.PH" -> "Lea Bernardo";
            case "LETTYALMENDRAS9@GMAIL.COM" -> "LETICIA COSICO";
            case "LHANARNALDO07@GMAIL.COM" -> "lhan arnaldo";
            case "LHANARNALDO@GMAIL.COM" -> "Lhan Arnaldo";
            case "LHENDELAPAZ7@GMAIL.COM" -> "LEONILYN DE LA PAZ";
            case "LHENDIZON@GMAIL.COM" -> "ROSALENE DIZON";
            case "LHEO.ABANILLA@GMAIL.COM" -> "Lheo Abanilla";
            case "LHONMANANSALA@GMAIL.COM" -> "marlon manansala";
            case "LIBERTYMARTINEZ123128@GMAIL.COM" -> "liberty martinez";
            case "LIBERTYMARTINEZ_14@GMAIL.COM" -> "liberty martinez";
            case "LIEZLMIRAFLOR3@GMAIL.COM" -> "liezl miraflor";
            case "LIGLESIA@ASI-EES.COM" -> "lito iglesia";
            case "LINAR7615@GMAIL.COM" -> "Lina Rivera";
            case "LINARIVERA001@GMAIL.COM" -> "Lina Rivera";
            case "LINETHHERNANDEZDEFENSA@GMAIL.COM" -> "lineth defensa";
            case "LINGADRUSETTE@GMAIL.COM" -> "rusette lingad";
            case "LIZA.PENPENA@GMAIL.COM" -> "LIZA PENPENA";
            case "LJSARIEGO65@GMAIL.COM" -> "Leo Jerome Sariego";
            case "LLABADAN@CSYOUTSOURCING.COM" -> "Lalaine Labadan";
            case "LOPEZJONATHAN1805@GMAIL.COM" -> "JONATHAN LOPEZ";
            case "LORIN032383@GMAIL.COM" -> "Florenda Capila";
            case "LORJAVIER080589@GMAIL.COM" -> "Lorraine Javier";
            case "LOUISSESHANE30@GMAIL.COM" -> "maricel barcoma";
            case "LOUREMAESISON@GMAIL.COM" -> "Loure Mae Sison";
            case "LOVELYNJOYALEJAGA@GMAIL.COM" -> "lovelyn joy alejaga";
            case "LOVIECABANGISAN933@GMAIL.COM" -> "Lovely Cabangisan";
            case "LRBACULAOLOYO@GMAIL.COM" -> "Leo Baculaoloyo";
            case "LUCYJEAN276@GMAIL.COM" -> "lucy jean gura";
            case "LUCYJEAN276@YAHOO.COM" -> "lucy jean gura";
            case "LYN.02DC@GMAIL.COM" -> "Maybelyn Dela Cruz";
            case "LYNCELD@YAHOO.COM" -> "lyncel dela cruz";
            case "LYNLYN061981@GMAIL.COM" -> "reynalyn corvera";
            case "MACALALADMELYN1989@GMAIL.COM" -> "REMELYN PACAUL";
            case "MADRIDABEGAIL18@GMAIL.COM" -> "abegail madrid";
            case "MAEJHEANEVALDERAMA30@GMAIL.COM" -> "MAE JHEANE VALDERAMA";
            case "MAESTRELLA_CORNELIO@YAHOO.COM.PH" -> "ma. estrella cornelio";
            case "MAFESXON14@GMAIL.COM" -> "ma.fe bernardo";
            case "MAGTAASJJ@GMAIL.COM" -> "Jonnel Jayson Magtaas";
            case "MAJA2625@GMAIL.COM" -> "Marivic Tudlas";
            case "MALTOMARIE@GMAIL.COM" -> "maricel malto";
            case "MALZVSIPALAY27@GMAIL.COM" -> "MALOU VARGAS";
            case "MANLICLICBINA@GMAIL.COM" -> "Bina Manliclic";
            case "MANNANSHENG@GMAIL.COM" -> "sherwina baginda";
            case "MAPRILCAMAY0326@GMAI.COM" -> "April Camay Hernandez";
            case "MAPRILCAMAY0326@GMAIL.COM" -> "April Camay Hernandez";
            case "MARAHLEIKA26@YAHOO.COM" -> "Marah Leika De La Torre";
            case "MARGEDELOSREYES09@GMAIL.COM" -> "Ma. Georiza Roxas";
            case "MARIAELISABARCELONA@GMAIL.COM" -> "Maria Elisa Gonzales";
            case "MARICARBLANDO1984@GMAIL.COM" -> "Maricar Blando";
            case "MARICARLY2012@GMAIL.COM" -> "maricarl marcelo";
            case "MARICARREGIDOR001@GMAIL.COM" -> "Maricar Regidor";
            case "MARICARREGIDOR2@GMAIL.COM" -> "maricar regidor";
            case "MARICELGONZALES@GMAIL.COM" -> "Maricel Gonzales";
            case "MARICELPACUN62@GMAIL.COM" -> "maricel pacun";
            case "MARICELPACUN@GMAIL.COM" -> "Maricel Pacun";
            case "MARICORNOORA08@GMAIL.COM" -> "Maricor Noora";
            case "MARICORNOORA@GMAIL.COM" -> "maricor noora";
            case "MARIELCORTEZ23@GMAIL.COM" -> "Maria Lourdes Cortez";
            case "MARIVICTUDLAS001@GMAIL.COM" -> "Marivic Tudlas";
            case "MARIVICTUDLAS84" -> "Marivic Tudlas";
            case "MARIZADIONISIO14@GMAIL.COM" -> "mariza dionisio";
            case "MARJOERIEMORAL@GMAIL.COM" -> "MARJOERIE MORAL";
            case "MARKANTHONYLOCSIN20@GMAIL.COM" -> "Mark Anthony Locsin";
            case "MARKBELGA1234@GMAIL.COM" -> "mark belga";
            case "MARKBELGA620@GMAIL.COM" -> "mark belga";
            case "MARKDEXTERMALAPITAN96@GMAIL.COM" -> "Mark Dexter Malapitan";
            case "MARKJARAVATA457@GMAIL.COM" -> "Mark John Jaravata";
            case "MARKLEE.SITCHON@GMAIL.COM" -> "mark lee sitchon";
            case "MARQUEZ@AEONFANTASY.COM.PH" -> "Gina Marquez";
            case "MARRIANNE874@GMAIL.COM" -> "MARRIANNE UNTALAN";
            case "MARSHAMIAGAO@GMAIL.COM" -> "Ma. Marsha Miagao";
            case "MARVIN_ANCHETA@AEONFANTASY.COM.PH" -> "Marvin Ancheta";
            case "MARYANNMARGALLO2121@GMAIL.COM" -> "Mary Ann Margallo";
            case "MARYGRACEDIMACALI11@GMAIL.COM" -> "mary grace dimacali";
            case "MARYGRACEDIMACALI@GMAIL.COM" -> "mary grace dimacali";
            case "MARYJOYORIA42@GMAIL.COM" -> "Mary Joy Oria";
            case "MARYLENEANNDIOLE@GMAIL.COM" -> "Marylene Ann Diole";
            case "MARYNITTD@GMAIL.COM" -> "mary nitt damolo";
            case "MARYNITTDAMOLO@GMAIL.COM" -> "mary nitt damolo";
            case "MARYPREXY_RAMILO@YAHOO.COM" -> "Mary Prexy Ramilo";
            case "MARYROSEMAHINAY23@GMAIL.COM" -> "MARY ROSE MAHINAY";
            case "MATEOERICA17@GMAIL.COM" -> "Erica Mateo";
            case "MAVERICK.MEJALA7@GMAIL.COM" -> "Andrie Maverick Mejala";
            case "MAVERICK0626@YAHOO.COM" -> "Lady Madonna Cato";
            case "MAYANNGEPIT49@GMAIL.COM" -> "may ann gepit";
            case "MAYAROSE.19.RI@GMAIL.COM" -> "rosemarie ignacio";
            case "MAYBELBANSAGAN@GMAIL.COM" -> "Maybel Bansagan";
            case "MAYLENEBERNARDINO27@GMAIL.COM" -> "Maylene Bernardino";
            case "MBOLOTAULO1977@GMAIL.COM" -> "Maila Bolotaulo";
            case "MBOLOTAULO77@GMAIL.COM" -> "maila bolotaulo";
            case "MCBALANA21@GMAIL.COM" -> "maria corazon balana";
            case "MCHE0930@GMAIL.COM" -> "MIchelle Paculaba";
            case "MEDELROVILYN@YAHOO.COM" -> "rovilyn menguito";
            case "MEGMARISCOTES@GMAIL.COM" -> "mercy grace libardo";
            case "MENDIOLAMYRNA0424@GMAIL.COM" -> "Myrna Mendiola";
            case "MERCADO.AEONFANTASY@GMAIL.COM" -> "theresa mercado";
            case "MGRARROZA80@GMAIL.COM" -> "Melynn Grace Arroza";
            case "MGROXAS09@GMAIL.COM" -> "Ma.Georiza Roxas";
            case "MHAUEE_CABIGAO014@YAHOO.COM" -> "maurene cabigao";
            case "MHEGZVILLAS@GMAIL.COM" -> "gemalyn villas";
            case "MHENGHO18@GMAIL.COM" -> "Shairmaine Advincula";
            case "MIAGAO@AEONFANTASY.COM.PH" -> "ma marsha miagao";
            case "MIAQUERENCIA1@GMAIL.COM" -> "MIRIAM QUERENCIA";
            case "MICAH.RYOAKI@GMAIL.COM" -> "Micah Factor";
            case "MICHELLEANNBARNUEVO@GMAIL.COM" -> "michelle ann barnuevo";
            case "MICHELLEANNDELORIA@GMAIL.COM" -> "michelle ann deloria";
            case "MICKEYVANJIEMOUSE@GMAIL.COM" -> "Vangeline Galano";
            case "MIKEL26_1984@YAHOO.COM" -> "michael fabregas";
            case "MILAGROSBARBOSA1985@GMAIL.COM" -> "Milagros Barbosa";
            case "MINSUMMER082915@GMAIL.COM" -> "Jan Mark Gasendo";
            case "MINSUMMER082915@YAHOO.COM" -> "Jan Mark Gasendo";
            case "MINTBUTTERFLY01@YAHOO.COM" -> "Jhasmin Nuqui";
            case "MINZLAPUZ@GMAIL.COM" -> "minerva lapuz";
            case "MISHSHA2011@GMAIL.COM" -> "Doris Michelle Permelona";
            case "MJANE.CABER@GMAIL.COM" -> "MARY JANE CABER";
            case "MJCAGALITAN" -> "mjcagalitan";
            case "MONARES.RAIN@YAHOO.COM" -> "Randy Monares";
            case "MONARES@AEONFANTASY.COM.PH" -> "Randy Monares";
            case "MONICAGRACE.MANGUNAY@GMAIL.COM" -> "MONICA GRACE MANGUNAY";
            case "MOWAYIR921@WUZAK.COM" -> "Al";
            case "MPADA@ASI-EES.COM" -> "Shaundy Pada";
            case "MRYNAMENDIOLA@GMAIL.COM" -> "myrna mendiola";
            case "MSALAZAR@ASI-EES.COM.PH" -> "marcel salazar";
            case "MSARCIA010185@GMAIL.COM" -> "Ma. Theresa Sarcia";
            case "MTAGLE137@GMAIL.COM" -> "JANELLE";
            case "MVILLEGAS@ASI-EES.COM" -> "Jecca Villegas";
            case "MYLINE.DEGUZMAN77@YAHOO.COM" -> "myline domingo";
            case "MYLINEDOMINGO.17@GMAIL.COM" -> "Myline Domingo";
            case "MYRAALTIZON@GMAIL.COM" -> "Myra Pelegria";
            case "MYRNAMENDIOLA0424@GMAIL.COM" -> "Myrna Mendiola";
            case "M_CHOSA@AEONFANTASY.JP" -> "Manabu Chosa";
            case "NAIZACASINILLO0@GMAIL.COM" -> "naiza casinillo";
            case "NARIKAOREO@GMAIL.COM" -> "ERNALEZA KARINA OREO";
            case "NATALIELEANNEMALONZO@GMAIL.COM" -> "natalie esmeralda";
            case "NATIVIDADARIEL230@GMAIL.COM" -> "ariel natividad";
            case "NAVAREZCATHERENE@GMAIL.COM" -> "catherene navarez";
            case "NCARINGAL@ASI-DEV5.COM" -> "Nicson Caringal";
            case "NETSIE27@GMAIL.COM" -> "Vanessa";
            case "NETTEJOVINAL0325@YAHOO.COM" -> "nette jovinal";
            case "NETTEJOVINAL@GMAIL.COM" -> "nette jovinal";
            case "NHAT27FLORES@GMAIL.COM" -> "Nhat Flores";
            case "NHORMHIN0808@GMAIL.COM" -> "Normin Lacio";
            case "NICANICOLEDR00@GMAIL.COM" -> "danica nicole del rosario";
            case "NICONISRAEL29@GMAIL.COM" -> "Nicon Israel Jr";
            case "NIDOY.JHONA@YAHOO.COM" -> "Jonalyn Cuyong";
            case "NIWREERILLEDAE@GMAIL.COM" -> "Danica Camille Carillo";
            case "NMENDOZA@ASI-EES.COM.PH" -> "Nervin Mendoza";
            case "NOEMIBUENCONSEJO1234@GMAIL.COM" -> "noemi buenconsejo";
            case "NOREENAGUDA@GMAIL.COM" -> "noreen aguda";
            case "NOSAJ341@YAHOO.COM" -> "Jason Aquino";
            case "NPDAL16@YAHOO.COM" -> "Nina Dalusung";
            case "N_ISRAEL@AEONFANTASY.COM.PH" -> "Nicon Israel Jr";
            case "OBLIANDALYNNJUN@GMAIL.COM" -> "lynn jun oblianda";
            case "ODIAMAR.PAULO@GMAIL.COM" -> "Paulo Odiamar";
            case "OLARVE.MARIEKRIS@GMAIL.COM" -> "Marie Kris Olarve";
            case "ORIAEDNA06@GMAIL.COM" -> "edna oria";
            case "PABELLO@ASI-DEV6.COM" -> "Pete Vincent Abello";
            case "PADILLODIONE4@GMAIL.COM" -> "Dione Padillo";
            case "PALARAN22@GMAIL.COM" -> "WENEFREDA PALARAN";
            case "PATERO@143.COM" -> "stephin Patero";
            case "PAUL19ALMOQUIRA@GMAIL.COM" -> "Paulino Jr. Almoquira";
            case "PAULINE_GRANADA2002@YAHOO.COM" -> "Pauline Gayle Viray";
            case "PAWDMR@GMAIL.COM" -> "Paulo Odiamar";
            case "PBABYLYN1983@GMAIL.COM" -> "babylyn perez";
            case "PIANARACHEL@GMAIL.COM" -> "Rachel Piana";
            case "PIANARACHEL@YAHOO.COM" -> "rachel piana";
            case "PMISHELANNE@YAHOO.COM" -> "mishel anne pulanco";
            case "POLENG.FLORENCE26@GMAIL.COM" -> "Florence Acosta";
            case "POLILA@ASI-DEV5.COM" -> "bien bien";
            case "PRECIOUS1809@GMAIL.COM" -> "preciosa esquilona";
            case "PRECIOUSESQUILONA7@GMAIL.COM" -> "preciosa esquilona";
            case "PRECIOUSNICOLEDIMACALE@GMAIL.COM" -> "Mary Grace Dimacali";
            case "PRETTY_KATE80@YAHOO.COM" -> "Cathelyn Rose Rico";
            case "PROYALES@ASI-EES.COM.PH" -> "Paul Royales";
            case "PSYCH.BRUISERS@GMAIL.COM" -> "Noemi Sagrado";
            case "P_ODIAMAR@AEONFANTASY.COM.PH" -> "Paulo Odiamar";
            case "QTMARIANE@GMAIL.COM" -> "Ariane Capacio";
            case "QUIBOLOY2@GMAIL.COM" -> "Aimee Quiboloy";
            case "QUIBOLOYAIMEE1@GMAIL.COM" -> "Aimee Quiboloy";
            case "RACHEL.CATIMBANG@YAHOO.COM" -> "Rachel Catimbang";
            case "RACHELBALUYUT12@GMAIL.COM" -> "Rachel Baluyut";
            case "RACHELLAJO@GMAIL.COM" -> "Rachel Lajo";
            case "RARECA3154@TRACKDEN.COM" -> "Aj";
            case "RAYMOND081074@GMAIL.COM" -> "Raymond Besa";
            case "RBALIONG@ASI-EES.COM.PG" -> "Ryan Baliong";
            case "RBALIONG@ASI-EES.COM.PH" -> "Ryan Baliong";
            case "RBERCASIO@DELOITTE.COM" -> "Rein Ronald Bercasio";
            case "RCAMPOSANO@ASI-DEV3.COM" -> "Ruzel Camposano";
            case "RCHING006@GMAIL.COM" -> "ROCHE BETINOL";
            case "RCRUZ@ASI-DEV3.COM" -> "Robert Cruz";
            case "REALYNSARRONDO@GMAIL.COM" -> "Realyn Sarrondo";
            case "REAMARIESAZON143@GMAIL.COM" -> "REA MARIE SAZON";
            case "RECHELLEDIANA15@GMAIL.COM" -> "rechelle diana";
            case "RECONDOAIZA@GMAIL.COM" -> "Alejandrina Isabel Recondo";
            case "REGLOSROY@GMAIL.COM" -> "roy reglos";
            case "REJANOHERSHEY2005@GMAIL.COM" -> "hershey rejano";
            case "REMPACAUL0407@GMAIL.COM" -> "remelyn pacaul";
            case "RESHEILAMAGLAYA0631@GMAIL.COM" -> "Resheila Maglaya";
            case "REVIEOTOOLE@YAHOO.COM" -> "Revie O'Toole";
            case "RGACAD@ASI-EES.COM" -> "Ruben Gacad";
            case "RHEANMELENDEZ@GMAIL.COM" -> "Rhean Charbel Melendez";
            case "RHEYSANPEDRO@YAHOO.COM" -> "Reylina San Pedro";
            case "RHIE.KIDZOONA155@GMAIL.COM" -> "Merie Ann Reyes";
            case "RHIE.KIDZOOONA155@GMAIL.COM" -> "Merie Ann Reyes";
            case "RIABALANA0921@GMAIL.COM" -> "Maria Corazon Balana";
            case "RICHARDMARQUEZ1223@GMAIL.COM" -> "richard marquez";
            case "RICHELLELIM18@GMAIL.COM" -> "Richelle Lim";
            case "RIENAVILLAFUERTE@GMAIL.COM" -> "RIENA JILLE VILLAFUERTE";
            case "RIGELCHAVEZ029@GMAIL.COM" -> "rigel chavez";
            case "RIZZAOBLIGADO9@GMAIL.COM" -> "Rizza Obligado";
            case "RJANDRES1116@GMAIL.COM" -> "rose andres";
            case "RLEONORIA@GMAIL.COM" -> "Leonoria Reye";
            case "RLUARDO@ASI-EES.COM.PH" -> "Luardo Ralph";
            case "RLUCERO@ASI-EES.COM" -> "remrem lucero";
            case "RMOLINA@ASI-EES.COM" -> "Renan Molina";
            case "ROBIEALBOR@GMAIL.COM" -> "Robie Ann Albor";
            case "ROBINGONZALES743@GMAIL.COM" -> "Robin Gonzales";
            case "RODZ_SALVE@YAHOO.COM" -> "Rodolfo Salve";
            case "ROLLETTEOLA@GMAIL.COM" -> "rollette ola";
            case "ROMMEL.RACHO@GMAIL.COM" -> "ROMMEL RACHO";
            case "ROMMEL_RACHO18@YAHOO.COM" -> "rommel racho";
            case "ROMNICKRAPZKIDOTS@GMAIL.COM" -> "Romnick Plata";
            case "RONA.SANJUAN@GMAIL.COM" -> "Ronna San Juan";
            case "RONASANJUAN20@GMAIL.COM" -> "ronnalyn sanjuan";
            case "RONASANJUAN20@YAHHO.COM" -> "ronna sanjuan";
            case "RONASANJUAN@GMAIL.COM" -> "RONNALYN SAN JUAN";
            case "RONASSANJUAN@GMAIL.COM" -> "Ronna San Juan";
            case "RONAYENIEN@GMAIL.COM" -> "Ronna San Juan";
            case "RONNASANJUAN@GMAIL.COM" -> "Ronna San Juan";
            case "ROSALENEDIZON@GMAIL.COM" -> "Rosalene Dizon";
            case "ROSARIOROMA435@GMAIL.COM" -> "Roma Rosario";
            case "ROSEACNNCABUNYAG@GMAIL.COM" -> "Rose Ann Cabunyag";
            case "ROSEANNCABUNYAG@GMAIL.COM" -> "Rose Ann Cabunyag";
            case "ROSEANNSARMIENTO1207@GMAIL.COM" -> "rose ann sarmiento";
            case "ROSEMARIEBATTUNG02@YAHOO.COM" -> "Rosemarie Dalluay";
            case "ROSETTE.NAAGAS0314@GMAIL.COM" -> "ROSETTE NAAGAS";
            case "ROWENA.DREO@GMAIL.COM" -> "Rowena Dreo";
            case "ROWENA.EBRIO@YAHOO.COM" -> "rowena ebrio";
            case "ROWENABARROZO@GMAIL.COM" -> "rowena barrozo";
            case "RQUIA-OT@CSYOUTSOURCING.COM" -> "Alliance IT- Ricky Quia-ot";
            case "RREGIDORMARICAR0@GMAIL.COM" -> "MARICAR REGIDOR";
            case "RREGIDORMARICAR2GMAIL.COM" -> "MARICAR REGIDOR";
            case "RREGIDORMARICAR@GMAIL.COM" -> "MARICAR REGIDOR";
            case "RSANCHEZ@ASI-DEV6.COM" -> "Raffy Sanchez";
            case "RSISON@ASI-EES.COM" -> "Reymer Sison";
            case "RSULTONES@ASI-DEV6.COM" -> "Ranielo L Sultones";
            case "RTGOMEZ09@GMAIL.COM" -> "roy gomez";
            case "RUBY09BADILLA@GMAIL.COM" -> "RUBY ANN Badilla";
            case "RUBYLYNTRIAGATDULA@GMAIL.COM" -> "rubylyn gatdula";
            case "RUSSEL.WAJE16@GMAIL.COM" -> "Russel Waje";
            case "RUSSEL.WAJE73@GMAIL.COM" -> "Russel Waje";
            case "RUSSELWAJE16@GMAIL.COM" -> "Russel Waje";
            case "RUSSELWAJE73@GMAIL.COM" -> "Russel Waje";
            case "RUSSEL_WAJE16@GMAIL.COM" -> "Russel Waje";
            case "RUSSWAJE16@GMAIL.COM" -> "Russel Waje";
            case "R_OLA@AEONFANTASY.COM.PH" -> "ROLLETTE OLA";
            case "SABADIANO@ASI-DEV4.COM" -> "Shelly Abadiano";
            case "SACLOTE.JIMMYLYN13@GMAIL.COM" -> "Jimmylyn Saclote";
            case "SAGER.RASHID@YAHOO.COM" -> "Rashid Sager";
            case "SALANAPRONNA@YAHOO.COM" -> "ronna salanap";
            case "SALAZARMAANGELINE@GMAIL.COM" -> "ma.angeline salazar";
            case "SALVADORCORY97@GMAIL.COM" -> "Corazon Salvador";
            case "SANPEDROSALIE03@GMAIL.COM" -> "rosalie san pedro";
            case "SARAH.CAPUNO@YMAIL.COM" -> "sarah capuno";
            case "SARAHBENAVIDES30@GMAIL.COM" -> "Sarah Benavides";
            case "SARDANKHAI@GMAIL.COM" -> "Khai Buaya";
            case "SAVIF92359@WUZAK.COM" -> "Alan";
            case "SBTURAELYKA17@GMAIL.COM" -> "Elyka San Buenaventura";
            case "SEANCRUZ533@GMAIL.COM" -> "Hazel Dangaran";
            case "SEANHAZEL1227@GMAIL.COM" -> "Hazel Dangaran";
            case "SERKHINZEE@GMAIL.COM" -> "shane marie roma";
            case "SHAIGIDII@GMAIL.COM" -> "Sarah Joy Nuyda";
            case "SHAYNEKOOLETZ08@GMAIL.COM" -> "Louie Shayne Papa";
            case "SHEEN_MHAJAL@YAHOO.COM" -> "Ramiera Gatchalian";
            case "SHEERJUNE@YAHOO.COM" -> "junevie sison";
            case "SHENNALUZ.MONTES@GMAIL.COM" -> "Shenna Luz Versoza";
            case "SHERACAMPOS3@GMAIL.COM" -> "SHEIRA ABELLA";
            case "SHIEDSD@GMAIL.COM" -> "Sherylou San Diego";
            case "SHIELAACABO1@GMAIL.COM" -> "SHIELA ACABO";
            case "SHIELAACACBO1@GMAIL.COM" -> "SHIELA ACABO";
            case "SHREKICECHICHA@YAHOO.COM" -> "Donna Loythi Besabella";
            case "SIMON@AEONFANTASY.COM.PH" -> "hazel simon";
            case "SKTIMTIMAN@GMAIL.COM" -> "Samantha Kristine Timtiman";
            case "SKYSARRONDO14@GMAIL.COM" -> "Realyn Supremido";
            case "SMILINGJAY0603@GMAIL.COM" -> "ADMIN ISTRATOR";
            case "SMILINGJYA0603@GMAIL.COM" -> "ADMIN ISTRATOR";
            case "SONNYLABITAN@GMAIL.COM" -> "Sonny Lou Labitan";
            case "SORDILLAJOAN@GMAIL.COM" -> "Joan Sordilla";
            case "STJOHNJOCELYN031327@GMAIL.COM" -> "jocelyn san juan";
            case "STUNGLANCE@GMAIL.COM" -> "Noriel Duzon";
            case "SYSAD" -> "SYSAD TEAM";
            case "TABACRACIELME@YAHOO.COM" -> "Racielme Tabac";
            case "TACBASROWENA@GMAIL.COM" -> "ROWENA TACBAS";
            case "TALACAYLHENEX8@GMAIL.COM" -> "aileen talacay";
            case "TASICPEARLJOY@GMAIL.COM" -> "Pearl Joy Tasic";
            case "TAYAMMARIACECILIA@GMAIL.COM" -> "Maria Cecilia Tayam";
            case "TEAROSEBARGUES@GMAIL.COM" -> "Tea Rose Junio";
            case "TEST@TEST.COM" -> "test test";
            case "TILO_SANDY@YAHOO.COM" -> "SANDY TILO";
            case "TINYMIAGAO@ROCKETMAIL.COM" -> "Ma. Marsha Miagao";
            case "TMASARU1209@GMAIL.COM" -> "Tetsuhiro Masaru";
            case "TOLENTINOELTI@GMAIL.COM" -> "lorna educado";
            case "TRAROSEBARGUES@GMAIL.COM" -> "Tea Rose Bargues";
            case "TRIXI.ALEXA@GMAIL.COM" -> "Trixia Alexa Mojica";
            case "TRIXIA.ALEXA@GMAIL.COM" -> "Trixia Alexa Mojica";
            case "TROQUERO0512@GMAIL.COM" -> "Teresita Roquero";
            case "TSAMBALANG00@GMAIL.COM" -> "Beverly De La Cruz";
            case "TUDLASMARIVIC4@GMAIL.COM" -> "Marivic Tudlas";
            case "TUDLASMARIVIC684@GMAIL.COM" -> "marivic tudlas";
            case "TWOMARIA02@GMAIL.COM" -> "JERLYN BERNARDINO";
            case "T_KYOGOKU@AEONFANTASY.JP" -> "Takeshi Kyogoku";
            case "T_MASARU@AEONFANTASY.JP" -> "Tetsuhiro Masaru";
            case "T_MENDOZA@AEONFANTASY.COM.PH" -> "Trishia Mendoza";
            case "UNOY941@GMAIL.COM" -> "UNO YUKI";
            case "V-BRYAN.CABANEROS@NEC.COM.PH" -> "James Cabaneros";
            case "V-RJ.ESCANO@NEC.COM.PH" -> "ralph joseph escano";
            case "VALDEZMARANATHA@GMAIL.COM" -> "Maranatha Sharmaine Valdez";
            case "VALDEZ_OLIVIA@YAHOO.COM" -> "Olivia Valdez";
            case "VALENTINEBIENES@GMAIL.COM" -> "VALINTINE DE CLARO";
            case "VALERIE.OLAN04@GMAIL.COM" -> "VALERIE ERIKA OLAN";
            case "VALERIEOLAN30@GMAIL.COM" -> "valerie erika olan";
            case "VALINTINEDECLARO@GMAIL.COM" -> "VALINTINE DE CLARO";
            case "VANSQUIROGA@YAHOO.COM" -> "VANESSA BESANA";
            case "VARDEKARSHEY@GMAIL.COM" -> "Karen Marie Quila";
            case "VAREMARDALOG99@GMAIL.COM" -> "Varemar Dalog";
            case "VASQUEZ89PASTOR@GMAIL.COM" -> "Jessica Vasquez";
            case "VENUSPAGADUAN.15@GMAIL.COM" -> "Venus Pagaduan";
            case "VHANJGALANO7@GMAIL.COM" -> "vangeline galano";
            case "VHERL_22@YAHOO.COM" -> "Berlie Nikko MaraÃ±on";
            case "VICTORIAABEGAIL07@GMAIL.COM" -> "abegail De La Cuesta";
            case "VILLANUEVAEDELENE@GMAIL.COM" -> "EDELENE VILLANUEVA";
            case "VINCEJOAQUINPINEDA@GMAIL.COM" -> "Patrick Ryan Pineda";
            case "VINCEOPADA@YAHOO.COM" -> "MARY ROSE JOSE";
            case "VONNETAN777@GMAIL.COM" -> "YVONNE ARNADO";
            case "WCABALLES@ASI-EES.COM.PH" -> "Alliance IT- Win Mark";
            case "WENEFREDA1981@GMAIL.COM" -> "wenefreda palaran";
            case "WENEFREDAPALARAN1981@GMAIL.COM" -> "wenefreda palaran";
            case "WHEINKNOWLES@GMAIL.COM" -> "WENEFREDA NOCILO";
            case "WILDACORRINE.MORENO@GMAIL.COM" -> "Wilda Corrine Moreno";
            case "XHEYMZLIN.SHAMEZLY@GMAIL.COM" -> "Shamez Lyn Reyes";
            case "XHEYMZLIN.SHAMEZLYN@GMAIL.COM" -> "Shamez Lyn Reyes";
            case "XLIPDIANA22@GMAIL.COM" -> "philip joshua diana";
            case "YAMLACBAY06@GMAIL.COM" -> "Miriam Lacbay";
            case "YAMLACBAY1@GMAIL.COM" -> "Miriam Lacbay";
            case "YOJ15ASSIRAC@GMAIL.COM" -> "Carissa Joy Tumaliuan";
            case "YRICH_ANNE08@YAHOO.COM" -> "CHERRIE ANNE RAMOS";
            case "Y_HISANO@AEONFANTASY.JP" -> "Yusuke Hisano";
            case "ZBUGAYONG@ASI-DEV4.COM" -> "Zariele Bugayong";
            case "ZERAVLA08@GMAIL.COM" -> "Jonathan Alvarez";
            case "ZEV.02.HON@GMAIL.COM" -> "zoe valero";
            case "ZHANDRATHERESA.AMOR@GMAIL.COM" -> "Zhandra Theresa Barce";
            case "ZJCJCASCO@GMAIL.COM" -> "jackie casco";
            case "ZLASTIMOSA@ASI-EES.COM.PH" -> "zorosteve lastimosa";
            case "ZULUETAESTELLA11@GMAIL.COM" -> "ESTELLA ZULUETA";
            case "ZYRAGAMBOA@GMAIL.COM" -> "zyralyn gamboa";
            default -> null;
        };
    }

    private static Integer chooseCompanyLoginIdFrom(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "ABARAQUIO28@GMAIL.COM" -> 1;
            case "ABAYA@AEONFANTASY.COM.PH" -> 2;
            case "ABECURRAY@GMAIL.COM" -> 3;
            case "ABUANANALIZA11@GMAIL.COM" -> 4;
            case "ACEDERARANNY21@GMAIL.COM" -> 5;
            case "ACOSTAFLORENCE302720@GMAIL.COM" -> 6;
            case "ADAMAOLAO@CSYOUTSOURCING.COM" -> 7;
            case "ADAMCHRISMARCERA@GMAIL.COM" -> 8;
            case "ADJHETEARLTHERESE@GMAIL.COM" -> 9;
            case "ADMIN" -> 10;
            case "AFP.KODI@GMAIL.COM" -> 11;
            case "AFP112KIDZOOONA@GMAIL.COM" -> 12;
            case "AFP118TAGUM@GMAIL.COM" -> 13;
            case "AFP139JEAN@GMAIL.COM" -> 14;
            case "AIRKNEE_DS14@YAHOO.COM" -> 15;
            case "AIVEEYHOJ@GMAIL.COM" -> 16;
            case "AKOCLYN24@GMAIL.COM" -> 17;
            case "ALA.JENNIFER88@GMAIL.COM" -> 18;
            case "ALDRINESIASAT2@GMAIL.COM" -> 19;
            case "ALDRINLUANG@GMAIL.COM" -> 20;
            case "ALFAROJHAJHA@GMAIL.COM" -> 21;
            case "ALLAINEB03@GMAIL.COM" -> 22;
            case "ALLENDINGLE0006@GMAIL.COM" -> 23;
            case "ALLIANCEHELPDESK@ASI-EES.COM" -> 24;
            case "ALLIYAHCAMBAL595@GMAIL.COM" -> 25;
            case "ALTANIBARICHARD@GMAIL.COM" -> 26;
            case "AMADORJHEN0114@GMAIL.COM" -> 27;
            case "AMMARIELAQUIAN.KIDZOOONA@GMAIL.COM" -> 28;
            case "AMURAODOLFJR@GMAIL.COM" -> 29;
            case "ANAMAEZING1029@GMAIL.COM" -> 30;
            case "ANDALLO.AEON@GMAIL.COM" -> 31;
            case "ANGELESANALYN990@GMAIL.COM" -> 32;
            case "ANGELICAPLAZOS243@GMAIL.COM" -> 33;
            case "ANGELIEDELACRUZ925@GMAIL.COM" -> 34;
            case "ANNABRUGADA1007@GMAIL.COM" -> 35;
            case "ANNALYN2022@GMAIL.COM" -> 36;
            case "ANNAMARIEVALENTIN19@GMAIL.COM" -> 37;
            case "ANNBADILLA012@GMAIL.COM" -> 38;
            case "ANNEESPINAASAY@GMAIL.COM" -> 39;
            case "ANNEGZG23@GMAIL.COM" -> 40;
            case "ANNEKIMBERLY22@GMAIL.COM" -> 41;
            case "ANNERELLONS@GMAIL.COM" -> 42;
            case "ANNERESENTES08@GMAIL.COM" -> 43;
            case "ANTHEAADVINCULAMUGOT@GMAIL.COM" -> 44;
            case "ANTHEANOBLEZA03@GMAIL.COM" -> 45;
            case "ANTHEATAM02@GMAIL.COM" -> 46;
            case "APATSTEPHANIE@GMAIL.COM" -> 47;
            case "APREALMALDITA@GMAIL.COM" -> 48;
            case "APRILGCABRERA01@GMAIL.COM" -> 49;
            case "APRILPLATON01@GMAIL.COM" -> 50;
            case "ARANASDANALEE@GMAIL.COM" -> 51;
            case "ARCELYNJARALBE21@GMAIL.COM" -> 52;
            case "ARGIEMEN@YAHOO.COM" -> 53;
            case "ARIAJAMILLE05@GMAIL.COM" -> 54;
            case "ARIANNBALUYOT55@GMAIL.COM" -> 55;
            case "ARJANE_ATANACIO@ICLOUD.COM" -> 56;
            case "ARLENELAYSON@GMAIL.COM" -> 57;
            case "ARNALDOAILYN9@GMAIL.COM" -> 58;
            case "ARVHIN04@GMAIL.COM" -> 59;
            case "ASHARKY15@GMAIL.COM" -> 60;
            case "ASHLEY0819SAMSON@GMAIL.COM" -> 61;
            case "ASHLEYANDAJ96@GMAIL.COM" -> 62;
            case "AVMARKVILLES@YAHOO.COM" -> 63;
            case "BACUSMOEDMELYN@GMAIL.COM" -> 64;
            case "BAJITJEANETTE8@GMAIL.COM" -> 65;
            case "BALMES.MARJA@GMAIL.COM" -> 66;
            case "BALOLOR" -> 67;
            case "BARAQUIEL.YOJYRAM@GMAIL.COM" -> 68;
            case "BARRIENTOSKRISANGELI23@GMAIL.COM" -> 69;
            case "BAUTISTA@AEONFANTASY.COM.PH" -> 70;
            case "BAYLONMARIALOLITA@GMAIL.COM" -> 71;
            case "BERMUDOMELVIN7@GMAIL.COM" -> 72;
            case "BHEBIEJEANP@GMAIL.COM" -> 73;
            case "BHERNA_0506@YAHOO.COM" -> 74;
            case "BMANGONDATO@GMAIL.COM" -> 75;
            case "BRANDODEGUZMAN13@GMAIL.COM" -> 76;
            case "BRIONESRUFA2020@GMAIL.COM" -> 77;
            case "BRYANJAYENRIQUEZ03@GMAIL.COM" -> 78;
            case "C_CONTRIDAS@AEONFANTASY.COM.PH" -> 116;
            case "CABIGAO430@GMAIL.COM" -> 79;
            case "CALDWELLBAYLON@GMAIL.COM" -> 80;
            case "CAMILLEBUERANO02@GMAIL.COM" -> 81;
            case "CAMPOSRUDYLYN@GMAIL.COM" -> 82;
            case "CAPACITE33@GMAIL.COM" -> 83;
            case "CAPACITEFRECILDA@GMAIL.COM" -> 84;
            case "CAROL102517@GMAIL.COM" -> 85;
            case "CASELYNTABASAN@GMAIL.COM" -> 86;
            case "CATHCATH.VASQUEZ@YAHOO.COM" -> 87;
            case "CATHERENENAVAREZ@YAHOO.COM" -> 88;
            case "CATHERINECRUZ014@GMAIL.COM" -> 89;
            case "CBAJADO@ASI-EES.COM" -> 90;
            case "CEBRERO@AEONFANTASY.COM.PH" -> 91;
            case "CESZ26@YMAIL.COM" -> 92;
            case "CGAYOTIN@ASI-EES.COM" -> 93;
            case "CHEBROSAS15@GMAIL.COM" -> 94;
            case "CHENIZ_29@YAHOO.COM" -> 95;
            case "CHUA@AEONFANTASY.COM.PH" -> 96;
            case "CINMENZO@ASI-DEV1.COM" -> 97;
            case "CJENYVIEVE@GMAIL.COM" -> 98;
            case "CLAIREANNGAVINO9@GMAIL.COM" -> 99;
            case "CLARISARAMOS24@GMAIL.COM" -> 100;
            case "CLAROSALYN2018@GMAIL.COM" -> 101;
            case "CLARSMOD102018@GMAIL.COM" -> 102;
            case "CMALINAO829@GMAIL.COM" -> 103;
            case "COLLADOERICKAROSE@GMAIL.COM" -> 104;
            case "COLYNGAMBOA8@GMAIL.COM" -> 105;
            case "CONSUELOBIASON45@GMAIL.COM" -> 106;
            case "CORPUZEMIREEN@GMAIL.COM" -> 107;
            case "CQUIMBO@ASI-EES.COM.PH" -> 108;
            case "CRIMENROSEANNE@YAHOO.COM" -> 109;
            case "CRISANTAMARTIN.ZHYRAH@GMAIL.COM" -> 110;
            case "CRISTINA.MATURAN28@GMAIL.COM" -> 111;
            case "CRISTYLUCEROANGELKITA@GMAIL.COM" -> 112;
            case "CSOMBILLO@ASI-EES.COM" -> 113;
            case "CUTIEFIGHTER16@GMAIL.COM" -> 114;
            case "CZARLANDICHO14@GMAIL.COM" -> 115;
            case "DALRYMPLEJANE.LUMEN.BSES@GMAIL.COM" -> 117;
            case "DANGBARDOS.22@GMAIL.COM" -> 118;
            case "DANICASENIN.18@GMAIL.COM" -> 119;
            case "DANIELLUMONGSOD84@GMAIL.COM" -> 120;
            case "DANNAKAYESD@GMAIL.COM" -> 121;
            case "DAPHNEANNEDB.AEON@GMAIL.COM" -> 122;
            case "DARCHIEVERGARA@GMAIL.COM" -> 123;
            case "DARIARUSSELANNP@GMAIL.COM" -> 124;
            case "DARWINPALMESCHAVEZ@GMAIL.COM" -> 125;
            case "DAYEN.IGDALINO@GMAIL.COM" -> 126;
            case "DCMORA75@GMAIL.COM" -> 127;
            case "DDEEBONA@GMAIL.COM" -> 128;
            case "DELCASTILLO_KATRINA@YAHOO.COM" -> 129;
            case "DELING82@YAHOO.COM" -> 130;
            case "DELLOMASMICHELLE04@GMAIL.COM" -> 131;
            case "DENNIS.RAPANOT@NEC.COM.PH" -> 132;
            case "DGFETILUNA@ASI-DEV6.COM" -> 133;
            case "DHALLYALIGADO@GMAIL.COM" -> 134;
            case "DIANAADRALES44@GMAIL.COM" -> 135;
            case "DIONEPADILLO3@GMAIL.COM" -> 136;
            case "DIOSAPRINCESSTOLENTINO@GMAIL.COM" -> 137;
            case "DIVINEGADDI26@GMAIL.COM" -> 138;
            case "DIZON_REIAN@YAHOO.COM" -> 141;
            case "DIZONLYZA35@GMAIL.COM" -> 139;
            case "DIZONROSALENE@GMAIL.COM" -> 140;
            case "DJPITCHSIDON@GMAIL.COM" -> 142;
            case "DLOUPERT@GMAIL.COM" -> 143;
            case "DMACARILAY@CSYOUTSOURCING.COM" -> 144;
            case "DMANAOG@ASI-DEV6.COM" -> 145;
            case "DONNJHAYR@GMAIL.COM" -> 146;
            case "E_ANCHETA@AEONFANTASY.COM.PH" -> 177;
            case "ECORAMOSJAVIER@GMAIL.COM" -> 147;
            case "EDELFA.GARCIA13@GMAIL.COM" -> 148;
            case "EDELYNCURRIE@GMAIL.COM" -> 149;
            case "EDERLITARESURRECCION@GMAIL.COM" -> 150;
            case "EDNAPEDERNAL01@GMAIL.COM" -> 151;
            case "EDU.CAPAROSO@GMAIL.COM" -> 152;
            case "EFIBELQUEVADA@GMAIL.COM" -> 153;
            case "EFRELYN772@GMAIL.COM" -> 154;
            case "ELISEDETORRES06@YAHOO.COM" -> 155;
            case "ELJHAEY0529@GMAIL.COM" -> 156;
            case "ELMO_TWISTER22@YAHOO.COM" -> 157;
            case "EM08IAN@YAHOO.COM" -> 158;
            case "EMILYFLORES022487@GMAIL.COM" -> 159;
            case "EMMEVELLTANO@GMAIL.COM" -> 160;
            case "EMTAGUDANDO@GMAIL.COM" -> 161;
            case "EMZGULPERE@GMAIL.COM" -> 162;
            case "ENCISAEMMANUEL@GMAIL.COM" -> 163;
            case "ERGACITA16@GMAIL.COM" -> 164;
            case "ERICAABANA2000@GMAIL.COM" -> 165;
            case "ERICAQGUZMAN@GMAIL.COM" -> 166;
            case "ERICAVENTURA0000@GMAIL.COM" -> 167;
            case "ERIKAMORENOBUSTILLOS@GMAIL.COM" -> 168;
            case "ERMAJOY84@GMAIL.COM" -> 169;
            case "ERNALEZAKARINAOREO10@GMAIL.COM" -> 170;
            case "ESMABEIVYROSE26@GMAIL.COM" -> 171;
            case "ESPADERO.REYNAN@GMAIL.COM" -> 172;
            case "ESPINOJJ0109@GMAIL.COM" -> 173;
            case "ESTERALJULIENNE@GMAIL.COM" -> 174;
            case "EUNICEAGUSTIN2613@GMAIL.COM" -> 175;
            case "EYAH_AEHR@YAHOO.COM" -> 176;
            case "FEDERISJANINE@GMAIL.COM" -> 178;
            case "FEMME.SPILL@GMAIL.COM" -> 179;
            case "FLORESMHEAN@GMAIL.COM" -> 180;
            case "FPENNYLLANE@YAHOO.COM" -> 181;
            case "FRANCIALIEZL8@GMAIL.COM" -> 182;
            case "FRHEA987@GMAIL.COM" -> 183;
            case "G_AFANTE@AEONFANTASY.COM.PH" -> 199;
            case "GALAROZAARMIE08@GMAIL.COM" -> 184;
            case "GALIGAOABIGAIL@GMAIL.COM" -> 185;
            case "GAYO.CRISANTA@GMAIL.COM" -> 186;
            case "GAYOPOLENTISIMA@GMAIL.COM" -> 187;
            case "GEMALYN_V@YAHOO.COM" -> 188;
            case "GERONAIBYANG@GMAIL.COM" -> 189;
            case "GHEAHERMOSA03@GMAIL.COM" -> 190;
            case "GILBERTTEJADA31@YAHOO.COM" -> 191;
            case "GLENDAABAYA0629@GMAIL.COM" -> 192;
            case "GLENNEVARDONE33@GMAIL.COM" -> 193;
            case "GREGORYBEN29@GMAIL.COM" -> 194;
            case "GRENDYJOY@GMAIL.COM" -> 195;
            case "GRISK_SHAYNE_21@YAHOO.COM" -> 196;
            case "GTANCIOCO@ASI-EES.COM.PH" -> 197;
            case "GUERREROROGER92@GMAIL.COM" -> 198;
            case "HACIR_06@YAHOO.COM" -> 200;
            case "HAGUIREKHANAME@GMAIL.COM" -> 201;
            case "HAIDEE.JUBAN@GMAIL.COM" -> 202;
            case "HANAH.CABALES10@GMAIL.COM" -> 203;
            case "HANNAHLEEBAQUIL@YAHOO.COM" -> 204;
            case "HAPPYAUBREY@GMAIL.COM" -> 205;
            case "HAVEGAEL@GMAIL.COM" -> 206;
            case "HAYDEEYUMO19@GMAIL.COM" -> 207;
            case "HAZEL_ARQUIZA@YAHOO.COM.PH" -> 209;
            case "HAZELESPELITA030318@GMAIL.COM" -> 208;
            case "HMPUNZALAN.ZEPER@GMAIL.COM" -> 210;
            case "I.REMOROZO@YAHOO.COM" -> 211;
            case "ILYNTESORA14@GMAIL.COM" -> 212;
            case "IMELENDRES@ASI-EES.COM" -> 213;
            case "IMPERIALMAY.ACC@GMAIL.COM" -> 214;
            case "INAMIAH79@GMAIL.COM" -> 215;
            case "INOCENCIOMICHELLEGRACE8@GMAIL.COM" -> 216;
            case "INTERINONELVIEGRACE@GMAIL.COM" -> 217;
            case "J_AMARILLE@AEONFANTASY.COM.PH" -> 303;
            case "J_BAJIT@AEONFANTASY.COM.PH" -> 304;
            case "J_CRUZ@AEONFANTASY.COM.PH" -> 305;
            case "J_DACLES@AEONFANTASY.COM.PH" -> 306;
            case "JACKSON.ANG23@YAHOO.COM" -> 218;
            case "JAIEVANGELISTA07@GMAIL.COM" -> 219;
            case "JAIMEJEPSANI@GMAIL.COM" -> 220;
            case "JALLER@CSYOUTSOURCING.COM" -> 221;
            case "JAMAICSAJELA@GMAIL.COM" -> 222;
            case "JANECANO0509@GMAIL.COM" -> 223;
            case "JANESAGARIO1020@GMAIL.COM" -> 224;
            case "JANNSENDIONISIO04@GMAIL.COM" -> 225;
            case "JARABELOJEN@GMAIL.COM" -> 226;
            case "JASMINEANDAYA78@GMAIL.COM" -> 227;
            case "JASMINVELASCO1988@GMAIL.COM" -> 228;
            case "JAYAEVELUZ@GMAIL.COM" -> 229;
            case "JBOLOTAOLO@CSYOUTSOURCING.COM" -> 230;
            case "JCABALE@ASI-EES.COM" -> 231;
            case "JCABALE@ASI-EES.COM.PH" -> 232;
            case "JCANILLO@ASI-EES.COM.PH" -> 233;
            case "JDESCARTIN@CSYOUTSOURCING.COM" -> 234;
            case "JELICAKUDEMUS0806@GMAIL.COM" -> 235;
            case "JEMMARBAGAMAN@GMAIL.COM" -> 236;
            case "JENEYURI29@GMAIL.COM" -> 237;
            case "JENNIFERURSUA93@GMAIL.COM" -> 238;
            case "JENNLASCUNA28@GMAIL.COM" -> 239;
            case "JENNYJASMIN1317@GMAIL.COM" -> 240;
            case "JENNYRUIZ060586@GMAIL.COM" -> 241;
            case "JEPZHYKIE0811@GMAIL.COM" -> 242;
            case "JEREZANNE02@GMAIL.COM" -> 243;
            case "JERICOBAGTASOS11@GMAIL.COM" -> 244;
            case "JERRYLYNALAURIN@GMAIL.COM" -> 245;
            case "JESSAMAYPAULME10@GMAIL.COM" -> 246;
            case "JESSAMIELAMARCA@GMAIL.COM" -> 247;
            case "JESSAVARGAS1997@GMAIL.COM" -> 248;
            case "JESSICABASQUINAS7@GMAIL.COM" -> 249;
            case "JESSICALOZANO00018@GMAIL.COM" -> 250;
            case "JESSICASHINESANTOS2590@GMAIL.COM" -> 251;
            case "JESZAPARAN1204@GMAIL.COM" -> 252;
            case "JEWELANNFABRICANTE@GMAIL.COM" -> 253;
            case "JEZZDARVIN@GMAIL.COM" -> 254;
            case "JHAGONDRANEOS@GMAIL.COM" -> 255;
            case "JHAMDUMALIG977@GMAIL.COM" -> 256;
            case "JHAZANDAYA@GMAIL.COM" -> 257;
            case "JHENAMADOR2@GMAIL.COM" -> 258;
            case "JHENCASERES@YAHOO.COM" -> 259;
            case "JHENCORTEZ120@GMAIL.COM" -> 260;
            case "JHONGAYLORDCALANOGA21@GMAIL.COM" -> 261;
            case "JIMMYLYNSACLOTE013@GMAIL.COM" -> 262;
            case "JINJIM1992@GMAIL.COM" -> 263;
            case "JINKZLAZARTE@GMAIL.COM" -> 264;
            case "JMONSANTO@CSYOUTSOURCING.COM" -> 265;
            case "JNARCA@ASI-DEV3.COM" -> 266;
            case "JO.CALAMAYA24@GMAIL.COM" -> 267;
            case "JOANAMARIEDUMASIG2019@GMAIL.COM" -> 268;
            case "JOANNENARAG33@GMAIL.COM" -> 269;
            case "JOANTAMAYO99@GMAIL.COM" -> 270;
            case "JOCELAZADON19@GMAIL.COM" -> 271;
            case "JOHNMURPHYMIRANDE@GMAIL.COM" -> 272;
            case "JOLS_1978@YAHOO.COM.PH" -> 273;
            case "JONALYNCUYONG@YAHOO.COM" -> 275;
            case "JONALYNCUYONG28@GMAIL.COM" -> 274;
            case "JORENZO060515@GMAIL.COM" -> 276;
            case "JORLANDA@CSYOUTSOURCING.COM" -> 277;
            case "JOSEMARYROSE28@GMAIL.COM" -> 278;
            case "JOSHCAYABYAB07@GMAIL.COM" -> 279;
            case "JOSHUAPHILIPMONISIT@ROCKETMAIL.COM" -> 280;
            case "JOVINALNETTE@GMAIL.COM" -> 281;
            case "JOYCEANNEPENTECOSTES@YAHOO.COM" -> 282;
            case "JOYCEDP.358@GMAIL.COM" -> 283;
            case "JOYESMABE3@GMAIL.COM" -> 284;
            case "JOYFEVILLANUEVA20@GMAIL.COM" -> 285;
            case "JOZELLE_JOY17@YAHOO.COM" -> 286;
            case "JPAPASIN@CSYOUTSOURCIN.COM" -> 287;
            case "JPONCE150@YAHOO.COM" -> 288;
            case "JRDESOYO@CSYOUTSOURCING.COM" -> 289;
            case "JRMOISES2324@GMAIL.COM" -> 290;
            case "JRONATO@ASI-EES.COM" -> 291;
            case "JRUIZ@CSYOUTSOURCING.COM" -> 292;
            case "JSARDA@ASI-EES.COM.PH" -> 293;
            case "JSMNESNCHO@GMAIL.COM" -> 294;
            case "JSOLANO@ASI-EES.COM.PH" -> 295;
            case "JSUMAYAO@ASI-EES.COM.PH" -> 296;
            case "JUDYANNDIRIJE1@GMAIL.COM" -> 297;
            case "JULIE_GERALDINO25@YAHOO.COM" -> 298;
            case "JUNELOU23@GMAIL.COM" -> 299;
            case "JUNIOTEAROSE@GMAIL.COM" -> 300;
            case "JUVIEALAS@GMAIL.COM" -> 301;
            case "JUVY.ALIANZA@GMAIL.COM" -> 302;
            case "K_NOHAY@AEONFANTASY.COM.PH" -> 330;
            case "KABELA@CSYOUTSOURCING.COM" -> 307;
            case "KATHLENEJOYCEMARGELINO@GMAIL.COM" -> 308;
            case "KAZYANNS@GMAIL.COM" -> 309;
            case "KCEEGIEH18@GMAIL.COM" -> 310;
            case "KCIRTAPHUGO09@GMAIL.COM" -> 311;
            case "KDELFINO@CSYOUTSOURCING.COM" -> 312;
            case "KENETHJOY.AEON@GMAIL.COM" -> 313;
            case "KHANJIE0811@GMAIL.COM" -> 314;
            case "KHAYEVILLANUEVA11@GMAIL.COM" -> 315;
            case "KHAYSINDAC@GMAIL.COM" -> 316;
            case "KIDZOOONAPH@GMAIL.COM" -> 317;
            case "KIMBERLY.GALABAN@YAHOO.COM" -> 318;
            case "KIMBERLYBASILIO02@YAHOO.COM" -> 319;
            case "KIMJHADE09@YAHOO.COM" -> 320;
            case "KIMQUINQUINO.LAZADA@GMAIL.COM" -> 321;
            case "KOCARIZA93@GMAIL.COM" -> 322;
            case "KOIZELWIN45@GMAIL.COM" -> 323;
            case "KQUEMUEL@ASI-EES.COM.PH" -> 324;
            case "KRADOVAN022215@GMAIL.COM" -> 325;
            case "KRISTELLECONCEPCION23@GMAIL.COM" -> 326;
            case "KRISTINAGACOSTA27@GMAIL.COM" -> 327;
            case "KRIZALAINE7@GMAIL.COM" -> 328;
            case "KYRA_BREZUELA@YAHOO.COM" -> 329;
            case "LAARNITAROSANAN09@GMAIL.COM" -> 331;
            case "LANYCUTAMORA99@GMAIL.COM" -> 332;
            case "LARRASERAFINO25@GMAIL.COM" -> 333;
            case "LBACULAOLOYO@GMAIL.COM" -> 334;
            case "LEAH.CORITANA@YAHOO.COM" -> 335;
            case "LEALOPEZ888@GMAIL.COM" -> 336;
            case "LEIJUNDOMINGO51586@GMAIL.COM" -> 337;
            case "LHENDELAPAZ7@GMAIL.COM" -> 338;
            case "LHONMANANSALA@GMAIL.COM" -> 339;
            case "LIEZLMIRAFLOR3@GMAIL.COM" -> 340;
            case "LIGLESIA@ASI-EES.COM" -> 341;
            case "LINARIVERA001@GMAIL.COM" -> 342;
            case "LINGADRUSETTE@GMAIL.COM" -> 343;
            case "LIZA.PENPENA@GMAIL.COM" -> 344;
            case "LJSARIEGO65@GMAIL.COM" -> 345;
            case "LLABADAN@CSYOUTSOURCING.COM" -> 346;
            case "LOPEZJONATHAN1805@GMAIL.COM" -> 347;
            case "LORIN032383@GMAIL.COM" -> 348;
            case "LORJAVIER080589@GMAIL.COM" -> 349;
            case "LOUISSESHANE30@GMAIL.COM" -> 350;
            case "LOUREMAESISON@GMAIL.COM" -> 351;
            case "LOVELYNJOYALEJAGA@GMAIL.COM" -> 352;
            case "LOVIECABANGISAN933@GMAIL.COM" -> 353;
            case "LUCYJEAN276@GMAIL.COM" -> 354;
            case "LYN.02DC@GMAIL.COM" -> 355;
            case "LYNCELD@YAHOO.COM" -> 356;
            case "LYNLYN061981@GMAIL.COM" -> 357;
            case "M_CHOSA@AEONFANTASY.JP" -> 428;
            case "MACALALADMELYN1989@GMAIL.COM" -> 358;
            case "MADRIDABEGAIL18@GMAIL.COM" -> 359;
            case "MAEJHEANEVALDERAMA30@GMAIL.COM" -> 360;
            case "MAESTRELLA_CORNELIO@YAHOO.COM.PH" -> 361;
            case "MAFESXON14@GMAIL.COM" -> 362;
            case "MAGTAASJJ@GMAIL.COM" -> 363;
            case "MALTOMARIE@GMAIL.COM" -> 364;
            case "MALZVSIPALAY27@GMAIL.COM" -> 365;
            case "MANNANSHENG@GMAIL.COM" -> 366;
            case "MAPRILCAMAY0326@GMAIL.COM" -> 367;
            case "MARGEDELOSREYES09@GMAIL.COM" -> 368;
            case "MARICARBLANDO1984@GMAIL.COM" -> 369;
            case "MARICARLY2012@GMAIL.COM" -> 370;
            case "MARICARREGIDOR001@GMAIL.COM" -> 371;
            case "MARICELGONZALES@GMAIL.COM" -> 372;
            case "MARICELPACUN62@GMAIL.COM" -> 373;
            case "MARICORNOORA08@GMAIL.COM" -> 374;
            case "MARIELCORTEZ23@GMAIL.COM" -> 375;
            case "MARIVICTUDLAS001@GMAIL.COM" -> 376;
            case "MARIZADIONISIO14@GMAIL.COM" -> 377;
            case "MARJOERIEMORAL@GMAIL.COM" -> 378;
            case "MARKANTHONYLOCSIN20@GMAIL.COM" -> 379;
            case "MARKDEXTERMALAPITAN96@GMAIL.COM" -> 380;
            case "MARKJARAVATA457@GMAIL.COM" -> 381;
            case "MARKLEE.SITCHON@GMAIL.COM" -> 382;
            case "MARQUEZ@AEONFANTASY.COM.PH" -> 383;
            case "MARRIANNE874@GMAIL.COM" -> 384;
            case "MARSHAMIAGAO@GMAIL.COM" -> 385;
            case "MARYANNMARGALLO2121@GMAIL.COM" -> 386;
            case "MARYGRACEDIMACALI11@GMAIL.COM" -> 387;
            case "MARYJOYORIA42@GMAIL.COM" -> 388;
            case "MARYLENEANNDIOLE@GMAIL.COM" -> 389;
            case "MARYNITTD@GMAIL.COM" -> 390;
            case "MARYPREXY_RAMILO@YAHOO.COM" -> 391;
            case "MATEOERICA17@GMAIL.COM" -> 392;
            case "MAVERICK.MEJALA7@GMAIL.COM" -> 393;
            case "MAVERICK0626@YAHOO.COM" -> 394;
            case "MAYANNGEPIT49@GMAIL.COM" -> 395;
            case "MAYAROSE.19.RI@GMAIL.COM" -> 396;
            case "MAYBELBANSAGAN@GMAIL.COM" -> 397;
            case "MAYLENEBERNARDINO27@GMAIL.COM" -> 398;
            case "MCBALANA21@GMAIL.COM" -> 399;
            case "MCHE0930@GMAIL.COM" -> 400;
            case "MEDELROVILYN@YAHOO.COM" -> 401;
            case "MEGMARISCOTES@GMAIL.COM" -> 402;
            case "MENDIOLAMYRNA0424@GMAIL.COM" -> 403;
            case "MERCADO.AEONFANTASY@GMAIL.COM" -> 404;
            case "MGRARROZA80@GMAIL.COM" -> 405;
            case "MHAUEE_CABIGAO014@YAHOO.COM" -> 406;
            case "MHEGZVILLAS@GMAIL.COM" -> 407;
            case "MHENGHO18@GMAIL.COM" -> 408;
            case "MIAGAO@AEONFANTASY.COM.PH" -> 409;
            case "MIAQUERENCIA1@GMAIL.COM" -> 410;
            case "MICAH.RYOAKI@GMAIL.COM" -> 411;
            case "MICHELLEANNBARNUEVO@GMAIL.COM" -> 412;
            case "MICHELLEANNDELORIA@GMAIL.COM" -> 413;
            case "MIKEL26_1984@YAHOO.COM" -> 414;
            case "MILAGROSBARBOSA1985@GMAIL.COM" -> 415;
            case "MINSUMMER082915@GMAIL.COM" -> 416;
            case "MINZLAPUZ@GMAIL.COM" -> 417;
            case "MISHSHA2011@GMAIL.COM" -> 418;
            case "MJANE.CABER@GMAIL.COM" -> 419;
            case "MJCAGALITAN" -> 420;
            case "MONARES.RAIN@YAHOO.COM" -> 421;
            case "MONICAGRACE.MANGUNAY@GMAIL.COM" -> 422;
            case "MRYNAMENDIOLA@GMAIL.COM" -> 423;
            case "MSARCIA010185@GMAIL.COM" -> 424;
            case "MTAGLE137@GMAIL.COM" -> 425;
            case "MYLINE.DEGUZMAN77@YAHOO.COM" -> 426;
            case "MYRNAMENDIOLA0424@GMAIL.COM" -> 427;
            case "N_ISRAEL@AEONFANTASY.COM.PH" -> 441;
            case "NARIKAOREO@GMAIL.COM" -> 429;
            case "NATALIELEANNEMALONZO@GMAIL.COM" -> 430;
            case "NATIVIDADARIEL230@GMAIL.COM" -> 431;
            case "NHORMHIN0808@GMAIL.COM" -> 432;
            case "NICANICOLEDR00@GMAIL.COM" -> 433;
            case "NICONISRAEL29@GMAIL.COM" -> 434;
            case "NIWREERILLEDAE@GMAIL.COM" -> 435;
            case "NMENDOZA@ASI-EES.COM.PH" -> 436;
            case "NOEMIBUENCONSEJO1234@GMAIL.COM" -> 437;
            case "NOREENAGUDA@GMAIL.COM" -> 438;
            case "NOSAJ341@YAHOO.COM" -> 439;
            case "NPDAL16@YAHOO.COM" -> 440;
            case "OBLIANDALYNNJUN@GMAIL.COM" -> 442;
            case "OLARVE.MARIEKRIS@GMAIL.COM" -> 443;
            case "ORIAEDNA06@GMAIL.COM" -> 444;
            case "P_ODIAMAR@AEONFANTASY.COM.PH" -> 453;
            case "PABELLO@ASI-DEV6.COM" -> 445;
            case "PADILLODIONE4@GMAIL.COM" -> 446;
            case "PAULINE_GRANADA2002@YAHOO.COM" -> 447;
            case "PBABYLYN1983@GMAIL.COM" -> 448;
            case "PIANARACHEL@GMAIL.COM" -> 449;
            case "POLILA@ASI-DEV5.COM" -> 450;
            case "PRECIOUS1809@GMAIL.COM" -> 451;
            case "PRETTY_KATE80@YAHOO.COM" -> 452;
            case "QTMARIANE@GMAIL.COM" -> 454;
            case "QUIBOLOY2@GMAIL.COM" -> 455;
            case "QUIBOLOYAIMEE1@GMAIL.COM" -> 456;
            case "RACHELBALUYUT12@GMAIL.COM" -> 457;
            case "RACHELLAJO@GMAIL.COM" -> 458;
            case "RAYMOND081074@GMAIL.COM" -> 459;
            case "RBALIONG@ASI-EES.COM.PH" -> 460;
            case "RCHING006@GMAIL.COM" -> 461;
            case "RCRUZ@ASI-DEV3.COM" -> 462;
            case "REAMARIESAZON143@GMAIL.COM" -> 463;
            case "RECHELLEDIANA15@GMAIL.COM" -> 464;
            case "RECONDOAIZA@GMAIL.COM" -> 465;
            case "REGLOSROY@GMAIL.COM" -> 466;
            case "REJANOHERSHEY2005@GMAIL.COM" -> 467;
            case "REMPACAUL0407@GMAIL.COM" -> 468;
            case "RESHEILAMAGLAYA0631@GMAIL.COM" -> 469;
            case "REVIEOTOOLE@YAHOO.COM" -> 470;
            case "RHEYSANPEDRO@YAHOO.COM" -> 471;
            case "RHIE.KIDZOOONA155@GMAIL.COM" -> 472;
            case "RIABALANA0921@GMAIL.COM" -> 473;
            case "RICHARDMARQUEZ1223@GMAIL.COM" -> 474;
            case "RICHELLELIM18@GMAIL.COM" -> 475;
            case "RIENAVILLAFUERTE@GMAIL.COM" -> 476;
            case "RIGELCHAVEZ029@GMAIL.COM" -> 477;
            case "RIZZAOBLIGADO9@GMAIL.COM" -> 478;
            case "RJANDRES1116@GMAIL.COM" -> 479;
            case "RLEONORIA@GMAIL.COM" -> 480;
            case "RLUARDO@ASI-EES.COM.PH" -> 481;
            case "RLUCERO@ASI-EES.COM" -> 482;
            case "ROBIEALBOR@GMAIL.COM" -> 483;
            case "ROLLETTEOLA@GMAIL.COM" -> 484;
            case "ROMMEL_RACHO18@YAHOO.COM" -> 485;
            case "ROMNICKRAPZKIDOTS@GMAIL.COM" -> 486;
            case "RONASANJUAN20@GMAIL.COM" -> 487;
            case "ROSARIOROMA435@GMAIL.COM" -> 488;
            case "ROSEANNCABUNYAG@GMAIL.COM" -> 489;
            case "ROSEANNSARMIENTO1207@GMAIL.COM" -> 490;
            case "ROSETTE.NAAGAS0314@GMAIL.COM" -> 491;
            case "ROWENA.DREO@GMAIL.COM" -> 492;
            case "ROWENA.EBRIO@YAHOO.COM" -> 493;
            case "ROWENABARROZO@GMAIL.COM" -> 494;
            case "RQUIA-OT@CSYOUTSOURCING.COM" -> 495;
            case "RSANCHEZ@ASI-DEV6.COM" -> 496;
            case "RSISON@ASI-EES.COM" -> 497;
            case "RSULTONES@ASI-DEV6.COM" -> 498;
            case "RTGOMEZ09@GMAIL.COM" -> 499;
            case "RUBYLYNTRIAGATDULA@GMAIL.COM" -> 500;
            case "RUSSEL.WAJE73@GMAIL.COM" -> 501;
            case "RUSSWAJE16@GMAIL.COM" -> 502;
            case "SAGER.RASHID@YAHOO.COM" -> 503;
            case "SALANAPRONNA@YAHOO.COM" -> 504;
            case "SALAZARMAANGELINE@GMAIL.COM" -> 505;
            case "SALVADORCORY97@GMAIL.COM" -> 506;
            case "SARAH.CAPUNO@YMAIL.COM" -> 507;
            case "SARAHBENAVIDES30@GMAIL.COM" -> 508;
            case "SBTURAELYKA17@GMAIL.COM" -> 509;
            case "SEANCRUZ533@GMAIL.COM" -> 510;
            case "SEANHAZEL1227@GMAIL.COM" -> 511;
            case "SERKHINZEE@GMAIL.COM" -> 512;
            case "SHAIGIDII@GMAIL.COM" -> 513;
            case "SHEEN_MHAJAL@YAHOO.COM" -> 514;
            case "SHEERJUNE@YAHOO.COM" -> 515;
            case "SHENNALUZ.MONTES@GMAIL.COM" -> 516;
            case "SHERACAMPOS3@GMAIL.COM" -> 517;
            case "SHIEDSD@GMAIL.COM" -> 518;
            case "SHIELAACABO1@GMAIL.COM" -> 519;
            case "SHREKICECHICHA@YAHOO.COM" -> 520;
            case "SIMON@AEONFANTASY.COM.PH" -> 521;
            case "SKTIMTIMAN@GMAIL.COM" -> 522;
            case "SKYSARRONDO14@GMAIL.COM" -> 523;
            case "SONNYLABITAN@GMAIL.COM" -> 524;
            case "SORDILLAJOAN@GMAIL.COM" -> 525;
            case "STJOHNJOCELYN031327@GMAIL.COM" -> 526;
            case "STUNGLANCE@GMAIL.COM" -> 527;
            case "T_MASARU@AEONFANTASY.JP" -> 542;
            case "T_MENDOZA@AEONFANTASY.COM.PH" -> 543;
            case "TABACRACIELME@YAHOO.COM" -> 528;
            case "TACBASROWENA@GMAIL.COM" -> 529;
            case "TALACAYLHENEX8@GMAIL.COM" -> 530;
            case "TASICPEARLJOY@GMAIL.COM" -> 531;
            case "TAYAMMARIACECILIA@GMAIL.COM" -> 532;
            case "TEAROSEBARGUES@GMAIL.COM" -> 533;
            case "TILO_SANDY@YAHOO.COM" -> 534;
            case "TINYMIAGAO@ROCKETMAIL.COM" -> 535;
            case "TMASARU1209@GMAIL.COM" -> 536;
            case "TOLENTINOELTI@GMAIL.COM" -> 537;
            case "TRIXIA.ALEXA@GMAIL.COM" -> 538;
            case "TROQUERO0512@GMAIL.COM" -> 539;
            case "TSAMBALANG00@GMAIL.COM" -> 540;
            case "TWOMARIA02@GMAIL.COM" -> 541;
            case "UNOY941@GMAIL.COM" -> 544;
            case "V-BRYAN.CABANEROS@NEC.COM.PH" -> 545;
            case "VALDEZMARANATHA@GMAIL.COM" -> 546;
            case "VALENTINEBIENES@GMAIL.COM" -> 547;
            case "VALERIEOLAN30@GMAIL.COM" -> 548;
            case "VALINTINEDECLARO@GMAIL.COM" -> 549;
            case "VANSQUIROGA@YAHOO.COM" -> 550;
            case "VAREMARDALOG99@GMAIL.COM" -> 551;
            case "VASQUEZ89PASTOR@GMAIL.COM" -> 552;
            case "VENUSPAGADUAN.15@GMAIL.COM" -> 553;
            case "VHANJGALANO7@GMAIL.COM" -> 554;
            case "VHERL_22@YAHOO.COM" -> 555;
            case "VICTORIAABEGAIL07@GMAIL.COM" -> 556;
            case "VILLANUEVAEDELENE@GMAIL.COM" -> 557;
            case "VINCEJOAQUINPINEDA@GMAIL.COM" -> 558;
            case "VINCEOPADA@YAHOO.COM" -> 559;
            case "VONNETAN777@GMAIL.COM" -> 560;
            case "WCABALLES@ASI-EES.COM.PH" -> 561;
            case "WENEFREDAPALARAN1981@GMAIL.COM" -> 562;
            case "WILDACORRINE.MORENO@GMAIL.COM" -> 563;
            case "XHEYMZLIN.SHAMEZLYN@GMAIL.COM" -> 564;
            case "XLIPDIANA22@GMAIL.COM" -> 565;
            case "Y_HISANO@AEONFANTASY.JP" -> 568;
            case "YAMLACBAY1@GMAIL.COM" -> 566;
            case "YOJ15ASSIRAC@GMAIL.COM" -> 567;
            case "ZERAVLA08@GMAIL.COM" -> 569;
            case "ZEV.02.HON@GMAIL.COM" -> 570;
            case "ZHANDRATHERESA.AMOR@GMAIL.COM" -> 571;
            case "ZJCJCASCO@GMAIL.COM" -> 572;
            case "ZLASTIMOSA@ASI-EES.COM.PH" -> 573;
            default -> null;
        };
    }

    private static Integer chooseGroupTypeMaster(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "CORPORATE" -> 1;
            case "REG" -> 2;
            case "VIP" -> 3;
            case "WALKIN" -> 4;
            default -> null; // Default case
        };
    }

    private static Integer chooseForTaxType(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "0" -> 1;
            case "1" -> 2;
            case "2" -> 3;
            case "3" -> 4;
            case "4" -> 5;
            default -> null; // Default case
        };
    }

    private static Integer chooseNationalityMaster(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "AD" -> 1;
            case "AE" -> 2;
            case "AF" -> 3;
            case "AI" -> 4;
            case "AM" -> 5;
            case "AO" -> 6;
            case "AQ" -> 7;
            case "AR" -> 8;
            case "AS" -> 9;
            case "AU" -> 10;
            case "AW" -> 11;
            case "BA" -> 12;
            case "BB" -> 13;
            case "BE" -> 14;
            case "BH" -> 15;
            case "BM" -> 16;
            case "BO" -> 17;
            case "BR" -> 18;
            case "BS" -> 19;
            case "BT" -> 20;
            case "BU" -> 21;
            case "BY" -> 22;
            case "BZ" -> 23;
            case "CA" -> 24;
            case "CG" -> 25;
            case "CH" -> 26;
            case "CL" -> 27;
            case "CM" -> 28;
            case "CO" -> 29;
            case "CR" -> 30;
            case "CU" -> 31;
            case "CY" -> 32;
            case "DE" -> 33;
            case "DK" -> 34;
            case "DM" -> 35;
            case "EC" -> 36;
            case "EE" -> 37;
            case "EG" -> 38;
            case "ET" -> 39;
            case "FI" -> 40;
            case "FJ" -> 41;
            case "FR" -> 42;
            case "GB" -> 43;
            case "GE" -> 44;
            case "GH" -> 45;
            case "GN" -> 46;
            case "GR" -> 47;
            case "GY" -> 48;
            case "HK" -> 49;
            case "HR" -> 50;
            case "HU" -> 51;
            case "ID" -> 52;
            case "IE" -> 53;
            case "IN" -> 54;
            case "IQ" -> 55;
            case "IR" -> 56;
            case "IS" -> 57;
            case "IT" -> 58;
            case "JM" -> 59;
            case "JO" -> 60;
            case "JP" -> 61;
            case "KE" -> 62;
            case "KO" -> 63;
            case "KW" -> 64;
            case "KZ" -> 65;
            case "LB" -> 66;
            case "LK" -> 67;
            case "LT" -> 68;
            case "LU" -> 69;
            case "MA" -> 70;
            case "MC" -> 71;
            case "ME" -> 72;
            case "MM" -> 73;
            case "MN" -> 74;
            case "MO" -> 75;
            case "MU" -> 76;
            case "MV" -> 77;
            case "MY" -> 78;
            case "NA" -> 79;
            case "NG" -> 80;
            case "NL" -> 81;
            case "NO" -> 82;
            case "NP" -> 83;
            case "NZ" -> 84;
            case "OM" -> 85;
            case "PA" -> 86;
            case "PE" -> 87;
            case "PH" -> 88;
            case "PK" -> 89;
            case "PO" -> 90;
            case "PT" -> 91;
            case "PY" -> 92;
            case "QA" -> 93;
            case "RO" -> 94;
            case "RU" -> 95;
            case "SA" -> 96;
            case "SC" -> 97;
            case "SE" -> 98;
            case "SG" -> 99;
            case "SK" -> 100;
            case "SN" -> 101;
            case "SO" -> 102;
            case "SP" -> 103;
            case "TH" -> 104;
            case "TN" -> 105;
            case "TR" -> 106;
            case "TW" -> 107;
            case "TZ" -> 108;
            case "UA" -> 109;
            case "UG" -> 110;
            case "US" -> 111;
            case "UY" -> 112;
            case "UZ" -> 113;
            case "VE" -> 114;
            case "VN" -> 115;
            case "YE" -> 116;
            case "ZA" -> 117;
            case "ZM" -> 118;
            case "ZW" -> 119;
            default -> null; // Default case
        };
    }

    private static Integer choosePriceLevelMaster(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "01" -> 1;
            case "02" -> 2;
            case "03" -> 3;
            case "04" -> 4;
            case "05" -> 5;
            case "07" -> 6;
            case "08" -> 7;
            case "50DISC" -> 8;
            case "50EMPDISC" -> 9;
            case "99" -> 10;
            case "AFP147-30" -> 11;
            case "FAMGUARD" -> 12;
            case "FAMPERKS" -> 13;
            case "FAMPERKS1" -> 14;
            case "FAMPERKS2" -> 15;
            case "MKTDSC" -> 16;
            case "STP001" -> 17;
            default -> null; // Default case
        };
    }

    private static Integer chooseProductHierarchy(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "KIDZOOONA" -> 1;
            case "AMUSEMENT" -> 2;
            case "OTHERS" -> 3;
            default -> null; // Default case
        };
    }

    private static Integer chooseUomMaster(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "ADULT" -> 1;
            case "CHILD" -> 2;
            case "INFANT" -> 3;
            case "PACKAGE" -> 4;
            case "PAIRS" -> 5;
            case "PCS" -> 6;
            case "SWIPE" -> 7;
            case "UNIT" -> 8;
            default -> null; // Default case
        };
    }

    private static Integer chooseProductCategory(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "KIDZOOONA" -> 1;
            case "SWIPECARDS" -> 2;
            case "LOYALTY" -> 3;
            case "OTHERMERCHANDISE" -> 4;
            case "REDEMPTION" -> 5;
            case "SOCKS" -> 6;
            default -> null; // Default case
        };
    }

    private static Integer chooseUomConversion(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "ADULT" -> 1;
            case "CHILD" -> 2;
            case "INFANT" -> 3;
            case "PACKAGE" -> 4;
            case "PAIRS" -> 5;
            case "PCS" -> 6;
            case "SWIPE" -> 7;
            case "UNIT" -> 8;
            default -> null; // Default case
        };
    }

    private static Integer choosePriceMaster(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "GOPROMO" -> 1;
            case "KZ50OFF" -> 2;
            case "KZFREECODES" -> 3;
            default -> null; // Default case
        };
    }

    private static Integer chooseProductMasterId(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "000" -> 1;
            case "0GEN" -> 2;
            case "ADJ" -> 3;
            case "ADJN" -> 4;
            case "AMUSEMENT-001" -> 5;
            case "AMUSEMENT-002" -> 6;
            case "AMUSEMENT-003" -> 7;
            case "AMUSEMENT-PROMO-001" -> 8;
            case "BEVERAGE-001" -> 9;
            case "BEVERAGE-002" -> 10;
            case "BEVERAGE-003" -> 11;
            case "BEVERAGE-004" -> 12;
            case "BEVERAGE-005" -> 13;
            case "DSCEMP" -> 14;
            case "DSCEMPN" -> 15;
            case "DSCGPC" -> 16;
            case "DSCGPCN" -> 17;
            case "DSCOT1" -> 18;
            case "DSCOT1N" -> 19;
            case "DSCOT2" -> 20;
            case "DSCOT2N" -> 21;
            case "DSCOT3" -> 22;
            case "DSCOT3N" -> 23;
            case "DSCOT4" -> 24;
            case "DSCOT4N" -> 25;
            case "DSCOT5" -> 26;
            case "DSCOT5N" -> 27;
            case "DSCOT6" -> 28;
            case "DSCOT6N" -> 29;
            case "DSCPWD" -> 30;
            case "DSCPWDN" -> 31;
            case "DSCREG" -> 32;
            case "DSCREGN" -> 33;
            case "DSCVIP" -> 34;
            case "DSCVIPN" -> 35;
            case "EXCESS-001" -> 36;
            case "EXCESS-002" -> 37;
            case "EXCESS-003" -> 38;
            case "EXCESS-004" -> 39;
            case "KIDZOOO0NA-073" -> 40;
            case "KIDZOOON-161" -> 41;
            case "KIDZOOON-166" -> 42;
            case "KIDZOOONA-001" -> 43;
            case "KIDZOOONA-002" -> 44;
            case "KIDZOOONA-003" -> 45;
            case "KIDZOOONA-004" -> 46;
            case "KIDZOOONA-005" -> 47;
            case "KIDZOOONA-006" -> 48;
            case "KIDZOOONA-007" -> 49;
            case "KIDZOOONA-008" -> 50;
            case "KIDZOOONA-009" -> 51;
            case "KIDZOOONA-010" -> 52;
            case "KIDZOOONA-011" -> 53;
            case "KIDZOOONA-012" -> 54;
            case "KIDZOOONA-013" -> 55;
            case "KIDZOOONA-014" -> 56;
            case "KIDZOOONA-015" -> 57;
            case "KIDZOOONA-016" -> 58;
            case "KIDZOOONA-017" -> 59;
            case "KIDZOOONA-018" -> 60;
            case "KIDZOOONA-019" -> 61;
            case "KIDZOOONA-020" -> 62;
            case "KIDZOOONA-021" -> 63;
            case "KIDZOOONA-022" -> 64;
            case "KIDZOOONA-023" -> 65;
            case "KIDZOOONA-024" -> 66;
            case "KIDZOOONA-025" -> 67;
            case "KIDZOOONA-026" -> 68;
            case "KIDZOOONA-027" -> 69;
            case "KIDZOOONA-028" -> 70;
            case "KIDZOOONA-029" -> 71;
            case "KIDZOOONA-030" -> 72;
            case "KIDZOOONA-031" -> 73;
            case "KIDZOOONA-032" -> 74;
            case "KIDZOOONA-033" -> 75;
            case "KIDZOOONA-034" -> 76;
            case "KIDZOOONA-035" -> 77;
            case "KIDZOOONA-036" -> 78;
            case "KIDZOOONA-037" -> 79;
            case "KIDZOOONA-038" -> 80;
            case "KIDZOOONA-039" -> 81;
            case "KIDZOOONA-040" -> 82;
            case "KIDZOOONA-041" -> 83;
            case "KIDZOOONA-042" -> 84;
            case "KIDZOOONA-043" -> 85;
            case "KIDZOOONA-044" -> 86;
            case "KIDZOOONA-045" -> 87;
            case "KIDZOOONA-046" -> 88;
            case "KIDZOOONA-047" -> 89;
            case "KIDZOOONA-048" -> 90;
            case "KIDZOOONA-049" -> 91;
            case "KIDZOOONA-050" -> 92;
            case "KIDZOOONA-051" -> 93;
            case "KIDZOOONA-052" -> 94;
            case "KIDZOOONA-053" -> 95;
            case "KIDZOOONA-055" -> 96;
            case "KIDZOOONA-056" -> 97;
            case "KIDZOOONA-057" -> 98;
            case "KIDZOOONA-060" -> 99;
            case "KIDZOOONA-061" -> 100;
            case "KIDZOOONA-062" -> 101;
            case "KIDZOOONA-063" -> 102;
            case "KIDZOOONA-064" -> 103;
            case "KIDZOOONA-065" -> 104;
            case "KIDZOOONA-066" -> 105;
            case "KIDZOOONA-067" -> 106;
            case "KIDZOOONA-068" -> 107;
            case "KIDZOOONA-069" -> 108;
            case "KIDZOOONA-070" -> 109;
            case "KIDZOOONA-071" -> 110;
            case "KIDZOOONA-072" -> 111;
            case "KIDZOOONA-073" -> 112;
            case "KIDZOOONA-074" -> 113;
            case "KIDZOOONA-075" -> 114;
            case "KIDZOOONA-076" -> 115;
            case "KIDZOOONA-077" -> 116;
            case "KIDZOOONA-078" -> 117;
            case "KIDZOOONA-079" -> 118;
            case "KIDZOOONA-080" -> 119;
            case "KIDZOOONA-081" -> 120;
            case "KIDZOOONA-082" -> 121;
            case "KIDZOOONA-083" -> 122;
            case "KIDZOOONA-084" -> 123;
            case "KIDZOOONA-085" -> 124;
            case "KIDZOOONA-086" -> 125;
            case "KIDZOOONA-087" -> 126;
            case "KIDZOOONA-089" -> 127;
            case "KIDZOOONA-090" -> 128;
            case "KIDZOOONA-091" -> 129;
            case "KIDZOOONA-092" -> 130;
            case "KIDZOOONA-093" -> 131;
            case "KIDZOOONA-094" -> 132;
            case "KIDZOOONA-095" -> 133;
            case "KIDZOOONA-096" -> 134;
            case "KIDZOOONA-097" -> 135;
            case "KIDZOOONA-098" -> 136;
            case "KIDZOOONA-099" -> 137;
            case "KIDZOOONA-100" -> 138;
            case "KIDZOOONA-101" -> 139;
            case "KIDZOOONA-102" -> 140;
            case "KIDZOOONA-103" -> 141;
            case "KIDZOOONA-104" -> 142;
            case "KIDZOOONA-105" -> 143;
            case "KIDZOOONA-106" -> 144;
            case "KIDZOOONA-107" -> 145;
            case "KIDZOOONA-108" -> 146;
            case "KIDZOOONA-109" -> 147;
            case "KIDZOOONA-110" -> 148;
            case "KIDZOOONA-111" -> 149;
            case "KIDZOOONA-112" -> 150;
            case "KIDZOOONA-113" -> 151;
            case "KIDZOOONA-114" -> 152;
            case "KIDZOOONA-115" -> 153;
            case "KIDZOOONA-116" -> 154;
            case "KIDZOOONA-117" -> 155;
            case "KIDZOOONA-118" -> 156;
            case "KIDZOOONA-119" -> 157;
            case "KIDZOOONA-120" -> 158;
            case "KIDZOOONA-121" -> 159;
            case "KIDZOOONA-122" -> 160;
            case "KIDZOOONA-123" -> 161;
            case "KIDZOOONA-124" -> 162;
            case "KIDZOOONA-125" -> 163;
            case "KIDZOOONA-126" -> 164;
            case "KIDZOOONA-127" -> 165;
            case "KIDZOOONA-128" -> 166;
            case "KIDZOOONA-129" -> 167;
            case "KIDZOOONA-130" -> 168;
            case "KIDZOOONA-131" -> 169;
            case "KIDZOOONA-132" -> 170;
            case "KIDZOOONA-133" -> 171;
            case "KIDZOOONA-134" -> 172;
            case "KIDZOOONA-135" -> 173;
            case "KIDZOOONA-136" -> 174;
            case "KIDZOOONA-137" -> 175;
            case "KIDZOOONA-138" -> 176;
            case "KIDZOOONA-139" -> 177;
            case "KIDZOOONA-140" -> 178;
            case "KIDZOOONA-141" -> 179;
            case "KIDZOOONA-142" -> 180;
            case "KIDZOOONA-143" -> 181;
            case "KIDZOOONA-144" -> 182;
            case "KIDZOOONA-145" -> 183;
            case "KIDZOOONA-146" -> 184;
            case "KIDZOOONA-147" -> 185;
            case "KIDZOOONA-148" -> 186;
            case "KIDZOOONA-149" -> 187;
            case "KIDZOOONA-150" -> 188;
            case "KIDZOOONA-151" -> 189;
            case "KIDZOOONA-152" -> 190;
            case "KIDZOOONA-153" -> 191;
            case "KIDZOOONA-154" -> 192;
            case "KIDZOOONA-155" -> 193;
            case "KIDZOOONA-156" -> 194;
            case "KIDZOOONA-157" -> 195;
            case "KIDZOOONA-158" -> 196;
            case "KIDZOOONA-159" -> 197;
            case "KIDZOOONA-160" -> 198;
            case "KIDZOOONA-161" -> 199;
            case "KIDZOOONA-162" -> 200;
            case "KIDZOOONA-163" -> 201;
            case "KIDZOOONA-164" -> 202;
            case "KIDZOOONA-165" -> 203;
            case "KIDZOOONA-167" -> 204;
            case "KIDZOOONA-168" -> 205;
            case "KIDZOOONA-169" -> 206;
            case "KIDZOOONA-170" -> 207;
            case "KIDZOOONA-171" -> 208;
            case "KIDZOOONA-172" -> 209;
            case "KIDZOOONA-173" -> 210;
            case "KIDZOOONA-174" -> 211;
            case "KIDZOOONA-175" -> 212;
            case "KIDZOOONA-176" -> 213;
            case "KIDZOOONA-177" -> 214;
            case "KIDZOOONA-178" -> 215;
            case "KIDZOOONA-179" -> 216;
            case "KIDZOOONA-180" -> 217;
            case "KIDZOOONA-181" -> 218;
            case "KIDZOOONA-182" -> 219;
            case "KIDZOOONA-183" -> 220;
            case "KIDZOOONA-184" -> 221;
            case "KIDZOOONA-185" -> 222;
            case "KIDZOOONA-186" -> 223;
            case "KIDZOOONA-187" -> 224;
            case "KIDZOOONA-188" -> 225;
            case "KIDZOOONA-189" -> 226;
            case "KIDZOOONA-190" -> 227;
            case "KIDZOOONA-191" -> 228;
            case "KIDZOOONA-192" -> 229;
            case "KIDZOOONA-193" -> 230;
            case "KIDZOOONA-194" -> 231;
            case "KIDZOOONA-195" -> 232;
            case "KIDZOOONA-196" -> 233;
            case "KIDZOOONA-197" -> 234;
            case "KIDZOOONA-198" -> 235;
            case "KIDZOOONA-199" -> 236;
            case "KIDZOOONA-200" -> 237;
            case "KIDZOOONA-201" -> 238;
            case "KIDZOOONA-202" -> 239;
            case "KIDZOOONA-203" -> 240;
            case "KIDZOOONA-204" -> 241;
            case "KIDZOOONA-205" -> 242;
            case "KIDZOOONA-206" -> 243;
            case "KIDZOOONA-207" -> 244;
            case "KIDZOOONA-208" -> 245;
            case "KIDZOOONA-209" -> 246;
            case "KIDZOOONA-210" -> 247;
            case "KIDZOOONA-211" -> 248;
            case "KIDZOOONA-212" -> 249;
            case "KIDZOOONA-213" -> 250;
            case "KIDZOOONA-214" -> 251;
            case "KIDZOOONA-215" -> 252;
            case "KIDZOOONA-216" -> 253;
            case "KIDZOOONA-217" -> 254;
            case "KIDZOOONA-218" -> 255;
            case "KIDZOOONA-219" -> 256;
            case "KIDZOOONA-220" -> 257;
            case "KIDZOOONA-221" -> 258;
            case "KIDZOOONA-222" -> 259;
            case "KIDZOOONA-223" -> 260;
            case "KIDZOOONA-224" -> 261;
            case "KIDZOOONA-225" -> 262;
            case "KIDZOOONA-226" -> 263;
            case "KIDZOOONA-227" -> 264;
            case "KIDZOOONA-228" -> 265;
            case "KIDZOOONA-229" -> 266;
            case "KIDZOOONA-230" -> 267;
            case "KIDZOOONA-231" -> 268;
            case "KIDZOOONA-232" -> 269;
            case "KIDZOOONA-233" -> 270;
            case "KIDZOOONA-234" -> 271;
            case "KIDZOOONA-235" -> 272;
            case "KIDZOOONA-236" -> 273;
            case "KIDZOOONA-237" -> 274;
            case "KIDZOOONA-238" -> 275;
            case "KIDZOOONA-239" -> 276;
            case "KIDZOOONA-240" -> 277;
            case "KIDZOOONA-241" -> 278;
            case "KIDZOOONA-242" -> 279;
            case "KIDZOOONA-243" -> 280;
            case "KIDZOOONA-244" -> 281;
            case "KIDZOOONA-245" -> 282;
            case "KIDZOOONA-246" -> 283;
            case "KIDZOOONA-247" -> 284;
            case "KIDZOOONA-248" -> 285;
            case "KIDZOOONA-249" -> 286;
            case "KIDZOOONA-250" -> 287;
            case "KIDZOOONA-251" -> 288;
            case "KIDZOOONA-252" -> 289;
            case "KIDZOOONA-253" -> 290;
            case "KIDZOOONA-254" -> 291;
            case "KIDZOOONA-255" -> 292;
            case "KIDZOOONA-256" -> 293;
            case "KIDZOOONA-257" -> 294;
            case "KIDZOOONA-258" -> 295;
            case "KIDZOOONA-259" -> 296;
            case "KIDZOOONA-260" -> 297;
            case "KIDZOOONA-261" -> 298;
            case "KIDZOOONA-262" -> 299;
            case "KIDZOOONA-263" -> 300;
            case "KIDZOOONA-264" -> 301;
            case "KIDZOOONA-265" -> 302;
            case "KIDZOOONA-266" -> 303;
            case "KIDZOOONA-267" -> 304;
            case "KIDZOOONA-268" -> 305;
            case "KIDZOOONA-269" -> 306;
            case "KIDZOOONA-270" -> 307;
            case "KIDZOOONA-271" -> 308;
            case "KIDZOOONA-272" -> 309;
            case "KIDZOOONA-273" -> 310;
            case "KIDZOOONA-274" -> 311;
            case "KIDZOOONA-275" -> 312;
            case "KIDZOOONA-276" -> 313;
            case "KIDZOOONA-277" -> 314;
            case "KIDZOOONA-278" -> 315;
            case "KIDZOOONA-279" -> 316;
            case "KIDZOOONA-280" -> 317;
            case "KIDZOOONA-281" -> 318;
            case "KIDZOOONA-282" -> 319;
            case "KIDZOOONA-283" -> 320;
            case "KIDZOOONA-284" -> 321;
            case "KIDZOOONA-285" -> 322;
            case "KIDZOOONA-286" -> 323;
            case "KIDZOOONA-287" -> 324;
            case "KIDZOOONA-288" -> 325;
            case "KIDZOOONA-289" -> 326;
            case "KIDZOOONA-290" -> 327;
            case "KIDZOOONA-291" -> 328;
            case "KIDZOOONA-293" -> 329;
            case "KIDZOOONA-299" -> 330;
            case "KIDZOOONA-300" -> 331;
            case "KIDZOOONA-301" -> 332;
            case "KIDZOOONA-302" -> 333;
            case "KIDZOOONA-303" -> 334;
            case "KIDZOOONA-304" -> 335;
            case "KIDZOOONA-305" -> 336;
            case "KIDZOOONA-306" -> 337;
            case "KIDZOOONA-307" -> 338;
            case "KIDZOOONA-308" -> 339;
            case "KIDZOOONA-309" -> 340;
            case "KIDZOOONA-310" -> 341;
            case "KIDZOOONA-311" -> 342;
            case "KIDZOOONA-312" -> 343;
            case "KIDZOOONA-313" -> 344;
            case "KIDZOOONA-314" -> 345;
            case "KIDZOOONA-315" -> 346;
            case "KIDZOOONA-316" -> 347;
            case "KIDZOOONA-317" -> 348;
            case "KIDZOOONA-318" -> 349;
            case "KIDZOOONA-320" -> 350;
            case "KIDZOOONA-321" -> 351;
            case "KIDZOOONA-322" -> 352;
            case "KIDZOOONA-323" -> 353;
            case "KIDZOOONA-324" -> 354;
            case "KIDZOOONA-325" -> 355;
            case "KIDZOOONA-326" -> 356;
            case "KIDZOOONA-327" -> 357;
            case "KIDZOOONA-328" -> 358;
            case "KIDZOOONA-345" -> 359;
            case "KIDZOOONA-350" -> 360;
            case "KIDZOOONA-380" -> 361;
            case "KIDZOOONA-381" -> 362;
            case "KIDZOOONA-382" -> 363;
            case "KIDZOOONA-383" -> 364;
            case "KIDZOOONA-384" -> 365;
            case "KIDZOOONA-386" -> 366;
            case "KIDZOOONA-399" -> 367;
            case "KIDZOOONA-400" -> 368;
            case "KIDZOOONA-401" -> 369;
            case "KIDZOOONA-58" -> 370;
            case "KIDZOOONA-59" -> 371;
            case "KIDZOOONA-PROMO1PESO" -> 372;
            case "KIDZOOONA20" -> 373;
            case "KZ-PARTY-084" -> 374;
            case "KZAPP-001" -> 375;
            case "KZAPP-002" -> 376;
            case "KZAPP-003" -> 377;
            case "KZAPP-004" -> 378;
            case "KZAPP-005" -> 379;
            case "KZAPP-006" -> 380;
            case "KZAPP-007" -> 381;
            case "KZAPP-008" -> 382;
            case "KZAPP-009" -> 383;
            case "KZAPP-010" -> 384;
            case "KZAPP-011" -> 385;
            case "KZAPP-012" -> 386;
            case "KZAPP-013" -> 387;
            case "KZAPP-014" -> 388;
            case "KZAPP-015" -> 389;
            case "KZAPP-016" -> 390;
            case "KZAPP-017" -> 391;
            case "KZAPP-018" -> 392;
            case "KZAPP-019" -> 393;
            case "KZAPP-020" -> 394;
            case "KZAPP-021" -> 395;
            case "KZAPP-022" -> 396;
            case "KZAPP-023" -> 397;
            case "KZAPP-024" -> 398;
            case "KZAPP-025" -> 399;
            case "KZAPP-026" -> 400;
            case "KZAPP-027" -> 401;
            case "KZAPP-028" -> 402;
            case "KZAPP-029" -> 403;
            case "KZAPP-030" -> 404;
            case "KZAPP-031" -> 405;
            case "KZAPP-032" -> 406;
            case "KZAPP-033" -> 407;
            case "KZAPP-034" -> 408;
            case "KZAPP-035" -> 409;
            case "KZAPP-036" -> 410;
            case "KZAPP-037" -> 411;
            case "KZAPP-038" -> 412;
            case "KZAPP-039" -> 413;
            case "KZAPP-040" -> 414;
            case "KZAPP-041" -> 415;
            case "KZAPP-042" -> 416;
            case "KZAPP-043" -> 417;
            case "KZAPP-044" -> 418;
            case "KZAPP-045" -> 419;
            case "KZAPP-046" -> 420;
            case "KZAPP-047" -> 421;
            case "KZAPP-048" -> 422;
            case "KZAPP-049" -> 423;
            case "KZAPP-050" -> 424;
            case "KZAPP-051" -> 425;
            case "KZAPP-052" -> 426;
            case "KZAPP-053" -> 427;
            case "KZAPP-054" -> 428;
            case "KZAPP-055" -> 429;
            case "KZAPP-056" -> 430;
            case "KZAPPTEST-000" -> 431;
            case "KZMERCH-0001" -> 432;
            case "KZPARTY-0002" -> 433;
            case "KZPARTY-0004" -> 434;
            case "KZPARTY-001" -> 435;
            case "KZPARTY-002" -> 436;
            case "KZPARTY-003" -> 437;
            case "KZPARTY-004" -> 438;
            case "KZPARTY-005" -> 439;
            case "KZPARTY-006" -> 440;
            case "KZPARTY-007" -> 441;
            case "KZPARTY-008" -> 442;
            case "KZPARTY-009" -> 443;
            case "KZPARTY-010" -> 444;
            case "KZPARTY-011" -> 445;
            case "KZPARTY-012" -> 446;
            case "KZPARTY-013" -> 447;
            case "KZPARTY-014" -> 448;
            case "KZPARTY-015" -> 449;
            case "KZPARTY-016" -> 450;
            case "KZPARTY-017" -> 451;
            case "KZPARTY-018" -> 452;
            case "KZPARTY-019" -> 453;
            case "KZPARTY-020" -> 454;
            case "KZPARTY-021" -> 455;
            case "KZPARTY-022" -> 456;
            case "KZPARTY-023" -> 457;
            case "KZPARTY-024" -> 458;
            case "KZPARTY-025" -> 459;
            case "KZPARTY-026" -> 460;
            case "KZPARTY-027" -> 461;
            case "KZPARTY-029" -> 462;
            case "KZPARTY-031" -> 463;
            case "KZPARTY-033" -> 464;
            case "KZPARTY-034" -> 465;
            case "KZPARTY-035" -> 466;
            case "KZPARTY-036" -> 467;
            case "KZPARTY-037" -> 468;
            case "KZPARTY-038" -> 469;
            case "KZPARTY-039" -> 470;
            case "KZPARTY-040" -> 471;
            case "KZPARTY-041" -> 472;
            case "KZPARTY-042" -> 473;
            case "KZPARTY-043" -> 474;
            case "KZPARTY-044" -> 475;
            case "KZPARTY-045" -> 476;
            case "KZPARTY-046" -> 477;
            case "KZPARTY-047" -> 478;
            case "KZPARTY-048" -> 479;
            case "KZPARTY-049" -> 480;
            case "KZPARTY-050" -> 481;
            case "KZPARTY-051" -> 482;
            case "KZPARTY-052" -> 483;
            case "KZPARTY-053" -> 484;
            case "KZPARTY-054" -> 485;
            case "KZPARTY-055" -> 486;
            case "KZPARTY-056" -> 487;
            case "KZPARTY-057" -> 488;
            case "KZPARTY-058" -> 489;
            case "KZPARTY-059" -> 490;
            case "KZPARTY-060" -> 491;
            case "KZPARTY-061" -> 492;
            case "KZPARTY-062" -> 493;
            case "KZPARTY-063" -> 494;
            case "KZPARTY-064" -> 495;
            case "KZPARTY-065" -> 496;
            case "KZPARTY-066" -> 497;
            case "KZPARTY-067" -> 498;
            case "KZPARTY-068" -> 499;
            case "KZPARTY-069" -> 500;
            case "KZPARTY-070" -> 501;
            case "KZPARTY-071" -> 502;
            case "KZPARTY-073" -> 503;
            case "KZPARTY-075" -> 504;
            case "KZPARTY-077" -> 505;
            case "KZPARTY-079" -> 506;
            case "KZPARTY-080" -> 507;
            case "KZPARTY-081" -> 508;
            case "KZPARTY-082" -> 509;
            case "KZPARTY-083" -> 510;
            case "KZPARTY-085" -> 511;
            case "KZPARTY-087" -> 512;
            case "KZPARTY-089" -> 513;
            case "KZPARTY-091" -> 514;
            case "KZPARTY-100" -> 515;
            case "KZPARTY-101" -> 516;
            case "KZPARTY-28" -> 517;
            case "KZPARTY-30" -> 518;
            case "KZPARTY-32" -> 519;
            case "KZTM-00001" -> 520;
            case "KZTM-00002" -> 521;
            case "KZTM-00003" -> 522;
            case "KZTM-00004" -> 523;
            case "KZTM-00005" -> 524;
            case "KZTM-00006" -> 525;
            case "KZTM-00007" -> 526;
            case "KZTM-00008" -> 527;
            case "KZTM-00009" -> 528;
            case "KZTM-00010" -> 529;
            case "KZTM-00011" -> 530;
            case "KZTM-00012" -> 531;
            case "KZTM-00013" -> 532;
            case "KZTM-00014" -> 533;
            case "KZTM-00015" -> 534;
            case "KZTM-00016" -> 535;
            case "KZTM-00017" -> 536;
            case "KZTM-00018" -> 537;
            case "KZTM-00019" -> 538;
            case "KZTM-00020" -> 539;
            case "KZTM-00021" -> 540;
            case "KZTM-00022" -> 541;
            case "KZTM-00023" -> 542;
            case "KZTM-00024" -> 543;
            case "KZTM-00025" -> 544;
            case "KZTM-00026" -> 545;
            case "PISO" -> 546;
            case "PROMO-001" -> 547;
            case "PROMO-002" -> 548;
            case "PROMO-003" -> 549;
            case "PROMO-004" -> 550;
            case "PROMO-005" -> 551;
            case "PROMO-006" -> 552;
            case "PROMO-007" -> 553;
            default -> null; // Default case
        };
    }


    private static String chooseTerminalType(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "0" -> "Sales Counter";
            case "1" -> "Food and Beverage";
            case "2" -> "Station Detail";
            case "3" -> "Mobile Order";
            case "4" -> "Billing and Collection";
            case "5" -> "EC Terminal";
            case "6" -> "SPA";
            case "7" -> "Kiosk";
            case "8" -> "Price Verifier";
            case "9" -> "In-Store Advertising";
            case "A" -> "Table Ordering";

            default -> null; // Default case
        };
    }

    private static Integer choosePrinter(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "ABSDOTMATRIXCUT" -> 4;


            default -> 1; // Default case
        };
    }


    private static Integer chooseDrawer(String value) {
        if (value == null || value.isEmpty()) {
            return null; // Handle null or empty strings
        }

        return switch (value) {
            case "ABSDOTMATRIX" -> 2;
            case "OPOS" -> 5;

            default -> 1; // Default case
        };
    }


    public static String generateUUID() {
        String template = "10000000-1000-4000-8000-100000000000";
        StringBuilder uuidBuilder = new StringBuilder(template);

        for (int i = 0; i < template.length(); i++) {
            char c = template.charAt(i);
            if (c == '0' || c == '1' || c == '8') {
                int randomValue = new SecureRandom().nextInt(16);
                int hexValue = Character.digit(c, 16) ^ (randomValue & (15 >> (Character.digit(c, 16) / 4)));
                char hexChar = Character.forDigit(hexValue, 16);
                uuidBuilder.setCharAt(i, hexChar);
            }
        }

        return uuidBuilder.toString();
    }



}
