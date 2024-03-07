package java.com.bestrookie.springframework.beans;

import lombok.Data;

/**
 * @Author bestrookie
 * @Date 2023/11/29 16:03
 * @Desc 属性值
 */
@Data
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
