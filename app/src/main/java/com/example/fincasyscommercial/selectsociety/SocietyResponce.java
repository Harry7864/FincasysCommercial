package com.example.fincasyscommercial.selectsociety;

import com.example.fincasyscommercial.network.CommonResponce;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocietyResponce extends CommonResponce {

    @SerializedName("society")
    @Expose
    private List<Society> society = null;

    public List<Society> getSociety() {
        return society;
    }

    public void setSociety(List<Society> society) {
        this.society = society;
    }




    public class Society {

        @SerializedName("society_id")
        @Expose
        private String societyId;
        @SerializedName("societyUserId")
        @Expose
        private String societyUserId;
        @SerializedName("society_name")
        @Expose
        private String societyName;
        @SerializedName("society_address")
        @Expose
        private String societyAddress;
        @SerializedName("secretary_email")
        @Expose
        private String secretaryEmail;
        @SerializedName("secretary_mobile")
        @Expose
        private String secretaryMobile;
        @SerializedName("socieaty_logo")
        @Expose
        private String socieatyLogo;
        @SerializedName("builder_name")
        @Expose
        private String builderName;
        @SerializedName("builder_address")
        @Expose
        private String builderAddress;
        @SerializedName("builder_mobile")
        @Expose
        private String builderMobile;
        @SerializedName("socieaty_status")
        @Expose
        private String socieatyStatus;

        @SerializedName("sub_domain")
        @Expose
        private String subDomain;

        @SerializedName("api_key")
        @Expose
        private String apiKey;

        private boolean isClicked = false;


        public String getSubDomain() {
            return subDomain;
        }

        public void setSubDomain(String subDomain) {
            this.subDomain = subDomain;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getSocietyId() {
            return societyId;
        }

        public void setSocietyId(String societyId) {
            this.societyId = societyId;
        }

        public String getSocietyUserId() {
            return societyUserId;
        }

        public void setSocietyUserId(String societyUserId) {
            this.societyUserId = societyUserId;
        }

        public String getSocietyName() {
            return societyName;
        }

        public void setSocietyName(String societyName) {
            this.societyName = societyName;
        }

        public String getSocietyAddress() {
            return societyAddress;
        }

        public void setSocietyAddress(String societyAddress) {
            this.societyAddress = societyAddress;
        }

        public String getSecretaryEmail() {
            return secretaryEmail;
        }

        public void setSecretaryEmail(String secretaryEmail) {
            this.secretaryEmail = secretaryEmail;
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }

        public String getSecretaryMobile() {
            return secretaryMobile;
        }

        public void setSecretaryMobile(String secretaryMobile) {
            this.secretaryMobile = secretaryMobile;
        }

        public String getSocieatyLogo() {
            return socieatyLogo;
        }

        public void setSocieatyLogo(String socieatyLogo) {
            this.socieatyLogo = socieatyLogo;
        }

        public String getBuilderName() {
            return builderName;
        }

        public void setBuilderName(String builderName) {
            this.builderName = builderName;
        }

        public String getBuilderAddress() {
            return builderAddress;
        }

        public void setBuilderAddress(String builderAddress) {
            this.builderAddress = builderAddress;
        }

        public String getBuilderMobile() {
            return builderMobile;
        }

        public void setBuilderMobile(String builderMobile) {
            this.builderMobile = builderMobile;
        }

        public String getSocieatyStatus() {
            return socieatyStatus;
        }

        public void setSocieatyStatus(String socieatyStatus) {
            this.socieatyStatus = socieatyStatus;
        }
    }
}
