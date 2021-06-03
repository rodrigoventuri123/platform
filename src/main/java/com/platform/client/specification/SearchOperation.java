package com.platform.client.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchOperation {
    EQUAL(":");
    public String text;

    public String getText() {
        return this.text;
    }

    public static SearchOperation fromString(String text) {
        for (SearchOperation b : SearchOperation.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
