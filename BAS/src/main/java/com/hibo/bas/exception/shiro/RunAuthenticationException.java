package com.hibo.bas.exception.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * <p>标题：验证时运行异常</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月28日 下午2:52:10</p>
 * <p>类全名：com.hibo.bas.exception.shiro.RunAuthenticationException</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class RunAuthenticationException extends AuthenticationException{
	
	private static final long serialVersionUID = -7301740242782835609L;

	/**
     * Creates a new AuthenticationException.
     */
    public RunAuthenticationException() {
        super();
    }

    /**
     * Constructs a new CaptchaException.
     *
     * @param message the reason for the exception
     */
    public RunAuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs a new CaptchaException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public RunAuthenticationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new CaptchaException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public RunAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
