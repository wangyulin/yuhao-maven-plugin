package com.wyl.maven.utils;

import com.squareup.javapoet.*;
import org.apache.maven.plugin.logging.Log;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * TODO
 *
 * @author wangyulin
 * @date 2021/7/3 7:56 PM
 */
public class EnumDemo {
    public static final Map<String, Integer> values = new HashMap<String, Integer>(){{
        put("Test1", 1);
        put("Test2", 2);
        put("Test3", 3);
        put("Test4", 4);
    }};

    public static void gener(Path target, String enumsPkg, Log log) throws IOException {
        CodeBlock typeJavaDocBlock = CodeBlock.builder().add("This is the example for generating class").build();

        CodeBlock valueJavaDocBlock = CodeBlock.builder().add("枚举值").build();

        CodeBlock descJavaDocBlock = CodeBlock.builder().add("枚举备注").build();

        FieldSpec value = FieldSpec.builder(Integer.class, "value")
                .addModifiers(Modifier.PRIVATE)
                .addJavadoc(valueJavaDocBlock)
                .build();
        FieldSpec desc = FieldSpec.builder(String.class, "desc")
                .addModifiers(Modifier.PRIVATE)
                .addJavadoc(descJavaDocBlock)
                .build();

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addParameter(Integer.class,"value")
                .addParameter(String.class, "desc")
                .addStatement("this.$N=$N", "value", "value")
                .addStatement("this.$N=$N", "desc", "desc")
                .build();

        TypeSpec.Builder enumsBuilder = TypeSpec.enumBuilder("StreamTypeEnum")
                // .superclass(superclass)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructor)
                .addField(value)
                .addField(desc)
                .addJavadoc(typeJavaDocBlock);

        for (String key : values.keySet()) {
            Integer v = values.get(key);
            TypeSpec constant = TypeSpec
                    .anonymousClassBuilder("$L, $S", v, key)
                    .build();
            enumsBuilder.addEnumConstant(key, constant);
        }

        List<FieldSpec> fieldSpecList = Arrays.asList(value, desc);
        List<MethodSpec> methodSpecList = generateGetSetMethod(fieldSpecList);
        methodSpecList.stream().forEach(it -> enumsBuilder.addMethod(it));

        TypeSpec enums = enumsBuilder.build();

        log.info("---> EnumDemo : " + enumsPkg);
        log.info("---> target : " + target.toString());

        JavaFile javaFile = JavaFile.builder(enumsPkg, enums).build();
        // javaFile.writeTo(System.out);
        javaFile.writeTo(target);
    }

    public static List<MethodSpec> generateGetSetMethod(List<FieldSpec> fieldSpecList) {
        List<MethodSpec> result = new ArrayList<>();
        MethodSpec methodSpec;
        for (FieldSpec fieldSpec : fieldSpecList) {
            String name = upper(fieldSpec.name);
            methodSpec = MethodSpec.methodBuilder("get" + name)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(fieldSpec.type)
                    .addStatement("return " + fieldSpec.name)
                    .build();
            result.add(methodSpec);

            methodSpec = MethodSpec.methodBuilder("set" + name)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(fieldSpec.type, fieldSpec.name)
                    .addStatement("this." + fieldSpec.name + "=" + fieldSpec.name)
                    .build();
            result.add(methodSpec);

        }
        return result;
    }

    public static String upper(String target) {
        String start = target.substring(0, 1).toUpperCase();
        String after = target.substring(1);
        return start + after;
    }
}
