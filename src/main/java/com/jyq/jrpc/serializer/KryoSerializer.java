/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.jyq.jrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 *
 * @author wb-jyq573332
 * @version $Id: KryoSerializer.java, v 0.1 2019年12月31日 16:58 wb-jyq573332 Exp $
 */
public class KryoSerializer {

    private static final String DEFAULT_ENCODING = "UTF-8";

    //每个线程的 Kryo 实例
    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();

            /**
             * 不要轻易改变这里的配置！更改之后，序列化的格式就会发生变化，
             * 上线的同时就必须清除 Redis 里的所有缓存，
             * 否则那些缓存再回来反序列化的时候，就会报错
             */
            //支持对象循环引用（否则会栈溢出）
            kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

            //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置
            //            kryo.register(UnmodifiableCollectionsSerializer.class);
            UnmodifiableCollectionsSerializer.registerSerializers(kryo);

            //Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
                    .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

            return kryo;
        }
    };

    /**
     * 获得当前线程的 Kryo 实例
     *
     * @return 当前线程的 Kryo 实例
     */
    public static Kryo getInstance() {
        return kryoLocal.get();
    }

    //-----------------------------------------------
    //          序列化/反序列化对象，及类型信息
    //          序列化的结果里，包含类型的信息
    //          反序列化时不再需要提供类型
    //-----------------------------------------------

    /**
     * 将对象【及类型】序列化为字节数组
     *
     * @param obj 任意对象
     * @return 序列化后的字节数组
     */
    public static byte[] writeToByteArray(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);

        Kryo kryo = getInstance();
        kryo.writeClassAndObject(output, obj);
        output.flush();

        return byteArrayOutputStream.toByteArray();
    }



    /**
     * 将字节数组反序列化为原对象
     *
     * @param byteArray writeToByteArray 方法序列化后的字节数组
     * @return 原对象
     */
    @SuppressWarnings("unchecked")
    public static Object readFromByteArray(byte[] byteArray) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        Input input = new Input(byteArrayInputStream);

        Kryo kryo = getInstance();
        return kryo.readClassAndObject(input);
    }




}