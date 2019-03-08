package com.thinkgem.jeesite.modules.baiduApi.api.track;


import com.thinkgem.jeesite.modules.baiduApi.model.BaseResponse;

/**
 * 上传响应
 * 
 * @author baidu
 *
 */
public class UploadResponse extends BaseResponse {

    /**
     * 
     * @param responseID 响应ID
     * @param status 响应状态
     * @param message 响应消息
     */
    public UploadResponse(long responseID, int status, String message) {
        super(responseID, status, message);
    }

    @Override
    public String toString() {
        return "UploadResponse [" + super.toString() + "]";
    }

}
