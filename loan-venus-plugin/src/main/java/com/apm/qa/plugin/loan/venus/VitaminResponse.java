package com.apm.qa.plugin.loan.venus;

import java.util.List;

public class VitaminResponse {
    VitaminResult result;
    VitaminStatus status;

    public VitaminResult getResult() {
        return result;
    }

    public void setResult(VitaminResult result) {
        this.result = result;
    }

    public VitaminStatus getStatus() {
        return status;
    }

    public void setStatus(VitaminStatus status) {
        this.status = status;
    }
}

class VitaminResult{
    public List<VitaminNode> nodes;
    public String groupId;
    public String serviceId;
    public String md5;

    public List<VitaminNode> getNode() {
        return nodes;
    }

    public void setNode(List<VitaminNode> nodes) {
        this.nodes = nodes;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}

class VitaminNode{
    String nodeValue;
    String regionName;
    String operateType;
    String nodeKey;
    String status;

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class VitaminStatus{
    int statusCode;
    String status_reason;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus_reason() {
        return status_reason;
    }

    public void setStatus_reason(String status_reason) {
        this.status_reason = status_reason;
    }
}
