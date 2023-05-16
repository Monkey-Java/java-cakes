package com.cakes.log;

import com.alibaba.fastjson.JSONObject;
import com.cakes.enums.LoggerEnum;

public class Test {
    public static void main(String[] args) {

        SubSlf4jLogger logger = new SubSlf4jLogger(Test.class);
        // 例如是工号，在过滤器中可以传给日志对象。打印时，就不用手动把Tag写在日志打印中。根据Tag检索可以提升效率
        logger.setCtx("1135955087");

        // 基础打印测试
        logger.trace("我的名字是{}","MonkeyJava");
        logger.debug("我的名字是{}","MonkeyJava");
        logger.info("我的名字是{}","MonkeyJava");
        logger.warn("我的名字是{}","MonkeyJava");
        logger.error("我的名字是{}","MonkeyJava");

        // 自动序列化Bean测试
        People monkeyJava = new People(25,"MonkeyJava",183);
        logger.info("1、我的信息如下：{}",monkeyJava);
        logger.info("2、我的信息如下：{}", JSONObject.toJSONString(monkeyJava));

        // 打印时自动携带系统上下文
        logger.logWithCtx("使用默认日志界别，信息如下{}",monkeyJava);
        logger.logWithCtx(LoggerEnum.WARN,"指定级别为warn：信息如下{}",monkeyJava);
    }

    static class People{
        public People(int age, String name, int height) {
            this.age = age;
            this.name = name;
            this.height = height;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        private int age;
        private String name;
        private int height;
    }
}
