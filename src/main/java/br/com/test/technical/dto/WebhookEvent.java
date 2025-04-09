package br.com.test.technical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
public class WebhookEvent {
    @JsonProperty("eventId")
    Long eventId;
    @JsonProperty("subscriptionId")
    Long subscriberId;
    @JsonProperty("portalId")
    Long portalId;
    @JsonProperty("appId")
    Long appId;
    @JsonProperty("occurredAt")
    Long occurredAt;
    @JsonProperty("subscriptionType")
    String subscriptionType;
    @JsonProperty("attemptNumber")
    int attemptNumber;
    @JsonProperty("changeFlag")
    String changeFlag;
    @JsonProperty("changeSource")
    String changeSource;
    @JsonProperty("objectId")
    Long objectId;
    @JsonProperty("sourceId")
    String sourceId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Long getPortalId() {
        return portalId;
    }

    public void setPortalId(Long portalId) {
        this.portalId = portalId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Long occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getChangeSource() {
        return changeSource;
    }

    public void setChangeSource(String changeSource) {
        this.changeSource = changeSource;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "WebhookEvent{" +
                "eventId=" + eventId +
                ", subscriberId=" + subscriberId +
                ", portalId=" + portalId +
                ", appId=" + appId +
                ", occurredAt=" + occurredAt +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", attemptNumber=" + attemptNumber +
                ", changeFlag='" + changeFlag + '\'' +
                ", changeSource='" + changeSource + '\'' +
                ", objectId=" + objectId +
                ", sourceId='" + sourceId + '\'' +
                '}';
    }
}
