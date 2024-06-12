package com.bestrookie.springframework.core.convert.converter;

/**
 * @Author bestrookie
 * @Date 2024/6/6 16:23
 * @Desc
 */
public interface ConverterRegistry {
    /**
     * 添加一个转换器实例。
     *
     * 转换器的作用是在序列化和反序列化过程中将一种数据类型转换为另一种数据类型。
     * 通过添加转换器，系统可以支持更多的数据类型转换，提高了系统的灵活性和扩展性。
     *
     * @param converter 要添加的转换器实例。转换器必须实现Converter接口，
     *                  ?和?表示转换器可以支持任意类型的转换。
     */
    void addConverter(Converter<? ,?> converter);


    /**
     * 添加一个通用转换器。
     *
     * 此方法用于注册一个自定义的转换器实例，以便系统可以在适当时刻使用该转换器进行类型转换。
     * 转换器的用途是在特定情况下将一种类型的数据转换为另一种类型，这对于系统的灵活性和扩展性非常重要。
     * 通过添加自定义的转换器，用户可以处理系统默认转换器无法处理的类型转换需求。
     *
     * @param converter 要添加的转换器实例。这个参数不应该为null，且应该是一个实现了GenericConverter接口的实例。
     *                  转换器实例将被用于执行特定的类型转换任务。
     */
    void  addConverter(GenericConverter converter);

    /**
     * 添加一个转换工厂，用于将一种数据格式转换为另一种数据格式。
     * 转换工厂是责任链模式中的一个节点，负责处理特定类型的转换。
     * 如果当前没有适合的转换器，该转换工厂将提供一个转换器。
     *
     * @param converterFactory 要添加的转换工厂，它是一个泛型类，可以处理两种数据类型之间的转换。
     *                         两个问号表示这个转换工厂可以支持任意类型的转换。
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

}
