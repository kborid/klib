package com.kborid.common.network.func;

/**
 * @Title ServiceException
 * @Description 业务异常（用于统一错误处理）
 * @Author 赵臣
 * @CreateTime 2016/12/1
 * @Company 北京华宇信息技术有限公司
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String msg) {
        super(msg);
    }
}
