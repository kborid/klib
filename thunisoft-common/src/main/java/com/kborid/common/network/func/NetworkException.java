package com.kborid.common.network.func;

import java.io.IOException;

/**
 * @Title NetworkException
 * @Description 网络异常
 * @Author 赵臣
 * @CreateTime 2016/12/10
 * @Company 北京华宇信息技术有限公司
 */

public class NetworkException extends IOException {
    public NetworkException(String msg) {
        super(msg);
    }
}
