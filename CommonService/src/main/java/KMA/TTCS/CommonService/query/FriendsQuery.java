package KMA.TTCS.CommonService.query;

public class FriendsQuery {
    String idProfile;
    int pageNo;
    int pageSize;
    public FriendsQuery() {
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public FriendsQuery(String idProfile, int pageNo, int pageSize) {
        this.idProfile = idProfile;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
