package com.bycsmys.aviator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.Options;

import java.util.Map;

public class AviatorTest {

    private static long ONE_HOUR_MILL = 1 * 60 * 60 * 1000L;

    public static void main(String[] args) {
        BeanContext beanContext = BeanContext.builder()
                .startTime(System.currentTimeMillis() - 2 * ONE_HOUR_MILL)
                .endTime(System.currentTimeMillis())
                .finishTime(System.currentTimeMillis() - 1 * ONE_HOUR_MILL)
                .gapTime(ONE_HOUR_MILL * 3)
                .build();
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(beanContext), new TypeReference<Map<String, Object>>() {
        });


        String expression = "endTime - startTime > gapTime";

        AviatorEvaluatorInstance instance = AviatorEvaluator.getInstance();
        instance.setOption(Options.TRACE_EVAL, true);
        Expression exp = instance.compile(expression);

        System.out.println(exp);

        Object execute = exp.execute(map);
        System.out.println(execute);


    }
}
