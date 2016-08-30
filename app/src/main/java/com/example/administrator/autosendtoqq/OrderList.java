package com.example.administrator.autosendtoqq;

/**
 * Created by Administrator on 2016/8/29.
 */
public class OrderList {
    public static String[][] list = new String[7][8];
    public static String[][] add() {

        list[0] = orderQQ;
        list[1] = orderQQ1;
        list[2] = orderQQ2;
        list[3] = orderQQ3;
        list[4] = orderQQ4;
        list[5] = orderQQ5;
        list[6] = orderQQ6;
        return list;
    }


    /*
    * 点击qq图标*/
    static String[] orderQQ = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 352",
            "sendevent /dev/input/event3 3 54 550",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"
    };
    /*
    * 点击qq联系人列表第一个*/
    static String[] orderQQ1 = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 162",
            "sendevent /dev/input/event3 3 54 189",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"

    };

    /*
    * 点击输入框弹出输入法
    */
    static String[] orderQQ2 = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 95",
            "sendevent /dev/input/event3 3 54 891",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"

    };


    /*
    * 长点击弹出输入法后的对话框启动粘贴功能*/
    static String[] orderQQ3 = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 80",
            "sendevent /dev/input/event3 3 54 429",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"
    };

    /*
    * 点击粘贴按钮*/
    static String[] orderQQ4 = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 64",
            "sendevent /dev/input/event3 3 54 395",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"

    };



    /*点击发送按钮*/
    static String[] orderQQ5 = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 486",
            "sendevent /dev/input/event3 3 54 437",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"

    };
    /*点击返回按钮*/
    static String[] orderQQ6 = {
            "sendevent /dev/input/event3 3 50 5",
            "sendevent /dev/input/event3 3 53 85",
            "sendevent /dev/input/event3 3 54 58",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0",
            "sendevent /dev/input/event3 0 0 0 ",
            "sendevent /dev/input/event3 0 2 0"

    };
}
