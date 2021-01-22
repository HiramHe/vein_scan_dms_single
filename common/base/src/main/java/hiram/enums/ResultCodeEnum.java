package hiram.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Author: HiramHe
 * @Date: 2020/5/4 7:13
 * @Description: ""
 */

@JsonFormat
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(2000, "执行成功"),
    SUCCESS_NOACTION(2005,"成功，无动作"),
    SUCCESS_AUTHENTICATE(2011,"身份认证成功"),
    SUCCESS_LOGOUT(2021,"退出成功"),
    SUCCESS_UPLOAD(2022,"数据上传成功"),


    /**
     * 失败
     */
    FAILED(4000, "操作失败"),
    FAILED_NOACTION(4001,"失败，无动作"),
    RECORD_EXIST(4002,"数据已存在"),
    FUNCTION_TODO(4003,"功能待实现"),
    RECORD_NOT_EXIST(4004,"数据不存在"),
    COLLECTION_NULL(4005,"集合不得为空"),
    ARITHMETIC_EXCEPTION(4006,"算术异常"),
    ID_EMPTY(4007,"id不得为空"),
    BEDNUMBER_EXIST(4008,"床位号已存在"),
    FAILED_INSERT(4009,"插入失败"),
    LOGIN_FIRST(4010,"请先登录"),
    FAILED_AUTHENTICATE(4011, "身份认证失败"),
    LOGIN_EXPIRED(4012,"登录已失效，请重新登录"),
    FORBIDDEN(4012,"权限不足，不允许访问"),
    USER_EXIST(4014,"用户已存在"),
    NAME_EXIST(4015,"姓名已存在"),
    USERNAME_PASSWORD_NULL(4016,"用户名和密码不得为空"),
    PHONENUMBER_NOT_UNIQUE(4017,"手机号已存在"),
    EMAIL_NOT_UNIQUE(4018,"邮箱已存在"),
    TOKEN_UNTRUSTED(4021,"不受信的token凭证"),
    TOKEN_NULL(4022,"token不能为空"),
    TOKEN_EXPIRED(4023,"token已过期，请重新登录"),
    USER_NOT_EXIST(4031,"用户不存在"),
    OLDPASSWORD_ERROR(4040,"旧密码错误"),
    NEWPASSWORD_SAME_ERROR(4042,"新密码与旧密码不能相同"),
    RESETPASSWORD_ERROR(4044,"修改密码异常，请联系管理员"),
    WRONG_USERID(4045,"userId不正确"),
    FILE_UPLOAD_INFRARED_EMPTY(4111,"红外图像不得为空"),
    FILE_FORMAT_NOT_SUPPORT(4112,"文件格式不支持"),
    FILE_SIZE_TOO_LARGE(4113,"文件大小过大"),
    COORDINATE_WRONG(4114,"坐标错误"),
    EXCEPTION_IO(4115,"IO异常，请联系管理员"),
    EXCEPTION_DAO(4116,"数据持久层异常，请联系管理员"),
    EXCEPTION_SERVER(4120,"服务器异常，请联系管理员")
    ;

    private final long code;
    private final String msg;

    private ResultCodeEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
