package com.chaos.bmob.base;

import cn.bmob.v3.BmobObject;

/**
 * Created on 2021/3/12
 *
 * @author zsp
 * @desc BaseBmobObject
 * Bmob 存储数据以 BmobObject 为基础，任何需保存的数据对象必须继承 BmobObject。
 * 不建议用抽象类继承 BmobObject 或定义父类后于子类写 bean，这样不可解析，通不这么用。
 * 一个 bean 对应一张表，类似 ORM 库。
 * <p>
 * BmobObject 本身包含四默认属性：
 * objectId 数据唯一标示
 * createdAt 数据创建时间
 * updatedAt 数据最后修改时间
 * ACL 数据操作权限
 */
public class BaseBmobObject extends BmobObject {

}
