package com.atilika.kuromoji;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class KuromojiExampleTest {

    @Test
    public void testKuromoji() {
        Tokenizer tokenizer = new Tokenizer() ;
        List<Token> tokens = tokenizer.tokenize("お寿司が食べたい。");
        for (Token token : tokens) {
            System.out.println(token.getSurface() + "\t" + token.getAllFeatures());
        }
    }

    @Test
    public void classLoaderTest() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassLoader classLoader = new URLClassLoader(
            new URL[]{
                new File(System.getenv("user.home"), ".m2/repository/com/atilika/kuromoji/kuromoji-core/0.9.0/kuromoji-core-0.9.0.jar").toURI().toURL(),
                new File(System.getenv("user.home"), ".m2/repository/com/atilika/kuromoji/kuromoji-ipadic/0.9.0/kuromoji-ipadic-0.9.0.jar").toURI().toURL()
            }
        );
        Class<?> clazz = classLoader.loadClass("com.atilika.kuromoji.ipadic.Tokenizer$Builder");
        Thread.currentThread().setContextClassLoader(classLoader);
        Object builder = clazz.newInstance();
        Tokenizer tokenizer = (Tokenizer) clazz.getMethod("build").invoke(builder);

        List<Token> tokens = tokenizer.tokenize("お刺身が食べたい。");
        for (Token token : tokens) {
            System.out.println(token.getSurface() + "\t" + token.getAllFeatures());
        }
    }
}
