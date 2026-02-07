package com.exteragram.messenger.p003ai.data;

import com.exteragram.messenger.p003ai.AiConfig;
import java.io.Serializable;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class Role implements Comparable, Serializable {
    private boolean isSuggestion;
    private String name;
    private String prompt;

    public Role(String str, String str2) {
        this.name = str;
        this.prompt = str2;
    }

    @Override // java.lang.Comparable
    public int compareTo(Role role) {
        return this.name.compareTo(role.getName());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.name.equals(((Role) obj).name);
    }

    public String getName() {
        return this.name;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public boolean isSuggestion() {
        return this.isSuggestion;
    }

    public Role setSuggestion(boolean z) {
        this.isSuggestion = z;
        return this;
    }

    public boolean isSelected() {
        return Objects.equals(AiConfig.getSelectedRole(), this.name);
    }
}
