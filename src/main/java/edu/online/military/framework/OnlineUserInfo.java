package edu.online.military.framework;

public class OnlineUserInfo {
    private String id;
    private String card_id;
    private String cookieId;
    private String clientIp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String toString() {
        return "OnlineUserInfo{" +
                "id='" + id + '\'' +
                ", card_id='" + card_id + '\'' +
                ", cookieId='" + cookieId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                '}';
    }
}
