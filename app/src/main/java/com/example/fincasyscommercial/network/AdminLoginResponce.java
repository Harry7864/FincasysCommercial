package com.example.fincasyscommercial.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminLoginResponce extends CommonResponce
{
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("society_id")
    @Expose
    private String societyId;
    @SerializedName("admin_name")
    @Expose
    private String adminName;
    @SerializedName("admin_email")
    @Expose
    private String adminEmail;
    @SerializedName("admin_mobile")
    @Expose
    private String adminMobile;
    @SerializedName("admin_address")
    @Expose
    private String adminAddress;
    @SerializedName("admin_password")
    @Expose
    private String adminPassword;
    @SerializedName("admin_profile")
    @Expose
    private String adminProfile;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("token_date")
    @Expose
    private String tokenDate;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("base_url")
    @Expose
    private String base_url;
    @SerializedName("society_name")
    @Expose
    private String society_name;


    @SerializedName("socieaty_logo")
    @Expose
    private String socieaty_logo;

    @SerializedName("adminAppPrivilege")
    @Expose
    private String adminAppPrivilege;

    public String getAdminAppPrivilege() {
        return adminAppPrivilege;
    }

    public void setAdminAppPrivilege(String adminAppPrivilege) {
        this.adminAppPrivilege = adminAppPrivilege;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSocietyId() {
        return societyId;
    }

    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminProfile() {
        return adminProfile;
    }

    public void setAdminProfile(String adminProfile) {
        this.adminProfile = adminProfile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(String tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }


    public String getSociety_name() {
        return society_name;
    }

    public void setSociety_name(String society_name) {
        this.society_name = society_name;
    }

    public String getSocieaty_logo() {
        return socieaty_logo;
    }

    public void setSocieaty_logo(String socieaty_logo) {
        this.socieaty_logo = socieaty_logo;
    }
}
