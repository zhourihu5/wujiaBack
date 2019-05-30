package com.wj.core.entity;


public class AllEnum {
    enum UserInfo {
        One("后台"), Two("ipad"), Three("app");
        //以上是枚举的成员，必须先定义，而且使用分号结束
        private final String name;

        private UserInfo(String name) {
            this.name = name;
        }

        public static void getNum(int i) {
            switch (i) {
                case 1:
                    System.out.println(UserInfo.One);
                    break;
                case 2:
                    System.out.println(UserInfo.Two);
                    break;
                case 3:
                    System.out.println(UserInfo.Three);
                    break;
            }
        }

        public String getName() {
            return name;
        }
    }
}
