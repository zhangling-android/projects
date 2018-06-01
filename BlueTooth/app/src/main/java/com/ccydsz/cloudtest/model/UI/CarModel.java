package com.ccydsz.cloudtest.model.UI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CarModel {

    private String carId;
    private String carUid;
    private String carName;
    private int carTypeId;
    private int carGroup;
    private int carOwner;
    private int carArea;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarUid() {
        return carUid;
    }

    public void setCarUid(String carUid) {
        this.carUid = carUid;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public int getCarGroup() {
        return carGroup;
    }

    public void setCarGroup(int carGroup) {
        this.carGroup = carGroup;
    }

    public int getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(int carOwner) {
        this.carOwner = carOwner;
    }

    public int getCarArea() {
        return carArea;
    }

    public void setCarArea(int carArea) {
        this.carArea = carArea;
    }

    public String getCarAreaLocator() {
        return carAreaLocator;
    }

    public void setCarAreaLocator(String carAreaLocator) {
        this.carAreaLocator = carAreaLocator;
    }

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

    public double getAddTime() {
        return addTime;
    }

    public void setAddTime(double addTime) {
        this.addTime = addTime;
    }

    public double getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(double updateTime) {
        this.updateTime = updateTime;
    }

    public Type getCarType() {
        return carType;
    }

    public void setCarType(Type carType) {
        this.carType = carType;
    }

    private String carAreaLocator;
    private int carStatus;
    private double createTime;
    private double addTime;
    private double updateTime;
    private Type carType;

    public String getCarDepartment() {
        return carDepartment;
    }

    public void setCarDepartment(String carDepartment) {
        this.carDepartment = carDepartment;
    }

    public String getCarAttrList() {
        return carAttrList;
    }

    public void setCarAttrList(String carAttrList) {
        this.carAttrList = carAttrList;
    }

    private String carDepartment;
    private String carAttrList;

    public static class Type{
        private int typeId; // 1吉利   4是长城
        private String typeName;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeColor() {
            return typeColor;
        }

        public void setTypeColor(String typeColor) {
            this.typeColor = typeColor;
        }

        public int getTypeTime() {
            return typeTime;
        }

        public void setTypeTime(int typeTime) {
            this.typeTime = typeTime;
        }

        public int getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(int updateTime) {
            this.updateTime = updateTime;
        }

        public List<Item> getTypeAttrList() {
            return typeAttrList;
        }

        public void setTypeAttrList(List<Item> typeAttrList) {
            this.typeAttrList = typeAttrList;
        }

        private String typeColor;
        private int typeTime;
        private int updateTime;
        private List<Item> typeAttrList;
        public static class Item {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }


            public List<Index> getChild() {
                return child;
            }

            public void setChild(List<Index> child) {
                this.child = child;
            }

            private List<Index> child;
            //指标
            public static class Index{
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                private String code;
                private String value;
            }
        }


    }
}
