package com.zhangling.bluetooth.model.db;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserDBModel  implements RealmModel {
    @PrimaryKey
    private long uid;
    private String name;
    private String nickName;
    private String password;
    private String account;
    private int state;
    private int roleId;
    private String createdAt;
    private String updatedAt;
    private long phone;
    private String avatar;

}
