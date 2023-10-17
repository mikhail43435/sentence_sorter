package ru.hmp.sentencesorter.dataprovider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileDataProviderTest {

    private static DataProvider provider;

    @BeforeAll
    public static void init() {
        provider = new FileDataProvider();
    }

    @Test
    void testWrongSourcInput() {
        assertThat(provider.setSource("")).isFalse();
        assertThat(provider.setSource("1")).isFalse();
        assertThat(provider.setSource("C")).isFalse();
        assertThat(provider.setSource("C:")).isFalse();
        assertThat(provider.setSource("C:\\")).isFalse();
        assertThat(provider.setSource(".")).isFalse();
        assertThat(provider.setSource("...")).isFalse();
        assertThat(provider.setSource("\\..\\")).isFalse();
    }

    @Test
    void testWrongTargetInput() {
        assertThat(provider.setTarget("")).isFalse();
        assertThat(provider.setTarget("1")).isFalse();
        assertThat(provider.setTarget("C")).isFalse();
        assertThat(provider.setTarget("C:")).isFalse();
        assertThat(provider.setTarget("C:\\")).isFalse();
        assertThat(provider.setTarget(".")).isFalse();
        assertThat(provider.setTarget("...")).isFalse();
        assertThat(provider.setTarget("\\..\\")).isFalse();
    }

    @Test
    void testCorrectTargetInput() {
        assertThat(provider.setTarget("c:\\txt.txt")).isTrue();
        assertThat(provider.setTarget("C:\\txt.txt")).isTrue();
        assertThat(provider.setTarget("C:\\results\\txt.txt")).isTrue();
    }
}