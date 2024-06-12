package com.bestrookie.springframework.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/6/6 16:25
 * @Desc
 */
public interface GenericConverter {
    /**
     * 获取支持转换的类型对集合。
     *
     * @return 返回一个Set集合，其中包含了所有支持类型转换的类型对。每个类型对都表示一种转换关系，
     *         从一个源类型转换到一个目标类型。这个方法的目的是为了提供给调用者一个明确的指示，
     *         哪些类型的对象可以通过特定的转换器进行相互转换。
     *
     *         例如，如果一个转换器能够将String类型转换为Integer类型，那么这个方法将返回一个包含
     *         ConvertiblePair<String, Integer>的集合。这样的信息对于调用者来说是非常重要的，
     *         它们可以基于这些信息决定是否使用这个转换器，以及如何使用它来进行类型转换。
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 将源对象转换为目标类型。
     *
     * 此方法提供了一个通用的类型转换机制，允许在不同场景下将一个对象的值转换为另一种类型。
     * 它处理了类型转换的核心逻辑，调用者只需提供源对象、源类型和目标类型，方法将负责实际的转换过程。
     *
     * @param source 待转换的源对象。它可以是任何类型的对象，转换的规则由源类型和目标类型决定。
     * @param sourceType 源对象的类型。此参数用于明确源对象的原始类型，以便进行准确的类型转换。
     * @param targetType 转换后目标对象的类型。此参数指定了源对象需要转换成的目标类型。
     * @return 转换后的目标对象。如果转换失败，可能返回null或者抛出异常。
     */
    Object convert(Object source, Class sourceType, Class targetType);

    final class ConvertiblePair{
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType){
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType(){
            return this.sourceType;
        }

        public Class<?> getTargetType(){
            return this.targetType;
        }

        @Override
        public boolean equals(Object obj){
            if (this == obj){
                return true;
            }

            if (obj == null || obj.getClass() != ConvertiblePair.class){
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;

            return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }
}
