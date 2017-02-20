package ru.bars_open.medvtr.amqp.biomaterial.util;

import org.slf4j.MDC;

import java.util.Stack;

/**
 * Author: Upatov Egor <br>
 * Date: 14.02.2017, 19:35 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MDCHelper {
    private static ThreadLocal<Stack<String>> prefix = ThreadLocal.withInitial(Stack::new);

    public static void start(final long deliveryTag) {
        MDC.clear();
        final String nextPrefix = "#" + String.valueOf(deliveryTag);
        prefix.get().push(nextPrefix);
        MDC.put("prefix", nextPrefix+ ": ");
    }


    public static void push(final Integer value) {
       push(String.valueOf(value));
    }

    public static void push(final String value) {
        final String nextPrefix = prefix.get().peek() + "-" + value;
        prefix.get().push(nextPrefix);
        MDC.put("prefix", nextPrefix+": ");
    }

    public static void pop() {
        prefix.get().pop();
        MDC.put("prefix", prefix.get().peek()+": ");
    }


}
