package com.nathansdev.stack;

import com.google.auto.value.AutoValue;

/**
 * Configuration for Stack Query App.
 * Use {@linkplain #builder()} to generate a new instance.
 */
@AutoValue
public abstract class AppConfig {
    public static Builder builder() {
        return new AutoValue_AppConfig.Builder();
    }

    public abstract String clientId();

    public abstract String accesskey();

    public abstract String clientSecretId();

    public abstract String redirectUri();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract AppConfig build();

        public abstract Builder clientId(String s);

        public abstract Builder accesskey(String s);

        public abstract Builder clientSecretId(String s);

        public abstract Builder redirectUri(String a);
    }
}
