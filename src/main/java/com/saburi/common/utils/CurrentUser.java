/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.dbaccess.UserRoleDetailDA;
import com.saburi.common.entities.AppUser;
import com.saburi.common.entities.UserRoleDetail;
import com.saburi.common.utils.CommonEnums.Rights;
import java.time.LocalDateTime;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author CLINICMASTER13
 */
public class CurrentUser {

    private static AppUser appUser;
    private static LocalDateTime loginDateTime;
    public static String loginTime;
    public static final UserRoleDetailDA oUserRoleDetailDA = new UserRoleDetailDA();

    public static AppUser getAppUser() {
        return appUser;
    }

    public static void setAppUser(AppUser appUser) {
        CurrentUser.appUser = appUser;
    }

    public static void setLoginTime(LocalDateTime loginDateTime) {
        CurrentUser.loginDateTime = loginDateTime;
    }

    public static String getLoginID() {
        return appUser.getLoginID();
    }

    public static String getFullName() {
        return appUser.getDisplayKey();
    }

    public static LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public static String getLoginTime() {
        return Utilities.formatTime(loginDateTime);
    }

    public static String getSimpleUserData() {
        return "Login ID: " + getLoginID() + " | Full Name: " + getFullName()
                + " | Role: " + appUser.getRole().getRoleName()
                + " | Login Time: " + Utilities.formatNullDateTime(getLoginDateTime());
    }

    public static void applyRights(Node node, Rights right) {
        String objectName = node.getId();

        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);
        if (userRoleDetail == null) {
            node.disableProperty().set(true);
        } else {
            boolean action = false;
            switch (right) {
                case Create:
                    action = userRoleDetail.isCanCreate();
                    break;
                case Read:
                    action = userRoleDetail.isCanRead();
                    break;
                case Update:
                    action = userRoleDetail.isCanUpdate();
                    break;
                case Delete:
                    action = userRoleDetail.isCanDelete();
                    break;
                default:
                    break;
            }

            node.disableProperty().set(!action);
        }

    }

    public static void applyRights(Node node) {
        String objectName = node.getParent().getId();
        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);

        if (userRoleDetail == null) {
            node.disableProperty().set(true);
        } else {
            String right = node.getId();
            if (Utilities.isNullOrEmpty(right)) {
                node.disableProperty().set(true);
            } else {
                boolean action = false;
                switch (right) {
                    case "Create":
                        action = userRoleDetail.isCanCreate();
                        break;
                    case "Read":
                        action = userRoleDetail.isCanRead();
                        break;
                    case "Update":
                        action = userRoleDetail.isCanUpdate();
                        break;
                    case "Delete":
                        action = userRoleDetail.isCanDelete();
                        break;
                    default:
                        break;
                }

                node.disableProperty().set(!action);
            }
        }
    }

    public static void applyRights(MenuItem menuItem, boolean hasParent) {

        if (hasParent) {
            String objectName = menuItem.getParentMenu().getId();
            UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);
            if (userRoleDetail == null) {
                menuItem.disableProperty().set(true);
            } else {
                String right = menuItem.getId();
                if (Utilities.isNullOrEmpty(right)) {
                    menuItem.disableProperty().set(true);
                } else {
                    boolean action = false;

                    switch (right) {
                        case "Create":
                            action = userRoleDetail.isCanCreate();
                            break;
                        case "Read":
                            action = userRoleDetail.isCanRead();
                            break;
                        case "Update":
                            action = userRoleDetail.isCanUpdate();
                            break;
                        case "Delete":
                            action = userRoleDetail.isCanDelete();
                            break;
                        default:
                            break;
                    }

                    menuItem.disableProperty().set(!action);
                }
            }
        } else {
            String objectName = menuItem.getId();
            UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);
            menuItem.disableProperty().set(userRoleDetail == null);
        }
    }

    public static void applyRights(String objectName, MenuItem menuItem) {
        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);

        if (userRoleDetail == null) {
            menuItem.disableProperty().set(true);
        } else {
            String right = menuItem.getId();
            if (Utilities.isNullOrEmpty(right)) {
                menuItem.disableProperty().set(true);
            } else {
                boolean action = false;
                switch (right) {
                    case "Create":
                        action = userRoleDetail.isCanCreate();
                        break;
                    case "Read":
                        action = userRoleDetail.isCanRead();
                        break;
                    case "Update":
                        action = userRoleDetail.isCanUpdate();
                        break;
                    case "Delete":
                        action = userRoleDetail.isCanDelete();
                        break;
                    default:
                        break;
                }

                menuItem.disableProperty().set(!action);
            }
        }
    }

    public static void applyRights(Parent parent) {
        String objectName = parent.getId();
        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);
        parent.disableProperty().set(userRoleDetail == null);
        parent.getChildrenUnmodifiable().forEach(node -> applyRights(node));
    }

    public static void applyRights(String objectName, Node node) {
        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);
        node.disableProperty().set(userRoleDetail == null);
    }

    public static void applyRights(MenuItem menuItem, Rights right) {
        try {
            String objectName = menuItem.getId();
            UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);

            if (userRoleDetail == null) {
                menuItem.disableProperty().set(true);
            } else {
                boolean action = false;

                switch (right) {
                    case Create:
                        action = userRoleDetail.isCanCreate();
                        break;
                    case Read:
                        action = userRoleDetail.isCanRead();
                        break;
                    case Update:
                        action = userRoleDetail.isCanUpdate();
                        break;
                    case Delete:
                        action = userRoleDetail.isCanDelete();
                        break;
                    default:
                        break;
                }

                menuItem.disableProperty().set(!action);
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public static void applyRights(Menu parentMenu, boolean hasChildren) {
        String objectName = parentMenu.getId();
        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);
        parentMenu.disableProperty().set(userRoleDetail == null);
        parentMenu.getItems().forEach(menuItem -> applyRights(menuItem, hasChildren));
    }

    public static boolean hasRights(String objectName, Rights right) {
        UserRoleDetail userRoleDetail = oUserRoleDetailDA.getUserRoleDetail(appUser.getRole(), objectName);

        if (userRoleDetail == null) {
            return false;
        } else {
            boolean action = false;

            switch (right) {
                case Create:
                    action = userRoleDetail.isCanCreate();
                    break;
                case Read:
                    action = userRoleDetail.isCanRead();
                    break;
                case Update:
                    action = userRoleDetail.isCanUpdate();
                    break;
                case Delete:
                    action = userRoleDetail.isCanDelete();
                    break;
                default:
                    break;
            }

            return action;
        }
    }
}
