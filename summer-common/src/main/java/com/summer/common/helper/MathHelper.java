package com.summer.common.helper;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 数字工具类
 **/
public final class MathHelper {
    private static final Random RANDOM = new Random();

    private static final int DEFAULT_SCALE = 5;

    private MathHelper() {
    }

    public static String shuffleNum(int length) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(RANDOM.nextInt(10));
        }
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        list.forEach(sb::append);
        // 防止随机数字首位为 0
        if (length > 1 && 0 == list.get(0)) {
            if (0 != list.get(length - 1)) {
                return sb.reverse().toString();
            } else {
                int first = RANDOM.nextInt(9) + 1;
                sb.deleteCharAt(0).insert(0, first);
            }
        }
        return sb.toString();
    }

    public static BigDecimal nvl(final BigDecimal num) {
        return num == null ? BigDecimal.ZERO : num;
    }

    public static long nvl(final Long num) {
        return num == null ? 0L : num;
    }

    public static long toLong(final String num, long defaultVal) {
        try {
            return StringHelper.isNumeric(num) ? Long.parseLong(num) : defaultVal;
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static int nvl(final Integer num) {
        return nvl(num, 0);
    }

    public static int nvl(final Integer num, Integer defaultVal) {
        return num == null ? defaultVal : num;
    }

    public static int positive(final Integer num) {
        int numP = nvl(num);
        return numP < 0 ? 0 : numP;
    }

    public static Long positive(final Long num) {
        long numP = nvl(num);
        return numP < 0 ? 0L : numP;
    }

    public static int toInt(final String num, int defaultVal) {
        try {
            return StringHelper.isNumeric(num) ? Integer.parseInt(num) : defaultVal;
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static boolean nvl(final Boolean bool) {
        return null == bool ? Boolean.FALSE : bool;
    }

    public static BigDecimal limitScale(BigDecimal decimal) {
        return limitScale(decimal, DEFAULT_SCALE);
    }

    public static BigDecimal limitScale(BigDecimal decimal, int scale) {
        decimal = nvl(decimal);
        return decimal.scale() < scale ? decimal : decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static int ceil(final Long total, final Integer size) {
        if (nvl(total) <= 0 || nvl(size) <= 0) {
            return 0;
        }
        return (int) (Math.ceil(total.doubleValue() / size.doubleValue()));
    }

    public static boolean isBetween(final Number val, final Number min, final Number max) {
        if (null == val || null == min || null == max) {
            return false;
        }
        BigDecimal rv = new BigDecimal(String.valueOf(val));
        return rv.compareTo(new BigDecimal(String.valueOf(min))) >= 0
                && rv.compareTo(new BigDecimal(String.valueOf(max))) <= 0;
    }

    public static double minPacket() {
        return 0.01;
    }

    public static LinkedHashMap<Integer, Double> packets(double amount, int count) {
        if (0 == count) {
            return Maps.newLinkedHashMap();
        }
        LinkedHashMap<Integer, Double> packets = generatorPackets(amount, count);
        //如果 Packets 分配出错，重试三次
        for (int i = 0; i < 3; i++) {
            if (packets.size() == count) {
                return packets;
            }
            if (packets.size() < count) {
                packets = generatorPackets(amount, count);
            }
        }
        if (packets.size() < count) {
            throw new RuntimeException("packets split error");
        }
        return packets;
    }

    private static LinkedHashMap<Integer, Double> generatorPackets(double amount, int count) {
        Map<Integer, Double> map = Maps.newHashMap();
        packet(amount, count, map);
        LinkedHashMap<Integer, Double> packets = Maps.newLinkedHashMap();
        map.entrySet().stream()
           .sorted(Comparator.comparing(e -> e.getValue()))
           .filter(e -> e.getValue() > 0)
           .forEach(e -> packets.put(e.getKey(), e.getValue()));
        return packets;
    }

    private static void packet(double amount, int count, Map<Integer, Double> packets) {
        if (1 == count) {
            count--;
            packets.put(count, (double) Math.round(amount * 100) / 100);
            return;
        }
        Random r = new Random();
        double min = minPacket();
        double max = amount / count * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? minPacket() : money;
        money = Math.floor(money * 100) / 100;
        count--;
        amount -= money;
        packets.put(count, money);
        packet(amount, count, packets);
    }
}
