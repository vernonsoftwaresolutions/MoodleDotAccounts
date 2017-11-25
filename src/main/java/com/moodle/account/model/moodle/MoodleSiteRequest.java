package com.moodle.account.model.moodle;

/**
 * Created by andrewlarsen on 9/9/17.
 */
public class MoodleSiteRequest {
    private String id;
    private String stackName;
    private String clientName;
    private String vpcId;
    private String hostedZoneName;
    private Integer priority;

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getHostedZoneName() {
        return hostedZoneName;
    }

    public void setHostedZoneName(String hostedZoneName) {
        this.hostedZoneName = hostedZoneName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MoodleTenantRequest{" +
                "id='" + id + '\'' +
                ", stackName='" + stackName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", vpcId='" + vpcId + '\'' +
                ", hostedZoneName='" + hostedZoneName + '\'' +
                ", priority=" + priority +
                '}';
    }
}
