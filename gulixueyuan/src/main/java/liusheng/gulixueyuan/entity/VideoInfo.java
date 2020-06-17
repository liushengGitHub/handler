package liusheng.gulixueyuan.entity;

/**
 * 2020年:  05 月:  09 日:  11小时:  47分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class VideoInfo {

    /**
     * itemType : task
     * number : 1
     * published_number : 1
     * title : 01、简介-项目介绍
     * result : 3658147
     * resultStatus : start
     * lock : false
     * status : published
     * taskId : 13283
     * isOptional : 0
     * type : video
     * isTaskFree : 1
     * watchLimitRemaining : false
     * replayStatus :
     * activityStartTimeStr :
     * activityStartTime :
     * activityLength : 11:23
     * activityEndTime :
     * fileStorage : cloud
     * isTaskTryLookable : 0
     * isSingleTaskLesson : true
     */

    private String itemType;
    private String number;
    private String published_number;
    private String title;
    private String result;
    private String resultStatus;
    private boolean lock;
    private String status;
    private String taskId;
    private String isOptional;
    private String type;
    private String isTaskFree;
    private boolean watchLimitRemaining;
    private String replayStatus;
    private String activityStartTimeStr;
    private String activityStartTime;
    private String activityLength;
    private String activityEndTime;
    private String fileStorage;
    private int isTaskTryLookable;
    private boolean isSingleTaskLesson;

    private String url ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPublished_number() {
        return published_number;
    }

    public void setPublished_number(String published_number) {
        this.published_number = published_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(String isOptional) {
        this.isOptional = isOptional;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsTaskFree() {
        return isTaskFree;
    }

    public void setIsTaskFree(String isTaskFree) {
        this.isTaskFree = isTaskFree;
    }

    public boolean isWatchLimitRemaining() {
        return watchLimitRemaining;
    }

    public void setWatchLimitRemaining(boolean watchLimitRemaining) {
        this.watchLimitRemaining = watchLimitRemaining;
    }

    public String getReplayStatus() {
        return replayStatus;
    }

    public void setReplayStatus(String replayStatus) {
        this.replayStatus = replayStatus;
    }

    public String getActivityStartTimeStr() {
        return activityStartTimeStr;
    }

    public void setActivityStartTimeStr(String activityStartTimeStr) {
        this.activityStartTimeStr = activityStartTimeStr;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityLength() {
        return activityLength;
    }

    public void setActivityLength(String activityLength) {
        this.activityLength = activityLength;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }

    public int getIsTaskTryLookable() {
        return isTaskTryLookable;
    }

    public void setIsTaskTryLookable(int isTaskTryLookable) {
        this.isTaskTryLookable = isTaskTryLookable;
    }

    public boolean isIsSingleTaskLesson() {
        return isSingleTaskLesson;
    }

    public void setIsSingleTaskLesson(boolean isSingleTaskLesson) {
        this.isSingleTaskLesson = isSingleTaskLesson;
    }
}
